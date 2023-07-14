package de.manuelclever.dec07;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.Arrays;

public class Calculate_07 implements Calculator {

    @Override
    public long calculatePart1() {
        int[] input = getInput();
        int median = sortAndGetMedian(input);

        return calculateLeastFuel(median, input, 1)[0];
    }

    private int[] getInput() {
        MyFileReader fr = new MyFileReader(7,1);
        return (Arrays.stream(fr.getString().split(",")).mapToInt(Integer::parseInt).toArray());
    }

    private int sortAndGetMedian(int[] input) {
        Arrays.sort(input);
        return input[input.length / 2];
    }

    private int[] calculateLeastFuel(int median, int[] input, int part) {
        int[] leastFuel = new int[]{calculateFuel(median, input, part), median};
        int pos = 1;
        int fuelBehind = Integer.MAX_VALUE;

        while(true) {

            if(median - pos > 0) {
                fuelBehind = calculateFuel(median - pos, input, part);
            }

            int fuelAhead = calculateFuel(median + pos, input, part);

            if(fuelBehind < leastFuel[0] || fuelAhead < leastFuel[0]) {
                leastFuel = new int[]{Math.min(fuelBehind, fuelAhead),
                        fuelBehind < leastFuel[0] ? median - pos : median + pos};
            } else if(fuelBehind > leastFuel[0] * 2 || fuelAhead > leastFuel[0] * 2 ||
                    median - pos < 0 && median + pos > input.length) {
                return leastFuel;
            }
            pos++;
        }
    }

    private int calculateFuel(int x, int[] input, int part) {
        return part == 1 ? calculateFuelPart1(x, input) : calculateFuelPart2(x, input);
    }

    private int calculateFuelPart1(int x, int[] input) {
        int fuel = 0;
        for (int posOfCrab : input) {
            int distance = posOfCrab - x;
            fuel += distance < 0 ? distance * -1 : distance;
        }
        return fuel;
    }

    @Override
    public long calculatePart2() {
        int[] input = getInput();
        int median = sortAndGetMedian(input);

        return calculateLeastFuel(median, input, 2)[0];
    }

    private int calculateFuelPart2(int x, int[] input) {
        int fuel = 0;
        for (int posOfCrab : input) {
            int distance = posOfCrab - x;
            fuel += distance < 0 ? summenformel(distance * -1) : summenformel(distance);
        }
        return fuel;
    }

    private int summenformel(int x) {
        return (x * (x + 1)) / 2;
    }
}
