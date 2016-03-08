(ns yahtzee.core)

(def dice-nums (range 1 6))

(defn roll-dices [roll]
  (repeatedly 5 roll))

(defn yahtzee [roll-dices]
  (println "Category: Ones")
  (println
    (str "Dice: "
         (clojure.string/join
           " "
           (map #(str "D" %2 ":" %1)
                (roll-dices)
                dice-nums))))
  (println "[1] Dice to re-run:"))

(defn make-yahtzee [roll]
  (partial yahtzee #(roll-dices roll)))