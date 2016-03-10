(ns yahtzee.scoring)

(defn- score [value rolled-dice]
  (->> rolled-dice
       (group-by #(val %))
       (filter #(= value (first %)))
       (first)
       (second)
       (count)))

(def ^:private score-fn-by-category
  {:ones (partial score 1)
   :twos (partial score 2)
   :threes (partial score 3)})

(defn score-category [category rolled-dice]
  ((score-fn-by-category category) rolled-dice))
