package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var grammar = new Grammar("src/main/resources/g2.txt");
        System.out.println(grammar.getNonterminals());
        System.out.println(grammar.getTerminals());
        System.out.println(grammar.getProductionRules());
    }
}