(defproject clj-antlr-demo "0.0.1-SNAPSHOT"
  :description "Examples of using clj-antlr"
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [criterium "0.4.1"]
                 [clj-antlr "0.0.1-SNAPSHOT"]]
  :java-source-paths ["src/java/"]
  :main clj-antlr-demo.bin
  :profiles {:dev {:dependencies [[midje "1.5.0"]
                                  [cheshire "5.1.1"]]}})
  
