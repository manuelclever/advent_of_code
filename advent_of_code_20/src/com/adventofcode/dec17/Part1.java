package com.adventofcode.dec17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part1 {

    public static void main(String[] args) throws IOException {
        //raw input
        String[] input = Arrays.stream(Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\" +
                "Dokumente\\Coding\\Projects\\adventofcode\\src\\com\\adventofcode\\dec17\\input.txt"))
                .split("\r\n")).map(String::trim).toArray(String[]::new);

        //input -> Map of z,y,x
        Map<Integer,Map<Integer,Character>> mapTempY = new TreeMap<>();
        for (int i = 0; i < input.length; i++) {
            List<Character> chars = input[i].chars().mapToObj(c -> (char) c).collect(Collectors.toList());
            Map<Integer,Character> mapTempX = IntStream.range(0, chars.size()).boxed().collect(Collectors.toMap(n -> n, chars::get));
            mapTempY.put(i, mapTempX);
        }
        Map<Integer,Map<Integer,Map<Integer,Character>>> mapZ = new TreeMap<>();
        mapZ.put(0, mapTempY);

        System.out.println("Cycle: 0");
        print(mapZ);

        for(int i = 1; i <= 6; i++) {
            Map<Integer,Map<Integer,Map<Integer,Character>>> mapZCopy = new TreeMap<>(mapZ);

            //sizing the map
            //get smaller and bigger values
            int minZ = mapZ.keySet().stream().min(Integer::compareTo).get() - 1;
            int maxZ = mapZ.keySet().stream().max(Integer::compareTo).get() + 1;
            int minY = mapZ.get(0).keySet().stream().min(Integer::compareTo).get() - 1;
            int maxY = mapZ.get(0).keySet().stream().max(Integer::compareTo).get() + 1;
//            System.out.println("minZ: " + minZ + ", maxZ: " + maxZ + ", minY: " + minY + ", maxY: " + maxY);

            //create template for empty yMap and zMap
            Map<Integer,Map<Integer,Character>> templateY = new TreeMap<>();
            Map<Integer,Character> templateX = new TreeMap<>();
            IntStream.range(minY, maxY +1).boxed().forEach(j -> templateX.put(j, '.')); //+1,because end is exclusive, see java documentation
            IntStream.range(minY, maxY +1).boxed().forEach(j -> templateY.put(j, templateX)); //both with yValues, because each z is a square

            for(Integer keyZ : mapZ.keySet()) {
                Map<Integer,Map<Integer,Character>> mapYSizing = mapZ.get(keyZ); //for iterating
                Map<Integer,Map<Integer,Character>> mapYSizingCopy = new TreeMap<>(mapYSizing); //for changing

                for(Integer keyY : mapYSizing.keySet()) {
                    Map<Integer,Character> mapXSizing = mapYSizing.get(keyY); //for iterating
                    Map<Integer,Character> mapXSizingCopy = new TreeMap<>(mapXSizing); //for changing
                    //adding xPositions
                    mapXSizingCopy.put(minY, '.');
                    mapXSizingCopy.put(maxY, '.');
                    mapYSizingCopy.put(keyY, mapXSizingCopy);
                }
                //adding empty yMaps
                mapYSizingCopy.put(minY, templateX);
                mapYSizingCopy.put(maxY, templateX);
                mapZCopy.put(keyZ, mapYSizingCopy);
            }
            //adding empty zMaps
            mapZCopy.put(minZ, templateY);
            mapZCopy.put(maxZ, templateY);

            mapZ = new TreeMap<>(mapZCopy);

            //changing values
            for (Integer keyZ : mapZ.keySet()) {
                Map<Integer, Map<Integer, Character>> mapY = mapZ.get(keyZ);
                Map<Integer, Map<Integer, Character>> mapYCopy = new TreeMap<>(mapY);

                for (Integer keyY : mapY.keySet()) {
                    Map<Integer, Character> mapX = mapY.get(keyY);
                    Map<Integer, Character> mapXCopy = new TreeMap<>(mapX);

                    for (Integer keyX : mapX.keySet()) {
                        char c = mapX.get(keyX);
                        int active = checkActive(keyZ, keyY, keyX, mapZ);

                        if (c == '#' && active != 2 && active != 3 ) {
                            mapXCopy.put(keyX, '.');

                        } else if (c == '.' && active == 3) {
                            mapXCopy.put(keyX, '#');
                        }
                    }
                    mapYCopy.put(keyY, mapXCopy);
                }
                mapZCopy.put(keyZ, mapYCopy);
            }
            mapZ = new TreeMap<>(mapZCopy);
            System.out.println("Cycle: " + i);
            print(mapZ);
        }
        //count actives after all rounds
        int actives = 0;
        for (Integer keyZ : mapZ.keySet()) {
            Map<Integer, Map<Integer, Character>> mapY = mapZ.get(keyZ);

            for (Integer keyY : mapY.keySet()) {
                Map<Integer, Character> mapX = mapY.get(keyY);

                for (char c : mapX.values()) {
                    if(c == '#'){
                        actives++;
                    }

                }
            }
        }
        System.out.println(actives);
    }

    public static int checkActive(int keyZ, int keyY, int keyX,
                                  Map<Integer,Map<Integer,Map<Integer,Character>>> mapZ) {
        List<Integer[]> list = new ArrayList<>();
        list.add(new Integer[]{keyZ, keyY, keyX});

        //add all Possibilities to List
        for(int i = 2; i >= 0; i--) { //3x loop, because 3 coordinates
            List<Integer[]> tempList = new ArrayList<>();
            for (Integer[] pos : list) {
                Integer[] smaller = new Integer[]{i == 0 ? pos[0] -1: pos[0], i == 1 ? pos[1]-1 : pos[1], i == 2 ? pos[2]-1 : pos[2]};
                Integer[] bigger = new Integer[]{i == 0 ? pos[0] +1: pos[0], i == 1 ? pos[1]+1 : pos[1], i == 2 ? pos[2]+1 : pos[2]};
                tempList.add(smaller);
                tempList.add(bigger);
            }
            list.addAll(tempList);
        }

        //check who's active of all the positions
        int active = 0;
        for(Integer[] pos : list) {
            //count only if char is active and not the source
            if(mapZ.containsKey(pos[0]) &&
                    mapZ.get(pos[0]).containsKey(pos[1]) &&
                    mapZ.get(pos[0]).get(pos[1]).containsKey(pos[2]) &&
                    mapZ.get(pos[0]).get(pos[1]).get(pos[2]) == '#' &&
                    ( pos[0] != keyZ || pos[1] != keyY || pos[2] != keyX )) {
                active += 1;
            }
        }
        return active;
    }

    private static void print(Map<Integer,Map<Integer,Map<Integer,Character>>> mapZ) {

        for (Integer key : mapZ.keySet()) {
            Map<Integer,Map<Integer,Character>> mapY = mapZ.get(key);
            System.out.println("z: " + key);
            System.out.print("\t");
            for(Integer keyY : mapY.keySet()) {
                Map<Integer,Character> mapX = mapY.get(keyY);
                System.out.print(keyY + ": ");
                mapX.forEach((k,v) -> System.out.print(v));
                System.out.println("");
                System.out.print("\t");
            }
            System.out.println("");
        }
    }
}