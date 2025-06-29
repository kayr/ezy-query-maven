(defproject io.github.kayr/ezy-query-maven-plugin "0.0.24-SNAPSHOT"
  :description "A maven plugin for generating ezy-query code"
  :url "https://github.com/kayr/ezy-query-maven"
  :license {:name "Apache License, Version 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"}


  :dependencies [
                 [org.clojure/clojure "1.11.1"]
                 [io.github.kayr/ezy-query-codegen "0.0.23"]
                 [org.apache.maven/maven-plugin-api "3.9.3" :scope "provided"]
                 [org.apache.maven/maven-project "2.2.1" :scope "provided"]
                 [org.apache.maven.plugin-tools/maven-plugin-annotations "3.11.0" :scope "provided"]]


  :java-source-paths ["src"]
  :javac-options ["-target" "1.8" "-source" "1.8"]
  :main ^:skip-aot ezy_query_mvn_plugin.core

  ;see https://github.com/technomancy/leiningen/blob/135724235b1a963a806e24d036326a1bddeced1a/sample.project.clj
  :packaging "maven-plugin"
  :pom-plugins [
                [org.apache.maven.plugins/maven-plugin-plugin "3.9.0"
                 {
                  :configuration (
                                  [:goalPrefix "ezy-query"]
                                  [:skipErrorNoDescriptorsFound "false"])

                  :executions    [:execution ([:id "mojo-descriptor"]
                                              [:goals
                                               [:goal "descriptor"]])]}]]

  :pom-addition ([:properties
                  [:maven.compiler.source "1.8"]
                  [:maven.compiler.target "1.8"]
                  [:project.build.sourceEncoding "UTF-8"]
                  [:project.reporting.outputEncoding "UTF-8"]]
                 [:developers
                  [:developer
                   [:id "kayr"]
                   [:name "Ronald Kayondo"]
                   [:url "https://github.com/kayr/"]
                   [:roles
                    [:role "developer"]
                    [:role "maintainer"]]]])

  :scm {:name "git" :url "https://github.com/kayr/ezy-query-maven.git"}

  :target-path "target/%s"

  :profiles {:uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}


  :deploy-repositories [["releases" {:url   "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                                     :creds :gpg}
                         "snapshots" {:url   "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                                      :creds :gpg}]]

  :classifiers [["sources" {:source-paths      ^:replace []
                            :java-source-paths ^:replace ["src/java"]
                            :resource-paths    ^:replace []}]
                ["javadoc" {:source-paths      ^:replace []
                            :java-source-paths ^:replace []
                            :resource-paths    ^:replace ["javadoc"]}]])





