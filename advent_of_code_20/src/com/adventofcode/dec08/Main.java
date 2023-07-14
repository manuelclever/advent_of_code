package com.adventofcode.dec08;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static int acc = 0;

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\" +
                "Coding\\Projects\\adventofcode\\src\\com\\adventofcode\\dec08\\input.txt"));

        String[] lines = input.split("\n");

        //break if already visited
        for (int i = 0; i < lines.length; i++) {
            String pattern = "^\\w\\w\\w";
            Pattern pOperation = Pattern.compile(pattern);
            Matcher mOperation = pOperation.matcher(lines[i]);
            mOperation.find();

            if (mOperation.group(0).equals("jmp")) {
                lines[i] = lines[i].replaceAll(pattern, "nop");

                if(loop(lines) != 0) {
                    System.out.println("we endet it " + acc);
                    break;
                } else {
                    acc = 0;
                }
                lines[i] = lines[i].replaceAll(pattern, "jmp");
            }

            if (mOperation.group(0).equals("nop")) {
                lines[i] = lines[i].replaceAll(pattern, "jmp");

                if(loop(lines) != 0) {
                    System.out.println("we endet it " + acc);
                    break;
                } else {
                    acc = 0;
                }
                lines[i] = lines[i].replaceAll(pattern, "nop");

            }

        }
        System.out.println("we are through the loop or it was ended");
    }

        public static int loop(String[] lines) {
            String action = lines[0];
            String patternOperation = "^\\w\\w\\w";
            String patternArgument = ".\\d+$";
            int i = 0;
            Set<Integer> visited = new HashSet<>();

            while (action != null) {

                System.out.println(lines[i]);
                if (visited.contains(i)) {
                    System.out.println("-------------------------------------------------------");
                    return 0;
                }
                visited.add(i);

                Pattern pOperation = Pattern.compile(patternOperation);
                Matcher mOperation = pOperation.matcher(lines[i]);

                Pattern pArgument = Pattern.compile(patternArgument);
                Matcher mArgument = pArgument.matcher(lines[i]);

                int argumentInt = 0;
                if (mArgument.find()) {
                    String argument = mArgument.group(0).substring(0, 1);
                    argumentInt = Integer.parseInt(mArgument.group(0).substring(1));
                    if (argument.equals("-")) {
                        argumentInt *= -1;
                    }
                }

                if (mOperation.find()) {
                    String operation = mOperation.group(0);

                    if (operation.equals("nop")) {
                        i++;
                    } else if (operation.equals("acc")) {
                        acc += argumentInt;
                        i++;
                    } else if (operation.equals("jmp")) {
                        i += argumentInt;
                    }
                }
                if(i >= lines.length) {
                    action = null;
                } else {
                    action = lines[i];
                }
            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return 1;
        }
}
