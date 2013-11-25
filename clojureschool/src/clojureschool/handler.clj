(ns clojureschool.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.core :refer :all]
            [hiccup.page :refer [html5 include-css]]))

(def index-view
  (html5
    [:head [:title "Hiccup website"]]
    [:body [:h1 "Title goes here"]
      [:ul (for [x (range 10)]
             [:li (str "Item " x)])]]))

;; Update the handler to return a web page at:
;; http://localhost:3000/list/[list_id]?n=21
;; That returns a page with “List: [list id]” in the title and a list of integers from 1 to n.
(defn list-view [list-id n]
  (html5
    [:head [:title "Hiccup website"]]
    [:body [:h1 list-id]
      [:ul (for [x (range 1 (inc n))]
             [:li x])]]))

;; Add a visitor counter to the bottom of your web page that increments for each visit
(def counter (atom 0))

(defn counter-view [counter]
  (html5
    [:head [:title "Counter"]]
    [:body [:h1 "Visitor counter"]
      [:p (str "You are visitor " counter)]]))

;; Add a CSS stylesheet to your hiccup template and change the font color (hint: include-css)
(def colour-view
  (html5
    [:head 
      [:title "Change colour"]
      (include-css "css/main.css")]
    [:body [:h1 "h1"]]))

;; Write some middleware that returns a 401 status code unless ?token=123 is supplied in the URL
(defn ensure-token [handler]
  (fn [request]
    (if (= "123" (get (:query-params request) "token"))
      (handler request)
      {:status 401})))

(defroutes app-routes
  (GET "/" [] index-view)
  (GET "/colour" [] colour-view)
  (GET "/counter" [] (counter-view (swap! counter inc)))
  (GET "/list/:list-id" [list-id n] (list-view list-id (read-string n)))
  (GET "/ping" [] "Pong")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site (ensure-token app-routes)))
