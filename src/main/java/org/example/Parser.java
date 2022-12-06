package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Parser {

    private final Grammar grammar;

    public Parser(Grammar grammar) {
        this.grammar = grammar;
    }

    public List<String> First(String X) {

        ArrayList<String> first = new ArrayList<>();

        if (grammar.getTerminals().contains(X)) {
            first.add(X);
            return first;
        }

        List<List<String>> productions = grammar.getProductionsOf(X);

        for (List<String> production : productions) {
            if (!first.contains(production.get(0)))
                first.addAll(First(production.get(0)));
        }
        return first;
    }

    public List<String> Follow(String X) {
        List<String> follow = new ArrayList<>();

        var productions = grammar.getProductionsThatContain(X);

        for (List<String> key : productions.keySet()) {
            for (List<String> prodRule : productions.get(key)) {
                if (prodRule.indexOf(X) < prodRule.size() - 1) {
                    if (!prodRule.get(prodRule.indexOf(X) + 1).contains("epsilon"))
                        follow.addAll(First(prodRule.get(prodRule.indexOf(X) + 1)));
                    else {
                        follow.addAll(First(prodRule.get(prodRule.indexOf(X) + 1)));
                        follow.remove("epsilon");
                    }
                } else {
                    for (String A : key) {
                        if (!A.equals(X))
                            follow.addAll(Follow(A));
                    }
                }
            }
        }

        return new ArrayList<>(new HashSet<>(follow));
    }

}