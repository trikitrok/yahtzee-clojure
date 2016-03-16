(ns yahtzee.rolls-history)

(defrecord RollsHistory [initial last])

(defn start []
  (->RollsHistory
    (atom {})
    (atom {:d1 nil :d2 nil :d3 nil :d4 nil :d5 nil})))

(defn update-last! [rolled-dice new-rolled-dice]
  (swap! (:last rolled-dice)
         merge
         new-rolled-dice))

(defn the-last [rolled-dice]
  @(:last rolled-dice))

(defn set-last-to-initial [rolled-dice]
  (reset! (:last rolled-dice) @(:initial rolled-dice)))

(defn set-initial-to-last [rolled-dice]
  (reset! (:initial rolled-dice) @(:last rolled-dice)))

(defn no-rolls-yet? [rolled-dice]
  (empty? @(:initial rolled-dice)))