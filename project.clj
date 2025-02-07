(defproject clj-antlr "0.2.15-SNAPSHOT"
  :description "Clojure bindings for the ANTLR 4 parser library."
  :url "http://github.com/aphyr/clj-antlr"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [org.antlr/antlr4-runtime "4.13.2"]
                 [org.antlr/antlr4 "4.13.2"]
                 [org.clojure/tools.logging "1.3.0"]]
  :profiles {:dev {:dependencies
                   [[criterium "0.4.6"]
                    [cheshire "5.13.0"]
                    [org.clojure/test.check "1.1.1"]
                    [org.slf4j/slf4j-simple "2.0.16"]
                    [instaparse "1.5.0"]]}}
  :java-source-paths ["src/java/"]
  :javac-options ["-target" "1.8" "-source" "1.8"]
  :test-selectors {:default (complement :perf)
                   :perf :perf
                   :all  (fn [_] true)}
  :global-vars {*warn-on-reflection* true})
