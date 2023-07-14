package com.adventofcode.dec11;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Part1 {

    public static void main(String[] args) throws IOException, CloneNotSupportedException, InterruptedException {
        String input = Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\Coding\\Projects\\" +
                "adventofcode\\src\\com\\adventofcode\\dec11\\input.txt"));
        String[] inputArray = input.split("\n");
        List<List<CharacterCloneable>> layout = new ArrayList<>();
        for(String s : inputArray) {
            char[] array = s.trim().toCharArray();
            List<CharacterCloneable> temp = new ArrayList<>();

            for(char c : array) {
                temp.add(new CharacterCloneable(c));
            }
            layout.add(temp);
        }
        int c = 0;
        boolean flag = true;
        while(flag) {
            List<List<CharacterCloneable>> oldLayout = new ArrayList<>();

            for(List<CharacterCloneable> list : layout) {
                List<CharacterCloneable> newList = new ArrayList<>();

                for(CharacterCloneable ch : list) {
                    newList.add((CharacterCloneable) ch.clone());
                }
                oldLayout.add(newList);
            }

            for(int i = 0; i < oldLayout.size(); i++) {
                List<CharacterCloneable> row = oldLayout.get(i);

                for(int j = 0; j < row.size(); j++) {
                    boolean isTopLeft = false; boolean isTopMid = false; boolean isTopRight = false;
                    boolean isLeft = false; boolean isRight = false;
                    boolean isBottomLeft = false; boolean isBottomMid = false; boolean isBottomRight = false;

                    char topLeft = 0; char topMid = 0; char topRight = 0;
                    char left = 0; char thisSeat = row.get(j).getChar(); char right = 0;
                    char bottomLeft = 0; char bottomMid = 0; char bottomRight = 0;

                    if(i-1 >= 0 && j-1 >= 0) {
                        topLeft = oldLayout.get(i - 1).get(j - 1).getChar();
                        isTopLeft = topLeft == '#';
                    }
                    if(i-1 >= 0) {
                        topMid = oldLayout.get(i - 1).get(j).getChar();
                        isTopMid = topMid == '#';
                    }
                    if(i-1 >= 0 && j+1 < row.size()) {
                        topRight = oldLayout.get(i - 1).get(j + 1).getChar();
                        isTopRight = topRight == '#';
                    }
                    if(j-1 >= 0) {
                        left = row.get(j - 1).getChar();
                        isLeft = left == '#';
                    }
                    if(j+1 < row.size()) {
                        right = row.get(j + 1).getChar();
                        isRight = right == '#';
                    }
                    if(i + 1 < oldLayout.size() && j-1 >= 0) {
                        bottomLeft = oldLayout.get(i + 1).get(j - 1).getChar();
                        isBottomLeft = bottomLeft == '#';
                    }
                    if(i + 1 < oldLayout.size()) {
                        bottomMid = oldLayout.get(i + 1).get(j).getChar();
                        isBottomMid = bottomMid == '#';
                    }
                    if(i + 1 < oldLayout.size() && j+1 < row.size()) {
                        bottomRight = oldLayout.get(i + 1).get(j + 1).getChar();
                        isBottomRight = bottomRight == '#';
                    }

                    //if is empty
                    if(thisSeat == 'L') {

                        if(!isTopLeft && !isTopMid && !isTopRight &&
                                !isLeft && !isRight &&
                                !isBottomLeft && !isBottomMid && !isBottomRight) {
                            layout.get(i).set(j, new CharacterCloneable('#'));
                        }
                        //if is occupied
                    } else if(thisSeat == '#') {

                        int bCount = 0;
                        List<Boolean> booleanList = new ArrayList<>(Arrays.asList(isTopRight, isTopMid, isTopLeft,
                                isLeft, isRight, isBottomLeft, isBottomMid, isBottomRight));
                        for(Boolean b : booleanList) {

                            if(b) {
                                bCount++;
                            }
                        }
//                        System.out.println("This seat is " + thisSeat + " and bCount is " + bCount);

//                        System.out.println("This seat is " + thisSeat + " and there are " + bCount + " occupied seats around");
                        if(bCount >= 4) {
                            layout.get(i).set(j, new CharacterCloneable('L'));
                        }
                    }
                }
            }
            if(oldLayout.equals(layout)){
                int s = 0;
                for (List<CharacterCloneable> row : layout) {
                    for(CharacterCloneable seat : row) {
                        if(seat.getChar() == '#') {
                            s++;
                        }
                    }
                }
                System.out.println("Occupied seats: " + s);
                break;
            }
            c++;
        }
    }

    public static void print(List<List<CharacterCloneable>> list) {

        for(List<CharacterCloneable> inner : list) {

            for(CharacterCloneable c : inner) {
                System.out.print(c.getChar());
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
