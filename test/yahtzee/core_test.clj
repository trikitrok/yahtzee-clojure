(ns yahtzee.core-test
  (:require [midje.sweet :refer :all]
            [yahtzee.core :refer :all]))

(def stubbed-rands (atom [2 4 1 6 1
                          1 5 2
                          1 5]))

(def stubbed-dice-to-rerun-responses
  (atom ["D1 D2 D4"
         "D2 D4"]))

(defn make-stub-producing-list [atom-stub]
  #(let [next-val (first @atom-stub)]
    (swap! atom-stub rest)
    next-val))

(def stubbed-roll
  (make-stub-producing-list stubbed-rands))

(def stubbed-ask-dice-to-rerun
  (make-stub-producing-list stubbed-dice-to-rerun-responses))

(facts
  "about Yahtzee"
  (let [yahtzee (make-yahtzee stubbed-roll stubbed-ask-dice-to-rerun)]
    (fact
      "it outputs to the console"
      (let [cout (with-out-str (yahtzee))]
        (clojure.string/split
          cout #"\n") => ["Category: Ones"
                          "Dice: D1:2 D2:4 D3:1 D4:6 D5:1"
                          "[1] Dice to re-run:"
                          "Dice: D1:1 D2:5 D3:1 D4:2 D5:1"
                          "[2] Dice to re-run:"
                          "Dice: D1:1 D2:1 D3:1 D4:5 D5:1"]))))