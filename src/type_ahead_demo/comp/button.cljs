
(ns type-ahead-demo.comp.button
  (:require [hsl.core :refer [hsl]]
            [respo.alias :refer [create-comp div]]
            [respo.comp.text :refer [comp-text]]
            [type-ahead-demo.style.widget :as widget]))

(defn render []
  (fn [state mutate!]
    (div {:style widget/button} (comp-text "Click" nil))))

(def comp-button (create-comp :another-button render))
