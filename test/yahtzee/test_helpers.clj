(ns yahtzee.test-helpers
  (:require
    [yahtzee.core :refer :all]))

(defn make-list-generator [ls]
  (let [atom-ls (atom ls)]
    #(let [next-val (first @atom-ls)]
      (swap! atom-ls rest)
      next-val)))

(def game-factories-by-round
  {:round1 make-round1-yahtzee})

(defn make-yahtze [& {:keys [for-round using-as-rands rerunning-dice]}]
  (let [make-yahtzee (game-factories-by-round for-round)
        stubbed-roll (make-list-generator using-as-rands)
        stubbed-read-dice-to-rerun-input (make-list-generator rerunning-dice)]
    yahtzee (make-yahtzee stubbed-roll stubbed-read-dice-to-rerun-input)))


(defn output-lines-of-running [yahtzee]
  (let [cout (with-out-str (yahtzee))]
    (clojure.string/split
      cout
      #"\n")))