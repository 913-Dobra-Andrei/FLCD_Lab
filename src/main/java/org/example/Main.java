package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        var grammar = new Grammar("src/main/resources/g2.txt");
//        System.out.println(grammar.getNonterminals());
//        System.out.println(grammar.getTerminals());
//        System.out.println(grammar.getProductionRules());
        var parser = new Parser(grammar);
       // System.out.println(parser.First("Program"));
//        System.out.println(parser.First("while"));
       // System.out.println(parser.Follow("Identifier"));
       // System.out.println(parser.Follow("Stmt"));
        parser.constructParsingTable();
        System.out.println(parser.printParsingTable());
        Files.writeString(Path.of("src/main/resources/table.txt"),parser.printParsingTable());
    }
}