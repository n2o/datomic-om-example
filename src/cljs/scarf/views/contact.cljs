(ns scarf.views.contact
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom :include-macros true]))

(defui ^:private Contact
  Object
  (render [this]
          (dom/h1 nil "Contact")))
(def contact-view (om/factory Contact))
