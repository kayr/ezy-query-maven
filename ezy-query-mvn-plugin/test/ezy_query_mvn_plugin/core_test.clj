(ns ezy_query_mvn_plugin.core-test
  (:require [clojure.test :refer :all]
            [ezy_query_mvn_plugin.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))


(defn str-to-file-seq [path]
  (file-seq (io/file path)))

(defn find-sql-files [files]
  (filter #(-> % (.getName) (.endsWith ".sql")) files))

(comment
  (let [folder "/home/kayr/var/code/prsnl/ezy-query/ezy-query-codegen/src/test/"
        files  (str-to-file-seq folder)]
    (do
      (def ds [
               (doto (Dependency.) (.setGroupId "g1") (.setArtifactId "a1") (.setVersion "v1"))
               (doto (Dependency.) (.setGroupId "g2") (.setArtifactId "a2") (.setVersion "v3"))])
      (def maps (map dep-to-map ds))
      (def f folder)
      (def x files)
      (def sf (find-sql-files x)))))