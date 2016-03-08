(ns yahtzee.core-test
  (:require [midje.sweet :refer :all]
            [yahtzee.core :refer :all]))

(facts
  "about Yahtzee"
  (fact
    "it outputs to the console"
    (let [cout (with-out-str (yahtzee))]
      (= cout "Category: Ones\n") => true)))