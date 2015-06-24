(ns dekk.subs
  (:require [re-frame.core :refer [register-sub, subscribe]])
  (:require-macros [reagent.ratom :refer [reaction]]))

(register-sub
  :search-input
  (fn [db]
    (reaction (:search-input @db))))

(register-sub
  :order-prop
  (fn [db]
    (reaction (:order-prop @db))))

(register-sub
  :boards
  (fn [db]
    (reaction (:boards @db))))

(register-sub
  :lists
  (fn [db]
    (reaction (:boards @db))))

(register-sub
  :board
  (fn [db [_ board-id]]
    (reaction (get-in @db [:boards board-id]))))


