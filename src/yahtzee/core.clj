(ns yahtzee.core)

(def dice-nums (range 1 6))

(def titles-by-category
  {:ones "Category: Ones"})

(defn roll-dice [roll]
  (repeatedly 5 roll))

(defn dice-output-str [roll-dice]
  (str
    "Dice: "
    (clojure.string/join
      " "
      (map #(str "D" %2 ":" %1)
           (roll-dice)
           dice-nums))))

(defn dice-to-rerun [roll-num]
  (str "[" roll-num "] Dice to re-run:"))

(defn category-output-strs [category roll-num roll-dice]
  (clojure.string/join "\n"
    [(titles-by-category category)
     (dice-output-str roll-dice)
     (dice-to-rerun roll-num)]))

(defn yahtzee [roll-dices]
  (println (category-output-strs :ones 1 roll-dices)))

(defn make-yahtzee [roll]
  (partial yahtzee #(roll-dice roll)))