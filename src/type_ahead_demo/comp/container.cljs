
(ns type-ahead-demo.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo.alias :refer [create-comp div span input]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]
            [type-ahead-demo.style.widget :as widget]
            [respo-ui.style :as ui]
            [type-ahead-demo.comp.complete :refer [comp-complete]]))

(defn render []
  (fn [state mutate!]
    (div {:style (merge widget/global)} (comp-complete))))

(def comp-container (create-comp :container render))
