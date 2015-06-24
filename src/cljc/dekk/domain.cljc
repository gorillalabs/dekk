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

(defrecord Card
  [id
   idList
   idBoard
   name
   desc
   closed]

  listId
  (list-id [_] idList))


(defrecord Board
  [id])

(defrecord DekkList
  [id
   name
   idBoard]

  listId
  (list-id [_] id))




#?(:clj
   (extend-protocol listId
     String
     (list-id [x] x)))


#?(:cljs
   (register-tag-parser! "dekk.domain.Card" dekk.domain/map->Card))

(defn cards-by-list [list]
  (filter (fn [card]
            (= (list-id card)
               (list-id list)))))

