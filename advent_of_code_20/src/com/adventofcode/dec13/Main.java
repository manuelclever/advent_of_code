package com.adventofcode.dec13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\Coding\\Projects\\" +
                "adventofcode\\src\\com\\adventofcode\\dec13\\input.txt"));
        String[] tempLines = input.split("\n");
        int arriving = Integer.parseInt(tempLines[0].trim());
        String[] tempLines2 = tempLines[1].split(",");
        String[] lines = Arrays.stream(tempLines2)
                .map(String::trim)
                .toArray(String[]::new);
        int[] busses = Arrays.stream(lines)
                .filter(l -> !l.equals("x"))
                .mapToInt(Integer::parseInt)
                .toArray();

        List<List<Integer>> bussesDeparture = new ArrayList<>();
        int bus = 0;
        int c = 0;
        for (int i = 0; i < lines.length; i++) {
            if (!lines[i].equals("x")) {
                bussesDeparture.add(new ArrayList<>(Arrays.asList(Integer.parseInt(lines[i]), c)));
            }
            c++;
        }
//        bussesDeparture.stream().forEach(System.out::println);

        long time = busses[0];
        long timeFactor = busses[0];
        int count = 2;
        for(int i = 0; i < bussesDeparture.size() - 1; i++) {
            long[] temp = timeFactor(bussesDeparture, count, time, timeFactor);
            time = temp[0];
            timeFactor = temp[1];
            count++;
        }

        while (true) {
            int correct = 0;
            for (int i = 0; i < busses.length; i++) {
                int busID = bussesDeparture.get(i).get(0);
                int departureAfterT = bussesDeparture.get(i).get(1);
                if ((time + departureAfterT) % busID == 0) {
                    correct++;
                } else {
                    break;
                }
            }

            if (correct == busses.length) {
                System.out.println("correct: " + time);
                break;
            }
            time += timeFactor;
        }
    }

    public static long[] timeFactor(List<List<Integer>> bussesDeparture, int cBusses, long time, long timeFactor) {
        long[] correctSub= new long[2];
        int cCorrect = 0;

        while (true) {
            int correct = 0;
            for (int i = 0; i < cBusses; i++) {
                int busID = bussesDeparture.get(i).get(0);
                int departureAfterT = bussesDeparture.get(i).get(1);
                if ((time + departureAfterT) % busID == 0) {
                    correct++;
                } else {
                    break;
                }
            }

            if (correct == cBusses) {
                correctSub[cCorrect] = time;
                cCorrect++;
                if(cCorrect >= 2) {
                    return new long[]{correctSub[0], correctSub[1] - correctSub[0]};
                }
            }
            time += timeFactor;
        }
    }

        //Part 1
//        int time = arriving;
//        int earliestBus = 0;
//        outerLoop:
//        while(true) {
//            for(int i : busses) {
//                if(time % i == 0) {
//                    earliestBus = i;
//                    break outerLoop;
//                }
//            }
//            time++;
//        }
//        System.out.println(time);
//        System.out.println(arriving);
//        System.out.println(earliestBus);
//        System.out.println((time - arriving) * earliestBus);
//    }
}