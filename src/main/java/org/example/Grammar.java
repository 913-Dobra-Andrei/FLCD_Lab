package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Grammar {
    private List<String> nonterminals;
    private List<String> terminals;
    private String startSymbol;
    private final Map<List<String>, List<List<String>>> productionRules;
    private final String file;

    public List<String> getNonterminals() {
        return nonterminals;
    }

    public List<String> getTerminals() {
        return terminals;
    }

    public Map<List<String>, List<List<String>>> getProductionRules() {
        return productionRules;
    }

    public Grammar(String fileName) throws IOException {
        this.file = fileName;
        productionRules = new HashMap<>();
        readFromFile();
    }

    private void readFromFile() throws IOException {
        var lines = Files.readAllLines(Path.of(file));
        nonterminals = Arrays.stream(lines.get(0).split("=",2)[1].trim().split(" ")).collect(Collectors.toList());
        terminals = Arrays.stream(lines.get(1).split("=",2)[1].trim().split(" ")).collect(Collectors.toList());
        startSymbol = lines.get(2).split("=",2)[1].trim();
        for (int i = 4; i < lines.size(); i++) {
            var members = lines.get(i).split("=",2);
            var left = Arrays.stream(members[0].trim().split(" ")).collect(Collectors.toList());
            var right = Arrays.stream(members[1].trim().split(" ")).collect(Collectors.toList());
            if (productionRules.containsKey(left))
                productionRules.get(left).add(right);
            else
                productionRules.put(left, new ArrayList<>(List.of(right)));
        }
    }

    public List<List<String>> getProductionsOf(String left) {
        return productionRules.get(List.of(left));
    }

    public Map<List<String>, List<List<String>>> getProductionsThatContain(String X) {
        Map<List<String>, List<List<String>>> productions = new HashMap<>();

        for (List<String> key : productionRules.keySet()) {
            for (List<String> prodRule : productionRules.get(key)) {
                if(prodRule.contains(X))
                    if (productions.containsKey(key))
                        productions.get(key).add(prodRule);
                    else
                        productions.put(key, new ArrayList<>(List.of(prodRule)));
            }
        }
        return productions;
    }

    public boolean checkCFG() {
        return productionRules.keySet().stream().allMatch(left -> left.size() == 1);
    }

}