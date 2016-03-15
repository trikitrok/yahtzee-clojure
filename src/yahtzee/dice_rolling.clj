(ns yahtzee.dice-rolling
  (:require [yahtzee.rolls-history :as rolls-history]))

(defn- roll-all-dice [roll dice]
  (into {} (for [d dice] [d (roll)])))

(defn first-roll-dice [rolled-dice roll dice]
  (if (rolls-history/no-rolls-yet? rolled-dice)
    (do (rolls-history/update-last! rolled-dice (roll-all-dice roll dice))
        (rolls-history/set-initial-to-last rolled-dice))
    (rolls-history/set-last-to-initial rolled-dice)))

(defn roll-dice [rolled-dice roll dice]
  (rolls-history/update-last! rolled-dice (roll-all-dice roll dice)))
