(defproject yahtzee "0.0.1-SNAPSHOT"
  :description "Yahtzee Kata"
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :profiles {:dev {:dependencies [[midje "1.7.0"]]}
             ;; You can add dependencies that apply to `lein midje` below.
             ;; An example would be changing the logging destination for test runs.
             :midje {}}
  :main yahtzee.core)
