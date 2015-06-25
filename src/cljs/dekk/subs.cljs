(ns dekk.subs
  (:require [re-frame.core :refer [register-sub, subscribe]]
            [re-frame.utils :refer [log]])
  (:require-macros [reagent.ratom :refer [reaction]]))

(register-sub
  :search-input
  (fn [app-state]
    (reaction (:search-input @app-state))))

(register-sub
  :order-prop
  (fn [app-state]
    (reaction (:order-prop @app-state))))

(register-sub
  :cards
  (fn [app-state [_ board-id list-id]]
    (reaction (filter #(and
                        (= (:idBoard %) board-id)
                        (= (:idList %) list-id))
                      (:cards @app-state)))))

(register-sub
  :lists
  (fn [app-state [_ board-id]]
    (reaction (filter #(= (:idBoard %) board-id)
                      (:lists @app-state)))))

(defn board-by-shortLink [boards shortLink]
  (first (filter #(= (:shortLink %)
              shortLink)
          boards)))

(register-sub
  :board-by-shortLink
  (fn [app-state [_ shortLink]]
    (reaction (board-by-shortLink (:boards @app-state) shortLink))))



