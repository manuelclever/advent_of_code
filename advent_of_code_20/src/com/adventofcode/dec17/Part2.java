package com.adventofcode.dec17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part2 {

    public static void main(String[] args) throws IOException {
        //raw input
        String[] input = Arrays.stream(Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\" +
                "Dokumente\\Coding\\Projects\\adventofcode\\src\\com\\adventofcode\\dec17\\input.txt"))
                .split("\r\n")).map(String::trim).toArray(String[]::new);

        //input -> Map of w,z,y,x
        Map<Integer,Map<Integer,Character>> mapTempY = new TreeMap<>();
        for (int i = 0; i < input.length; i++) {
            List<Character> chars = input[i].chars().mapToObj(c -> (char) c).collect(Collectors.toList());
            Map<Integer,Character> mapTempX = IntStream.range(0, chars.size()).boxed().collect(Collectors.toMap(n -> n, chars::get));
            mapTempY.put(i, mapTempX);
        }
        Map<Integer,Map<Integer,Map<Integer,Character>>> mapZTemp = new TreeMap<>();
        mapZTemp.put(0, mapTempY);
        Map<Integer,Map<Integer,Map<Integer,Map<Integer,Character>>>> mapW = new TreeMap<>();
        mapW.put(0, mapZTemp);

        System.out.println("Cycle: 0");
        print(mapW);

        //cycle loop
        for(int i = 1; i <= 6; i++) {
            Map<Integer,Map<Integer,Map<Integer,Map<Integer,Character>>>> mapWCopy = new TreeMap<>(mapW);

            //sizing the map
            //get smaller and bigger values
            int minW = mapW.keySet().stream().min(Integer::compareTo).get() - 1;
            int maxW = mapW.keySet().stream().max(Integer::compareTo).get() + 1;
            int minZ = mapW.get(0).keySet().stream().min(Integer::compareTo).get() - 1;
            int maxZ = mapW.get(0).keySet().stream().max(Integer::compareTo).get() + 1;
            int minY = mapW.get(0).get(0).keySet().stream().min(Integer::compareTo).get() - 1;
            int maxY = mapW.get(0).get(0).keySet().stream().max(Integer::compareTo).get() + 1;

            //create template for empty yMap and zMap
            Map<Integer,Map<Integer,Map<Integer,Character>>> templateZ = new TreeMap<>();
            Map<Integer,Map<Integer,Character>> templateY = new TreeMap<>();
            Map<Integer,Character> templateX = new TreeMap<>();
            IntStream.range(minY, maxY +1).boxed().forEach(j -> templateX.put(j, '.')); //+1,because end is exclusive, see java documentation
            IntStream.range(minY, maxY +1).boxed().forEach(j -> templateY.put(j, templateX)); //both with yValues, because each z is a square
            IntStream.range(minZ, maxZ +1).boxed().forEach(j -> templateZ.put(j, templateY));

            //add new empty xPositions and all empty templates
            for(Integer keyW : mapW.keySet()) {
                Map<Integer, Map<Integer, Map<Integer, Character>>> mapZSizing = mapW.get(keyW); //for iterating
                Map<Integer, Map<Integer, Map<Integer, Character>>> mapZSizingCopy = new TreeMap<>(mapZSizing); //for changing

                for (Integer keyZ : mapZSizing.keySet()) {
                    Map<Integer, Map<Integer, Character>> mapYSizing = mapZSizing.get(keyZ); //for iterating
                    Map<Integer, Map<Integer, Character>> mapYSizingCopy = new TreeMap<>(mapYSizing); //for changing

                    for (Integer keyY : mapYSizing.keySet()) {
                        Map<Integer, Character> mapXSizing = mapYSizing.get(keyY); //for iterating
                        Map<Integer, Character> mapXSizingCopy = new TreeMap<>(mapXSizing); //for changing
                        //adding xPositions
                        mapXSizingCopy.put(minY, '.');
                        mapXSizingCopy.put(maxY, '.');
                        mapYSizingCopy.put(keyY, mapXSizingCopy);
                    }
                    //adding empty yMaps
                    mapYSizingCopy.put(minY, templateX);
                    mapYSizingCopy.put(maxY, templateX);
                    mapZSizingCopy.put(keyZ, mapYSizingCopy);
                }
                //adding empty zMaps
                mapZSizingCopy.put(minZ, templateY);
                mapZSizingCopy.put(maxZ, templateY);
                mapWCopy.put(keyW, mapZSizingCopy);
            }
            //adding empty wMaps
            mapWCopy.put(minW, templateZ);
            mapWCopy.put(maxW, templateZ);

            mapW = new TreeMap<>(mapWCopy);

            //changing active/inactive states
            for(Integer keyW : mapW.keySet()) {
                Map<Integer,Map<Integer, Map<Integer, Character>>> mapZ = mapW.get(keyW);
                Map<Integer,Map<Integer, Map<Integer, Character>>> mapZCopy = new TreeMap<>(mapZ);

                for (Integer keyZ : mapZ.keySet()) {
                    Map<Integer, Map<Integer, Character>> mapY = mapZ.get(keyZ);
                    Map<Integer, Map<Integer, Character>> mapYCopy = new TreeMap<>(mapY);

                    for (Integer keyY : mapY.keySet()) {
                        Map<Integer, Character> mapX = mapY.get(keyY);
                        Map<Integer, Character> mapXCopy = new TreeMap<>(mapX);

                        for (Integer keyX : mapX.keySet()) {
                            char c = mapX.get(keyX);
                            int active = checkActive(keyW, keyZ, keyY, keyX, mapW);

                            if (c == '#' && active != 2 && active != 3) {
                                mapXCopy.put(keyX, '.');
                            } else if (c == '.' && active == 3) {
                                mapXCopy.put(keyX, '#');
                            }
                        }
                        mapYCopy.put(keyY, mapXCopy);
                    }
                    mapZCopy.put(keyZ, mapYCopy);
                }
                mapWCopy.put(keyW, mapZCopy);
            }
            mapW = new TreeMap<>(mapWCopy);
            System.out.println("Cycle: " + i);
            print(mapW);
        }

        //count actives after all rounds
        int actives = 0;
        for(Integer keyW : mapW.keySet()) {
            Map<Integer,Map<Integer, Map<Integer, Character>>> mapZ = mapW.get(keyW);

            for (Integer keyZ : mapZ.keySet()) {
                Map<Integer, Map<Integer, Character>> mapY = mapZ.get(keyZ);

                for (Integer keyY : mapY.keySet()) {
                    Map<Integer, Character> mapX = mapY.get(keyY);

                    for (char c : mapX.values()) {
                        if (c == '#') {
                            actives++;
                        }

                    }
                }
            }
        }
        System.out.println(actives);
    }

    public static int checkActive(int keyW, int keyZ, int keyY, int keyX,
                                  Map<Integer,Map<Integer,Map<Integer,Map<Integer,Character>>>> mapW) {
        List<Integer[]> list = new ArrayList<>();
        list.add(new Integer[]{keyW, keyZ, keyY, keyX});

        //add all Possibilities to List
        for(int i = 3; i >= 0; i--) { //4x loop, because 4 coordinates
            List<Integer[]> tempList = new ArrayList<>();
            for (Integer[] pos : list) {
                Integer[] smaller = new Integer[]{i == 0 ? pos[0] -1: pos[0], i == 1 ? pos[1]-1 : pos[1]
                                                 , i == 2 ? pos[2]-1 : pos[2], i == 3 ? pos[3]-1 : pos[3]};
                Integer[] bigger = new Integer[]{i == 0 ? pos[0] +1: pos[0], i == 1 ? pos[1]+1 : pos[1]
                                                , i == 2 ? pos[2]+1 : pos[2], i == 3 ? pos[3]+1 : pos[3]};
                tempList.add(smaller);
                tempList.add(bigger);
            }
            list.addAll(tempList);
        }
        //check who's active of all the positions
        int active = 0;
        for(Integer[] pos : list) {
            //count only if char is active and not the source
            if(mapW.containsKey(pos[0]) &&
                    mapW.get(pos[0]).containsKey(pos[1]) &&
                    mapW.get(pos[0]).get(pos[1]).containsKey(pos[2]) &&
                    mapW.get(pos[0]).get(pos[1]).get(pos[2]).containsKey(pos[3]) &&
                    mapW.get(pos[0]).get(pos[1]).get(pos[2]).get(pos[3]) == '#' &&
                    ( pos[0] != keyW || pos[1] != keyZ || pos[2] != keyY || pos[3] != keyX )) {
                active += 1;
            }
        }
        return active;
    }

    private static void print(Map<Integer,Map<Integer,Map<Integer,Map<Integer,Character>>>> mapW) {

        for(Integer keyW : mapW.keySet()) {
            Map<Integer,Map<Integer,Map<Integer,Character>>> mapZ = mapW.get(keyW);
            for (Integer keyZ : mapZ.keySet()) {
                Map<Integer, Map<Integer, Character>> mapY = mapZ.get(keyZ);
                System.out.println("w: " + keyW + ", z: " + keyZ);
                System.out.print("\t");
                for (Integer keyY : mapY.keySet()) {
                    Map<Integer, Character> mapX = mapY.get(keyY);
                    System.out.print(keyY + ": ");
                    mapX.forEach((k, v) -> System.out.print(v));
                    System.out.println("");
                    System.out.print("\t");
                }
                System.out.println("");
            }
        }
    }
}