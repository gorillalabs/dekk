(ns dekk.card-handling
  (:require [clj-jgit.porcelain :as git]
            [dekk.globals :as globals]
            [dekk.json-fs :as json-fs]
            [dekk.domain :as domain]
            [dekk.base-access :as base-access]))

(defn repo []
  (git/load-repo globals/dataRepo))

(defn add-card [card]
  (assoc card :idList (list-id list))
  (json-fs/write-cards
    (conj (base-access/cards) card))
  (git/git-add (repo) (str "/" globals/cards))
  (git/git-commit (repo) (str "Added card " (:id card) ", " (:name card))))
