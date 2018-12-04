(ns advent-of-code-2018.day-3a
  (:require [advent-of-code-2018.common :as common]))

(->> (common/input "day_3.txt")
     (mapcat (fn [claim-str]
               (let [[_ _ left top width height]
                     (re-find #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" claim-str)
                     left   (Integer/parseInt left)
                     top    (Integer/parseInt top)
                     width  (Integer/parseInt width)
                     height (Integer/parseInt height)]
                 (for [x (range left (+ left width))
                       y (range top  (+ top height))]
                   [x y]))))
     (frequencies)
     (filter (fn [[_ n]]
               (< 1 n)))
     (count))
