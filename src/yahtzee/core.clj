(ns yahtzee.core)

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
                (range 1 6)))))
  (println "[1] Dice to re-run:"))

(defn make-yahtzee [roll]
  (partial yahtzee #(roll-dices roll)))