package com.adventofcode.dec10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        //create input lines
        String input = Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\Coding\\" +
                "Projects\\adventofcode\\src\\com\\adventofcode\\dec10\\input.txt"));
        String[] arrayString = input.split("\n");
        List<Integer> lines = new ArrayList<>();
        for(String s : arrayString) {
            lines.add(Integer.parseInt(s.trim()));
        }

        //sort lines
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        lines.add(0);
        Collections.sort(lines, comparator);

        int joltOnes = 0;
        int joltThrees = 0;
        int lastJolt = 0;
        int poss = 1;
        //add own device
        lines.add(lines.get(lines.size()-1) + 3);

        int tempPossBefore = 0;
        for(int i = 0; i < lines.size() - 1; i++) {
            System.out.println(lines.get(i));

            int tempPoss = -1;
            for(int j = i + 1; j < lines.size(); j++) {
                int difference = lines.get(j) - lastJolt;
                System.out.println("\t" + lines.get(j) + " - " + lastJolt + " = "  + difference);

                if(difference > 3 ) {
                    System.out.println("\t\tout");
                    break;
                }
                tempPoss++;
            }

            if(tempPoss * poss != 0) {
                System.out.print("\t" + poss + " + (" + tempPoss + " * " + poss + ") = ");
                poss = poss + (tempPoss * poss);
                System.out.println(poss);
                if(tempPossBefore != 0) {
                    System.out.print("\t" + poss + " - (" + tempPossBefore + " / 2) - 1 = ");
                    poss = poss  - (tempPossBefore / 2) - 1;
                    System.out.println(poss);
                }

            } else {
                System.out.println("\tno change in poss: " + poss);
            }
            tempPossBefore = tempPoss;
            lastJolt = lines.get(i + 1);

        }

        System.out.println("Solution: " + poss);

















//        System.out.println(poss);


//        for(int i = 0; i < lines.size(); i++) {
//            int difference = lines.get(i) - lastJolt;
//
//            if(difference == 3) {
//                joltThrees++;
//            } else if(difference == 1) {
//                joltOnes++;
//            }
//
//            lastJolt = lines.get(i);
//        }
//        System.out.println("Ones: " + joltOnes + "\nThrees: " + joltThrees);
//        System.out.println(joltOnes * joltThrees);

    }

    public static void print(List<Integer> array) {
        for(int i : array) {
            System.out.print(i + ", ");
        }
        System.out.println("\n");
    }

    public static void printNested(List<List<Integer>> array) {
        for(List<Integer> innerArray : array) {
            for (int i : innerArray) {
                System.out.print(i + ", ");
            }
            System.out.println("\n");
        }
    }

}
