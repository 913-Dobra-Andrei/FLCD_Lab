package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Testing {
    public static void main(String[] args){
        var line = "x; dsadsa";
        String patternString = "(^[A-Za-z][A-Za-z0-9_]*)";

        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(line);

        String identifier;
        if (matcher.find()) {
            identifier = matcher.group(0);
            System.out.println(identifier);
        }else
            System.out.println("FMM!!!");

        System.out.println("{\r\t".startsWith("{"));

    }
}
