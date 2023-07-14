package com.adventofcode.dec01;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        String s = Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\Coding\\" +
                "Projects\\adventofcode\\src\\com\\adventofcode\\dec01\\numbers.txt"));

        String[] stringArray = Arrays.stream(s.split("\n")).map(String::trim).toArray(String[]::new);

        int[] numArray = new int[stringArray.length];

        int count = 0;
        for(String convert : stringArray) {
            int i = Integer.parseInt(convert);
            numArray[count] = i;
            count++;
        }

//        Part1
//        int num1 = 0;
//        int num2 = 0;
//
//        for(int i = 0; i < numArray.length; i++) {
//            int num = numArray[i];
//            for(int j = 0; j < numArray.length; j++) {
//                if(j != i) {
//                    if (numArray[i] + numArray[j] == 2020) {
//                        num1 = i;
//                        num2 = j;
//                        break;
//                    }
//                }
//            }
//        }
//        System.out.println("i = " + num1 + " and j = " + num2);
//        int sol1 =  numArray[num1];
//        int sol2 = numArray[num2] ;
//        System.out.println(sol1 + " + " + sol2 + " = " + (sol1 + sol2));
//        System.out.println("The solution is: " + sol1 * sol2);

//        Part2
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;

        for(int i = 0; i < numArray.length; i++) {
            int num = numArray[i];


            for(int j = 0; j < numArray.length; j++) {
                if(j != i) {


                    for(int k = 0; k < numArray.length; k++) {
                        if(j != k && i != k) {
                            if(numArray[i] + numArray[j] + numArray[k] == 2020) {
                                num1 = i;
                                num2 = j;
                                num3 = k;
                                break;
                            }
                        }
                    }

                }
            }
        }
        System.out.println("i = " + num1 + " and j = " + num2 + " and k = " + num3);
        int sol1 =  numArray[num1]; int sol2 = numArray[num2]; int sol3 = numArray[num3];
        System.out.println(sol1 + " + " + sol2 + " + " + sol3 + " = " + (sol1 + sol2 + sol3));
        System.out.println("The solution is: " + sol1 * sol2 * sol3);
    }
}
