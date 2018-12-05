(ns advent-of-code-2018.day-5b
  (:require [advent-of-code-2018.common :as common]))

(let [pairs (->> (range 65 91)
                 (map (fn [i]
                        [(char i) (char (+ i 32))]))
                 (into {}))]
  (->> (common/input "day_5.txt")
       (first)
       (reduce (fn [stacks unit]
                 (reduce-kv (fn [stacks ignored-unit stack]
                              (assoc stacks
                                     ignored-unit
                                     (let [unit' (peek stack)]
                                       (cond
                                         (= ignored-unit (Character/toUpperCase unit))
                                         stack
                                         (and unit' (or (= unit  (pairs unit'))
                                                        (= unit' (pairs unit))))
                                         (pop stack)
                                         :else
                                         (conj stack unit)))))
                            stacks
                            stacks))
               (->> pairs
                    (keys)
                    (reduce (fn [stacks unit]
                              (assoc stacks unit []))
                            {})))
       (vals)
       (map count)
       (apply min)))
