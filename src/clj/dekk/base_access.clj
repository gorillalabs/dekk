(ns dekk.base-access
  (:require [dekk.json-fs :as json-fs]
            [dekk.globals :as globals]
            [dekk.domain :as domain]
            #_[clojure.tools.logging :as log]))

(defn boards [] ;; must read stuff for all boards. Currently we only have one!
  (println #_log/info "Loading boards") ;; TODO: add logging lib.
  (vector (domain/map->Board
    (json-fs/read-from-json globals/board))))

(defn lists [boardId] ;; TODO: must read stuff for a specified boardId
  (println #_log/info "Loading lists for " boardId) ;; TODO: add logging lib.
  (json-fs/read-multiple domain/map->DekkList globals/lists))

(defn cards [boardId] ;; TODO must read stuff for a specified boardId
  (println #_log/info "Loading cards for " boardId) ;; TODO: add logging lib.
  (json-fs/read-multiple domain/map->Card globals/cards))
