(ns yahtzee.core
  (:require
    [yahtzee.game-sequence :refer [play]]
    [yahtzee.games-factory :as games-factory]))

(defn -main [& args]
  (let [round (keyword (first args))
        yahtzee (games-factory/make round #(inc (rand-int 6)) read-line)]
    (yahtzee)))
