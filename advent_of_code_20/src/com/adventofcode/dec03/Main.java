package com.adventofcode.dec03;

import java.io.*;

public class Main {
    static String[] stringArray;

    public static void main(String[] args) throws IOException {
        //set source of file
        File file = new File("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\Coding\\Projects\\" +
        "adventofcode\\src\\com\\adventofcode\\dec03\\input.txt");
        //initialize br and set file
        BufferedReader br = new BufferedReader(new FileReader(file));

        //read Line and add to one long String
        String s = "";
        StringBuilder stringBuilder = new StringBuilder();
        while((s = br.readLine()) != null) {
            stringBuilder.append(s).append(",");
        }

        //cut long String and put in Array
        s = stringBuilder.toString();
        stringArray = s.split(",");

        int[] xArray = {1, 3, 5, 7, 1};
        int[] yArray = {1, 1, 1, 1, 2};
        int[] treeEncounterArray = new int[xArray.length];

        if(xArray.length == yArray.length) {

            for(int i = 0; i < xArray.length; i++) {
                treeEncounterArray[i] = traverse(xArray[i], yArray[i]);
            }
        }

        long solution = 0;
        for(int i = 0; i < treeEncounterArray.length; i++) {

            if(i == 0) {
                solution = treeEncounterArray[i];
            } else {
                solution = solution * treeEncounterArray[i];
            }
        }
        System.out.println(solution);
    }

    private static int traverse(int x, int y) {
        int posX = 0;
        int treeCounter = 0;
        for(int i = 0; i < stringArray.length; i = i + y) {

            String s = stringArray[i];
            if(posX >= s.length()) {

                for(int j = i; j < stringArray.length; j++) {
                    StringBuilder stringBuilder = new StringBuilder(stringArray[j]); //reuse of stringBuilder, bc not needed anymore
                    stringBuilder.append(stringBuilder.toString());
                    stringArray[j] = stringBuilder.toString();

                    if(j == i) {
                        s = stringArray[j];
                    }
                }
            }
            char posChar = s.charAt(posX);
            if(posChar == '#') {
                treeCounter++;
            }
            posX = posX + x;
        }
        return treeCounter;
    }

    private static void printFullArray() {
        int l = 1;
        for(String s : stringArray) {
            System.out.println(l + ": " + s);
            l++;
        }
    }
}
