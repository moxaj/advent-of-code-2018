(ns advent-of-code-2018.day-5a
  (:require [advent-of-code-2018.common :as common]))

(let [pairs (->> (range 65 91)
                 (map (fn [i]
                        [(char i) (char (+ i 32))]))
                 (into {}))]
  (->> (first (common/input "day_5.txt"))
       (reduce (fn [stack unit]
                 (let [unit' (peek stack)]
                   (if (or (nil? unit')
                           (and (not= unit  (pairs unit'))
                                (not= unit' (pairs unit))))
                     (conj stack unit)
                     (pop stack))))
               [])
       (count)))
