(ns issues.core
  (:require [cheshire.core :as json]
            [clj-jgit.porcelain :as git]))


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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Read API

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

(defn repo []
  (git/load-repo "testdata/"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Ability to read/write files


;; this does read the first card from trello, populating all fields given in json.
(defn read-cards []
  (mapv map->Card
        (json/parse-stream
          (clojure.java.io/reader "./testdata/cards.json")
          true
          ))
  )

;; this writes all the cards to foo.json
(defn write-cards [cards]
  (json/generate-stream
    cards
    (clojure.java.io/writer "./testdata/foo.json")
    {:pretty true})
  )

(defn add-card [cards card]
  (write-cards
    (conj cards card))
  (git/git-add (repo) "/foo.json")
  (git/git-commit (repo) (str "Added card " (:id card) ", " (:name card)))
  )






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