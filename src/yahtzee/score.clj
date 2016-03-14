(ns yahtzee.score)

(defrecord Score [so-far])

(defn start []
  (->Score
    (atom {:by-category {}})))

(defn annotate-to-category [{:keys [so-far]} category score]
  (swap! so-far update-in [:by-category] assoc category score))

(defn for-category [{:keys [so-far]} category]
  (let [scores-by-category (:by-category @so-far)]
    (scores-by-category category)))

(defn total-for-categories [score categories]
  (reduce + (map (partial for-category score) categories)))
