(defproject dekk "0.1.0-SNAPSHOT"
            :description ""
            :url "http://example.com/FIXME"
            :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}

            :source-paths ["src/clj" "src/cljc" "src/cljs"]

            :dependencies [[org.clojure/clojure "1.7.0-RC2"]
                           [cljsjs/react "0.13.3-0"]
                           [reagent "0.5.0"]
                           [reagent-utils "0.1.5"]
                           [secretary "1.2.3"]
                           [org.clojure/clojurescript "0.0-3308" :scope "provided"]
                           [ring "1.4.0-RC1"]
                           [ring/ring-defaults "0.1.5"]
                           [prone "0.8.2"]
                           [compojure "1.3.4"]
                           [selmer "0.8.2"]
                           [environ "1.0.0"]
                           [re-frame "0.4.1"]
                           [re-com "0.5.4"]
                           [cljs-ajax "0.3.13"]
                           [cheshire "5.5.0"]                         ;; json support
                           [clj-jgit "0.8.8"]
                           [com.velisco/tagged "0.3.0"]]

            :plugins [
                      [lein-cljsbuild "1.0.4"]
                      [lein-environ "1.0.0"]
                      [lein-ring "0.9.1"]
                      [lein-asset-minifier "0.2.2"]
                      [lein-ancient "0.6.7"]]

            :ring {:handler      dekk.handler/app
                   :uberwar-name "dekk.war"}

            :min-lein-version "2.5.0"

            :uberjar-name "dekk.jar"

            :main dekk.server

            :clean-targets ^{:protect false} ["resources/public/js"]

            :minify-assets {:assets {"resources/public/css/site.min.css"
                                     "resources/public/css/site.css"}}

            :cljsbuild {:builds {:app {:source-paths ["src/cljs" "src/cljc"]
                                       :compiler     {:output-to     "resources/public/js/app.js"
                                                      :output-dir    "resources/public/js/out"
                                                      :asset-path    "js/out"
                                                      :optimizations :none
                                                      :pretty-print  true}}}}

            :profiles {:dev        {:repl-options {:init-ns          dekk.handler
                                                   :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

                                    :dependencies [[ring-mock "0.1.5"]
                                                   [ring/ring-devel "1.3.2"]
                                                   [leiningen "2.5.1"]
                                                   [figwheel "0.3.3"]
                                                   [weasel "0.7.0"]
                                                   [com.cemerick/piggieback "0.2.1"]
                                                   [org.clojure/tools.nrepl "0.2.10"]
                                                   [pjstadig/humane-test-output "0.7.0"]]

                                    :source-paths ["env/dev/clj"]
                                    :plugins      [[lein-figwheel "0.3.3"]]

                                    :injections   [(require 'pjstadig.humane-test-output)
                                                   (pjstadig.humane-test-output/activate!)]

                                    :figwheel     {:http-server-root "public"
                                                   :server-port      3449
                                                   :css-dirs         ["resources/public/css"]
                                                   :ring-handler     dekk.handler/app}

                                    :env          {:dev? true}

                                    :cljsbuild    {:builds {:app {:source-paths ["env/dev/cljs"]
                                                                  :compiler     {:main       "dekk.dev"
                                                                                 :source-map true}}}}}

                       :uberjar    {:hooks       [leiningen.cljsbuild minify-assets.plugin/hooks]
                                    :env         {:production true}
                                    :aot         :all
                                    :omit-source true
                                    :cljsbuild   {:jar    true
                                                  :builds {:app {:source-paths ["env/prod/cljs"]
                                                                 :compiler     {:optimizations :advanced
                                                                                :pretty-print  false}}}}}

                       :production {:ring      {:open-browser? false
                                                :stacktraces?  false
                                                :auto-reload?  false}
                                    :cljsbuild {:builds {:app {:compiler {:main "dekk.prod"}}}}}})
