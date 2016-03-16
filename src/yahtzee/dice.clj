(ns yahtzee.dice
  (:require
    [clojure.string :as string]))

(def dice [:d1 :d2 :d3 :d4 :d5])

(defn extract [input-str]
  (->> (string/split input-str #" ")
       (map clojure.string/lower-case)
       (map keyword)))
