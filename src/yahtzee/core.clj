(ns yahtzee.core
  (:require
    [yahtzee.notifications :as notifications]
    [yahtzee.dice-scoring :as dice-scoring]
    [yahtzee.dice-rolling :as dice-rolling]
    [yahtzee.game-score :as game-score]))

(def ^:private dice ["D1" "D2" "D3" "D4" "D5"])

(defn- extract-dice [input-str]
  (clojure.string/split input-str #" "))

(defn- ask-which-dice-to-rerun [num-reruns]
  (println (str "[" num-reruns "] Dice to re-run:")))

(defn- dice-to-rerun [read-dice-to-rerun-input]
  (extract-dice (read-dice-to-rerun-input)))

(defn- do-reruns [roll-dice read-dice-to-rerun-input]
  (doseq [num-reruns [1 2]]
    (ask-which-dice-to-rerun num-reruns)
    (roll-dice (dice-to-rerun read-dice-to-rerun-input))
    (notifications/notify-dice (dice-rolling/last-rolled-dice) dice)))

(defn- play-category [roll-dice read-dice-to-rerun-input category]
  (notifications/notify-category category)
  (dice-rolling/initial-roll-dice roll-dice dice)
  (notifications/notify-dice (dice-rolling/last-rolled-dice) dice)
  (do-reruns roll-dice read-dice-to-rerun-input)
  (game-score/save category (dice-scoring/score category (dice-rolling/last-rolled-dice)))
  (notifications/notify-category-score category game-score/score-by-category))

(defn- play-categories [roll-dice read-dice-to-rerun-input categories]
  (doseq [category categories]
    (play-category roll-dice read-dice-to-rerun-input category)))

(defn yahtzee [roll-dice read-dice-to-rerun-input]
  (let [categories [:ones :twos :threes]]
    (play-categories roll-dice read-dice-to-rerun-input categories)
    (notifications/notify-scores-summary categories game-score/score-by-category)
    (notifications/notify-final-score (game-score/final-categories-score categories))))

(defn make-yahtzee [roll read-dice-to-rerun-input]
  (partial yahtzee (partial dice-rolling/roll-dice roll) read-dice-to-rerun-input))

(defn -main [& args]
  (let [yahtzee (make-yahtzee #(inc (rand-int 6)) read-line)]
    (yahtzee)))