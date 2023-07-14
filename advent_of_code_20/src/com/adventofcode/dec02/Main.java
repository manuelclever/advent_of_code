package com.adventofcode.dec02;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\Coding\\Projects\\" +
                "adventofcode\\src\\com\\adventofcode\\dec02\\input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String s = null;
        StringBuilder stringBuilder = new StringBuilder();
        while((s = br.readLine()) != null) {
            stringBuilder.append(s).append(",");
        }
        s = stringBuilder.toString();
        String[] stringArray = s.split(",");

        //First Part

//        int correctPWs = 0;
//
//        for(String string : stringArray) {
//            String[] temp = string.split(":");
//            String pw = temp[1];
//            String[] temp2 = temp[0].split(" ");
//            String cpL = temp2[1];
//            String[] temp3 = temp2[0].split("-");
//            int cpMin = Integer.parseInt(temp3[0]);
//            int cpMax = Integer.parseInt(temp3[1]);
//
//            String[] pwChars = pw.split("");
//            int countChar = 0;
//
//            for(String myChar : pwChars) {
//
//                if (myChar.equals(cpL)) {
//                    countChar++;
//                }
//            }
//
//            if(countChar >= cpMin && countChar <= cpMax) {
//                correctPWs++;
//            }
//        }
//
//        System.out.println("Correct passwords: " + correctPWs);

        //Second Part

        int correctPWs = 0;

        for(String string : stringArray) {
            String[] temp = string.split(":");
            String pw = temp[1].trim();
            String[] temp2 = temp[0].split(" ");
            char cpL = temp2[1].charAt(0);
            String[] temp3 = temp2[0].split("-");
            int cpPos1 = Integer.parseInt(temp3[0]);
            int cpPos2 = Integer.parseInt(temp3[1]);

            boolean check1 = pw.charAt(cpPos1 - 1) == cpL;
            boolean check2 = pw.charAt(cpPos2 - 1) == cpL;
            if(check1 || check2) {
                if(check1 && check2) {
                    continue;
                }
                correctPWs++;
            }

        }

        System.out.println("Correct passwords: " + correctPWs);

    }
}
