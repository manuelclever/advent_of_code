package de.manuelclever.dec02;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.List;

public class Calculate_02 implements Calculator {

    @Override
    public long calculatePart1() {
        List<String> rawInput = getInput();
        long[] pos = new long[]{0,0}; //x,z

        for (String line : rawInput) {
            String[] split = line.split("\\s");

            String direction = split[0];
            int distance = Integer.parseInt(split[1]);

            switch(direction) {
                case "forward" -> pos[0] += distance;
                case "down" -> pos[1] += distance;
                case "up" -> pos[1] -= distance;
            }
        }
        return pos[0] * pos[1];
    }

    private List<String> getInput() {
        MyFileReader fileReader = new MyFileReader(2,1);
        List<String> rawInput = fileReader.getStringList();

        return rawInput;
    }

    @Override
    public long calculatePart2() {
        List<String> rawInput = getInput();
        long[] pos = new long[]{0,0,0}; //x,z,aim

        for (String line : rawInput) {
            String[] split = line.split("\\s");

            String direction = split[0];
            long value = Long.parseLong(split[1]);

            switch(direction) {
                case "forward" -> { pos[0] += value; pos[1] += pos[2] * value;}
                case "down" -> pos[2] += value;
                case "up" -> pos[2] -= value;
            }
        }
        return pos[0] * pos[1];
    }
}
