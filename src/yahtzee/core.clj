(ns yahtzee.core)

(def dice [:d1 :d2 :d3 :d4 :d5])

(def titles-by-category
  {:ones "Category: Ones"})

(def rolled-dice
  (atom {:d1 nil :d2 nil :d3 nil :d4 nil :d5 nil}))

(def description-by-dice
  {:d1 "D1", :d2 "D2", :d3 "D3", :d4 "D4", :d5 "D5"})

(defn roll-dice [roll dice]
  (swap! rolled-dice
         merge
         (into {} (for [d dice] [d (roll)]))))

(defn dice-output-str [rolled-dice]
  (str
    "Dice: "
    (clojure.string/join
      " "
      (map #(str (description-by-dice %) ":" (rolled-dice %)) dice))))

(defn dice-to-rerun [roll-num]
  (str "[" roll-num "] Dice to re-run:"))

(defn produce-category-output [category roll-num]
  (clojure.string/join
    "\n"
    [(titles-by-category category)
     (dice-output-str @rolled-dice)
     (dice-to-rerun roll-num)]))

(defn yahtzee [roll-dice]
  (roll-dice dice)
  (println (produce-category-output :ones 1)))

(defn make-yahtzee [roll]
  (partial yahtzee (partial roll-dice roll)))