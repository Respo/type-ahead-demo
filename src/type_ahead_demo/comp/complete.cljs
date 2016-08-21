
(ns type-ahead-demo.comp.complete
  (:require [respo.alias :refer [create-comp div input]]
            [respo-ui.style :as ui]
            [hsl.core :refer [hsl]]
            [respo.comp.text :refer [comp-text]]
            [type-ahead-demo.ajax.results :refer [query-results]]
            [clojure.core.async :refer [>!
                                        <!
                                        timeout
                                        chan
                                        offer!
                                        alts!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn on-input [mutate! ch-input]
  (fn [e dispatch!]
    (mutate! {:text (:value e)})
    (go
      (offer! ch-input true)
      (let [[v ch] (alts! [ch-input (timeout 400)])]
        (if (nil? v)
          (do
            (mutate! {:searching? true})
            (let [ch-result (query-results (:value e))
                  results (<! ch-result)]
              (mutate! {:searching? false, :candidates results}))))))))

(def style-menu {})

(defn init-state []
  {:searching? false,
   :candidates [],
   :ch-input (chan),
   :show-menu? false,
   :text ""})

(def style-item {:line-height 2, :font-size 14, :padding "0 8px"})

(defn render []
  (fn [state mutate!]
    (div
      {}
      (input
        {:style (merge ui/input {:width "400px"}),
         :event {:input (on-input mutate! (:ch-input state))},
         :attrs {:placeholder "Search...", :value (:text state)}})
      (if (:searching? state) (comp-text "searching" nil))
      (comment comp-text (pr-str state) nil)
      (div
        {:style style-menu}
        (->>
          (:candidates state)
          (map-indexed
            (fn [idx item] [idx
                            (div
                              {:style style-item}
                              (comp-text item nil))])))))))

(def comp-complete (create-comp :complete init-state merge render))
