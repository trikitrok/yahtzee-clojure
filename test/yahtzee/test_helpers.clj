(ns yahtzee.test-helpers
  (:require
    [yahtzee.games-factory :as games-factory]))

(defn make-list-generator [ls]
  (let [atom-ls (atom ls)]
    (fn []
      (let [next-val (first @atom-ls)]
        (swap! atom-ls rest)
        next-val))))

(defn- make-yahtze [{:keys [for-round using-as-rands and-user-input]}]
  (let [stubbed-roll (make-list-generator using-as-rands)
        stubbed-read-user-input (make-list-generator and-user-input)]
    (games-factory/make for-round stubbed-roll stubbed-read-user-input)))


(defn output-lines-of-running-yahtzee [& {:as params}]
  (let [yahtzee (make-yahtze params)
        cout (with-out-str (yahtzee))]
    (clojure.string/split
      cout
      #"\n")))
