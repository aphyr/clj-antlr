(defproject clj-antlr "0.2.5"
  :description "Clojure bindings for the ANTLR 4 parser library."
  :url "http://github.com/aphyr/clj-antlr"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.antlr/antlr4-runtime "4.7.2"]
                 [org.antlr/antlr4 "4.7.2"]
                 [org.clojure/tools.logging "0.5.0"]]
  :profiles {:dev {:dependencies
                   [[criterium "0.4.5"]
                    [cheshire "5.8.1"]
                    [org.clojure/test.check "0.9.0"]
                    [instaparse "1.4.10"]]}}
  :java-source-paths ["src/java/"]
  :test-selectors {:default (complement :perf)
                   :perf :perf
                   :all  (fn [_] true)}
  :global-vars {*warn-on-reflection* true})
