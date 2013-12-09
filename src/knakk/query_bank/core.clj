(ns knakk.query-bank.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clostache.parser :refer [render]]))

(def ^:private query-tag-rx #"^#\s*tag:\s+([\w-]+)\s*$")

(defn- matches-query-tag?
  [line]
  (boolean (re-matches query-tag-rx line)))

(defn- parse-query-tag
  [line]
  (second (re-matches query-tag-rx line)))

(defn- parse-query-body
  [lines]
  (-> (str/join " " (remove empty? lines))
      (str/replace #"\s{2,}" " ")))

(defn- read-bank
  [file]
  (with-open [rdr (io/reader (io/resource file))]
    (into {}
          (for [[[tag] body] 
                (partition 2 (partition-by matches-query-tag? (line-seq rdr)))]
            [(parse-query-tag tag) (parse-query-body body)]))))

(defn query-bank
  [resource-name]
  (let [qb (read-bank resource-name)]
    (fn lookup-query
      ([query-name]
       (lookup-query query-name {}))
      ([query-name query-params]
       (if-let [query (qb query-name)]
         (render query query-params)
         (throw (IllegalArgumentException.
                  (str "query-bank " resource-name " has no query " query-name))))))))