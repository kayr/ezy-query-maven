#!/usr/bin/env bash

# fail if any commands fails
set -e

assert_false() {
  local expression=$1
  local message=$2
  if [[ $(eval "$expression") ]]; then
    echo "Assertion failed: (>$expression) - $message"
    exit 1
  fi
}

assert_eq() {
  if [[ "$1" != "$2" ]]; then
    echo "$3"
    exit 1
  fi
}

assert_not_empty() {
  # check if the first argument is empty after trimming
  if [[ -z "${1//[[:space:]]/}" ]]; then
    echo "$2"
    exit 1
  fi
}

get_current_branch() { git rev-parse --abbrev-ref HEAD; }

assert_on_branch() {
  local branch=$1
  local current_branch
  current_branch=$(get_current_branch)
  if [[ "$current_branch" != "$branch" ]]; then
    echo "Not on branch $branch, aborting."
    exit 1
  fi
}

confirm() {
  local message=$1
  local response
  read -p "$message [n]: " response
  response=${response:-n}
  if [[ "$response" != "y" ]]; then
    echo "Aborting."
    exit 1
  fi
}

assert_clean_branch() { assert_false "git status --porcelain" "There are uncommitted changes, aborting."; }

increment_version() { echo "$1" | awk -F. '{$NF = $NF + 1;} 1' | sed 's/ /./g'; }

assert_tag_not_exists() { assert_false "git tag -l $1" "Tag $1 already exists, aborting."; }

assert_branch_not_exist() { assert_false "git branch -l $1" "Branch $1 already exists, aborting."; }


get_current_project_version() {
  grep "(defproject" ezy-query-mvn-plugin/project.clj | sed -n 's/.*"\(.*\)".*/\1/p' | xargs
}

remove_snapshot() {
  echo "$1" | sed 's/-SNAPSHOT//g'
}

prompt_for_version() {
  local default_version=$1
  read -p "Enter the next version [$default_version]: " actual_default_version
  actual_default_version=${actual_default_version:-$default_version}
  echo "$actual_default_version"
}

assert_branch_is_up_to_date() {
  git fetch
  HEAD=$(git rev-parse HEAD)
  UPSTREAM=$(git rev-parse '@{u}')
  assert_eq "$HEAD" "$UPSTREAM" "Local branch is not up-to-date, aborting."
}

update_version_in_readme() {
  echo "Updating README.md and gradle.properties to [$1]"
  sed -i -e "s/<version>.*<\/version>/<version>$1<\/version>/g" README.md

}

update_version_project_builds() {
  echo "Updating project.clj to [$1]"
  sed -i -e "1s/\".*\"/\"$1\"/" ezy-query-mvn-plugin/project.clj

  echo "update pom.xml to [$1]"
  PLUGIN_LINE=$(grep -n ezy-query-maven-plugin ezy-query-mvn-itest/pom.xml |  cut -d: -f1)
  sed -i -e "${PLUGIN_LINE},+1s/<version>.*<\/version>/<version>$1<\/version>/g" ezy-query-mvn-itest/pom.xml

}

MAIN_BRANCH="main"
CURRENT_BRANCH=$(get_current_branch)
CURR_VERSION=$(get_current_project_version)
RELEASE_VERSION=$(remove_snapshot "$CURR_VERSION")


echo "check the current branch is clean"
assert_clean_branch

echo "check the current branch is up-to-date"
# assert_branch_is_up_to_date

echo "check the current branch is $MAIN_BRANCH"
assert_eq "$CURRENT_BRANCH" "$MAIN_BRANCH" "Not on branch $MAIN_BRANCH, aborting."

echo "Current version is $CURR_VERSION. Release version will be $RELEASE_VERSION"
NEW_VERSION=$(prompt_for_version "$RELEASE_VERSION")
assert_not_empty "$NEW_VERSION" "Version cannot be empty"

NEXT_VERSION=$(increment_version "$NEW_VERSION")
NEXT_VERSION="${NEXT_VERSION}-SNAPSHOT"
echo "Next version will be $NEXT_VERSION"

echo "check tag [$NEW_VERSION] and branch  [release/$NEW_VERSION] does not exist"
assert_tag_not_exists "$NEW_VERSION"
assert_branch_not_exist "release/$NEW_VERSION"

echo "  -> Creating branch release/$NEW_VERSION"
git checkout -b "release/$NEW_VERSION"

echo "  -> Updating README.md and gradle.properties to [$NEW_VERSION]"
update_version_in_readme "$NEW_VERSION"
update_version_project_builds "$NEW_VERSION"

echo "  -> Committing changes"
git commit -am "[RELEASE] Release $NEW_VERSION"

# ======= PUBLISHING =====================
echo "  -> Testing && Publishing"
make publish

echo "  -> Creating tag $NEW_VERSION"
git tag -a "$NEW_VERSION" -m "Release $NEW_VERSION"

echo "  -> Set the next version to $NEXT_VERSION"
update_version_project_builds "$NEXT_VERSION"
git commit -am "[RELEASE] Set the next version to $NEXT_VERSION"

echo "  -> Switching to branch $MAIN_BRANCH"
git checkout "$MAIN_BRANCH"

echo "  -> Merging branch release/$NEW_VERSION into $MAIN_BRANCH"
git merge "release/$NEW_VERSION"

