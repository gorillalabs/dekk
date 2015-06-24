(ns dekk.handlers
  (:require [ajax.core :refer [GET]]
            [re-frame.core :refer [register-handler, dispatch]]
            [re-frame.utils :as util]
            [re-frame.middleware :refer [undoable]]
            [cljs.reader :refer [read-string, register-tag-parser!]]
            [dekk.domain :as domain]))

(register-handler
  :log-error
  (fn
    ;; log a bad response with an error message
    [app-state [_ response msg]]
    (util/error msg " - response: " + response)
    app-state))

(register-handler
  :assoc-in-app-state
  (fn
    ;; store the response under a given path in the app state
    [app-state [_ response path]]
    (assoc-in app-state path response)))

(register-handler
  :load-board
  (fn
    ;; Fetch the board and process the response
    [app-state _]
    (GET "board"
         {:handler         #(dispatch [:assoc-in-app-state %1 [:board]])
          :error-handler   #(dispatch [:log-error %1 "error loading board from server"])
          :response-format :edn})
    app-state))

(register-handler
  :load-cards
  (fn
    ;; Fetch the lists and process the response
    [app-state _]
    (GET "lists"
         {:handler         #(dispatch [:assoc-in-app-state %1 [:lists]])
          :error-handler   #(dispatch [:log-error %1 "error loading lists from server"])
          :response-format :edn})
    app-state))

(register-handler
  :load-cards
  (fn
    ;; Fetch all cards and process the response
    [app-state _]
    (GET "cards"
         {:handler         #(dispatch [:assoc-in-app-state %1 [:cards]])
          :error-handler   #(dispatch [:log-error %1 "error loading cards from server"])
          :response-format :edn})
    app-state))

(register-handler
  :init-app-state
  (fn
    [_ _]
    ;; TODO remove :boards substructure
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