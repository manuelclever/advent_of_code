package com.adventofcode.dec06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\" +
                "Coding\\Projects\\adventofcode\\src\\com\\adventofcode\\dec06\\input.txt"));

        String[] groups = input.split("\n\r"); //answer sheets in grouped array
        int finalCount = 0;

        for (int i = 0; i < groups.length; i++) {
            String[] answers = groups[i].trim().split("\n"); //each answer as own string in group array

            Set<Character> comp1 = new HashSet<>();
            for (int j = 0; j < answers[0].length(); j++) {
                if( ((Character) answers[0].charAt(j)).toString().codePointAt(0) != 13 ) {
                    comp1.add(answers[0].charAt(j));
                }
            }

            for (int j = 1; j < answers.length; j++) {

                Set<Character> comp2 = new HashSet<>();
                for (int k = 0; k < answers[j].length(); k++) {
                    if( ((Character) answers[j].charAt(k)).toString().codePointAt(0) != 13 ) {
                        comp2.add(answers[j].charAt(k));
                    }
                }
                comp1.retainAll(comp2);
            }
            System.out.println(comp1.size() + "\t" + comp1);
            finalCount = finalCount + comp1.size();
        }
        System.out.println(finalCount);
    }
}
