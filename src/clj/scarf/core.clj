(ns scarf.core
  (:require [ring.util.response :refer [file-response]]
            [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [defroutes GET PUT POST]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [clojure.edn :as edn]
            [datomic.api :as d]))

#_(def uri "datomic:free://localhost:4334/scarf")
#_(def conn (d/connect uri))

(defn generate-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/edn"}
   :body (pr-str data)})

(defn read-inputstream-edn [input]
  (edn/read
   {:eof nil}
   (java.io.PushbackReader.
    (java.io.InputStreamReader. input "UTF-8"))))

(defn parse-edn-body [handler]
  (fn [request]
    (handler (if-let [body (:body request)]
               (assoc request
                 "edn-body" (read-inputstream-edn body))
               request))))

(defn index []
  (file-response "public/html/index.html" {:root "resources"}))

(defroutes routes
  (GET "/" [] (index))
  (GET "/foo" [] (index))
  (route/files "/" {:root "resources/public/assets"}))

(def handler
  (-> routes
      parse-edn-body))
