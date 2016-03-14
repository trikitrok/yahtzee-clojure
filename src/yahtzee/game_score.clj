(ns yahtzee.game-score)

(def ^:private scores-by-category
  (atom {}))

(defn save [category score]
  (swap! scores-by-category assoc category score))

(defn score-by-category [category]
  (@scores-by-category category))

(defn final-categories-score [categories]
  (reduce + (map score-by-category categories)))
