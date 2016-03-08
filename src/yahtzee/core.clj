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

(defn score [rolled-dice value]
  (->> rolled-dice
       (group-by #(val %))
       (filter #(= value (first %)))
       (first)
       (second)
       (count)))

(defn produce-category-title [category]
  (str "Category: " (titles-by-category category)))

(defn produce-category-score-output [category rolled-dice]
  (str "Category " (titles-by-category category)
       " score: " (score rolled-dice 1)))

(defn yahtzee [roll-dice ask-dice-to-rerun]
  (println (produce-category-title :ones))
  (roll-dice dice)
  (println (produce-dice-output @rolled-dice))
  (doseq [num-reruns [1 2]]
      (println (dice-to-rerun num-reruns))
      (roll-dice (extract-dice (ask-dice-to-rerun)))
      (println (produce-dice-output @rolled-dice)))
  (println (produce-category-score-output :ones @rolled-dice)))

(defn make-yahtzee [roll ask-dice-to-rerun]
  (partial yahtzee (partial roll-dice roll) ask-dice-to-rerun))