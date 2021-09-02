(ns koans.17-atoms
  (:require [koan-engine.core :refer :all]))

(def atomic-clock (atom 0))

(meditations
  "Atoms are like refs"
  (= 0 @atomic-clock)

  "You can change at the swap meet"
  (= 1 (do
          (swap! atomic-clock inc)
          @atomic-clock))
;; user=> (def vanibe (atom ()))
;; #'user/vanibe
;; user=> (swap! vanibe conj :rena)
;; (:rena)
;; user=> (swap! vanibe conj :risa)
;; (:risa :rena)
;; user=> (deref vanibe)
;; (:risa :rena)

  "Keep taxes out of this: swapping requires no transaction"
  (= 5 (do
         (swap! atomic-clock (partial + 4))
         @atomic-clock))

  "Any number of arguments might happen during a swap"
  (= 20 (do
          (swap! atomic-clock + 1 2 3 4 5)
        ;;   (println atomic-clock)
          @atomic-clock))

  
  "Atomic atoms are atomic"
  (= 20 (do
          (compare-and-set! atomic-clock 100 :fin)
          @atomic-clock))
  ;; (compare-and-set! atom oldval newval)
;; user=> (def a (atom 0))
;; #'user/a
;; user=> (compare-and-set! a 1 2)
;; false
;; user=> @a
;; 0
;; user=> (compare-and-set! a 0 100)
;; true
;; user=> @a
;; 100

  "When your expectations are aligned with reality, things proceed that way"
  (= :fin (do
            (compare-and-set! atomic-clock 20 :fin)
            @atomic-clock)))
