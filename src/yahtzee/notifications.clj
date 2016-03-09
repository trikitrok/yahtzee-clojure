(ns yahtzee.notifications)

(def ^:private titles-by-category
  {:ones "Ones"
   :twos "Twos"
   :threes "Threes"})

(defn- produce-category-title [category]
  (str "Category: " (titles-by-category category)))

(defn- produce-category-score-output [category score]
  (str "Category " (titles-by-category category)
       " score: " score))

(defn- produce-short-category-score [category scores-by-category]
  (str (titles-by-category category) ": " (scores-by-category category)))

(defn- final-categories-score [categories scores-by-category]
  (reduce + (map scores-by-category categories)))

(defn- produce-final-score-output [categories scores-by-category]
  (str "Final score: " (final-categories-score categories scores-by-category)))

(defn notify-scores-summary [categories scores-by-category]
  (println "Yahtzee score")
  (doseq [category categories]
    (println (produce-short-category-score category scores-by-category)))
  (println (produce-final-score-output categories scores-by-category)))

(defn notify-category [category]
  (println (produce-category-title category)))

(defn notify-dice-output [rolled-dice dice]
  (println (str "Dice: " (clojure.string/join
                           " "
                           (map #(str % ":" (rolled-dice %)) dice)))))

(defn ask-which-dice-to-rerun [num-reruns]
  (println (str "[" num-reruns "] Dice to re-run:")))

(defn notify-category-score [scores-by-category category]
  (println (produce-category-score-output category (scores-by-category category))))
