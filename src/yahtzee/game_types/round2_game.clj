(ns yahtzee.game-types.round2-game
  (:require
    [yahtzee.notifications :as notifications]
    [yahtzee.score :as score]
    [yahtzee.reruns :as reruns]
    [yahtzee.dice-rolling :as dice-rolling]
    [yahtzee.game-sequence :as game-sequence]
    [yahtzee.rolls-history :as rolls-history]
    [yahtzee.dice-scoring :as dice-scoring]
    [yahtzee.categories :as categories]
    [yahtzee.dice :as dice]))

(defn annotate-to-category [score-so-far category rolled-dice]
  (score/annotate-to-category
    score-so-far
    category
    (dice-scoring/score category (rolls-history/the-last rolled-dice))))

(defn play-round [{:keys [score-so-far rolled-dice selected-categories roll read-user-input] :as game} categories]
  (dice-rolling/first-roll-dice rolled-dice roll dice/dice)
  (notifications/notify-dice (rolls-history/the-last rolled-dice) dice/dice)
  (reruns/do game)
  (notifications/notify-available-categories (categories/available selected-categories categories))
  (categories/select-category! selected-categories (categories/category-to-add-input-to (read-user-input)))
  (annotate-to-category score-so-far (categories/last-selected selected-categories) rolled-dice)
  (notifications/notify-adding-points-to (categories/last-selected selected-categories)))

(defn play-rounds [game categories num]
  (loop [n 0]
    (when (< n num)
      (play-round game categories)
      (recur (inc n)))))

(defrecord Game2 [score-so-far rolled-dice selected-categories roll read-user-input]
  game-sequence/GameSequence
  (play [this]
    (let [categories [:ones :twos :threes]]
      (play-rounds this categories 3)
      (notifications/notify-scores-summary categories (partial score/for-category score-so-far))
      (notifications/notify-final-score (score/total-for-categories score-so-far categories)))))

(defn make [roll read-user-input]
  (->Game2
    (score/start)
    (rolls-history/start)
    (categories/start)
    roll
    read-user-input))
