(ns yahtzee.rolls-history)

(defrecord RolledDiceRecording [initial last])

(defn make []
  (->RolledDiceRecording
    (atom {})
    (atom {"D1" nil "D2" nil "D3" nil "D4" nil "D5" nil})))

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