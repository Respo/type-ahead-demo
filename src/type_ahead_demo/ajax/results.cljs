
(ns type-ahead-demo.ajax.results
  (:require [clojure.core.async :refer [>! <! timeout chan offer!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn query-results [starting]
  (let [ch (chan)]
    (go
      (<! (timeout 400))
      (>!
        ch
        (->>
          (repeat 10 nil)
          (map (fn [x] (str starting "--" (.random js/Math))))
          (into []))))
    ch))
