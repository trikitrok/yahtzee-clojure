(ns yahtzee.game
  (:require
    [yahtzee.game-sequence :refer [play]]
    [yahtzee.game-types.round1-game :as round1-game]
    [yahtzee.game-types.round2-game :as round2-game]))

(defn- yahtzee [game]
  (play game))

(def ^:private game-factories-by-round
  {:round1 round1-game/make
   :round2 round2-game/make})

(defn make [round roll read-user-input]
  (let [make-yahtzee (game-factories-by-round round)]
    #(yahtzee (make-yahtzee roll read-user-input))))
