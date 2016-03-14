(ns yahtzee.core
  (:require
    [yahtzee.game-sequence :refer [play]]
    [yahtzee.game-types.round1-game :as round1-game]))

(defn yahtzee [game]
  (play game))

(defn make-round1-yahtzee [roll read-dice-to-rerun-input]
  (partial yahtzee (round1-game/make roll read-dice-to-rerun-input)))

(defn -main [& args]
  (let [yahtzee (round1-game/make #(inc (rand-int 6)) read-line)]
    (play yahtzee)))
