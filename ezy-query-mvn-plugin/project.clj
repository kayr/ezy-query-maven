(defproject io.github.kayr/ezy-query-maven-plugin "0.1.0-SNAPSHOT"
  :description "A maven plugin for generating ezy-query code"
  :url "http://example.com/FIXME"
  :license {:name "Apache License, Version 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"}


  :dependencies [
                 [org.clojure/clojure "1.11.1"]
                 [io.github.kayr/ezy-query-codegen "0.0.15"]
                 [org.apache.maven/maven-plugin-api "3.9.3"]
                 [org.apache.maven/maven-project "2.2.1"]
                 [org.apache.maven.plugin-tools/maven-plugin-annotations "3.11.0"]]


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
                  [:project.reporting.outputEncoding "UTF-8"]])

  :target-path "target/%s"
  :profiles {:uberjar {:aot      :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
