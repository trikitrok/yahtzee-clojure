(ns yahtzee.core-test
  (:require [midje.sweet :refer :all]
            [yahtzee.core :refer :all]))

(def stubbed-rands (atom [2 4 1 6 1
                          1 5 2
                          1 5
                          2 3
                          6 1 2
                          5 1 3 2 3
                          6 2 4]))

(def stubbed-dice-to-rerun-inputs
  (atom ["D1 D2 D4"
         "D2 D4"
         "D2 D5"
         "D3 D4 D5"
         "D1 D2 D3 D4 D5"
         "D1 D2 D4"]))

(defn make-stub-producing-list [atom-stub]
  #(let [next-val (first @atom-stub)]
    (swap! atom-stub rest)
    next-val))

(def stubbed-roll
  (make-stub-producing-list stubbed-rands))

(def stubbed-read-dice-to-rerun-input
  (make-stub-producing-list stubbed-dice-to-rerun-inputs))

(facts
  "about Yahtzee kata"

  (facts
    "about Round 1"
    (let [yahtzee (make-yahtzee stubbed-roll stubbed-read-dice-to-rerun-input println println)]
      (fact
        "it prints the right output into the console"
        (let [cout (with-out-str (yahtzee))]
          (clojure.string/split
            cout #"\n") => ["Category: Ones"
                            "Dice: D1:2 D2:4 D3:1 D4:6 D5:1"
                            "[1] Dice to re-run:"
                            "Dice: D1:1 D2:5 D3:1 D4:2 D5:1"
                            "[2] Dice to re-run:"
                            "Dice: D1:1 D2:1 D3:1 D4:5 D5:1"
                            "Category Ones score: 4"
                            "Category: Twos"
                            "Dice: D1:2 D2:4 D3:1 D4:6 D5:1"
                            "[1] Dice to re-run:"
                            "Dice: D1:2 D2:2 D3:1 D4:6 D5:3"
                            "[2] Dice to re-run:"
                            "Dice: D1:2 D2:2 D3:6 D4:1 D5:2"
                            "Category Twos score: 3"
                            "Category: Threes"
                            "Dice: D1:2 D2:4 D3:1 D4:6 D5:1"
                            "[1] Dice to re-run:"
                            "Dice: D1:5 D2:1 D3:3 D4:2 D5:3"
                            "[2] Dice to re-run:"
                            "Dice: D1:6 D2:2 D3:3 D4:4 D5:3"
                            "Category Threes score: 2"
                            "Yahtzee score"
                            "Ones: 4"
                            "Twos: 3"
                            "Threes: 2"
                            "Final score: 9"])))))
