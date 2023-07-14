package com.adventofcode.dec07;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static int c = 0;

    public static void main(String[] args) throws IOException {
        //read input
        String input = Files.readString(FileSystems.getDefault().getPath("src", "com", "adventofcode", "dec07",
                "input.txt").toAbsolutePath());
        String[] lines = input.split("\n");

        //set pattern for one outer bag and one or several other bags or no bags
        String patternOuter = "^\\w+\\s\\w+\\s\\w+"; // 1 group
        String patternInner = "((?<=\\bcontain\\b\\s|.\\s)\\d\\s\\w+\\s\\w+\\s\\w+|\\bno\\b\\s\\bother\\b\\s\\bbags\\b)"; // 2 groups

        //trim every raw input line
        for(int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].trim();
        }

//        Pattern r = Pattern.compile(patternOuter);
//        Matcher m = r.matcher(lines[9]);
//
//        while(m.find()) {
//            System.out.println("Found group 0: " + m.group(0));
//        }

        //map for outer bags with zero or more inner bags
        Map<String, Map<String, Integer>> rules = new HashMap<>();

        //set patterns for int, kind of bag and no bags
        String patternInt = "^\\d{1,2}";
        String patternInnerBag = "((?<=^\\d\\s|^\\d\\d\\s)\\w+\\s\\w+\\s\\w+)";
        String patternInnerNoBag = "\\bno\\b\\s\\bother\\b\\s\\bbags\\b";
        //for every ra line
        for(String s : lines) {
            //set classes for outer and several or none inner bags
            Pattern pOuter = Pattern.compile(patternOuter);
            Matcher mOuter = pOuter.matcher(s);

            Pattern pInner = Pattern.compile(patternInner);
            Matcher mInner = pInner.matcher(s);

            //map for all the inner bags
            Map<String, Integer> mapInner = new HashMap<>();
            //for every one of the inner bags
            while (mInner.find()) {
                //string for the inner bag and classes for the three patterns:
                // int, several other bags and no bags
                String group = mInner.group().trim();

                Pattern pInnerInt = Pattern.compile(patternInt);
                Matcher mInnerInt = pInnerInt.matcher(group);

                Pattern pInnerBag = Pattern.compile(patternInnerBag);
                Matcher mInnerBag = pInnerBag.matcher(group);

                Pattern pInnerNoBag = Pattern.compile(patternInnerNoBag);
                Matcher mInnerNoBag = pInnerNoBag.matcher(group);

                //find either int and with that other bags,
                // or find no int, so it has to be no bag
                int innerInt = 0;
                String innerBag = "";
                if (mInnerInt.find() && mInnerBag.find()) {
                    innerInt = Integer.parseInt(mInnerInt.group(0));
                    innerBag = mInnerBag.group(0).replaceAll("[s]$", ""); //make bags singular
                } else {
                    mInnerNoBag.find();
                    innerBag = mInnerNoBag.group(0).replaceAll("[s]$", ""); //make bags singular
                }

                //map for inner bags
                mapInner.put(innerBag, innerInt);
            }

            //nested map for outer bags with possible several inner bags
            if (mOuter.find()) {
                rules.put(mOuter.group(0).replaceAll("[s]$", ""), mapInner);
            }
        }

//        System.out.println(solutionPart1(rules, "shiny gold bag"));
        System.out.println("shiny gold bag contains " +
                solutionPart2(rules, "shiny gold bag", 1, true) + " other bags");

    }

    public static int solutionPart2(Map<String, Map<String, Integer>> outerMap, String outerBag, int bags, boolean isFirst) {
        int totalBags;
        if(isFirst) {
            totalBags = 0;
            isFirst = false;
        } else {
            totalBags = bags;
        }

        for(Map.Entry<String, Integer> entry : outerMap.get(outerBag).entrySet()) {

            if(entry.getValue() != 0) {
                totalBags += solutionPart2(outerMap, entry.getKey(), entry.getValue(), isFirst) * bags;
            } else {
                return bags;
            }
        }
        return totalBags;
    }





    public static int solutionPart1(Map<String, Map<String, Integer>> outerMap, String wantedBag) {
        c = 0;
        for(Map.Entry<String, Map<String, Integer>> entryOuter : outerMap.entrySet()) {
            Map<String, Integer> innerMap = outerMap.get(entryOuter.getKey());

            for(Map.Entry<String, Integer> entryInner : innerMap.entrySet()) {

                if(entryInner.getKey().equals(wantedBag)) {
                    System.out.println(entryInner.getKey() + " in " + entryOuter.getKey() + " is equal to " + wantedBag);
                    c++;
                    break;
                } else {
                    if(recursivePart1(outerMap, wantedBag, entryInner.getKey())) {
                        break;
                    }
                }
            }
        }
        return c;
    }

    public static boolean recursivePart1(Map<String, Map<String, Integer>> outerMap, String wantedBag, String bagToSearch) {

        Map<String, Integer> innerMap = outerMap.get(bagToSearch);
        if(innerMap != null) {
            for (Map.Entry<String, Integer> entryInner : innerMap.entrySet()) {

                if (entryInner.getKey().equals(wantedBag)) {
                    System.out.println(entryInner.getKey() + " in " + bagToSearch + " is equal to " + wantedBag);
                    c++;
                    return true;
                } else {
                    if(recursivePart1(outerMap, wantedBag, entryInner.getKey())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
