(ns dekk.card-handling
  (:require [clj-jgit.porcelain :as git]
            [dekk.globals :as globals]
            [dekk.json-fs :as json-fs]))

(defn repo []
  (git/load-repo globals/dataRepo))

(defn add-card [cards card]
  (json-fs/write-cards
    (conj cards card))
  (git/git-add (repo) (str "/" globals/cards))
  (git/git-commit (repo) (str "Added card " (:id card) ", " (:name card))))