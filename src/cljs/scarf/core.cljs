(ns ^:figwheel-always scarf.core
  (:require [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom :include-macros true]
            [cemerick.url :refer [url]]
            [scarf.views.contact :as cview]))
(enable-console-print!)

;; ----------------------------------------------------------------------------
;; Routes
(def path (:path (url js/window.location)))
path

;;;;
(def init-data
  {:foo :bar})

(defmulti read om/dispatch)
(defmethod read :dashboard/items
  [{:keys [state]} k _]
  (let [st @state]
    {:value (into [] (map #(get-in st %)) (get st k))}))

(defmulti mutate om/dispatch)
(defmethod mutate 'dashboard/favorite
  [{:keys [state]} k {:keys [ref]}]
  {:action
   (fn [] (swap! state update-in (conj ref :favorites) inc))})

(def reconciler
  (om/reconciler
    {:state  init-data
     :parser (om/parser {:read read :mutate mutate})}))

(cond
  (= "/kontakt" path) (om/add-root! reconciler cview/contact-view (gdom/getElement "app"))
  (= "/" path) (println "index"))

(defn on-js-reload []

)
