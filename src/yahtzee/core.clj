(ns yahtzee.core)

(defn yahtzee [rand-int]
  (println "Category: Ones")
  (println
    (str "Dice: "
         (clojure.string/join
           " "
           (map #(str "D" % ":" (rand-int 0 6))
                (range 1 6))))))

(defn make-yahtzee [rand-int]
  #(yahtzee rand-int))