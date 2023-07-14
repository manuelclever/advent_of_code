package com.adventofcode.dec16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        //dont forget to add an empty line after input in txt file
        String input = Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\Coding\\Projects\\" +
                "adventofcode\\src\\com\\adventofcode\\dec16\\input.txt"));
        String[] threeWay = input.split("\r\n\r\n");

        //get Rules
        Pattern pRules = Pattern.compile("(\\w+\\s?\\w*).\\s(\\d+).(\\d+)\\s\\w+\\s(\\d+).(\\d+)");
        Matcher mRules = pRules.matcher(threeWay[0]);
        Map<String, int[]> allRules = new HashMap<>();
        while(mRules.find()) {
//            System.out.println("found: " +
//                    mRules.group(1) + ", " +
//                    mRules.group(2) + ", " +
//                    mRules.group(3) + ", " +
//                    mRules.group(4) + ", " +
//                    mRules.group(5) + ", ");
            int[] numbers = new int[4];
            numbers[0] = Integer.parseInt(mRules.group(2));
            numbers[1] = Integer.parseInt(mRules.group(3));
            numbers[2] = Integer.parseInt(mRules.group(4));
            numbers[3] = Integer.parseInt(mRules.group(5));
            allRules.put(mRules.group(1), numbers);
        }
//        allRules.forEach((k,v) -> {System.out.print("key: " + k + " value: "); Arrays.stream(v).forEach(System.out::print);});

        //get myT
        Pattern pMyT = Pattern.compile("\\d+");
        Matcher mMyT = pMyT.matcher(threeWay[1]);
        List<Integer> myT = new ArrayList<>();
        while(mMyT.find()) {
            myT.add(Integer.parseInt(mMyT.group(0)));
        }

        //get nearbyTickets
        Pattern pNearbyT = Pattern.compile("(\\d+.)+(\\d+)(?=\\r\\n)");
        Matcher mNearbyT = pNearbyT.matcher(threeWay[2]);
        List<List<Integer>> allNearbyT = new ArrayList<>();
        while(mNearbyT.find()) {
            Pattern pTemp = Pattern.compile("\\d+");
            Matcher mTemp = pTemp.matcher(mNearbyT.group(0));
            List<Integer> list = new ArrayList<>();

            while(mTemp.find()) {
                list.add(Integer.parseInt(mTemp.group(0)));
            }
            allNearbyT.add(list);
        }

        //Part 2
        //remove invalid Tickets
        List<List<Integer>> validTickets = new ArrayList<>();
        for(List<Integer> list : allNearbyT) {

            int allowedCount = 0;
            for(int val : list) {
                int deniedRules = 0;
                for(String key : allRules.keySet()) {
                    int[] between = allRules.get(key);
                    int min1 = between[0];
                    int max1 = between[1];
                    int min2 = between[2];
                    int max2 = between[3];

                    if( (val >= min1 && val <= max1 ) || (val >= min2  && val <= max2) ) {
                        break;
                    } else {
                        deniedRules++;
                    }
                }
                if(deniedRules < allRules.size()) {
                    allowedCount++;
                }
            }
            if(allowedCount == list.size()) {
                validTickets.add(list);
            }
        }

        //create List for each rule, set for each number if true or not
        Map<String, List<Map<Integer, Boolean>>> isRule = new HashMap<>();
        for(String key : allRules.keySet()) {
            int[] between = allRules.get(key);
            int min1 = between[0];
            int max1 = between[1];
            int min2 = between[2];
            int max2 = between[3];

            List<Map<Integer, Boolean>> listTemp = new ArrayList<>();
            for (List<Integer> list : validTickets) {
                Map<Integer, Boolean> temp = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {

                    int val = list.get(i);
                    if( (val >= min1 && val <= max1 ) || (val >= min2  && val <= max2) ) {
                        temp.put(i, true);
                    } else {
                        temp.put(i, false);
                    }
                }
                listTemp.add(temp);
            }
            isRule.put(key, listTemp);
        }

//        isRule.forEach((k,v) -> System.out.println(k + " : " + v));

        //create map which contains all classes for each column
        Map<Integer, Set<String>> couldBeMap = new HashMap<>();
        for(int i = 0; i < myT.size(); i++) {
            couldBeMap.put(i, isRule.keySet());
        }

        //test which positions could be which key and put in map
        for(int i = 0; i < myT.size(); i++) {

            for(String key : isRule.keySet()) {
                List<Map<Integer, Boolean>> list = isRule.get(key);

                int trueCount = 0;
                for(Map<Integer, Boolean> map : list) {
                    if(map.get(i)) {
                        trueCount++;
                    }
                }
                if(trueCount != list.size()) {
                    Set<String> tempSet = new HashSet<>(couldBeMap.get(i));
                    tempSet.remove(key);
                    couldBeMap.put(i, tempSet);
                }
            }
        }
        int stop = 0;
        while(true) {
            Map<Integer, Set<String>> couldBeMapNew = new HashMap<>(couldBeMap);
            for (int i = 0; i < couldBeMap.size(); i++) {

                if (couldBeMap.get(i).size() == 1) {
                    Object[] temp = couldBeMap.get(i).toArray();
                    String toRemove = (String) temp[0];
                    for (Set<String> set : couldBeMapNew.values()) {
                        if(!set.equals(couldBeMap.get(i)))
                        set.remove(toRemove);
                    }
                }
            }

            couldBeMap = new HashMap<>(couldBeMapNew);

            int c = 0;
            for(Set<String> set : couldBeMap.values()) {

                if(set.size() == 1) {
                    c++;
                }
            }

            stop++;
            if(c == couldBeMap.size() || stop == 20) {
                break;
            }
        }
        couldBeMap.forEach((k,v) -> System.out.println("key: " + k + ", pos: " + v));

        List<Integer> solutionList = new ArrayList<>();
        for(int key : couldBeMap.keySet()) {
            for(String s : couldBeMap.get(key)) {
                if(s.contains("departure")) {
                    solutionList.add(key);
                    break;
                }
            }
        }

        long sum = 1;
        for(int i : solutionList) {
            System.out.println(i + ": " + myT.get(i));
            sum*= myT.get(i);
        }
        System.out.println("Sum: " + sum);

        /*Part 1
        List<Integer> allWrong = new ArrayList<>();
        for(List<Integer> list : allNearbyT) {

            for(int val : list) {
                int deniedRules = 0;
                for(int[] between : allRules.values()) {
                    int min1 = between[0];
                    int max1 = between[1];
                    int min2 = between[2];
                    int max2 = between[3];

                    if( (val >= min1 && val <= max1 ) || (val >= min2  && val <= max2) ) {
                        break;
                    } else {
                        deniedRules++;
                    }
                }

                if(deniedRules == allRules.size()) {

                    allWrong.add(val);
                }
            }
        }
        System.out.println(allWrong.stream().mapToInt(Integer::intValue).sum());

         */
    }
}
