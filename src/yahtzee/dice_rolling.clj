(ns yahtzee.dice-rolling)

(def ^:private rolled-dice
  (atom {"D1" nil "D2" nil "D3" nil "D4" nil "D5" nil}))

(def ^:private initial-rolled-dice
  (atom {}))

(defn roll-dice [roll dice]
  (swap! rolled-dice
         merge
         (into {} (for [d dice] [d (roll)]))))

(defn initial-roll-dice [roll-dice dice]
  (if (empty? @initial-rolled-dice)
    (do (roll-dice dice)
        (reset! initial-rolled-dice @rolled-dice))
    (reset! rolled-dice @initial-rolled-dice)))

(defn last-rolled-dice []
  @rolled-dice)
