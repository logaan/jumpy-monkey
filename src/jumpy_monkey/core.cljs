(ns jumpy-monkey.core
  (:require [om.core :as om :include-macros true]
            [sablono.core :as html :refer-macros [html]]))

(enable-console-print!)

(defn log [v]
  (js/console.log v)
  v)

(def game-state
  (atom {:monkey {:height 50 :velocity 0}
         :pipes  [[50 20] [110 40] [170 60]]
         :last-update (js/Date.)

         :screen {:width 200 :height 200}
         :pipe   {:width 20 :gap 20}
         :gravity 0.005
         :flapv   -5}))

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

(defn c-game [{:keys [monkey pipes screen] :as game}]
  (reify om/IRender
    (render [this]
      (html
        [:div
         [:svg {:width (:width screen)
                :height (:height screen)
                :style {:border "1px solid black"}}
          (render-monkey monkey)
          (render-pipes pipes)]]))))

(om/root game-state c-game (js/document.getElementById "my-app"))

(defn tick [{{:keys [height velocity]} :monkey
             :keys [last-update gravity] :as game}]
  (let [new-update   (js/Date.)
        time-diff    (- new-update last-update)
        grav-effect  (/ (* gravity 1000) time-diff)
        new-velocity (+ velocity grav-effect)
        new-height   (+ height velocity)
        new-monkey   {:velocity new-velocity :height new-height}]
    (assoc game :monkey new-monkey
                :last-update new-update)))

(js/setInterval
  #(swap! game-state tick)
  16)

(let [my-app (js/document.getElementById "my-app")]
  (aset my-app "tabIndex" 0)
  (.focus my-app)
  (aset my-app "onkeypress"
        (fn [event]
          (if (= 119 (aget event "charCode"))
           (swap! game-state assoc-in [:monkey :velocity]
                  (:flapv @game-state))))))
