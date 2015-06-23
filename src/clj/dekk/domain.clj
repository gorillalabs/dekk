(ns dekk.domain
  (:require [dekk.json-fs :as json-fs]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Data model

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
   idBoard])




(defn board []
  (map->Board
    (json-fs/read-from-json board)))

(defn lists []
  (json-fs/read-multiple map->List lists))

(defn cards []
  (json-fs/read-multiple map->Card cards))
