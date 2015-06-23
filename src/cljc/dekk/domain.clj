(ns dekk.domain
  (:require [dekk.json-fs :as json-fs]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Data model

(defprotocol listId
  (list-id [item]))

(defrecord Card
  [id
   idList
   idBoard
   name
   desc
   closed])

(defrecord Board
  [id])

(defrecord List
  [id
   name
   idBoard]

  listId
  (list-id [_] id))


(extend-protocol listId
  String
  (list-id [x] x))



(defn board []
  (map->Board
    (json-fs/read-from-json board)))

(defn lists []
  (json-fs/read-multiple map->List lists))

(defn cards []
  (json-fs/read-multiple map->Card cards))
