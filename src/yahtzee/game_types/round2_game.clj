(ns yahtzee.game-types.round2-game
  (:require
    [yahtzee.notifications :as notifications]
    [yahtzee.score :as score]
    [yahtzee.dice-rolling :as dice-rolling]
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

(defn- category-to-add-input-to [read-user-input]
  (read-user-input))

(defn- do-reruns [{:keys [rolled-dice roll read-user-input]}]
  (doseq [num-reruns [1 2]]
    (ask-which-dice-to-rerun num-reruns)
    (dice-rolling/roll-dice rolled-dice roll (dice-to-rerun read-user-input))
    (notifications/notify-dice (rolls-history/the-last rolled-dice) dice)))

(defrecord Round1Game [score-so-far rolled-dice roll read-user-input]
  game-sequence/GameSequence
  (play [this]
    (dice-rolling/first-roll-dice rolled-dice roll dice)
    (notifications/notify-dice (rolls-history/the-last rolled-dice) dice)
    (do-reruns this)
    (notifications/notify-available-categories [:ones :twos :threes])
    (notifications/notify-adding-points-to (category-to-add-input-to read-user-input))

    (dice-rolling/first-roll-dice rolled-dice roll dice)
    (notifications/notify-dice (rolls-history/the-last rolled-dice) dice)
    (do-reruns this)
    ))

(defn make [roll read-user-input]
  (->Round1Game
    (score/start)
    (rolls-history/start)
    roll
    read-user-input))
