package org.example;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {

    private SymbolTable symbolTable;
    private int index;
    private int currentLine;
    private int currentLineIndex;
    private final String program;
    private List<String> tokens;

    private List<Pair<String, Pair<Integer, Integer>>> pif;

    public List<Pair<String, Pair<Integer, Integer>>> getPif() {
        return pif;
    }

    public Scanner(String program, List<String> tokens, SymbolTable table) {
        this.index = 0;
        this.currentLine = 1;
        this.currentLineIndex = 0;
        this.program = program;
        this.tokens = tokens;
        this.symbolTable = table;
        this.pif = new ArrayList<>();
    }

    public void scan() throws Exception {
        while (index < program.length())
            nextToken();
    }

    private void nextToken() throws Exception {
        skipComments();
        skipWhiteSpaces();
        if (index == program.length())
            return;

        boolean noError;
        noError = treatToken();
        noError |= treatIdentifier();
        noError |= treatNumberConstant();
        noError |= treatStringConstant();

        if (!noError)
            throw new Exception(
                    String.format("Lexical error: Could not treat token at line %s:%s", currentLine, currentLineIndex)
            );
    }

    private void skipComments() {
        if (program.startsWith("//", index))
            while (program.charAt(index) != '\n') {
                index++;
                currentLineIndex++;
            }
    }

    private void skipWhiteSpaces() {
        while (index < program.length() && Character.isWhitespace(program.charAt(index))) {
            if (program.charAt(index) == '\n') {
                currentLine++;
                currentLineIndex = 0;
            }
            index++;
            currentLineIndex++;
        }
    }

    private boolean treatIdentifier() {
        String patternString = "(^[A-Za-z][A-Za-z0-9_]*)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(program.substring(index));
        String identifier;
        if (matcher.find()) {
            identifier = matcher.group(1);
        } else {
            return false;
        }
        var position = symbolTable.addIdentifier(identifier);
        pif.add(new Pair<>(identifier, position));
        index += identifier.length();
        currentLineIndex += identifier.length();

        return true;
    }

    private boolean treatStringConstant() {
        String patternString = "(^\"[a-zA-z0-9_ !?.,]*\")";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(program.substring(index));
        String string;
        if (matcher.find()) {
            string = matcher.group(1);
        } else {
            return false;
        }

        var position = symbolTable.addConstant(string);
        pif.add(new Pair<>(string, position));
        index += string.length();
        currentLineIndex += string.length();

        return true;
    }

    private boolean treatNumberConstant() {
        String patternString = "^([+-]?[1-9][0-9]*[ ;]|0)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(program.substring(index));
        String numberStr;
        if (matcher.find()) {
            numberStr = matcher.group(1);
        } else {
            return false;
        }

        var position = symbolTable.addConstant(numberStr);
        pif.add(new Pair<>(numberStr, position));
        index += numberStr.length();
        currentLineIndex += numberStr.length();

        return true;
    }

    private boolean treatToken() {
        for (var token : tokens) {
            if (program.startsWith(token, index)) {
                pif.add(new Pair<>(token, new Pair<>(null, null)));
                index += token.length();
                currentLineIndex += token.length();
                return true;
            }
        }
        return false;
    }


}
