package org.example;

import javafx.util.Pair;
import org.example.ADTs.HashTable;

public class SymbolTable {
    private final HashTable hashTable;

    public SymbolTable() {
        hashTable = new HashTable(100);
    }

    public Pair<Integer, Integer> add(String key){
        return hashTable.add(key);
    }

    public boolean isDefined(String key){
        return hashTable.containsKey(key);
    }

    public Pair<Integer, Integer> getPosition(String key){
        return hashTable.getPosition(key);
    }

    public String getFromPosition(Pair<Integer,Integer> position){
        return hashTable.getFromPosition(position);
    }

}
