(ns advent-of-code-2018.day-5a
  (:require [advent-of-code-2018.common :as common]))

(let [pairs (->> (range 65 91)
                 (map (fn [i]
                        [(char i) (char (+ i 32))]))
                 (into {}))]
  (->> (common/input "day_5.txt")
       (first)
       (reduce (fn [stack unit]
                 (let [unit' (peek stack)]
                   (if (and unit' (or (= unit  (pairs unit'))
                                      (= unit' (pairs unit))))
                     (pop stack)
                     (conj stack unit))))
               [])
       (count)))
