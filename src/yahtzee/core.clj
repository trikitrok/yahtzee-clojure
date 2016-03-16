(ns yahtzee.core
  (:require
    [yahtzee.game-sequence :refer [play]]
    [yahtzee.game :as game]))

(defn -main [& args]
  (let [yahtzee (game/make :round1 #(inc (rand-int 6)) read-line)]
    (play yahtzee)))
