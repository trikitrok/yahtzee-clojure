(ns yahtzee.dice-scoring)

(defn- score-dice [value rolled-dice]
  (->> rolled-dice
       (group-by #(val %))
       (filter #(= value (first %)))
       (first)
       (second)
       (count)))

(def ^:private score-fn-by-category
  {:ones (partial score-dice 1)
   :twos (partial score-dice 2)
   :threes (partial score-dice 3)})

(defn score [category rolled-dice]
  ((score-fn-by-category category) rolled-dice))
