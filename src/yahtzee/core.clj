(ns yahtzee.core
  (:require
    [yahtzee.notifications :as notifications]
    [yahtzee.scoring :as scoring]
    [yahtzee.state :as state]))

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
    (notifications/notify-dice (state/last-rolled-dice) dice)))

(defn- play-category [roll-dice read-dice-to-rerun-input category]
  (notifications/notify-category category)
  (state/initial-roll-dice roll-dice dice)
  (notifications/notify-dice (state/last-rolled-dice) dice)
  (do-reruns roll-dice read-dice-to-rerun-input)
  (state/store-score category (scoring/score-category category (state/last-rolled-dice)))
  (notifications/notify-category-score category state/score-by-category))

(defn- play-categories [roll-dice read-dice-to-rerun-input categories]
  (doseq [category categories]
    (play-category roll-dice read-dice-to-rerun-input category)))

(defn yahtzee [roll-dice read-dice-to-rerun-input]
  (let [categories [:ones :twos :threes]]
    (play-categories roll-dice read-dice-to-rerun-input categories)
    (notifications/notify-scores-summary categories state/score-by-category)
    (notifications/notify-final-score (state/final-categories-score categories))))

(defn make-yahtzee [roll read-dice-to-rerun-input]
  (partial yahtzee (partial state/roll-dice roll) read-dice-to-rerun-input))

(defn -main [& args]
  (let [yahtzee (make-yahtzee #(inc (rand-int 6)) read-line)]
    (yahtzee)))