package de.manuelclever.dec10;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Calculate_10 implements Calculator {
    List<String> rawInput;
    List<List<Character>> openLines;

    @Override
    public long calculatePart1() {
        setInput();
        List<Character> corruptBrackets = new ArrayList<>();
        openLines = new ArrayList<>();

        for(String line : rawInput) {
            List<Character> brackets = new ArrayList<>();
            boolean corrupt = false;

            for (int i = 0; i < line.length(); i++) {
                char bracket = line.charAt(i);

                if(bracket == '(' || bracket == '[' || bracket == '{' || bracket == '<') {
                    brackets.add(bracket);
                } else if(isClosingBracket(bracket, brackets)) {
                    brackets.remove(brackets.size() - 1);
                } else { //corrupt
                    corruptBrackets.add(bracket);
                    corrupt = true;
                    break;
                }
            }

            if(brackets.size() > 0 && !corrupt) {
                openLines.add(brackets);
            }
        }
        return calculateIllegal(corruptBrackets);
    }

    private void setInput() {
        MyFileReader fr = new MyFileReader(10,1);
        rawInput = fr.getStringList();
    }

    private boolean isClosingBracket(char c, List<Character> list) {
        char currBracket = list.get(list.size() - 1);

        switch(currBracket) {
            case '(' -> {return c == ')';}
            case '[' -> {return c == ']';}
            case '{' -> {return c == '}';}
            case '<' -> {return c == '>';}
        }
        return false;
    }

    private long calculateIllegal(List<Character> corruptBrackets) {
        int c = 0;

        for(char bracket : corruptBrackets) {
            switch(bracket) {
                case ')' -> c += 3;
                case ']' -> c += 57;
                case '}' -> c += 1197;
                case '>' -> c += 25137;
            }
        }
        return c;
    }

    @Override
    public long calculatePart2() {
        calculatePart1(); //generates List<List<Character>> openLines
        List<List<Character>> completingLines = new ArrayList<>();

        for(List<Character> line : openLines) {
            List<Character> completingLine = new LinkedList<>();

            for(char bracket : line) {
                switch(bracket) {
                    case '(' -> completingLine.add(0, ')');
                    case '[' -> completingLine.add(0, ']');
                    case '{' -> completingLine.add(0, '}');
                    case '<' -> completingLine.add(0, '>');
                }
            }
            completingLines.add(completingLine);
        }
        return calculateClosing(completingLines);
    }

    private long calculateClosing(List<List<Character>> completingLines) {
        List<Long> score = new ArrayList<>();

        for (List<Character> completingLine : completingLines) {
            long c = 0;

            for (char bracket : completingLine) {
                c *= 5;
                switch (bracket) {
                    case ')' -> c += 1;
                    case ']' -> c += 2;
                    case '}' -> c += 3;
                    case '>' -> c += 4;
                }
            }
            score.add(c);
        }
        score.sort(Long::compare);
        return score.get( (score.size() - 1) / 2);
    }
}
