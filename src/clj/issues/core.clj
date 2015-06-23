(ns issues.core
  (:require [cheshire.core :as json]
            [clj-jgit.porcelain :as git]))


(def dataRepo "dataRepo/")
(def board "board.json")
(def cards "cards.json")
(def lists "lists.json")

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

(defn repo []
  (git/load-repo dataRepo))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Ability to read/write files

(defn read-from-json [what]
  (json/parse-stream
    (clojure.java.io/reader (str "./" dataRepo what))
    true))


(defn read-multiple [convert-fn what]
  (mapv convert-fn
        (read-from-json what)))



(defn read-board []
  (map->Board
    (read-from-json board)))

(defn read-lists []
  (read-multiple map->List lists))

(defn read-cards []
  (read-multiple map->Card cards))






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