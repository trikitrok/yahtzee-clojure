(ns yahtzee.dice-rolling)

(def ^:private rolled-dice
  (atom {"D1" nil "D2" nil "D3" nil "D4" nil "D5" nil}))

(def ^:private initial-rolled-dice
  (atom {}))

(defn- update-rolled-dice! [new-rolled-dice]
  (swap! rolled-dice
         merge
         new-rolled-dice))

(defn- roll-all-dice [roll dice]
  (into {} (for [d dice] [d (roll)])))

(defn roll-dice [roll dice num-roll]
  (if (zero? num-roll)
    (if (empty? @initial-rolled-dice)
      (do (update-rolled-dice! (roll-all-dice roll dice))
          (reset! initial-rolled-dice @rolled-dice))
      (reset! rolled-dice @initial-rolled-dice))
    (update-rolled-dice! (roll-all-dice roll dice))))

(defn last-rolled-dice []
  @rolled-dice)
