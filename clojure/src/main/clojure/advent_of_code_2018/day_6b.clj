(ns advent-of-code-2018.day-6b
  (:require [advent-of-code-2018.common :as common]
            [clojure.pprint :as pprint]))

(defn sum-distance [[x y] beacons]
  (reduce (fn [distance [x' y']]
            (+ distance (Math/abs (- x x'))
                        (Math/abs (- y y'))))
          0
          beacons))

(let [beacons (->> (common/input "day_6.txt")
                   (into #{} (map (fn [line]
                                    (let [[_ x y] (re-find #"(\d+), (\d+)" line)]
                                      [(Integer/parseInt x)
                                       (Integer/parseInt y)])))))
      xs      (map first beacons)
      ys      (map second beacons)
      n       (count beacons)
      x0      (->> xs
                   (reduce +)
                   (* (/ n))
                   (int))
      y0      (->> ys
                   (reduce +)
                   (* (/ n))
                   (int))
      d0      (sum-distance [x0 y0] beacons)
      dx0     (- (sum-distance [(inc x0) y0] beacons) d0)
      dy0     (- (sum-distance [x0 (inc y0)] beacons) d0)
      x-diffs (frequencies xs)
      y-diffs (frequencies ys)]
  (loop [frontier [[x0 y0 dx0 dy0 d0]]
         seen     #{[x0 y0 dx0 dy0 d0]}]
    (if (empty? frontier)
      (count seen)
      (let [frontier' (into #{} (comp (mapcat (fn [[x y dx dy d]]
                                                [(let [x'  (inc x)
                                                       dx' (+ dx (* 2 (x-diffs x' 0)))
                                                       d'  (+ d dx)]
                                                   [x' y dx' dy d'])
                                                 (let [y'  (inc y)
                                                       dy' (+ dy (* 2 (y-diffs y' 0)))
                                                       d'  (+ d dy)]
                                                   [x y' dx dy' d'])
                                                 (let [x'  (dec x)
                                                       dx' (- dx (* 2 (x-diffs x 0)))
                                                       d'  (- d dx')]
                                                   [x' y dx' dy d'])
                                                 (let [y'  (dec y)
                                                       dy' (- dy (* 2 (y-diffs y 0)))
                                                       d'  (- d dy')]
                                                   [x y' dx dy' d'])]))
                                      (filter (fn [[_ _ _ _ d]]
                                                (< d 10000)))
                                      (remove seen))
                                frontier)]
        (recur frontier' (into seen frontier))))))
