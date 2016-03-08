(ns yahtzee.core)

(def dice ["D1" "D2" "D3" "D4" "D5"])

(def titles-by-category
  {:ones "Ones"})

(def rolled-dice
  (atom {"D1" nil "D2" nil "D3" nil "D4" nil "D5" nil}))

(defn roll-dice [roll dice]
  (swap! rolled-dice
         merge
         (into {} (for [d dice] [d (roll)]))))

(defn produce-dice-output []
  (str
    "Dice: "
    (clojure.string/join
      " "
      (map #(str % ":" (@rolled-dice %)) dice))))

(defn dice-to-rerun [roll-num]
  (str "[" roll-num "] Dice to re-run:"))

(defn extract-dice [input-str]
  (clojure.string/split input-str #" "))

(defn score [rolled-dice]
  (->> rolled-dice
       (group-by #(val %))
       (filter #(= 1 (first %)))
       (first)
       (second)
       (count)))

(defn produce-category-title [category]
  (str "Category: " (titles-by-category category)))

(defn produce-category-score-output [category]
  (str "Category " (titles-by-category category) " score: " (score @rolled-dice)))

(defn yahtzee [roll-dice ask-dice-to-rerun]
  (println (produce-category-title :ones))
  (roll-dice dice)
  (println (produce-dice-output))
  (println (dice-to-rerun 1))
  (roll-dice (extract-dice (ask-dice-to-rerun)))
  (println (produce-dice-output))
  (println (dice-to-rerun 2))
  (roll-dice (extract-dice (ask-dice-to-rerun)))
  (println (produce-dice-output))
  (println (produce-category-score-output :ones)))

(defn make-yahtzee [roll ask-dice-to-rerun]
  (partial yahtzee (partial roll-dice roll) ask-dice-to-rerun))