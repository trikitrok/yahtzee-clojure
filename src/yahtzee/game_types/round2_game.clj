(ns yahtzee.game-types.round2-game
  (:require
    [yahtzee.notifications :as notifications]
    [yahtzee.score :as score]
    [yahtzee.reruns :as reruns]
    [yahtzee.dice-rolling :as dice-rolling]
    [yahtzee.game-sequence :as game-sequence]
    [yahtzee.rolls-history :as rolls-history]
    [yahtzee.dice-scoring :as dice-scoring]))

(def ^:private dice [:d1 :d2 :d3 :d4 :d5])

(def selected-categories
  (atom []))

(def categories-by-input
  {"1" :ones
   "2" :twos
   "3" :threes})

(defn- category-to-add-input-to [input-category-num]
  (categories-by-input input-category-num))

(defn- select-category [c]
  (swap!
    selected-categories
    conj
    c))

(defn- last-selected-category []
  (last @selected-categories))

(defn- available-categories [categories]
  (filter #(not (contains? (set @selected-categories) %)) categories))

(defn annotate-to-category [score-so-far category rolled-dice]
  (score/annotate-to-category
    score-so-far
    category
    (dice-scoring/score category (rolls-history/the-last rolled-dice))))

(defn play-round [{:keys [score-so-far rolled-dice roll read-user-input] :as game} categories]
  (dice-rolling/first-roll-dice rolled-dice roll dice)
  (notifications/notify-dice (rolls-history/the-last rolled-dice) dice)
  (reruns/do game)
  (notifications/notify-available-categories (available-categories categories))
  (select-category (category-to-add-input-to (read-user-input)))
  (annotate-to-category score-so-far (last-selected-category) rolled-dice)
  (notifications/notify-adding-points-to (last-selected-category)))

(defn play-rounds [game categories num]
  (loop [n 0]
    (when (< n num)
      (play-round game categories)
      (recur (inc n)))))

(defrecord Game2 [score-so-far rolled-dice roll read-user-input]
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
    roll
    read-user-input))
