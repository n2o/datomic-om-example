(ns ^:figwheel-always scarf.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :as async :refer [put! chan alts!]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)
(println "Hello world!")

(def app-state
  (atom {:classes []}))


