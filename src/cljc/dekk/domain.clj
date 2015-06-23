(ns dekk.domain)

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



