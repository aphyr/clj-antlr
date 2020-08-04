(defproject clj-antlr "0.2.6"
  :description "Clojure bindings for the ANTLR 4 parser library."
  :url "http://github.com/aphyr/clj-antlr"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.antlr/antlr4-runtime "4.8-1"]
                 [org.antlr/antlr4 "4.8-1"]
                 [org.clojure/tools.logging "1.1.0"]]
  :profiles {:dev {:dependencies
                   [[criterium "0.4.6"]
                    [cheshire "5.10.0"]
                    [org.clojure/test.check "1.1.0"]
                    [instaparse "1.4.10"]]}}
  :java-source-paths ["src/java/"]
  :test-selectors {:default (complement :perf)
                   :perf :perf
                   :all  (fn [_] true)}
  :global-vars {*warn-on-reflection* true})
