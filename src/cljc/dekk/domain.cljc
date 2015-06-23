(ns dekk.domain
  #?(:clj (:require [miner.tagged :as tag])))

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

#?(:clj (defmethod print-method Card [this w]
  (tag/pr-tagged-record-on this w)))

(defrecord Board
  [id])

#?(:clj (defmethod print-method Board [this w]
  (tag/pr-tagged-record-on this w)))


(defrecord List
  [id
   name
   idBoard]

  listId
  (list-id [_] id))

#?(:clj (defmethod print-method List [this w]
  (tag/pr-tagged-record-on this w)))



(extend-protocol listId
  String
  (list-id [x] x))




(defn cards-by-list [list]
  (filter (fn [card]
            (= (list-id card)
               (list-id list)))))