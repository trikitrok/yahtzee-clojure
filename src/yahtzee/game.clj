(ns yahtzee.game
  (:require
    [yahtzee.score :as score]
    [yahtzee.dice-scoring :as dice-scoring]
    [yahtzee.rolls-history :as rolls-history]))

(defn annotate-to-category [score-so-far category rolled-dice]
  (score/annotate-to-category
    score-so-far
    category
    (dice-scoring/score category (rolls-history/the-last rolled-dice))))
