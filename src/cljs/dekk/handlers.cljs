(ns dekk.handlers
  (:require [ajax.core :refer [GET]]
            [re-frame.core :refer [register-handler, dispatch]]
            [re-frame.middleware :refer [undoable]]))


(register-handler
  :select-board
  (fn
    ;; store info for the specific phone-id in the db
    [app-state board-id]
    (assoc-in app-state [:board-id] board-id)))


(register-handler
  :initialise-db
  (fn
    [_ _]
    {:boards       {"example-board"
                    {:name  "Example Board"
                     :lists {"list-1" {:name  "Backlog"
                                       :cards {"card-1" {:name        "Bier kaufen"
                                                         :description "Ein Sixpack oder zwei"
                                                         :closed      false}
                                               "card-2" {:name "Chips kaufen"}}}
                             "list-2" {:name "To Do"}
                             "list-3" {:name "Doing"}
                             "list-4" {:name "Done"}}}}
     :board-id     "example-board"
     :search-input ""
     :order-prop   "name"}))

(register-handler
  :search-input-entered
  (fn [app-state [_ search-input]]
    (assoc-in app-state [:search-input] search-input)))

(register-handler
  :order-prop-changed
  (fn [app-state [_ order-prop]]
    (assoc-in app-state [:order-prop] order-prop)))