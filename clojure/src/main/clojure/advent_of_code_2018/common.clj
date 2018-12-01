(ns advent-of-code-2018.common
  (:require [clojure.java.io :as io]))

(defn input [resource-name]
  (with-open [reader (io/reader (io/resource (str "advent_of_code_2018/" resource-name)))]
    (doall (line-seq reader))))
