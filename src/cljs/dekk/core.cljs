(ns dekk.core
  (:require [dekk.views :refer [home-page, board-page]]
            [reagent.session :as session]
            [reagent.core :as reagent]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [re-frame.core :refer [dispatch subscribe]])
  (:require-macros [reagent.ratom :refer [reaction]])
  (:import goog.History))

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/boards" []
                    (session/put! :current-page #'home-page))

(secretary/defroute "/boards/:boardLink" {:as params}
                    (session/put! :current-page #'board-page)
                    (session/put! :params params)


                    #_(let [board (subscribe [:board-by-shortLink (:shortLink params)])]
                      (println "Finding board: " board)
                      (dispatch [:assoc-in-app-state [:board] board])
                      (dispatch [:load-lists board])
                      (dispatch [:load-cards board])
                      ))

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
  (println "INIT!")
  (dispatch [:init-app-state])
  (dispatch [:load-boards])
  (reagent/render-component [current-page] (.getElementById js/document "app")))
