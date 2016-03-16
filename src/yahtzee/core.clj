(ns yahtzee.core
  (:require
    [yahtzee.game-sequence :refer [play]]
    [yahtzee.game :as game]))

(defn -main [& args]
  (let [round (keyword (first args))
        yahtzee (game/make round #(inc (rand-int 6)) read-line)]
    (yahtzee)))
