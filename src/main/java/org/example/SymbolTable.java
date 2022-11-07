package org.example;

import javafx.util.Pair;
import org.example.ADTs.HashTable;

public class SymbolTable {
    private final HashTable identifierHashTable;
    private final HashTable constantsHashTable;

    private final int size;

    public int getSize() {
        return size;
    }

    public HashTable getIdentifierHashTable() {
        return identifierHashTable;
    }

    public HashTable getConstantsHashTable() {
        return constantsHashTable;
    }

    public SymbolTable(int s) {
        size = s;
        identifierHashTable = new HashTable(size);
        constantsHashTable = new HashTable(size);
    }

    public Pair<Integer, Integer> addIdentifier(String key) {
        return identifierHashTable.add(key);
    }

    public boolean isIdentifierDefined(String key) {
        return identifierHashTable.containsKey(key);
    }

    public Pair<Integer, Integer> getIdentifierPosition(String key) {
        return identifierHashTable.getPosition(key);
    }

    public String getIdentifierFromPosition(Pair<Integer, Integer> position) {
        return identifierHashTable.getFromPosition(position);
    }

    public Pair<Integer, Integer> addConstant(String key) {
        return constantsHashTable.add(key);
    }

    public boolean isConstantDefined(String key) {
        return constantsHashTable.containsKey(key);
    }

    public Pair<Integer, Integer> getConstantPosition(String key) {
        return constantsHashTable.getPosition(key);
    }

    public String getConstantFromPosition(Pair<Integer, Integer> position) {
        return constantsHashTable.getFromPosition(position);
    }

}
