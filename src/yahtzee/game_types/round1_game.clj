(ns yahtzee.game-types.round1-game
  (:require
    [yahtzee.notifications :as notifications]
    [yahtzee.game-score :as game-score]
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

(defn- play-category [{:keys [rolled-dice roll] :as game} category]
  (notifications/notify-category category)
  (dice-rolling/roll-dice rolled-dice roll dice 0)
  (notifications/notify-dice (rolls-history/the-last rolled-dice) dice)
  (do-reruns game)
  (game-score/save category (dice-scoring/score category (rolls-history/the-last rolled-dice)))
  (notifications/notify-category-score category game-score/score-by-category))

(defn- play-categories [this categories]
  (doseq [category categories]
    (play-category this category)))

(defrecord Round1Game [rolled-dice roll read-dice-to-rerun-input]
  game-sequence/GameSequence
  (play [this]
    (let [categories [:ones :twos :threes]]
      (play-categories this categories)
      (notifications/notify-scores-summary categories game-score/score-by-category)
      (notifications/notify-final-score (game-score/final-categories-score categories)))))

(defn make [roll read-dice-to-rerun-input]
  (->Round1Game
    (rolls-history/make)
    roll
    read-dice-to-rerun-input))
