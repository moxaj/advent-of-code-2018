(ns advent-of-code-2018.day-1a
  (:require [advent-of-code-2018.common :as common]))

(->> (common/input "day_1.txt")
     (map #(Integer/parseInt %))
     (reduce +))
