package org.example.ADTs;

import java.util.ArrayList;

import javafx.util.Pair;

public class HashTable {
    private ArrayList<ArrayList<String>> table;
    private int size;

    public HashTable(int size) {
        this.size = size;
        this.table = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            this.table.add(new ArrayList<>());
        }
    }

    private int hash(String key) {
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum += key.charAt(i);
        }
        return sum % size;
    }

    public Pair<Integer, Integer> add(String key) {
        int hash = this.hash(key);
        if (containsKey(key)) {
            throw new RuntimeException(String.format("Key %s already exists!",key));
        }
        this.table.get(hash).add(key);
        return new Pair<>(hash, table.get(hash).size() - 1);
    }

    public Pair<Integer, Integer> getPosition(String key) {
        int hash = hash(key);
        if (!this.containsKey(key)) {
            throw new RuntimeException(String.format("Key %s doesn't exist!",key));
        }
        return new Pair<>(hash, table.get(hash).indexOf(key));
    }

    public String getFromPosition(Pair<Integer, Integer> position) {
        if (position.getValue() >= table.get(position.getKey()).size())
            throw new RuntimeException("Invalid position!");
        return table.get(position.getKey()).get(position.getValue());
    }

    public boolean containsKey(String key) {
        return table.get(hash(key)).contains(key);
    }

}
