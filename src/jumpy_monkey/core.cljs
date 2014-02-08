(ns jumpy-monkey.core
  (:require [om.core :as om :include-macros true]
            [sablono.core :as html :refer-macros [html]]))

(enable-console-print!)

(defn log [v]
  (js/console.log v)
  v)

(def game-state
  (atom {:monkey {:height 50 :velocity 0}
         :pipes  [[50 200] [250 300] [450 400]]
         :last-update (js/Date.)

         :screen      {:width 480 :height 720}
         :monkey-config {:width 40 :height 30}
         :pipe-config {:width 100 :gap 120 :height 720}
         :gravity     0.005
         :flapv       -5}))

(defn pipe-to-top [{:keys [width gap]} [x y]]
  [:rect {:x x
          :y 0 
          :width width
          :height (- y (/ gap 2)) 
          :fill "green"}])

(defn pipe-to-bottom [{:keys [height width gap]} [x y]]
  [:rect {:x x
          :y (+ y (/ gap 2))
          :width width
          :height (- height y (/ gap 2))
          :fill "green"}])

(defn render-monkey [{:keys [width height]} {y :height}]
  [:rect {:x 20
          :y y
          :width width
          :height height
          :fill "brown"}])

(defn render-pipes [pipe-config pipes]
  (->> pipes
       (take 3)
       (mapcat (juxt (partial pipe-to-top pipe-config)
                     (partial pipe-to-bottom pipe-config)))
       (into '())))

(defn c-game
  [{:keys [monkey pipes screen monkey-config pipe-config] :as game}]
  (reify om/IRender
    (render [this]
      (html
        [:div
         [:svg {:width (:width screen)
                :height (:height screen)
                :style {:border "1px solid black"}}
          (render-monkey monkey-config monkey)
          (render-pipes pipe-config pipes)]]))))

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
