(ns issues.core
  (:require [dekk.card-handling :as card-handling]
            [dekk.domain :as domain]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Read API

(defn get-board []
  )

(defn get-lists [boardId]
  )

(defn get-cards [boardId]
  )

(defn get-card [cardId]
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Write API

(defn add-card [card]
  )

(defn update-card [old-card new-card]
  ;; this should be a generic method, to differentiate based upon change: move, comment, ...
  )

(defn alter-board []
    (card-handling/add-card (domain/map->Card {:id 'foo})))


;;;;
;; Utility stuff copied over from https://github.com/boxuk/trello/blob/master/src/trello/util.clj

(defn get-gravatar-image
  "Given a gravatar hash, return the users avatar.
   If it can't be found a placeholder is returned"
  [hash]
  (let [gravatar-hash hash
        base-url "http://www.gravatar.com/avatar"]
    [:img {:src (format "%s/%s?d=mm" base-url gravatar-hash)}]))