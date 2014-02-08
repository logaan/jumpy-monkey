(ns jumpy-monkey.core
  (:require [om.core :as om :include-macros true]
            [sablono.core :as html :refer-macros [html]]))

(enable-console-print!)

(defn log [v]
  (js/console.log (str v))
  v)

(def config
  {:game {:width 200
          :height 200}
   :pipe {:width 20
          :gap 20}})

(def game-state
  (atom {:monkey {:height 50 :velocity 0}
         :pipes [[50 20] [110 40] [170 60]]}))

(defn pipe-to-top [[x y]]
  [:rect {:x x
          :y 0 
          :width 20 
          :height (- y 10) 
          :fill "green"}])

(defn pipe-to-bottom [[x y]]
  [:rect {:x x
          :y (+ y 10)
          :width 20
          :height (- 200 y 10)
          :fill "green"}])

(defn render-monkey [{:keys [height]}]
  [:rect {:x 20
          :y height
          :width 15
          :height 10
          :fill "brown"}])

(defn render-pipes [pipes]
  (->> pipes
       (take 3)
       (mapcat (juxt pipe-to-top pipe-to-bottom))
       (into '())))

(defn render-debug [game]
  [:p [:strong "config"]
   [:pre (str config)]  
   [:strong "game"]
   [:pre (str (into {} game))]])

(defn c-game [{:keys [monkey pipes] :as game}]
  (reify om/IRender
    (render [this]
      (let [width  (:width  (:game config))
            height (:height (:game config))]
        (html
        [:div
         [:svg {:width width :height height :style {:border "1px solid black"}}
             (render-monkey monkey)
             (render-pipes pipes)]
         (render-debug game)])))))

(om/root game-state c-game (js/document.getElementById "my-app"))

