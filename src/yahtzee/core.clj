(ns yahtzee.core
  (:require [yahtzee.notifications :as notifications]))

(def dice ["D1" "D2" "D3" "D4" "D5"])

(def rolled-dice
  (atom {"D1" nil "D2" nil "D3" nil "D4" nil "D5" nil}))

(def initial-rolled-dice
  (atom {}))

(def scores-by-category
  (atom {}))

(defn roll-dice [roll dice]
  (swap! rolled-dice
         merge
         (into {} (for [d dice] [d (roll)]))))

(defn extract-dice [input-str]
  (clojure.string/split input-str #" "))

(defn score [value rolled-dice]
  (->> rolled-dice
       (group-by #(val %))
       (filter #(= value (first %)))
       (first)
       (second)
       (count)))

(def score-fn-by-category
  {:ones (partial score 1)
   :twos (partial score 2)
   :threes (partial score 3)})

(defn score-category [category rolled-dice]
  ((score-fn-by-category category) rolled-dice))

(defn initial-roll-dice [roll-dice]
  (if (empty? @initial-rolled-dice)
    (do (roll-dice dice)
        (reset! initial-rolled-dice @rolled-dice))
    (reset! rolled-dice @initial-rolled-dice)))

(defn store-score [category score]
  (swap! scores-by-category assoc category score))

(defn ask-which-dice-to-rerun [user-messaging-fn num-reruns]
  (user-messaging-fn (str "[" num-reruns "] Dice to re-run:")))

(defn do-reruns [user-messaging-fn notify-fn roll-dice read-dice-to-rerun-input]
  (doseq [num-reruns [1 2]]
    (ask-which-dice-to-rerun user-messaging-fn num-reruns)
    (roll-dice (extract-dice (read-dice-to-rerun-input)))
    (notifications/notify-dice notify-fn @rolled-dice dice)))

(defn play-category [user-messaging-fn notify-fn roll-dice read-dice-to-rerun-input category]
  (notifications/notify-category notify-fn category)
  (initial-roll-dice roll-dice)
  (notifications/notify-dice notify-fn @rolled-dice dice)
  (do-reruns user-messaging-fn notify-fn roll-dice read-dice-to-rerun-input)
  (store-score category (score-category category @rolled-dice))
  (notifications/notify-category-score notify-fn @scores-by-category category))

(defn yahtzee [roll-dice read-dice-to-rerun-input notify-fn user-messaging-fn]
  (let [categories [:ones :twos :threes]]
    (doseq [category categories]
      (play-category user-messaging-fn notify-fn roll-dice read-dice-to-rerun-input category))
    (notifications/notify-scores-summary notify-fn categories @scores-by-category)))

(defn make-yahtzee [roll read-dice-to-rerun-input notify-fn user-messaging-fn]
  (partial yahtzee (partial roll-dice roll) read-dice-to-rerun-input notify-fn user-messaging-fn))

(defn -main [& args]
  (let [yahtzee (make-yahtzee #(inc (rand-int 6)) read-line println println)]
    (yahtzee)))