(ns jumpy-monkey.core
  (:require [om.core :as om :include-macros true]
            [sablono.core :as html :refer-macros [html]]))

(def number-of-things-in-list
  (atom 5))

(defn widget [data]
  (reify
    om/IRender
    (render [this]
      (html [:div
             [:p "Hello world!"]
             [:button {:onClick #(swap! number-of-things-in-list inc)}
              "Longer!"]
             [:ul (for [n (range 1 @number-of-things-in-list)]
                    [:li n])]]))))

(om/root number-of-things-in-list
         widget
         (js/document.getElementById "my-app"))
