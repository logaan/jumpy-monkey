(defproject jumpy-monkey "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2156"]
                 [om "0.3.5"]
                 [sablono "0.2.6"]]
  :plugins [[lein-cljsbuild "1.0.2"]]
  :cljsbuild {:builds {:prod {:source-paths ["src"]
                              :compiler {:output-to "resources/prod/js/main.js"
                                         :output-dir "resources/prod/js"
                                         :optimizations :advanced
                                         ; :preamble ["resources/react/react.min.js"]
                                         :externs ["resources/react/externs/react.js"]}}
                       :test {:source-paths ["src" "test"]
                              :compiler {:output-to "resources/test/js/main.js"
                                         :output-dir "resources/test/js"
                                         :optimizations :none
                                         :source-map "resources/test/js/main.js.map"}}}})

