# query-bank

This simple library loads a file of tagged SPARQL queries into a map.
The library is modeled after [sql-phrasebook](https://github.com/ray1729/sql-phrasebook) by Ray Miller.

## Installation
Include in your project.clj:

    [knakk/query-bank "0.7"]

## Usage

Given the following contents in `resources/queries.sparql`:

```sparql
# tag: q1
SELECT *
WHERE
 {
  ?s ?p ?o 
  FILTER(?s = <{{subj}}>)
 }
```

Any variables will be interpolated into the query:

```clojure
(require '[knakk.query-bank.core :refer [query-bank]])

(def query (query-bank "resources/queries.sparql"))

(query q1 {:subj "http://dott.com/mysubject"})
;; "SELECT * WHERE { ?s ?p ?o FILTER(?s = <http://dott.com/mysubject>) }"
```

## License

Copyright © 2013 Petter Goksøyr Åsen

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
