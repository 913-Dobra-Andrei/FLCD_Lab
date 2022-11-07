package org.example;

import javafx.util.Pair;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        var program = Files.readString(Path.of("src/main/resources/p1.in"));
        var tokens = Files.readAllLines(Path.of("src/main/resources/tokens.in"));
        var symbolTable = new SymbolTable(100);

        var scanner = new Scanner(program, tokens, symbolTable);
        scanner.scan();

        StringBuilder pif = new StringBuilder();

        for (Pair<String, Pair<Integer, Integer>> pair : scanner.getPif()) {
            pif.append(pair.getKey()).append(" -> ");
            pif.append("(").append(pair.getValue().getKey()).append(";").append(pair.getValue().getValue()).append(")\n");
        }

        Files.write(Path.of("src/main/resources/PIF.out"), pif.toString().getBytes());

        StringBuilder st = new StringBuilder();

        st.append("Table size:").append(symbolTable.getSize()).append("\n");
        st.append("\n");
        st.append("IDENTIFIERS\n");
        var identifiersTable = symbolTable.getIdentifierHashTable().getTable();
        for (int i = 0; i < identifiersTable.size(); i++) {
            if (!identifiersTable.get(i).isEmpty())
                for (int j = 0; j < identifiersTable.get(i).size(); j++)
                    st.append(i).append(", ").append(j).append(": ").append(identifiersTable.get(i).get(j)).append("\n");
        }
        st.append("\n");
        st.append("CONSTANTS\n");
        var constantsTable = symbolTable.getConstantsHashTable().getTable();
        for (int i = 0; i < constantsTable.size(); i++) {
            if (!constantsTable.get(i).isEmpty())
                for (int j = 0; j < constantsTable.get(i).size(); j++)
                    st.append(i).append(", ").append(j).append(": ").append(constantsTable.get(i).get(j)).append("\n");
        }

        Files.write(Path.of("src/main/resources/ST.out"), st.toString().getBytes());


        System.out.println("Lexically correct!");

    }

}
