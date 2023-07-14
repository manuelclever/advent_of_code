package com.adventofcode.dec12;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Part1 {

    public static void main(String[] args) throws IOException {
        String input = Files.readString(FileSystems.getDefault().getPath("src", "com", "adventofcode", "dec12",
                "input.txt").toAbsolutePath());
        String[] tempLines = input.split("\n");
        String[] lines = Arrays.stream(tempLines).map(String::trim).toArray(String[]::new);

        char direction = 'E'; int northSouth = 0; int eastWest = 0;
        for(String s : lines) {
            char moving = s.charAt(0);
            int quantity = Integer.parseInt(s.substring(1));

            switch(moving) {
                case 'N':
                    northSouth += quantity;
                    break;
                case 'E':
                    eastWest += quantity;
                    break;
                case 'S':
                    northSouth -= quantity;
                    break;
                case 'W':
                    eastWest -= quantity;
                    break;
                case 'L':
                    direction = turn(direction, quantity, false);
                    break;
                case 'R':
                    direction = turn(direction, quantity, true);
                    break;
                case 'F':
                    switch(direction) {
                        case 'N':
                            northSouth += quantity;
                            break;
                        case 'E':
                            eastWest += quantity;
                            break;
                        case 'S':
                            northSouth -= quantity;
                            break;
                        case 'W':
                            eastWest -= quantity;
                            break;
                    }
                    break;
            }
            System.out.println(s);
            System.out.println("\t" + northSouth + " " + eastWest);
        }
        System.out.println("final: " + northSouth + " " + eastWest);

        int result = (northSouth > 0 ? northSouth : northSouth * -1)  + (eastWest > 0 ? eastWest : eastWest * -1);
        System.out.println("result: " + result);
    }

    public static char turn(char direction, int degree, boolean right) {
        char[] directions = new char[]{'N', 'E', 'S', 'W'};

        //find directionIndex
        int directionIndex = 0;
        for(int i = 0; i < directions.length; i++) {
            if(directions[i] == direction) {
                directionIndex = i;
            }
        }

        //iterating
        int turns = degree/90;
        int c = 0;
        if(right) {
            while (c < turns) {
                c++;
                directionIndex++;
                if (directionIndex >= directions.length) {
                    directionIndex = 0;
                }
            }
        } else {
            while (c < turns) {
                c++;
                directionIndex--;
                if (directionIndex < 0) {
                    directionIndex = directions.length - 1;
                }
            }
        }
        return directions[directionIndex];
    }


}
