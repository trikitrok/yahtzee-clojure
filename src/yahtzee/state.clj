(ns yahtzee.state)

(def ^:private rolled-dice
  (atom {"D1" nil "D2" nil "D3" nil "D4" nil "D5" nil}))

(def ^:private initial-rolled-dice
  (atom {}))

(def ^:private scores-by-category
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

(defn store-score [category score]
  (swap! scores-by-category assoc category score))

(defn last-rolled-dice []
  @rolled-dice)

(defn score-by-category [category]
  (@scores-by-category category))

(defn final-categories-score [categories]
  (reduce + (map score-by-category categories)))


