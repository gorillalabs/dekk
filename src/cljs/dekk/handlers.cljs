(ns dekk.handlers
  (:require [ajax.core :refer [GET]]
            [re-frame.core :refer [register-handler, dispatch]]
            [re-frame.utils :as util]
            [re-frame.middleware :refer [undoable]]
            [cljs.reader :refer [read-string, register-tag-parser!]]
            [dekk.domain :refer [map->Card]]))


(register-handler
  :select-board
  (fn
    ;; store info for the specific phone-id in the db
    [app-state board-id]
    (assoc-in app-state [:board-id] board-id)))

(register-handler
  :process-cards-response
  (fn
    ;; store the response of fetching the phones list in the phones attribute of the db
    [app-state [_ response]]
    (register-tag-parser! "dekk.domain/Card" map->Card)
    (util/log "response" response)
    (assoc-in app-state [:cards]
              (read-string {:body response}))))

(register-handler
  :process-cards-bad-response
  (fn
    ;; log a bad response fetching the phones list
    [app-state [_ response]]
    (println "Error getting phone list response")
    (println response)
    app-state))

(register-handler
  :load-cards
  (fn
    ;; Fetch the list of phones and process the response
    [app-state _]
    (util/log "load-cards")
    (GET "cards"
         {:handler         #(dispatch [:process-cards-response %1])
          :error-handler   #(dispatch [:process-cards-bad-response %1])
          :response-format :json
          :keywords?       true})
    (util/log "App-state after load-cards: " app-state)
    app-state))

(register-handler
  :initialise-db
  ; TODO call load-cards instead
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