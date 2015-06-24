(ns dekk.core
  (:require [dekk.views :refer [home-page, board-page]]
            [reagent.session :as session]
            [reagent.core :as reagent]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [re-frame.core :refer [dispatch]])
  (:require-macros [reagent.ratom :refer [reaction]])
  (:import goog.History))

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/boards" []
                    (session/put! :current-page #'home-page))

(secretary/defroute "/boards/:board-id" {:as params}
                    (session/put! :current-page #'board-page)
                    (session/put! :params params)
                    ;;(dispatch [:select-board (:board-id params)]) TODO implement when switching boards will be possible
                    )

(defn redirect-to
  [resource]
  (secretary/dispatch! resource)
  (.setToken (History.) resource))

(secretary/defroute "*" []
                    (redirect-to "/boards"))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      EventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app

(defn current-page []
  [(session/get :current-page) (session/get :params)])

(defn init! []
  (hook-browser-navigation!)
  (dispatch [:init-app-state])
  (dispatch [:load-boards])
  (dispatch [:load-lists])
  (dispatch [:load-cards])
  (reagent/render-component [current-page] (.getElementById js/document "app")))
