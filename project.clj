(defproject clj-antlr "0.2.13"
  :description "Clojure bindings for the ANTLR 4 parser library."
  :url "http://github.com/aphyr/clj-antlr"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.antlr/antlr4-runtime "4.9.3"]
                 [org.antlr/antlr4 "4.9.3"]
                 [org.clojure/tools.logging "1.2.4"]]
  :profiles {:dev {:dependencies
                   [[criterium "0.4.6"]
                    [cheshire "5.10.1"]
                    [org.clojure/test.check "1.1.1"]
                    [org.slf4j/slf4j-simple "1.7.33"]
                    [instaparse "1.4.10"]]}}
  :java-source-paths ["src/java/"]
  :javac-options ["-target" "1.8" "-source" "1.8"]
  :test-selectors {:default (complement :perf)
                   :perf :perf
                   :all  (fn [_] true)}
  :global-vars {*warn-on-reflection* true})
