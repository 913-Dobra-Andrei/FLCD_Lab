package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var grammar = new Grammar("src/main/resources/g2.txt");
//        System.out.println(grammar.getNonterminals());
//        System.out.println(grammar.getTerminals());
//        System.out.println(grammar.getProductionRules());
        var parser = new Parser(grammar);
        System.out.println(parser.First("Program"));
        System.out.println(parser.First("Expression"));
        System.out.println(parser.Follow("Identifier"));
        System.out.println(parser.Follow("Stmt"));
    }
}