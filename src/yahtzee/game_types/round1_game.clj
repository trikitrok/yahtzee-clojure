(ns yahtzee.game-types.round1-game
  (:require
    [yahtzee.notifications :as notifications]
    [yahtzee.score :as score]
    [yahtzee.dice-rolling :as dice-rolling]
    [yahtzee.dice-scoring :as dice-scoring]
    [yahtzee.game-sequence :as game-sequence]
    [yahtzee.rolls-history :as rolls-history]))

(def ^:private dice ["D1" "D2" "D3" "D4" "D5"])

(defn- extract-dice [input-str]
  (clojure.string/split input-str #" "))

(defn- ask-which-dice-to-rerun [num-reruns]
  (println (str "[" num-reruns "] Dice to re-run:")))

(defn- dice-to-rerun [read-dice-to-rerun-input]
  (extract-dice (read-dice-to-rerun-input)))

(defn- do-reruns [{:keys [rolled-dice roll read-dice-to-rerun-input]}]
  (doseq [num-reruns [1 2]]
    (ask-which-dice-to-rerun num-reruns)
    (dice-rolling/roll-dice rolled-dice roll (dice-to-rerun read-dice-to-rerun-input) num-reruns)
    (notifications/notify-dice (rolls-history/the-last rolled-dice) dice)))

(defn annotate-to-category [score-so-far category rolled-dice]
  (score/annotate-to-category
    score-so-far
    category
    (dice-scoring/score category (rolls-history/the-last rolled-dice))))

(defn- play-category [{:keys [score-so-far rolled-dice roll] :as game} category]
  (notifications/notify-category category)
  (dice-rolling/roll-dice rolled-dice roll dice 0)
  (notifications/notify-dice (rolls-history/the-last rolled-dice) dice)
  (do-reruns game)
  (annotate-to-category score-so-far category rolled-dice)
  (notifications/notify-category-score category (partial score/for-category score-so-far)))

(defn- play-categories [this categories]
  (doseq [category categories]
    (play-category this category)))

(defrecord Round1Game [score-so-far rolled-dice roll read-dice-to-rerun-input]
  game-sequence/GameSequence
  (play [this]
    (let [categories [:ones :twos :threes]]
      (play-categories this categories)
      (notifications/notify-scores-summary categories (partial score/for-category score-so-far))
      (notifications/notify-final-score (score/total-for-categories score-so-far categories)))))

(defn make [roll read-dice-to-rerun-input]
  (->Round1Game
    (score/start)
    (rolls-history/make)
    roll
    read-dice-to-rerun-input))
