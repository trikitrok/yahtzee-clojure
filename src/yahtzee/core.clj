(ns yahtzee.core)

(def dice-nums (range 1 6))

(defn roll-dices [roll]
  (repeatedly 5 roll))

(defn category-output-strs [roll-dices]
  (clojure.string/join
    "\n"
    ["Category: Ones"
     (str "Dice: "
          (clojure.string/join
            " "
            (map #(str "D" %2 ":" %1)
                 (roll-dices)
                 dice-nums)))
     "[1] Dice to re-run:"]))

(defn yahtzee [roll-dices]
  (println (category-output-strs roll-dices)))

(defn make-yahtzee [roll]
  (partial yahtzee #(roll-dices roll)))