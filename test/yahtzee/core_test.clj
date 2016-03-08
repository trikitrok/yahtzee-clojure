(ns yahtzee.core-test
  (:require [midje.sweet :refer :all]
            [yahtzee.core :refer :all]))

(def stubbed-rands (atom [2 4 1 6 1]))

(defn stub-rand [_ _]
  (let [r (first @stubbed-rands)]
    (swap! stubbed-rands rest)
    r))

(facts
  "about Yahtzee"
  (let [yahtzee (make-yahtzee stub-rand)]
    (fact
      "it outputs to the console"
      (let [cout (with-out-str (yahtzee))]
        (clojure.string/split
          cout #"\n") => ["Category: Ones"
                          "Dice: D1:2 D2:4 D3:1 D4:6 D5:1"]))))