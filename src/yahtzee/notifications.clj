(ns yahtzee.notifications)

(def ^:private titles-by-category
  {:ones "Ones"
   :twos "Twos"
   :threes "Threes"})

(defn notify-final-category-score [category score]
  (println (str (titles-by-category category) ": " score)))

(defn notify-final-score [score]
  (println (str "Final score: " score)))

(defn notify-scores-summary [categories score-by]
  (println "Yahtzee score")
  (doseq [category categories]
    (notify-final-category-score category (score-by category))))

(defn notify-category [category]
  (println (str "Category: " (titles-by-category category))))

(defn notify-dice [rolled-dice dice]
  (println
    (str "Dice: "
         (clojure.string/join
           " "
           (map #(str % ":" (rolled-dice %)) dice)))))

(defn notify-category-score [category score-by]
  (println (str "Category " (titles-by-category category)
                " score: " (score-by category))))
