(ns yahtzee.core
  (:require
    [yahtzee.game-sequence :refer [play]]
    [yahtzee.game-types.round1-game :as round1-game]
    [yahtzee.game-types.round2-game :as round2-game]))

(defn yahtzee [game]
  (play game))

(defn make-round1-yahtzee [roll read-dice-to-rerun-input]
  #(yahtzee (round1-game/make roll read-dice-to-rerun-input)))

(defn make-round2-yahtzee [roll read-dice-to-rerun-input]
  #(yahtzee (round2-game/make roll read-dice-to-rerun-input)))

(defn -main [& args]
  (let [yahtzee (round1-game/make #(inc (rand-int 6)) read-line)]
    (play yahtzee)))
