(ns dekk.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [selmer.parser :refer [render-file]]
            [prone.middleware :refer [wrap-exceptions]]
            [environ.core :refer [env]]
            [dekk.base-access :as base-access]
            [clojure.edn :as edn]))

(defroutes routes
           (GET "/" [] (render-file "templates/index.html" {:dev (env :dev?)}))
           (GET "/cards/:boardId" [boardId]
             (pr-str (base-access/cards boardId)))
           (GET "/lists/:boardId" [boardId]
             (pr-str (base-access/lists boardId)))
           (GET "/boards" []
             (pr-str (base-access/boards)))
           (resources "/")
           (not-found "Not Found"))

(def app
  (let [handler (wrap-defaults routes site-defaults)]
    (if (env :dev?) (wrap-exceptions handler) handler)))
