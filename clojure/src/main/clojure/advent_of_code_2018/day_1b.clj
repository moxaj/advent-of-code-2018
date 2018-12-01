(ns advent-of-code-2018.day-1b
  (:require [advent-of-code-2018.common :as common]))

(->> (common/input "day_1.txt")
     (map #(Integer/parseInt %))
     (cycle)
     (reductions +)
     (cons 0)
     (reduce (fn [seen n]
               (if (seen n)
                 (reduced n)
                 (conj seen n)))
             #{}))
