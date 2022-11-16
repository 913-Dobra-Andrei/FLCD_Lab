package org.example.FA;


import org.javatuples.Triplet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FiniteAutomata {
    private final String filename;
    private List<String> states;
    private String initialState;
    private List<String> finalStates;
    private List<String> alphabet;
    private final List<Triplet<String, String, String>> transitions;

    public FiniteAutomata(String filename) throws IOException {
        this.filename = filename;
        transitions = new ArrayList<>();
        readFromFile();
    }

    public List<String> getStates() {
        return states;
    }

    public String getInitialState() {
        return initialState;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public List<Triplet<String, String, String>> getTransitions() {
        return transitions;
    }

    private void readFromFile() throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filename));
        states = Arrays.stream(lines.get(0).split("=")[1].trim().split(" ")).toList();
        initialState = lines.get(1).split("=")[1].trim();
        finalStates = Arrays.stream(lines.get(2).split("=")[1].trim().split(" ")).toList();
        alphabet = Arrays.stream(lines.get(3).split("=")[1].trim().split(" ")).toList();
        for (int i = 5; i < lines.size(); i++) {
            var transition = lines.get(i).split(" ");
            transitions.add(new Triplet<>(transition[0], transition[1], transition[2]));
        }
    }

    public boolean checkSequence(String sequence) {
        List<String> newSequence = new ArrayList<>();
        for (int i = 0; i < sequence.length(); i++) {
            newSequence.add(String.valueOf(sequence.charAt(i)));
        }
        return checkSequence(newSequence);
    }


    public boolean checkSequence(List<String> sequence) {
        var currentState = initialState;
        for (String letter : sequence) {
            String newState = null;
            for (Triplet<String, String, String> transition : transitions) {
                if (transition.getValue0().equals(currentState) && transition.getValue2().equals(letter)) {
                    newState = transition.getValue1();
                }
            }
            if (newState == null)
                return false;
            currentState = newState;
        }
        return finalStates.contains(currentState);
    }

}
