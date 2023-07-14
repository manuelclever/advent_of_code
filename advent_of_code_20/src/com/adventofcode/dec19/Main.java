package com.adventofcode.dec19;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    // a ( (aa|bb) (ab|ba) | (ab|ba) (aa|bb) )

    public static void main(String[] args) throws IOException {
        Path path = FileSystems.getDefault().getPath("src", "com", "adventofcode", "dec19", "input.txt")
                .toAbsolutePath();
        Data data = new Data(path);
        String regex = RegexGen.generate(0, data);
        Pattern pattern = Pattern.compile(regex);

        System.out.println(regex);

        int c = 0;
        for(String msg : data.message) {
            Matcher matcher = pattern.matcher(msg);
            boolean isMatch = matcher.matches();
            if (isMatch) {
                c++;
            }
        }
        System.out.println("Solution Part 1: " + c);
    }
}

class RegexGen {
    private static StringBuilder regex;

    private RegexGen() {
    }

    public static String generate(int numOfRule, Data data) {
        regex = new StringBuilder();
        generateRegex(numOfRule, data);
        return regex.toString();
    }

    private static void generateRegex(int numOfRule, Data data) {

        if(data.rules.containsKey(numOfRule)) {
            int[] leftAlternative = data.rules.getLeft(numOfRule);
            boolean hasRight = data.rules.hasRight(numOfRule);

            regex.append("(");

            if(hasRight) {
                regex.append("(");
            }

            Arrays.stream(leftAlternative).forEach(partOfLeft -> {
                if(partOfLeft != 0) {
                    generateRegex(partOfLeft, data);
                }
            });
            regex.append(")");

            if(data.rules.hasRight(numOfRule)) {
                int[] rightAlternative = data.rules.getRight(numOfRule);

                regex.append("|(");

                Arrays.stream(rightAlternative).forEach(partOfRight -> {
                    if(partOfRight != 0) {
                        generateRegex(partOfRight, data);
                    }
                });

                regex.append("))");
            }
        } else {
            Character character = data.chars.get(numOfRule);
            regex.append("[").append(character).append("]");
        }
    }
}

class Data {
    Rules rules;
    Map<Integer, Character> chars;
    List<String> message;

    private static final Pattern PATTERN_RULE = Pattern.compile("\\d+");
    private static final Pattern PATTERN_CHAR = Pattern.compile("[\"](\\w)[\"]");
    private static final Pattern PATTERN_MSG = Pattern.compile("[a-z]{2,100}");

    public Data(Path path) {
        rules = new Rules();
        chars = new HashMap<>();
        message = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path.toUri())));
            reader.lines().forEach(line -> {
                Matcher matcherMsg = PATTERN_MSG.matcher(line);

                if(matcherMsg.find()) {
                    message.add(matcherMsg.group());
                }

                String[] data = line.split(":");
                try {
                    int numOfRule = Integer.parseInt(data[0]);
                    int[][] possibilities = new int[2][2];

                    String[] temp = data[1].split("\\|");
                    int[] left = new int[2];
                    int[] right = new int[2];

                    Matcher matcherLeft = PATTERN_RULE.matcher(temp[0]);
                    Matcher matcherChar = PATTERN_CHAR.matcher(data[1]);

                    int pos = 0;
                    while (matcherLeft.find()) {
                        match(matcherLeft, left, pos);
                        pos++;
                    }

                    try {
                        Matcher matcherRight = PATTERN_RULE.matcher(temp[1]);
                        pos = 0;
                        while (matcherRight.find()) {
                            match(matcherRight, right, pos);
                            pos++;
                        }
                    } catch(IndexOutOfBoundsException ignore){}

                    boolean isChar = false;
                    if(matcherChar.find()) {
                        isChar = true;
                        chars.put(numOfRule, matcherChar.group(1).toCharArray()[0]);
                    }

                    if(!isChar) {
                        rules.put(numOfRule, new int[][]{left, right});
                    }
                } catch (NumberFormatException e) {

                }


            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void match(Matcher matcher, int[] arr, int pos) {
        arr[pos] = Integer.parseInt(matcher.group());
    }

    public void print() {
        for(Map.Entry<Integer, int[][]> entry : rules.entrySet()) {
            int k = entry.getKey();
            int[][] v = entry.getValue();
            System.out.print(k +": ");
            Arrays.stream(v).forEach(arr -> {
                Arrays.stream(arr).forEach(num -> System.out.print(num + ", "));
                System.out.print("| ");
            });
            System.out.println("");
        }
        System.out.println("===");
        chars.forEach((k,v) -> System.out.println(k + ": " + v));
        System.out.println("===");
        message.forEach(System.out::println);
        System.out.println("===");
    }
}

class Rules {
    Map<Integer, int[][]> rules;

    public Rules() {
        rules = new HashMap<>();
    }

    public boolean containsKey(int key) {
        return rules.containsKey(key);
    }

    public void put(int key, int[][] arr) {
        rules.put(key, arr);
    }

    public Set<Map.Entry<Integer, int[][]>> entrySet() {
        return rules.entrySet();
    }

    public int[] getLeft(int key) {
        return rules.get(key)[0];
    }

    public int[] getRight(int key) {
        return rules.get(key)[1];
    }

    public boolean hasRight(int key) {
        int[] right  = rules.get(key)[1];
        return (right[0] != 0) && (right[1] != 0);
    }




}
