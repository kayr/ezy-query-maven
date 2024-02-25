(ns ezy_query_mvn_plugin.core
  (:require [clojure.java.io :as io])
  (:import (io.github.kayr.ezyquery EzyQueryVersion)
           (io.github.kayr.ezyquery.gen BatchQueryGen)
           (java.nio.file Path)
           (org.apache.maven.model Build Resource)))

(def REQUIRED-DEP {:group-id    "io.github.kayr"
                   :artifact-id "ezy-query-core"
                   :version     EzyQueryVersion/VERSION})

(defn dep-to-map [dep] {:group-id    (.getGroupId dep)
                        :artifact-id (.getArtifactId dep)
                        :version     (.getVersion dep)})

(defn check-has-core-dev [deps]
  (if (not (some #{REQUIRED-DEP} deps))
    (throw (RuntimeException. (str "EzyQuery dependency not found in project."
                                   "Please add the following to your pom.xml \n"
                                   "<dependency>\n"
                                   "  <groupId>io.github.kayr</groupId>\n"
                                   "  <artifactId>ezy-query-core</artifactId>\n"
                                   "  <version>" EzyQueryVersion/VERSION "</version>\n"
                                   "</dependency>")))))

(defn validate-deps [mvn]
  (->> mvn (.getDependencies) (map dep-to-map) (check-has-core-dev)))

(defn abs-path [f]
  (-> f (io/file) (.getCanonicalFile) (.getAbsolutePath)))

(defn file-sibling [file sibling-path]
  (abs-path (io/file file ".." sibling-path)))

(defn mvn-rsrc [f]
  (doto (Resource.) (.setDirectory (abs-path f))))

(defn resolve-dirs [mvn]
  (let [^Build mvn-build (.getBuild mvn)
        build-out-dir    (.getOutputDirectory mvn-build)
        src-dir          (.getSourceDirectory mvn-build)
        test-src-dir     (.getTestSourceDirectory mvn-build)

        ezy-main-src     (file-sibling src-dir "ezyquery")
        ezy-test-src     (file-sibling test-src-dir "ezyquery")
        ezy-main-out-dir (file-sibling build-out-dir "generated-sources/ezyquery/main-java")
        ezy-test-out-dir (file-sibling build-out-dir "generated-sources/ezyquery/test-java")]
    {:out-main-dir ezy-main-out-dir
     :out-test-dir ezy-test-out-dir
     :src-main-dir ezy-main-src
     :src-test-dir ezy-test-src}))

(defn file-exists? [f] (.exists (io/file f)))

(defn path [^String path] (Path/of path (into-array String [])))

(defn may-be-create-dir! [dirs]
  (doseq [[k v] dirs]
    (if (not (file-exists? v))
      (do
        (println "Creating directory: [" k "] -> " v)
        (.mkdirs (io/file v))))))

(defn set-up-dirs! [mvn]
  (let [dirs (resolve-dirs mvn)]
    ; Check ezy-query dependencies added
    (validate-deps mvn)
    ; Create directories
    (may-be-create-dir! dirs)
    ; Configure the project
    (doto mvn (.addCompileSourceRoot (:out-main-dir dirs))
              (.addTestCompileSourceRoot (:out-test-dir dirs)))
    ;configure test resources
    (doto (.getTestResources mvn) (.add (mvn-rsrc (:src-test-dir dirs))))
    (doto (.getResources mvn) (.add (mvn-rsrc (:src-main-dir dirs))))

    dirs))

(defn transpile! [in out]
  (doto (BatchQueryGen/create (path in) (path out))
    (.generateAndWrite)))
(defn mvn-gen! [mvn]
  (let [dirs     (set-up-dirs! mvn)
        src-main (:src-main-dir dirs)
        src-test (:src-test-dir dirs)]
    (if (file-exists? src-main) (transpile! src-main (:out-main-dir dirs)))
    (if (file-exists? src-test) (transpile! src-test (:out-test-dir dirs)))))


(defn mvn-init! [mvn] (set-up-dirs! mvn))



