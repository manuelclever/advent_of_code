package com.adventofcode.dec12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Part2 {

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\Coding\\Projects\\" +
                "adventofcode\\src\\com\\adventofcode\\dec12\\input.txt"));
        String[] tempLines = input.split("\n");
        String[] lines = Arrays.stream(tempLines).map(String::trim).toArray(String[]::new);

        int[] shipXY = new int[]{0, 0};
        int[] waypointXY = new int[]{1, 10};
        for(String s : lines) {
            char moving = s.charAt(0);
            int quantity = Integer.parseInt(s.substring(1));

            switch(moving) {
                case 'N':
                    waypointXY[0] += quantity;
                    break;
                case 'E':
                    waypointXY[1] += quantity;
                    break;
                case 'S':
                    waypointXY[0] -= quantity;
                    break;
                case 'W':
                    waypointXY[1] -= quantity;
                    break;
                case 'L':
                    waypointXY = turn(waypointXY, quantity, false);
                    break;
                case 'R':
                    waypointXY = turn(waypointXY, quantity, true);
                    break;
                case 'F':
                    int c = 0;
                    while(c < quantity) {
                        c++;
                        shipXY[0] += waypointXY[0];
                        shipXY[1] += waypointXY[1];
                    }
                    break;
            }
//            System.out.println(s);
//            System.out.println("\tship: " + shipXY[0] + " " + shipXY[1]);
//            System.out.println("\twaypoint: " + waypointXY[0] + " " + waypointXY[1]);
        }
        System.out.println("final: " + shipXY[0] + " " + shipXY[1]);
        int result = (shipXY[0] > 0 ? shipXY[0] : shipXY[0] * -1)  + (shipXY[1] > 0 ? shipXY[1] : shipXY[1] * -1);
        System.out.println("result: " + result);
    }

    public static int[] turn(int[] waypoint, int degree, boolean right) {
        char[] directions = new char[]{'N', 'E', 'S', 'W'};

        //find directionIndex
        int northSouth = waypoint[0];
        int eastWest = waypoint[1];

        //iterating
        int turns = degree/90;
        int c = 0;
        if(right) {
            while (c < turns) {
                c++;
                int tempNorthSouth = northSouth;
                if( (northSouth >= 0 && eastWest >= 0) || (northSouth <= 0 && eastWest <= 0) ) {
                    northSouth = eastWest * -1;
                    eastWest = tempNorthSouth;
                } else {
                    northSouth = eastWest * -1;
                    eastWest = tempNorthSouth;
                }
            }
        } else {
            while (c < turns) {
                c++;
                int tempNorthSouth = northSouth;
                if( (northSouth >= 0 && eastWest >= 0) || (northSouth <= 0 && eastWest <= 0) ) {
                    northSouth = eastWest;
                    eastWest = tempNorthSouth * -1;
                } else {
                    northSouth = eastWest;
                    eastWest = tempNorthSouth * -1;
                }
            }
        }
        return new int[]{northSouth, eastWest};
    }


}
