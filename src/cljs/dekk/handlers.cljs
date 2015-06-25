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
    ;; store the value under a given path in the app state
    [app-state [_ path value]]
    (assoc-in app-state path value)))


(register-handler
  :load-boards
  (fn
    ;; Fetch the board and process the response
    [app-state _]
    (println "load-boards")
    (GET "boards"
         {:handler       #(dispatch [:assoc-in-app-state [:boards] (cljs.reader/read-string %1)])
          :error-handler #(dispatch [:log-error %1 "error loading board from server"])})
    app-state))

(register-handler
  :load-lists
  (fn
    ;; Fetch the lists and process the response
    [app-state board]
    (println "load-lists:" app-state board)
    (GET (str "lists/" (domain/board-id board))
         {:handler       #(dispatch [:assoc-in-app-state [:lists] (cljs.reader/read-string %1)])
          :error-handler #(dispatch [:log-error %1 "error loading lists from server"])})
    app-state))

(register-handler
  :load-cards
  (fn
    ;; Fetch all cards and process the response
    [app-state board]
    (GET (str "cards/" (domain/board-id board))
         {:handler       #(dispatch [:assoc-in-app-state [:cards] (cljs.reader/read-string %1)])
          :error-handler #(dispatch [:log-error %1 "error loading cards from server"])})
    app-state))

(register-handler
  :init-app-state
  (fn
    [_ _]
    {:search-input ""
     :order-prop   "name"}))

(register-handler
  :search-input-entered
  (fn [app-state [_ search-input]]
    (assoc-in app-state [:search-input] search-input)))

(register-handler
  :order-prop-changed
  (fn [app-state [_ order-prop]]
    (assoc-in app-state [:order-prop] order-prop)))