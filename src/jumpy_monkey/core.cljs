(ns jumpy-monkey.core
  (:require [om.core :as om :include-macros true]
            [sablono.core :as html :refer-macros [html]]))

(enable-console-print!)

(defn log [v]
  (js/console.log (str v))
  v)

(def game-state
  (atom {:monkey {:height 50 :velocity 0}
         :pipes [[0 20] [1 40] [2 60] [3 80] [4 100] [5 120] [6 140] [7 160]]}))

(defn pipe-to-top [[x y]]
  (log [:rect {:x (+ (* x 60) 50)
               :y 0
               :width 20
               :height (- y 10)
               :fill "green"}]))

(defn render-monkey [{:keys [height]}]
  [:rect {:x 20 :y height :width 15 :height 10 :fill "brown"}])

(defn render-pipes [pipes]
  (into '() (map pipe-to-top (take 3 pipes))))

(defn c-game [{:keys [monkey pipes]}]
  (reify om/IRender
    (render [this]
      (html [:svg {:width 200 :height 200 :style {:border "1px solid black"}}
             (render-monkey monkey)
             (render-pipes pipes)]))))

(om/root game-state c-game (js/document.getElementById "my-app"))
