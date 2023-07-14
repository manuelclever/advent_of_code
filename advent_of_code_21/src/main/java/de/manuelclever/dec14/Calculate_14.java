package de.manuelclever.dec14;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Calculate_14 implements Calculator {
    StringBuilder polymer;
    Map<String, String> insertionRules;
    Map<String,Long> polymerShort;

    @Override
    public long calculatePart1() {
        generatePolymerAndRules();
        generatePolymerAsMap();
        step(10);

        return calculateSolution();
    }

    private void generatePolymerAndRules() {
        MyFileReader fr = new MyFileReader(14, 1);
        BufferedReader br = fr.createBufferedReader();

        try {
            polymer = new StringBuilder(br.readLine());
            br.readLine();

            insertionRules = new HashMap<>();
            while (true) {
                String[] rule = br.readLine().split(" -> ");
                insertionRules.put(rule[0], rule[1]);
            }

        } catch (IOException | NullPointerException ignore) {}
    }

    private void generatePolymerAsMap() {
        polymerShort = new HashMap<>();

        for(int i = 0; i < polymer.length()-1; i++) {
            String pair = String.valueOf(polymer.charAt(i)) + String.valueOf(polymer.charAt(i+1));
            polymerShort.merge(pair, 1L, Long::sum);
        }
    }

    private void step(int steps) {

        for(int i = 0; i < steps; i++) {
            Map<String,Long> changes = new HashMap<>();

            for(Map.Entry<String,Long> entry : polymerShort.entrySet()) {

                for(Map.Entry<String,String> rule : insertionRules.entrySet()) {

                    if(entry.getKey().equals(rule.getKey()) && entry.getValue() > 0) {
                        String newPair1 = rule.getKey().charAt(0) + rule.getValue();
                        String newPair2 = rule.getValue() + rule.getKey().charAt(1);

                        changes.merge(newPair1, entry.getValue(), Long::sum);
                        changes.merge(newPair2, entry.getValue(), Long::sum);
                        changes.merge(entry.getKey(), -entry.getValue(), Long::sum);
                    }
                }
            }
            for(Map.Entry<String,Long> change : changes.entrySet()) {
                polymerShort.merge(change.getKey(), change.getValue(), Long::sum);
            }
        }
    }

    private long calculateSolution() {
        Map<Character, Long> occurences = new HashMap<>();

        for(Map.Entry<String,Long> entry : polymerShort.entrySet()) {
            char element1 = entry.getKey().charAt(0);
            char element2 = entry.getKey().charAt(1);

            occurences.merge(element1, entry.getValue(), Long::sum);
            occurences.merge(element2, entry.getValue(), Long::sum);
        }

        //all characters have been counted twice, except first and last character
        occurences.replaceAll((k, v) -> occurences.get(k) / 2);
        occurences.merge(polymer.charAt(0), 1L, Long::sum); //adds a count for character at start
        occurences.merge(polymer.charAt(polymer.length()-1), 1L, Long::sum); //adds a count for character at end

        List<Map.Entry<Character,Long>> sorted = occurences.entrySet().stream()
                .sorted((o1, o2) -> {
                    if (o1.getValue() > o2.getValue()) {
                        return -1;
                    } else if (o1.getValue() < o2.getValue()) {
                        return 1;
                    } return 0;})
                .collect(Collectors.toList());

        return sorted.get(0).getValue() - sorted.get(sorted.size()-1).getValue();
    }

        @Override
    public long calculatePart2() {
            generatePolymerAndRules();
            generatePolymerAsMap();
            step(40);

            return calculateSolution();
    }
}
