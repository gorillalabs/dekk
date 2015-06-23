(ns issues.core
  (:require [cheshire.core :as json]
            [clj-jgit.porcelain :as git]))





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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Git interfacing











(defn alter-board []
    (add-card (read-cards) (map->Card {:id 'foo})))




;;;;
;; Utility stuff copied over from https://github.com/boxuk/trello/blob/master/src/trello/util.clj

(defn get-gravatar-image
  "Given a gravatar hash, return the users avatar.
   If it can't be found a placeholder is returned"
  [hash]
  (let [gravatar-hash hash
        base-url "http://www.gravatar.com/avatar"]
    [:img {:src (format "%s/%s?d=mm" base-url gravatar-hash)}]))