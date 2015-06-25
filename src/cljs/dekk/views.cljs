(ns dekk.views
  (:require dekk.subs
            dekk.handlers
            [re-frame.core :refer [subscribe, dispatch]]
            [re-com.core :refer [button, box, h-box, v-box, single-dropdown, input-text]]
            [re-frame.utils :refer [log]]
            [dekk.domain :as domain])
  (:require-macros [reagent.ratom :refer [reaction]]))

(defn search-component
  "component for the search input"
  []
  [input-text
   :model ""
   :width "150px"
   :placeholder "Search"
   :on-change #(dispatch [:search-input-entered %])
   :change-on-blur? false])

(defn order-by-component
  "component to define the sort order"
  []
  (let [order-prop (subscribe [:order-prop])]
    [single-dropdown
     :choices [{:id "name" :label "Sort by name"}
               {:id "age" :label "Newest first"}]
     :model @order-prop
     :placeholder "Sort order"
     :width "150px"
     :on-change #(dispatch [:order-prop-changed %])]))

(defn home-page
  "defines the home page which will be the phone list component"
  []
  [h-box :children
   [[v-box :gap "10px" :children
     [[search-component]
      [order-by-component]]]
    [v-box :children
     [
      ;; TODO list of boards
      ]]]])

(defn cards-box
  "box to display some cards"
  [cards]
  [v-box
   :style {:border "1px solid lightgrey"}
   :gap "10px"
   :children
   [(for [card @cards]
      ^{:key (:id card)}
      [v-box
       :style {:border "1px solid lightgrey"}
       :attr {:draggable true}
       :children
       [[box :child (:name card "")]
        [box :child (:desc card "")]]])]])

(defn lists-box
  "box to display some lists"
  [lists]
  [v-box
   :style {:border "1px solid lightgrey"}
   :children
   [[box :child "Lists"]
    [h-box
     :style {:border "1px solid lightgrey"}
     :gap "10px"
     :children
     [(for [list @lists]
        (let [board-id (:idBoard list)
              list-id (:id list)
              cards (subscribe [:cards board-id list-id])]
          ^{:key list-id}
          [v-box
           :style {:border "1px solid lightgrey"}
           :children
           [[box :child (:name list "")]
            [cards-box cards]]]))]]]])

(defn board-page
  "top level component for the phone page"
  [{board-link :boardLink}]
  (let [board (subscribe [:board-by-shortLink board-link])]
    (println "board-page for:" board)
    (when board
    (let [
        board-name (reaction (:name @board))
        lists (subscribe [:lists (domain/board-id @board)])]

    [v-box :children
     [[v-box :children [[:span "Board"]
                        [:span @board-name]]]
      [lists-box lists]
      ]]))))