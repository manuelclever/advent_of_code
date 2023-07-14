package com.adventofcode.dec05;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente" +
                "\\Coding\\Projects\\adventofcode\\src\\com\\adventofcode\\dec05\\input.txt"));

        List<Integer> allIDs = new ArrayList<>();
        for(String s : lines) {
            allIDs.add(recursive(s));
        }

        int myID = 0;
        int c = 0;
        for(Integer idToCheck : allIDs) {
            boolean hasPrev = false;
            boolean hasNext = false;

            for (Integer idCheckWith : allIDs) {

                if (idCheckWith == idToCheck - 1 || idCheckWith == (8)) { //or if first seat of row 1
                    hasPrev = true;
                }
                if (idCheckWith == idToCheck + 1 || idCheckWith == (126 * 8 + 7)) { //or if last seat of row 126 (1015)
                    hasNext = true;
                }
            }

            if(!hasPrev) {
                myID = allIDs.get(c) - 1;
                break;
            } else if (!hasNext) {
                myID = allIDs.get(c) + 1;
                break;
            }
            c++;
        }
        System.out.println(myID);
    }

    public static int recursive(String s) {
        int[] seat = recursive(s, 0, 127, 0, 7, 0);

        //don't count seats from last and first row
        if(seat[0] == 0 || seat[0] == 127) {
            return 0;
        }
        return (seat[0] * 8) + seat[1];
    }

    public static int[] recursive(String s, int minR, int maxR, int minC, int maxC, int iter) {
        char c = s.charAt(iter);
        iter++;

        //if last two seats, call column recursive
        int dif = ( (maxR + 1) - (minR) ) / 2;
        if(dif == 1) {
            return recursive(s, minR, maxR, minC, maxC, iter, new int[]{c == 'F' ? minR : maxR, 0} );
        }

        if(c == 'F') { //lower half row
            maxR = maxR - dif;
        } else { //upper half row
            minR = minR + dif;
        }
        return recursive(s, minR, maxR, minC, maxC, iter);
    }

    public static int[] recursive(String s, int minR, int maxR, int minC, int maxC, int iter, int[] array) {
        char c = s.charAt(iter);
        iter++;

        //if last tow seats return seat array
        int dif = ( (maxC + 1) - (minC) ) / 2;
        if(dif == 1) {
            return new int[]{array[0], c == 'L' ? minC : maxC};
        }

        if(c == 'L') { //lower half column
            maxC = maxC - dif;
        } else { //upper half column
            minC = minC + dif;
        }
        return recursive(s, minR, maxR, minC, maxC, iter, array );
    }
}
