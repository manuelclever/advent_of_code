package de.manuelclever.dec06;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.*;
import java.util.stream.Collectors;

public class Calculate_06 implements Calculator {

    @Override
    public long calculatePart1() {
        return simulate(80, getInput());
    }

    private Map<Integer, Long> getInput() {
        MyFileReader fileReader = new MyFileReader(6,1);
        List<Integer> fish = Arrays.stream(fileReader.getStringList().get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Map<Integer, Long> countFish = new HashMap<>();
        for(Integer daysUntilReproducing : fish) {
            countFish.merge(daysUntilReproducing, 1L, Long::sum);
        }
        return countFish;
    }

    private long simulate(int days, Map<Integer, Long> fish) {

        for(int i = 0; i < days; i++) {
            Map<Integer, Long> fishNextDay = new HashMap<>();

            for(Map.Entry<Integer, Long> entry : fish.entrySet()) {
                fishNextDay.merge(entry.getKey() - 1 < 0 ? 6 : entry.getKey() - 1, entry.getValue(), Long::sum);
            }

            fish.merge(0, 0L, Long::sum); //makes sure key 0 exists
            fishNextDay.put(8, fish.get(0));

            fish = fishNextDay;
        }

        long count = 0;
        for(Map.Entry<Integer, Long> entry : fish.entrySet()) {
            count += entry.getValue();
        }
        return count;
    }

    @Override
    public long calculatePart2() {
        return simulate(256, getInput());
    }
}
