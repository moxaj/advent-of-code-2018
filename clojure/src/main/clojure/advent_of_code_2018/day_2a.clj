(ns advent-of-code-2018.day-2a
  (:require [advent-of-code-2018.common :as common]))

(->> (common/input "day_2.txt")
     (map (fn [id]
            (let [freqs (set (vals (frequencies id)))]
              [(if (freqs 2) 1 0)
               (if (freqs 3) 1 0)])))
     (reduce (fn [[sum-a sum-b] [a b]]
               [(+ sum-a a)
                (+ sum-b b)])
             [0 0])
     ((fn [[sum-a sum-b]]
        (* sum-a sum-b))))
