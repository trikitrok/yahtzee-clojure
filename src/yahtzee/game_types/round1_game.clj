(ns yahtzee.game-types.round1-game
  (:require
    [yahtzee.notifications :as notifications]
    [yahtzee.score :as score]
    [yahtzee.reruns :as reruns]
    [yahtzee.dice-rolling :as dice-rolling]
    [yahtzee.dice-scoring :as dice-scoring]
    [yahtzee.game-sequence :as game-sequence]
    [yahtzee.rolls-history :as rolls-history]
    [yahtzee.categories :as categories]
    [yahtzee.dice :as dice]))

(defn annotate-to-category [score-so-far category rolled-dice]
  (score/annotate-to-category
    score-so-far
    category
    (dice-scoring/score category (rolls-history/the-last rolled-dice))))

(defn- play-category [{:keys [score-so-far rolled-dice roll] :as game} category]
  (notifications/notify-category category)
  (dice-rolling/first-roll-dice rolled-dice roll dice/dice)
  (notifications/notify-dice (rolls-history/the-last rolled-dice) dice/dice)
  (reruns/do game)
  (annotate-to-category score-so-far category rolled-dice)
  (notifications/notify-category-score category (partial score/for-category score-so-far)))

(defn- play-categories [this categories]
  (doseq [category categories]
    (play-category this category)))

(defrecord Game1 [score-so-far rolled-dice roll read-user-input]
  game-sequence/GameSequence
  (play [this]
    (play-categories this categories/categories)
    (notifications/notify-scores-summary categories/categories (partial score/for-category score-so-far))
    (notifications/notify-final-score (score/total-for-categories score-so-far categories/categories))))

(defn make [roll read-user-input]
  (->Game1
    (score/start)
    (rolls-history/start)
    roll
    read-user-input))
