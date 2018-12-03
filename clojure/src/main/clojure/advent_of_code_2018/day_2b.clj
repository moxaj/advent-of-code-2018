(ns advent-of-code-2018.day-2b
  (:require [advent-of-code-2018.common :as common]))

(->> (common/input "day_2.txt")
     (mapcat (fn [id]
               (->> (range (count id))
                    (map (fn [index]
                           (assoc (vec id) index nil))))))
     (frequencies)
     (filter (fn [[_ count]]
               (= 2 count)))
     (ffirst)
     (apply str))
