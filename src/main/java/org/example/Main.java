package org.example;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        var grammar = new Grammar("src/main/resources/g1.txt");
        var parser = new Parser(grammar);
        parser.constructParsingTable();
//        System.out.println(parser.printParsingTable());
        Files.writeString(Path.of("src/main/resources/table.txt"), parser.printParsingTable());
        Files.writeString(Path.of("src/main/resources/out1.txt"), String.valueOf(parser.parse(Files.readString(Path.of("src/main/resources/input.txt")))));
//        System.out.println(parser.parsePIF("src/main/resources/PIF.out"));
    }
}