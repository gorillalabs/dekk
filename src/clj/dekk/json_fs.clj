(ns dekk.json-fs
  (:require [dekk.globals :as globals]
            [cheshire.core :as json]
            ))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Ability to read/write files

(defn read-from-json
  ""
  [what]
  (json/parse-stream
    (clojure.java.io/reader (str "./" globals/dataRepo what))
    true))


(defn read-multiple [convert-fn what]
  (mapv convert-fn
        (read-from-json what)))

;; this writes all the cards to foo.json
(defn write-cards [cards]
  (json/generate-stream
    cards
    (clojure.java.io/writer "./testdata/foo.json")
    {:pretty true})
  )
