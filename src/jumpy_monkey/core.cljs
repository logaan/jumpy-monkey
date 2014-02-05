(ns jumpy-monkey.core)

(defn say [word num]
  (let [someval (+ 1 num)]
    (js/console.log word "from main" someval)))

(say "hello" 2)
(say "goodbye" 7)

