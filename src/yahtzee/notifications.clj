(ns yahtzee.notifications)

(def ^:private titles-by-category
  {:ones "Ones"
   :twos "Twos"
   :threes "Threes"})

(defn- final-categories-score [categories scores-by-category]
  (reduce + (map scores-by-category categories)))

(defn- notify-final-category-score [notify-fn category scores-by-category]
  (notify-fn (str (titles-by-category category) ": " (scores-by-category category))))

(defn- notify-final-score [notify-fn categories scores-by-category]
  (notify-fn (str "Final score: " (final-categories-score categories scores-by-category))))

(defn notify-scores-summary [notify-fn categories scores-by-category]
  (notify-fn "Yahtzee score")
  (doseq [category categories]
    (notify-final-category-score notify-fn category scores-by-category))
  (notify-final-score notify-fn categories scores-by-category))

(defn notify-category [notify-fn category]
  (notify-fn (str "Category: " (titles-by-category category))))

(defn notify-dice [notify-fn rolled-dice dice]
  (notify-fn (str "Dice: " (clojure.string/join
                       " "
                       (map #(str % ":" (rolled-dice %)) dice)))))

(defn notify-category-score [notify-fn scores-by-category category]
  (notify-fn (str "Category " (titles-by-category category)
            " score: " (scores-by-category category))))
