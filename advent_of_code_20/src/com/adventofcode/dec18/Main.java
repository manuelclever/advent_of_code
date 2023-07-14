package com.adventofcode.dec18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        //input in Array
        String[] lines = Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\Coding\\Projects\\" +
                "adventofcode\\src\\com\\adventofcode\\dec18\\input.txt")).split("\r\n");

        //stream each line in array and start dissolving brackets, then sum the solutions
        System.out.println((Long) Arrays.stream(lines).map(v -> Long.parseLong(dissolveBrackets(v))).mapToLong(Long::longValue).sum());
    }

    public static String dissolveBrackets(String s) {
        //find brackets with no other brackets inside, then calculate the inside
        Pattern pBrackets = Pattern.compile("[(][^()]+[)]");
        Matcher mBrackets = pBrackets.matcher(s);

        if(mBrackets.find()) {
            s = s.replaceFirst(pBrackets.pattern(), calculate(mBrackets.group(0)));
            return dissolveBrackets(s);
        } else {
            return calculate(s);
        }
    }

    public static String calculate (String s) {
        //if brackets only contains one number, remove brackets
        Pattern pBrackets = Pattern.compile("[(](\\d+)[)]");
        Matcher mBrackets = pBrackets.matcher(s);
        if(mBrackets.find()) {
            s = s.replaceFirst(pBrackets.pattern(), mBrackets.group(1));
            return s;
        }

        //first search for any two numbers that are getting added up and calculate them first, then replace input in String with solution
        //after that another recursion
        Pattern pSequenceFirst = Pattern.compile("(\\d+)\\s([+])\\s(\\d+)");
        Matcher mSequenceFirst = pSequenceFirst.matcher(s);
        if(mSequenceFirst.find()) {
            s = s.replaceFirst(pSequenceFirst.pattern(), Long.toString(
                  Long.parseLong(mSequenceFirst.group(1)) + Long.parseLong(mSequenceFirst.group(3))));
            return calculate(s);
        }

        //find any other (multiplications) and calculate them, then replace the input withe the solution and start another recursion
        Pattern pSequence = Pattern.compile("(\\d+)\\s(.)\\s(\\d+)");
        Matcher mSequence = pSequence.matcher(s);
        if(mSequence.find()) {

            if(mSequence.group(2).equals("*")) {
                s = s.replaceFirst(pSequence.pattern(), Long.toString(
                        Long.parseLong(mSequence.group(1)) * Long.parseLong(mSequence.group(3))));
                return calculate(s);
            } else if(mSequence.group(2).equals("+")) {
                s = s.replaceFirst(pSequence.pattern(), Long.toString(
                        Long.parseLong(mSequence.group(1)) + Long.parseLong(mSequence.group(3))));
                return calculate(s);
            }
        }
        return s;
    }
}
