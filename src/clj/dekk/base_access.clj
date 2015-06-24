(ns dekk.base-access
  (:require [dekk.json-fs :as json-fs]
            [dekk.globals :as globals]
            [dekk.domain :as domain]))

(defn board []
  (domain/map->Board
    (json-fs/read-from-json globals/board)))

(defn lists []
  (json-fs/read-multiple domain/map->DekkList globals/lists))

(defn cards []
  (json-fs/read-multiple domain/map->Card globals/cards))
