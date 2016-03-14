(ns yahtzee.current-score)

(def ^:private scores-by-category
  (atom {}))

(defn store-score [category score]
  (swap! scores-by-category assoc category score))

(defn score-by-category [category]
  (@scores-by-category category))

(defn final-categories-score [categories]
  (reduce + (map score-by-category categories)))
