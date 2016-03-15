(ns yahtzee.game-types.round1-game
  (:require
    [yahtzee.notifications :as notifications]
    [yahtzee.score :as score]
    [yahtzee.dice-rolling :as dice-rolling]
    [yahtzee.dice-scoring :as dice-scoring]
    [yahtzee.game-sequence :as game-sequence]
    [yahtzee.rolls-history :as rolls-history]
    [clojure.string :as string]))

(def ^:private dice [:d1 :d2 :d3 :d4 :d5])

(defn- extract-dice [input-str]
  (->> (string/split input-str #" ")
       (map clojure.string/lower-case)
       (map keyword)))

(defn- ask-which-dice-to-rerun [num-reruns]
  (println (str "[" num-reruns "] Dice to re-run:")))

(defn- dice-to-rerun [read-user-input]
  (extract-dice (read-user-input)))

(defn- do-reruns [{:keys [rolled-dice roll read-user-input]}]
  (doseq [num-reruns [1 2]]
    (ask-which-dice-to-rerun num-reruns)
    (dice-rolling/roll-dice rolled-dice roll (dice-to-rerun read-user-input))
    (notifications/notify-dice (rolls-history/the-last rolled-dice) dice)))

(defn annotate-to-category [score-so-far category rolled-dice]
  (score/annotate-to-category
    score-so-far
    category
    (dice-scoring/score category (rolls-history/the-last rolled-dice))))

(defn- play-category [{:keys [score-so-far rolled-dice roll] :as game} category]
  (notifications/notify-category category)
  (dice-rolling/first-roll-dice rolled-dice roll dice)
  (notifications/notify-dice (rolls-history/the-last rolled-dice) dice)
  (do-reruns game)
  (annotate-to-category score-so-far category rolled-dice)
  (notifications/notify-category-score category (partial score/for-category score-so-far)))

(defn- play-categories [this categories]
  (doseq [category categories]
    (play-category this category)))

(defrecord Round1Game [score-so-far rolled-dice roll read-user-input]
  game-sequence/GameSequence
  (play [this]
    (let [categories [:ones :twos :threes]]
      (play-categories this categories)
      (notifications/notify-scores-summary categories (partial score/for-category score-so-far))
      (notifications/notify-final-score (score/total-for-categories score-so-far categories)))))

(defn make [roll read-user-input]
  (->Round1Game
    (score/start)
    (rolls-history/start)
    roll
    read-user-input))
