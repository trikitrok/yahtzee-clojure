(ns yahtzee.notifications
  (:require
    [clojure.string :as string]))

(def ^:private titles-by-category
  {:ones "Ones"
   :twos "Twos"
   :threes "Threes"})

(def ^:private nums-by-category
  {:ones 1
   :twos 2
   :threes 3})

(defn- rolled-dice->str [rolled-dice dice]
  (->> dice
       (map #(str (string/capitalize (name %)) ":" (rolled-dice %)))
       (string/join " ")))

(defn notify-final-category-score [category score]
  (println (str (titles-by-category category) ": " score)))

(defn notify-final-score [score]
  (println (str "Final score: " score)))

(defn notify-scores-summary [categories score-by]
  (println "Yahtzee score")
  (doseq [category categories]
    (notify-final-category-score category (score-by category))))

(defn notify-category [category]
  (println (str "Category: " (titles-by-category category))))

(defn notify-dice [rolled-dice dice]
  (println (str "Dice: " (rolled-dice->str rolled-dice dice))))

(defn notify-category-score [category score-by]
  (println (str "Category " (titles-by-category category)
                " score: " (score-by category))))

(defn notify-adding-points-to [category]
  (println (str "Category to add points to: " (nums-by-category category))))

(defn notify-available-categories [categories]
  (->> categories
       (map #(str "[" (nums-by-category %) "] " (titles-by-category %)))
       (cons "Available categories:")
       (string/join "\n")
       (println)))
