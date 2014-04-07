(defproject clj-antlr "0.2.2-SNAPSHOT"
  :description "Clojure bindings for the ANTLR 4 parser library."
  :url "http://github.com/aphyr/clj-antlr"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.antlr/antlr4-runtime "4.2.2"]
                 [org.antlr/antlr4 "4.2.2"]]
  :profiles {:dev {:dependencies
                   [[criterium "0.4.0"]
                    [cheshire "5.3.1"]
                    [org.clojure/test.check "0.5.7"]
                    [instaparse "1.2.6"]]}}
  :java-source-paths ["src/java/"]
  :test-selectors {:default #(not (:perf :slow %))
                   :perf :perf
                   :all  (fn [_] true)}
  :global-vars {*warn-on-reflection* true})
