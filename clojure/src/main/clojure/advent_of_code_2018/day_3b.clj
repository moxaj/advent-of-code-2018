(ns advent-of-code-2018.day-3b
  (:require [advent-of-code-2018.common :as common]
            [clojure.set :as set]))

(let [claims (->> (common/input "day_3.txt")
                  (map (fn [claim-str]
                         (let [[_ id left top width height]
                               (re-find #"#(\d+) @ (\d+),(\d+): (\d+)x(\d+)" claim-str)]
                           [(Integer/parseInt id)
                            (Integer/parseInt left)
                            (Integer/parseInt top)
                            (Integer/parseInt width)
                            (Integer/parseInt height)]))))]
  (->> claims
       (reduce (fn [fabric [id left top width height]]
                 (->> (for [x (range left (+ left width))
                            y (range top  (+ top height))]
                        [x y])
                      (reduce (fn [fabric coord]
                                (update fabric coord (fnil conj #{}) id))
                              fabric)))
               {})
       (vals)
       (reduce (fn [good-claim-ids coord-claim-ids]
                 (cond-> good-claim-ids
                   (< 1 (count coord-claim-ids)) (set/difference coord-claim-ids)))
               (into #{} (map first) claims))))
