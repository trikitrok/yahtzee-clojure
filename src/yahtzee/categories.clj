(ns yahtzee.categories)

(def categories [:ones :twos :threes])

(def ^:private categories-by-input
  {"1" :ones
   "2" :twos
   "3" :threes})

(defn- list-of [selected-categories]
  @(:list selected-categories))

(defn category-to-add-input-to [input-category-num]
  (categories-by-input input-category-num))

(defn select-category! [selected-categories c]
  (swap! (:list selected-categories) conj c))

(defn last-selected [selected-categories]
  (last (list-of selected-categories)))

(defn available [selected-categories categories]
  (filter #(not (contains? (set (list-of selected-categories)) %)) categories))

(defrecord SelectedCategories [list])

(defn start []
  (->SelectedCategories (atom [])))
