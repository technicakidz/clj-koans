(ns koans.15-destructuring
  (:require [koan-engine.core :refer :all]))

(def test-address
  {:street-address "123 Test Lane"
   :city "Testerville"
   :state "TX"})

(meditations
  "Destructuring is an arbiter: it breaks up arguments"
  (= ":bar:foo" ((fn [[a b]] (str b a))
         [:foo :bar]))

  "Whether in function definitions"
  (= (str "An Oxford comma list of apples, "
          "oranges, "
          "and pears.")
     ((fn [[a b c]] (str "An Oxford comma list of " a ", " b ", " "and " c "."))
      ["apples" "oranges" "pears"]))

  "Or in let expressions"
  (= "Rich Hickey aka The Clojurer aka Go Time aka Lambda Guru"
     (let [[first-name last-name & aliases]
           (list "Rich" "Hickey" "The Clojurer" "Go Time" "Lambda Guru")]
       (apply str (interpose " " (apply list first-name last-name (interleave (repeat "aka") aliases))))))
       ;; interleave (interleave c1 c2 & colls)
       ;; koan-engine.runner=> (interleave [:a :b :c] [10 20])
       ;; (:a 10 :b 20)

  "You can regain the full argument if you like arguing"
  (= {:original-parts ["Stephen" "Hawking"] :named-parts {:first "Stephen" :last "Hawking"}}
     (let [[first-name last-name :as full-name] ["Stephen" "Hawking"]]
       __))

  "Break up maps by key"
  (= "123 Test Lane, Testerville, TX"
     (let [{street-address :street-address, city :city, state :state} test-address]
       __))

  "Or more succinctly"
  (= "123 Test Lane, Testerville, TX"
     (let [{:keys [street-address __ __]} test-address]
       __))

  "All together now!"
  (= "Test Testerson, 123 Test Lane, Testerville, TX"
     (___ ["Test" "Testerson"] test-address)))
