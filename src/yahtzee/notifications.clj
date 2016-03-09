(ns yahtzee.notifications)

(def ^:private titles-by-category
  {:ones "Ones"
   :twos "Twos"
   :threes "Threes"})

(defn- final-categories-score [categories scores-by-category]
  (reduce + (map scores-by-category categories)))

(defn- notify-final-category-score [category scores-by-category]
  (println (str (titles-by-category category) ": " (scores-by-category category))))

(defn- notify-final-score [categories scores-by-category]
  (println (str "Final score: " (final-categories-score categories scores-by-category))))

(defn notify-scores-summary [categories scores-by-category]
  (println "Yahtzee score")
  (doseq [category categories]
    (notify-final-category-score category scores-by-category))
  (notify-final-score categories scores-by-category))

(defn notify-category [category]
  (println (str "Category: " (titles-by-category category))))

(defn notify-dice [rolled-dice dice]
  (println (str "Dice: " (clojure.string/join
                           " "
                           (map #(str % ":" (rolled-dice %)) dice)))))

(defn notify-category-score [scores-by-category category]
  (println (str "Category " (titles-by-category category)
                " score: " (scores-by-category category))))
