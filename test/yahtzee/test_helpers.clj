(ns yahtzee.test-helpers
  (:require
    [yahtzee.core :refer :all]))

(defn make-list-generator [ls]
  (let [atom-ls (atom ls)]
    #(let [next-val (first @atom-ls)]
      (swap! atom-ls rest)
      next-val)))

(def game-factories-by-round
  {:round1 make-round1-yahtzee
   :round2 make-round2-yahtzee})

(defn- make-yahtze [{:keys [for-round using-as-rands and-user-input]}]
  (let [make-yahtzee (game-factories-by-round for-round)
        stubbed-roll (make-list-generator using-as-rands)
        stubbed-read-user-input (make-list-generator and-user-input)]
    yahtzee (make-yahtzee stubbed-roll stubbed-read-user-input)))


(defn output-lines-of-running-yahtzee [& {:as params}]
  (let [yahtzee (make-yahtze params)
        cout (with-out-str (yahtzee))]
    (clojure.string/split
      cout
      #"\n")))