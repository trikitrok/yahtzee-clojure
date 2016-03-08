(ns yahtzee.core)

(def dice ["D1" "D2" "D3" "D4" "D5"])

(def titles-by-category
  {:ones "Ones"
   :twos "Twos"
   :threes "Threes"})

(def rolled-dice
  (atom {"D1" nil "D2" nil "D3" nil "D4" nil "D5" nil}))

(def initial-rolled-dice
  (atom {}))

(def scores-by-category
  (atom {}))

(defn roll-dice [roll dice]
  (swap! rolled-dice
         merge
         (into {} (for [d dice] [d (roll)]))))

(defn produce-dice-output [rolled-dice]
  (str
    "Dice: "
    (clojure.string/join
      " "
      (map #(str % ":" (rolled-dice %)) dice))))

(defn dice-to-rerun [roll-num]
  (str "[" roll-num "] Dice to re-run:"))

(defn extract-dice [input-str]
  (clojure.string/split input-str #" "))

(defn score [value rolled-dice]
  (->> rolled-dice
       (group-by #(val %))
       (filter #(= value (first %)))
       (first)
       (second)
       (count)))

(defn produce-category-title [category]
  (str "Category: " (titles-by-category category)))

(def score-fn-by-category
  {:ones (partial score 1)
   :twos (partial score 2)
   :threes (partial score 3)})

(defn score-category [category rolled-dice]
  ((score-fn-by-category category) rolled-dice))

(defn produce-category-score-output [category score]
  (str "Category " (titles-by-category category)
       " score: " score))

(defn initial-roll-dice [roll-dice]
  (if (empty? @initial-rolled-dice)
    (do (roll-dice dice)
        (reset! initial-rolled-dice @rolled-dice))
    (reset! rolled-dice @initial-rolled-dice)))

(defn store-score [category score]
  (swap! scores-by-category assoc category score))

(defn play-category [roll-dice ask-dice-to-rerun category]
  (println (produce-category-title category))
  (initial-roll-dice roll-dice)
  (println (produce-dice-output @rolled-dice))
  (doseq [num-reruns [1 2]]
    (println (dice-to-rerun num-reruns))
    (roll-dice (extract-dice (ask-dice-to-rerun)))
    (println (produce-dice-output @rolled-dice)))
  (store-score category (score-category category @rolled-dice))
  (println (produce-category-score-output category (@scores-by-category category))))

(defn produce-short-category-score [category scores-by-category]
  (str (titles-by-category category) ": " (scores-by-category category)))

(defn final-categories-score [categories scores-by-category]
  (reduce + (map scores-by-category categories)))

(defn produce-final-score-output [categories scores-by-category]
  (str "Final score: " (final-categories-score categories scores-by-category)))

(defn print-scores-summary [categories scores-by-category]
  (println "Yahtzee score")
  (doseq [category categories]
    (println (produce-short-category-score category scores-by-category)))
  (println (produce-final-score-output categories scores-by-category)))

(defn yahtzee [roll-dice ask-dice-to-rerun]
  (let [categories [:ones :twos :threes]]
    (doseq [category categories]
    (play-category roll-dice ask-dice-to-rerun category))
  (print-scores-summary categories @scores-by-category)))

(defn make-yahtzee [roll ask-dice-to-rerun]
  (partial yahtzee (partial roll-dice roll) ask-dice-to-rerun))