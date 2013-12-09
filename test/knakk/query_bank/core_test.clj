(ns knakk.query-bank.core-test
  (:require [clojure.test :refer :all]
            [knakk.query-bank.core :refer [query-bank]]))

(def query (query-bank "resources/queries.sparql"))

(deftest queries
  (testing "no arguments"
    (is (= (query "q0") "SELECT * WHERE { ?s ?p ?o}")))
  
  (testing "one argument"
    (is (= (query "q1" {:subj "xyz"})
           "SELECT * WHERE { ?s ?p ?o FILTER(?s = <xyz>) }")))
  
  (testing "several arguments"
    (is (= (query "q2" {:obj1 "abc" :obj2 99})
           "SELECT * WHERE { ?s ?p <abc> ; ?p 99 . }"))))

(deftest error-handling
  (testing "non-existing query"
    (is (thrown? IllegalArgumentException (query "q99")))))
