package com.adventofcode.dec09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        //read .txt and turn StringArray in intArray
        String input = Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\" +
                "Coding\\Projects\\adventofcode\\src\\com\\adventofcode\\dec09\\input.txt"));
        String[] linesString = input.split("\n");
        long[] lines = new long[linesString.length];
        for(int i = 0; i < linesString.length; i++) {
            lines[i] = Long.parseLong(linesString[i].trim());
        }

        //iterate through lines Part 1
//        int preamble = 25;
//        for(int i = preamble; i < lines.length; i++) {
//            long num = lines[i];
//
//            if(!checkIfSum(lines, preamble, i,num)) {
//                System.out.println("Number " + num + " not a sum");
//                break;
//            }
//        }

        Comparator<Long> longComparator = new Comparator<>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o2);
            }
        };

        long solutionFirst = 21806024;
        outerLoop:
        for(int i = 0; i < lines.length; i++) {
            long sum = lines[i];
            List<Long> partSum = new ArrayList<>();
            partSum.add(lines[i]);

            for(int j = i + 1; j < lines.length; j++) {
                sum += lines[j];
                partSum.add(lines[j]);

                if(sum == solutionFirst) {
                    Long min = partSum.stream().min(longComparator).get();
                    Long max = partSum.stream().max(longComparator).get();
                    System.out.println(min + max);
                    break outerLoop;
                } else if(sum > solutionFirst) {
                    break;
                }
            }
        }
    }

    public static boolean checkIfSum(long[] lines, int preamble, int thisI, long num) {

        for(int j = thisI - preamble; j < thisI; j++) {
            long first = lines[j];
            long second = 0;

            for(int k = thisI - preamble + 1 ; k < thisI; k++) {
                second = lines[k];

                if(first + second == num) {
                    return true;
                }
            }
        }
        return false;
    }
}
