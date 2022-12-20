package org.example;

import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    private final Grammar grammar;

    private final ParsingTable parsingTable;

    public Parser(Grammar grammar) {
        this.grammar = grammar;
        parsingTable = new ParsingTable();
    }

    public boolean parse(String input) throws Exception {

        Stack<String> stack = new Stack<>();

        input += "$";
        stack.push("$");
        stack.push(grammar.getStartSymbol());
        var currentIndex = 0;

        while (!stack.empty()) {

            String A = stack.peek();
            var r = input.substring(currentIndex, currentIndex + 1);

            if (grammar.getTerminals().contains(A) || A.equals("$")) {
                if (A.equals(r)) {
                    stack.pop();
                    currentIndex++;
                } else throw new Exception("Error at " + currentIndex + "-" + r);
            } else if (grammar.getNonterminals().contains(A)) {
                if (parsingTable.table.containsKey(new Pair<>(A, r)) && !parsingTable.table.get(new Pair<>(A, r)).equals("-")) {
                    stack.pop();

                    var production = parsingTable.table.get(new Pair<>(A, r));
                    var aux = production.split(" = ")[1];
                    var B = Arrays.stream(aux.substring(1, aux.length() - 1).split(",")).collect(Collectors.toList());
                    B = B.stream().map(String::trim).collect(Collectors.toList());
                    Collections.reverse(B);
                    for (String s : B) {
                        stack.push(s);
                    }
                } else {
                    throw new Exception("Error at " + currentIndex + "-" + r);
                }

            }
        }
        return true;
    }





    public List<String> First(String X) {

        ArrayList<String> first = new ArrayList<>();

        if (grammar.getTerminals().contains(X)) {
            first.add(X);
            return first;
        }

        List<List<String>> productions = grammar.getProductionsOf(X);

        for (List<String> production : productions) {
            if (!first.contains(production.get(0))) first.addAll(First(production.get(0)));
        }
        return first;
    }

    public List<String> Follow(String X) {

        List<String> follow = new ArrayList<>();
        if (X.equals(grammar.getStartSymbol())) follow.add("$");


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
                        if (!A.equals(X)) follow.addAll(Follow(A));
                    }
                }
            }
        }

        return new ArrayList<>(new HashSet<>(follow));
    }

    public void initTable() {
        for (String terminal : grammar.getTerminals())
            for (String non_terminal : grammar.getNonterminals())
                parsingTable.table.put(new Pair<>(non_terminal, terminal), "-");
    }

    public void constructParsingTable() {
        var nonTerminals = grammar.getNonterminals();
        var terminals = grammar.getTerminals();

        this.initTable();

        Map<String, List<String>> firsts = new HashMap<>();
        Map<String, List<String>> follows = new HashMap<>();
        for (String nonTerminal : nonTerminals) {
            firsts.put(nonTerminal, First(nonTerminal));
            follows.put(nonTerminal, Follow(nonTerminal));
        }
        for (String terminal : terminals) {

            firsts.put(terminal, First(terminal));
            follows.put(terminal, Follow(terminal));
        }


        for (String nonTerminal : nonTerminals) {
            var productions = grammar.getProductionsOf(nonTerminal);

            for (List<String> production : productions) {

                var symb = production.get(0);
                for (String first : firsts.get(symb))
                    if (!first.equals("epsilon"))
                        parsingTable.table.put(new Pair<>(nonTerminal, first), nonTerminal + " = " + production);
                    else {
                        var follow = follows.get(nonTerminal);
                        for (String foll : follow) {
                            parsingTable.table.put(new Pair<>(nonTerminal, foll), nonTerminal + " = epsilon");
                            if (foll.equals("$")) {
                                parsingTable.table.put(new Pair<>(nonTerminal, "$"), nonTerminal + " = epsilon");
                            }
                        }
                    }

            }

//            E -> ABC ----> (
//            E -> DFXF ----> x
//                first(E) = (,
//            E -> ABC/DFXF
        }

    }

    public String printParsingTable() {
        var nonTerminals = grammar.getNonterminals();
        var terminals = grammar.getTerminals();
        var sb = new StringBuilder();

        sb.append(String.format("%-40s", "*"));
        for (String terminal : terminals)
            sb.append(String.format("%-40s", terminal));
        sb.append("\n");

        for (String nonTerminal : nonTerminals) {
            sb.append(String.format("%-40s", nonTerminal));
            for (String terminal : terminals) {
                sb.append(String.format("%-40s", parsingTable.table.get(new Pair<>(nonTerminal, terminal))));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public boolean parsePIF(String file) throws Exception {
        var lines = Files.readAllLines(Path.of(file));
        var input = new ArrayList<String>();

        for(String line : lines){
            input.add(line.split("->")[0].trim());
        }


        Stack<String> stack = new Stack<>();

        input.add("$");
        stack.push("$");
        stack.push(grammar.getStartSymbol());
        var currentIndex = 0;

        while (!stack.empty()) {

            String A = stack.peek();
            var r = input.get(currentIndex);

            if (grammar.getTerminals().contains(A) || A.equals("$")) {
                if (A.equals(r)) {
                    stack.pop();
                    currentIndex++;
                } else throw new Exception("Error at " + currentIndex + "-" + r);
            } else if (grammar.getNonterminals().contains(A)) {
                if (parsingTable.table.containsKey(new Pair<>(A, r)) && !parsingTable.table.get(new Pair<>(A, r)).equals("-")) {
                    stack.pop();

                    var production = parsingTable.table.get(new Pair<>(A, r));
                    var aux = production.split(" = ")[1];
                    var B = Arrays.stream(aux.substring(1, aux.length() - 1).split(",")).collect(Collectors.toList());
                    B = B.stream().map(String::trim).collect(Collectors.toList());
                    Collections.reverse(B);
                    for (String s : B) {
                        stack.push(s);
                    }
                } else {
                    throw new Exception("Error at " + currentIndex + "-" + r);
                }

            }
        }


        return true;
    }

}