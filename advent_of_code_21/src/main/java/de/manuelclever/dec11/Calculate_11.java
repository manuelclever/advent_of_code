package de.manuelclever.dec11;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.ArrayList;
import java.util.List;

public class Calculate_11 implements Calculator {
    List<List<Integer>> octopusMap;

    @Override
    public long calculatePart1() {
        generateOctopusMap();
        return stepping(100);
    }

    private void generateOctopusMap() {
        MyFileReader fr = new MyFileReader(11,1);
        List<String> rawInput = fr.getStringList();

        octopusMap = new ArrayList<>();
        for(int i = 0; i < rawInput.size(); i++) {
            octopusMap.add(new ArrayList<>());
        }

        for(int y = 0; y < rawInput.size(); y++) {
            String line = rawInput.get(y);
            for(int x = 0; x < line.length(); x++) {
                octopusMap.get(y).add(Integer.parseInt(String.valueOf(line.charAt(x))));
            }
        }
    }

    private long stepping(int steps) {
        long flashes = 0;

        for(int i = 0; i < steps; i++) {

            for (int y = 0; y < octopusMap.size(); y++) {

                for (int x = 0; x < octopusMap.get(y).size(); x++) {
                    int energy = octopusMap.get(y).get(x);

                    energy++;
                    octopusMap.get(y).set(x, energy);

                    if (energy == 10) {
                        flashes = influenceEnergyLevelAround(y, x, flashes + 1);
                    }
                }
            }
            allFlashedAndSetToZero();
        }
        return flashes;
    }

    private long influenceEnergyLevelAround(int y, int x, long flashes) {

        for(int yFlash  = y-1; yFlash <= y+1; yFlash++) {

            for(int xFlash = x-1; xFlash <= x+1; xFlash++) {

                if (yFlash == y && xFlash == x) {
                    //ignores itself
                } else if(insideEdges(yFlash, xFlash)){
                    int energy = octopusMap.get(yFlash).get(xFlash);
                    energy++;
                    octopusMap.get(yFlash).set(xFlash, energy);

                    if(energy == 10) {
                        flashes = influenceEnergyLevelAround(yFlash, xFlash, flashes + 1);
                    }
                }
            }
        }
        return flashes;
    }

    private boolean insideEdges(int y, int x) {
        return y >= 0 && y < octopusMap.size() &&
                x >= 0 && x < octopusMap.get(y).size();
    }

    private boolean allFlashedAndSetToZero() {
        boolean allFlashed = true;
        for(int y  = 0; y < octopusMap.size(); y++) {

            for(int x = 0; x < octopusMap.get(y).size(); x++) {
                int energy = octopusMap.get(y).get(x);

                if (energy > 9) {
                    octopusMap.get(y).set(x, 0);
                } else {
                    allFlashed = false;
                }
            }
        }
        return allFlashed;
    }

    @Override
    public long calculatePart2() {
        generateOctopusMap();

        return stepPart2(Integer.MAX_VALUE);
    }

    private long stepPart2(int steps) {
        long flashes = 0;

        for(int i = 0; i < steps; i++) {

            for (int y = 0; y < octopusMap.size(); y++) {

                for (int x = 0; x < octopusMap.get(y).size(); x++) {
                    int energy = octopusMap.get(y).get(x);

                    energy++;
                    octopusMap.get(y).set(x, energy);

                    if (energy == 10) {
                        flashes = influenceEnergyLevelAround(y, x, flashes + 1);
                    }
                }
            }
            if(allFlashedAndSetToZero()) {
                return i + 1; //+1 because counting with 0, but solution counts at 1
            }
        }
        return flashes;
    }
}
