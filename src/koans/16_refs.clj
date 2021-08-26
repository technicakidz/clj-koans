(ns koans.16-refs
  (:require [koan-engine.core :refer :all]))

(def the-world (ref "hello"))
(def bizarro-world (ref {}))

(meditations
  "In the beginning, there was a word"
  (= "hello" (deref the-world))
;; (deref ref timeout-ms timeout-val)

 "You can get the word more succinctly, but it's the same"
  (= "hello" @the-world)
;; Assertion failed!
;; You can get the word more succinctly, but it's the same
;; (= __ (clojure.core/deref the-world))

 "You can be the change you wish to see in the world."
  (= "better" (do
          (dosync (ref-set the-world "better"))
          @the-world))
 ;; (ref-set ref val)
;; koan-engine.runner=> (def foo (ref {}))
;; #'koan-engine.runner/foo
;; koan-engine.runner=> (dosync (ref-set foo {:foo "hoge"}))
;; {:foo "hoge"}
;; koan-engine.runner=> foo
;; #object[clojure.lang.Ref 0x1c4a12e5 {:status :ready, :val {:foo "hoge"}}]
;; koan-engine.runner=> @foo
;; {:foo "hoge"}

  "Alter where you need not replace"
  (= "better!!!" (let [exclamator (fn [x] (str x "!"))]
          (dosync
           (alter the-world exclamator)
           (alter the-world exclamator)
           (alter the-world exclamator))
          @the-world))

  "Don't forget to do your work in a transaction!"
  (= 0 (do (dosync (ref-set the-world 0))
           @the-world))

  "Functions passed to alter may depend on the data in the ref"
  (= 20 (do
          (dosync (alter the-world (fn [x] (+ 20 x))))))
  ;; fn と defn を混同した
  ;; defn は名前付きの関数を定義する
  ;; fn は無形関数を定義

  "Two worlds are better than one"
  (= ["Real Jerry" "Bizarro Jerry"]
       (do
         (dosync
          (ref-set the-world {})
          (alter the-world assoc :jerry "Real Jerry")
          (alter bizarro-world assoc :jerry "Bizarro Jerry")
          (map :jerry [@the-world @bizarro-world])))))
