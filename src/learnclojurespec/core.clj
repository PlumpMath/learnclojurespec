(ns learnclojurespec.core
  (:gen-class)
  (:require [clojure.spec :as s]))

;;we’ll take a tour of writing specifications for a clojure function, as well as the power of data generation. First, some inspirational words:
;;1
;;2
;;3
;;4
;;One fish
;;Two fish
;;Red fish
;;Blue fish

;; Back to the parameters. The first two are integers, that's pretty easy, but we want to say more about them. For example, we don't want them to be very big. In fact, we really want to say there is finte notion of fish-numbers and it's a map of integer to string representation.

(def fish-numbers {0 "Zero"
                   1 "One"
                   2 "Two"})

;; Then, we can use s/def to register the spec we are going to define for global reuse. We'll use a namespaced keyword ::fish-number to express that our specification for a valid number is the keys of the fish-numbers map

(s/def ::fish-number (set (keys fish-numbers)))
(s/def ::color #{"Red" "Blue" "Dun"}) 

;; Specifying the sequences of the values 

(s/def ::first-line (s/cat :n1 ::fish-number :n2 ::fish-number :c1 ::color :c2 ::color))

;; Specifying the second number should be one bigger than the first number.
(defn one-bigger? [{:keys [n1 n2]}]
  (= n2 (inc n1)))

(s/def ::first-line1 (s/and (s/cat :n1 ::fish-number :n2 ::fish-number :c1 ::color :c2 ::color ) one-bigger? #(not= (:c1 %) (:c2 %))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (println (s/valid? ::fish-number 1))
  (println (s/valid? ::fish-number 5))
  (println (s/explain ::fish-number 5))
  (println (s/explain ::first-line [1 2 "Red" "Black"]))
  (println (s/explain ::first-line1 [1 0 "Red" "Dun"]))
)
