(ns dekk.domain
  (:require [miner.tagged :as tag]))

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

(defmethod print-method Card [this w]
  (tag/pr-tagged-record-on this w))

(defrecord Board
  [id])

(defmethod print-method Board [this w]
  (tag/pr-tagged-record-on this w))


(defrecord List
  [id
   name
   idBoard]

  listId
  (list-id [_] id))

(defmethod print-method List [this w]
  (tag/pr-tagged-record-on this w))



(extend-protocol listId
  String
  (list-id [x] x))



