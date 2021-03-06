(ns advent-of-code-2018.day-4b
  (:require [advent-of-code-2018.common :as common]))

(->> (common/input "day_4.txt")
     (map (fn [line]
            (let [[_ year month day hour minute]
                  (re-find #"\[(\d\d\d\d)-(\d\d)-(\d\d) (\d\d):(\d\d)\]" line)
                  year   (Integer/parseInt year)
                  month  (Integer/parseInt month)
                  day    (Integer/parseInt day)
                  hour   (Integer/parseInt hour)
                  minute (Integer/parseInt minute)]
              [[year month day hour minute]
               (if-let [[_ guard] (re-find #"Guard #(\d+) begins shift" line)]
                 [:guard (Integer/parseInt guard)]
                 (if (re-find #"falls asleep" line)
                   [:sleep]
                   [:wake]))])))
     (sort)
     (partition-by (comp #{:guard} first second))
     (partition 2)
     (reduce (fn [guards [[[_ [_ guard]]] sleep-wakes]]
               (let [sleep-schedule
                     (->> sleep-wakes
                          (partition 2)
                          (reduce (fn [sleep-schedule [[[_ _ _ _ sleep-minute] _]
                                                       [[_ _ _ _ wake-minute]  _]]]
                                    (->> (range sleep-minute wake-minute)
                                         (reduce (fn [sleep-schedule time]
                                                   (update sleep-schedule time inc))
                                                 sleep-schedule)))
                                  (vec (repeat 60 0))))]
                 (merge-with (fn [sleep-schedule-1 sleep-schedule-2]
                               (map + sleep-schedule-1 sleep-schedule-2))
                             guards
                             {guard sleep-schedule})))
             {})
     (map (fn [[guard sleep-schedule]]
            [guard
             (->> sleep-schedule
                  (map-indexed vector)
                  (apply max-key second))]))
     (apply max-key (comp second second))
     ((fn [[guard [sleep-day _]]]
        (* guard sleep-day))))
