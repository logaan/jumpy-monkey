(defproject flappy-bird "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2156"]
                 [om "0.3.5"]]
  :plugins [[lein-cljsbuild "1.0.2"]]
  :cljsbuild {:builds {:test {:source-paths ["src" "test"]
                              :compiler {:output-to "resources/js/flappy-bird.js"
                                         :output-dir "resources/js"
                                         :optimizations :advanced
                                         :source-map "resources/js/flappy-bird.js.map"}}}})

