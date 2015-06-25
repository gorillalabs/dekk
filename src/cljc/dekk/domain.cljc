(ns dekk.domain
  #?(:clj
     (:require [miner.tagged :as tag])
     :cljs
     (:require [cljs.reader :refer [register-tag-parser!]])
     ))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Data model
(defprotocol listId
  (list-id [item]))

(defprotocol boardId
  (board-id [item]))

(defrecord Card
  [id
   idList
   idBoard
   name
   desc
   closed]

  listId
  (list-id [_] idList)

  boardId
  (board-id [_] idBoard))


(defrecord Board
  [id]
  boardId
  (board-id [_] id)
  )

(defrecord DekkList
  [id
   name
   idBoard]

  listId
  (list-id [_] id)

  boardId
  (board-id [_] idBoard))




#?(:clj
   (extend-protocol listId
     String
     (list-id [x] x)))


#?(:cljs (do
           (register-tag-parser! "dekk.domain.Card" dekk.domain/map->Card)
           (register-tag-parser! "dekk.domain.DekkList" dekk.domain/map->DekkList)
           (register-tag-parser! "dekk.domain.Board" dekk.domain/map->Board)))

(defn cards-by-list [list]
  (filter (fn [card]
            (= (list-id card)
               (list-id list)))))


