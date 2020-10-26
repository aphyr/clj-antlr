package org.antlr.v4.runtime;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.Tree;
import org.antlr.v4.runtime.tree.Trees;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.Rule;
import org.antlr.v4.tool.ast.AltAST;
import org.antlr.v4.tool.GrammarInterpreterRuleContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/*
 *  Adapted from
 *  https://github.com/antlr/intellij-plugin-v4/blob/e203c36c7edb138c1ab020252ac5c1c94a94786c/src/java/org/antlr/intellij/plugin/preview/AltLabelTextProvider.java
 */

public class AltLabelTextProvider {
    protected final Parser parser;
    protected final Grammar g;

    public AltLabelTextProvider(Parser parser, Grammar g) {
        this.parser = parser;
        this.g = g;
    }

    public String[] getAltLabels(Rule r) {
        String[] altLabels = null;
        Map<String, List<Pair<Integer, AltAST>>> altLabelsMap = r.getAltLabels();
        if ( altLabelsMap!=null ) {
            altLabels = new String[r.getOriginalNumberOfAlts() + 1];
            for (String altLabel : altLabelsMap.keySet()) {
                List<Pair<Integer, AltAST>> pairs = altLabelsMap.get(altLabel);
                for (Pair<Integer, AltAST> pair : pairs) {
                    altLabels[pair.a] = altLabel;
                }
            }
        }
        return altLabels;
    }

    public String getText(Tree node) {
        if ( node instanceof GrammarInterpreterRuleContext) {
            GrammarInterpreterRuleContext inode = (GrammarInterpreterRuleContext)node;
            Rule r = g.getRule(inode.getRuleIndex());
            String[] altLabels = getAltLabels(r);
            String name = r.name;
            int outerAltNum = inode.getOuterAltNum();
            if ( altLabels!=null ) {
                if ( outerAltNum>=0 && outerAltNum<altLabels.length ) {
                    return altLabels[outerAltNum];
                }
                else {
                    return name;
                }
            }
            else if ( r.getOriginalNumberOfAlts()>1 ) {
                return name; // + ":" +outerAltNum;
            }
            else {
                return name; // don't display an alternative number if there's only one
            }
        }
        return Trees.getNodeText(node, Arrays.asList(parser.getRuleNames()));
    }
}
