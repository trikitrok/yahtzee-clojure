(ns yahtzee.core-test
  (:require
    [midje.sweet :refer :all]
    [yahtzee.core :refer :all]
    [yahtzee.test-helpers :as helpers]))

(facts
  "about Yahtzee kata"

  (fact
    "about game described in kata's round 1"
    (helpers/output-lines-of-running-yahtzee
      :for-round :round1
      :using-as-rands [2 4 1 6 1
                       1 5 2
                       1 5
                       2 3
                       6 1 2
                       5 1 3 2 3
                       6 2 4]
      :and-user-input ["D1 D2 D4"
                       "D2 D4"
                       "D2 D5"
                       "D3 D4 D5"
                       "D1 D2 D3 D4 D5"
                       "D1 D2 D4"]) => ["Category: Ones"
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
                                        "Final score: 9"])

  (fact
    "about game described in kata's round 2"
    (helpers/output-lines-of-running-yahtzee
      :for-round :round2
      :using-as-rands [2 4 1 6 1
                       1 5 2
                       1 5
                       1 5 2
                       2 4 5
                       3 1
                       4 2 6]
      :and-user-input ["D1 D2 D4"
                       "D2 D4"
                       "1"
                       "D1 D2 D4"
                       "D1 D2 D5"
                       "2"
                       "D2 D4"
                       "D1 D3 D5"
                       "3"]) => ["Dice: D1:2 D2:4 D3:1 D4:6 D5:1"
                                 "[1] Dice to re-run:"
                                 "Dice: D1:1 D2:5 D3:1 D4:2 D5:1"
                                 "[2] Dice to re-run:"
                                 "Dice: D1:1 D2:1 D3:1 D4:5 D5:1"
                                 "Available categories:"
                                 "[1] Ones"
                                 "[2] Twos"
                                 "[3] Threes"
                                 "Category to add points to: 1"
                                 "Dice: D1:2 D2:4 D3:1 D4:6 D5:1"
                                 "[1] Dice to re-run:"
                                 "Dice: D1:1 D2:5 D3:1 D4:2 D5:1"
                                 "[2] Dice to re-run:"
                                 "Dice: D1:2 D2:4 D3:1 D4:2 D5:5"
                                 "Available categories:"
                                 "[2] Twos"
                                 "[3] Threes"
                                 "Category to add points to: 2"
                                 "Dice: D1:2 D2:4 D3:1 D4:6 D5:1"
                                 "[1] Dice to re-run:"
                                 "Dice: D1:2 D2:3 D3:1 D4:1 D5:1"
                                 "[2] Dice to re-run:"
                                 "Dice: D1:4 D2:3 D3:2 D4:1 D5:6"
                                 "Available categories:"
                                 "[3] Threes"
                                 "Category to add points to: 3"
                                 "Yahtzee score"
                                 "Ones: 4"
                                 "Twos: 2"
                                 "Threes: 1"
                                 "Final score: 7"]))
