package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Grammar {
    private List<String> nonterminals;
    private List<String> terminals;
    private String startSymbol;
    private final Map<List<String>, List<String>> productionRules;
    private final String file;

    public List<String> getNonterminals() {
        return nonterminals;
    }

    public List<String> getTerminals() {
        return terminals;
    }

    public Map<List<String>, List<String>> getProductionRules() {
        return productionRules;
    }

    public Grammar(String fileName) throws IOException {
        this.file = fileName;
        productionRules = new HashMap<>();
        readFromFile();
    }

    private void readFromFile() throws IOException {
        var lines = Files.readAllLines(Path.of(file));
        nonterminals = Arrays.stream(lines.get(0).split("=")[1].trim().split(" ")).collect(Collectors.toList());
        terminals = Arrays.stream(lines.get(1).split("=")[1].trim().split(" ")).collect(Collectors.toList());
        startSymbol = lines.get(2).split("=")[1].trim();
        for (int i = 4; i < lines.size(); i++) {
            var members = lines.get(i).split("=");
            var left = Arrays.stream(members[0].trim().split(" ")).collect(Collectors.toList());
            var right = Arrays.stream(members[1].trim().split(" ")).collect(Collectors.toList());
            productionRules.put(left, right);
        }
    }

    public List<String> getProductionsOf(String left){
        return productionRules.get(List.of(left));
    }

    public boolean checkCFG(){
        return productionRules.keySet().stream().allMatch(left -> left.size() == 1);
    }

}