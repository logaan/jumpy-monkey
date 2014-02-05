(ns jumpy-monkey.core
  (:require [om.core :as om :include-macros true]
            [sablono.core :as html :refer-macros [html]]))

(def num-things
  (atom 5))

(defn widget [data]
  (reify
    om/IRender
    (render [this]
      (html [:div
             [:p "Hello world!"]
             [:button {:onClick #(swap! num-things inc)}
              "Longer!"]
             [:ul (for [n (range 1 @num-things)]
                    [:li n])]]))))

(om/root num-things widget (js/document.getElementById "my-app"))
