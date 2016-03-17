(ns yahtzee.reruns
  (:require
    [yahtzee.dice :as dice]
    [yahtzee.dice-rolling :as dice-rolling]
    [yahtzee.notifications :as notifications]
    [yahtzee.rolls-history :as rolls-history]))

(defn- ask-which-dice-to-rerun [rerun-num]
  (println (str "[" rerun-num "] Dice to re-run:")))

(defn- dice-to-rerun [read-user-input]
  (dice/extract (read-user-input)))

(defn do [{:keys [rolled-dice roll read-user-input]}]
  (doseq [rerun-num [1 2]]
    (ask-which-dice-to-rerun rerun-num)
    (dice-rolling/roll-dice rolled-dice roll (dice-to-rerun read-user-input))
    (notifications/notify-dice (rolls-history/the-last rolled-dice) dice/dice)))
