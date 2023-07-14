package de.manuelclever.dec03;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.ArrayList;
import java.util.List;

public class Calculate_03 implements Calculator {

    @Override
    public long calculatePart1() {
        List<String> rawInput = getInput();
        List<List<Byte>> input = convertInput(rawInput);

        StringBuilder gammaBuilder = new StringBuilder();
        for(List<Byte> column : input) {
            gammaBuilder.append(mostCommon(column));
        }

        String gamma = gammaBuilder.toString();
        String epsilon = flipBits(gamma);

        return Long.parseLong(gamma, 2) * Long.parseLong(epsilon, 2);
    }

    private List<String> getInput() {
        MyFileReader fileReader = new MyFileReader(3, 1);
        return fileReader.getStringList();
    }

    private List<List<Byte>> convertInput(List<String> input) {
        int lengthOfBinary = input.get(0).length();

        List<List<Byte>> convertedInput = new ArrayList<>();
        for(int i = 0; i < lengthOfBinary; i++) {
            convertedInput.add(new ArrayList<>());
        }

        for (String line : input) {

            for(int i = 0; i < lengthOfBinary; i++) {
                String c = String.valueOf(line.charAt(i));
                convertedInput.get(i).add(Byte.parseByte(c));
            }
        }
        return convertedInput;
    }

    private byte mostCommon(List<Byte> column) {
            int c0 = 0; int c1 = 0;

            for(Byte i : column) {
                if(i == 0) {
                    c0++;
                } else {
                    c1++;
                }
            }
            return c0 > c1 ? (byte) 0 : 1;
    }

    private String flipBits(String bits) {
        StringBuilder flipped = new StringBuilder();

        for(int i = 0; i < bits.length(); i++) {
            String c = String.valueOf(bits.charAt(i));
            byte b = Byte.parseByte(c);
            byte flippedBit = b == 0 ? (byte) 1 : 0;
            flipped.append(flippedBit);
        }
        return flipped.toString();
    }

    @Override
    public long calculatePart2() {
        List<String> input = getInput();

        String oxygen = calculateRating("ox", input);
        String scrubber = calculateRating("c02", input);

        return (long) Integer.parseInt(oxygen, 2) * Integer.parseInt(scrubber, 2);
    }

    private String calculateRating(String key, List<String> input) {

        for(int i = 0; i < input.get(0).length(); i++) {
            List<String> bit0 = new ArrayList<>();
            List<String> bit1 = new ArrayList<>();

            for(String line : input) {
                int b = parseBitAt(i, line);

                if(b == 0) {
                    bit0.add(line);
                } else if(b == 1) {
                    bit1.add(line);
                }
            }

            switch(key) {
                case "ox" -> input = bit1.size() >= bit0.size() ? new ArrayList<>(bit1) : new ArrayList<>(bit0);
                case "c02" -> input = bit0.size() <= bit1.size() ? new ArrayList<>(bit0) : new ArrayList<>(bit1);
            }

            if(input.size() == 1) {
                break;
            }
        }
        return input.get(0);
    }

    private int parseBitAt(int index, String s) {
        return Integer.parseInt(String.valueOf(s.charAt(index)));
    }
}
