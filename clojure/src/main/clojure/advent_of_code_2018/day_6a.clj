(ns advent-of-code-2018.day-6a
  (:require [advent-of-code-2018.common :as common]
            [clojure.set :as set]))

(let [beacons (->> (common/input "day_6.txt")
                   (into #{} (map (fn [line]
                                    (let [[_ x y] (re-find #"(\d+), (\d+)" line)]
                                      [(Integer/parseInt x)
                                       (Integer/parseInt y)])))))
      xs      (map first beacons)
      ys      (map second beacons)
      x-min   (apply min xs)
      y-min   (apply min ys)
      x-max   (apply max xs)
      y-max   (apply max ys)
      x-range (range x-min (inc x-max))
      y-range (range y-min (inc y-max))
      grid    (loop [grid     (->> beacons
                                   (map (fn [beacon]
                                          [beacon beacon]))
                                   (into {}))
                     frontier (map (fn [beacon]
                                     [beacon #{beacon}])
                                   beacons)
                     seen     beacons]
                (if (empty? frontier)
                   grid
                   (let [frontier' (->> frontier
                                        (into #{} (comp (mapcat (fn [[[x y] beacons]]
                                                                  [[[(inc x) y] beacons]
                                                                   [[(dec x) y] beacons]
                                                                   [[x (inc y)] beacons]
                                                                   [[x (dec y)] beacons]]))
                                                        (filter (fn [[[x y] _]]
                                                                  (and (<= x-min x x-max)
                                                                       (<= y-min y y-max))))
                                                        (remove (comp seen first))))
                                        (reduce (fn [frontier' [coord beacons]]
                                                  (update frontier' coord set/union beacons))
                                                {}))
                         seen'     (into seen (keys frontier'))
                         grid'     (reduce (fn [grid' [coord beacons]]
                                             (cond-> grid'
                                               (== 1 (count beacons)) (assoc coord (first beacons))))
                                           grid
                                           frontier')]
                     (recur grid' frontier' seen'))))]
  (->> (concat (mapcat (fn [x]
                         [[x y-min] [x y-max]])
                       x-range)
               (mapcat (fn [y]
                         [[x-min y] [x-max y]])
                       y-range))
       (map grid)
       (reduce dissoc (group-by val grid))
       (map (fn [[_ coords]]
              (count coords)))
       (apply max)))
