(ns yahtzee.core)

(defn yahtzee [roll]
  (println "Category: Ones")
  (println
    (str "Dice: "
         (clojure.string/join
           " "
           (map #(str "D" % ":" (roll))
                (range 1 6))))))

(defn make-yahtzee [roll]
  #(yahtzee roll))