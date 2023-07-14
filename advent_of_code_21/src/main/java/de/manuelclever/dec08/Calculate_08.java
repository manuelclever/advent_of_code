package de.manuelclever.dec08;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.*;
import java.util.stream.Collectors;

public class Calculate_08 implements Calculator {
    List<List<List<Character>>> uniqueSignalPattern;
    List<List<List<Character>>> outputValue;

    @Override
    public long calculatePart1() {
        createInput();

        int c = 0;
        for (List<List<Character>> line : outputValue) {
            for(List<Character> number : line) {
                if(returnSimpleNumber(number) != -1) {
                    c++;
                }
            }
        }
        return c;
    }

    private void createInput() {
        MyFileReader fr = new MyFileReader(8,1);
        List<String> rawInput = fr.getStringList();

        uniqueSignalPattern = new ArrayList<>();
        outputValue = new ArrayList<>();
        for(String line : rawInput) {
            uniqueSignalPattern.add(listOfCharacters(line.split("[|]")[0]));
            outputValue.add(listOfCharacters(line.split("[|]")[1]));
        }
    }

    private List<List<Character>> listOfCharacters(String line) {
        return Arrays.stream(line.trim().split("\\s"))
                .map(s -> {
                    List<Character> chars = new ArrayList<>();
                    for(int i = 0; i < s.length(); i++) {
                        chars.add(s.charAt(i));
                    }
                    System.out.println();
                    return chars;
                })
                .collect(Collectors.toList());
    }

    private int returnSimpleNumber(List<Character> number) {
        int size = number.size();
        System.out.println(size);

        switch (size) {
            case 2 -> {return 1;}
            case 3 -> {return 7;}
            case 4 -> {return 4;}
            case 7 -> {return 8;}
            default -> {return -1;}
        }
    }

    @Override
    public long calculatePart2() {
        createInput();

        long c = 0;
        for (List<List<Character>> line : uniqueSignalPattern) {
            Map<Integer, Set<Character>> decoder = new HashMap<>();
            for(int i = 0; i < 10; i++) {
                decoder.put(i, new HashSet<>());
            }

            Map<Integer, List<Character>> simpleNumbers = new HashMap<>();
            List<List<Character>> complexNumbers = new ArrayList<>();

            for(List<Character> number : line) {
                int simpelNumber = returnSimpleNumber(number);
                if(simpelNumber != -1) {
                    simpleNumbers.put(simpelNumber, number);
                } else {
                    complexNumbers.add(number);
                }
            }

            /*
                0 = 6
                1 = 2
                2 = 5       0
                3 = 5   1       -2-
                4 = 4       3
                5 = 5   4       -5-
                6 = 6       6
                7 = 3
                8 = 7
                9 = 6
            */

            Map<Integer, List<Character>> simpleNumbersCopy = new HashMap<>(simpleNumbers);
            List<Character> intersection = intersection(simpleNumbersCopy, 1, 4, 7, 8);
            subtract(simpleNumbersCopy, intersection);

            for(Character myChar : intersection) {
                decoder.get(0).add(myChar);
                decoder.get(1).add(myChar);
                decoder.get(3).add(myChar);
                decoder.get(4).add(myChar);
                decoder.get(7).add(myChar);
                decoder.get(8).add(myChar);
                decoder.get(9).add(myChar);
            }

            /*
                0 = 4
                2 = 4      -0-
                3 = 3   1       2
                4 = 2       3
                5 = 4   4       5
                6 = 5       6
                8 = 5
                9 = 4
            */

            intersection = intersection(simpleNumbersCopy, 7, 8);
            subtract(simpleNumbersCopy, intersection);

            for(Character myChar : intersection) {
                decoder.get(0).add(myChar);
                decoder.get(2).add(myChar);
                decoder.get(3).add(myChar);
                decoder.get(5).add(myChar);
                decoder.get(6).add(myChar);
                decoder.get(7).add(myChar);
                decoder.get(8).add(myChar);
                decoder.get(9).add(myChar);
            }

            /*
                0 = 3
                2 = 3
                3 = 2  -1-      2
                4 = 2      -3-
                5 = 3   4       5
                6 = 4       6
                8 = 4
                9 = 3
            */

            intersection = intersection(simpleNumbersCopy, 4, 8);
            subtract(simpleNumbersCopy, intersection);

            for(Character myChar : intersection) {
                decoder.get(4).add(myChar);
                decoder.get(5).add(myChar);
                decoder.get(6).add(myChar);
                decoder.get(8).add(myChar);
                decoder.get(9).add(myChar);
            }

            /*
                0 = 2
                2 = 2   1       2
                3 = 1       3
                5 = 1   4       5
                6 = 2      -6-
                8 = 2
             */

            List<Character> nine = decode(1, complexNumbers, decoder.get(9));

            if(nine.size() == 1) {
                for(Character myChar : intersection) {
                    decoder.get(0).add(myChar);
                    decoder.get(2).add(myChar);
                    decoder.get(3).add(myChar);
                    decoder.get(5).add(myChar);
                    decoder.get(6).add(myChar);
                    decoder.get(8).add(myChar);
                    decoder.get(9).add(myChar);
                }
            }

            /*
                0 = 2
                2 = 1   1       2
                            3
                        4      -5-
                6 = 1
                8 = 1
             */

            List<Character> five = decode(0, complexNumbers, decoder.get(5));

            assert five != null;
            intersection = intersectionBetween(five, simpleNumbers.get(1));

            for(Character myChar : intersection) {
                decoder.get(5).add(myChar);
                decoder.get(6).add(myChar);
            }

            /*
                0 = 2
                2 = 1   1      -2-
                            3
                        4
                6 = 1
                8 = 1
             */

            List<Character> two = decode(0, complexNumbers, decoder.get(2));

            assert two != null;
            intersection = intersectionBetween(two, simpleNumbers.get(1));

            for(Character myChar : intersection) {
                decoder.get(2).add(myChar);
            }

            /*
                0 = 2
                2 = 1   1
                            3
                        4
                6 = 1
                8 = 1
             */

        }


        return 0;
    }

    private List<Character> intersection(Map<Integer, List<Character>> numbers, int ... pos) {

        List<Character> intersection = new ArrayList<>();
        for(int i = 1; i < pos.length; i++) {
            intersection = intersectionBetween(numbers.get(i - 1), numbers.get(i));
        }
        return intersection;
    }

    private List<Character> intersectionBetween(List<Character> a, List<Character> b) {
        return a.stream()
                .filter(b::contains)
                .collect(Collectors.toList());
    }

    private void subtract(Map<Integer, List<Character>> simpleNumbers, List<Character> intersection) {

        for(Map.Entry<Integer, List<Character>> entry : simpleNumbers.entrySet()) {
            for (Character c : intersection) {
                entry.getValue().remove(c);
            }
        }
    }

    private void subtract(List<Character> number, Set<Character> intersection) {

        for(Character c : number) {
            number.remove(c);
        }
    }

    private List<Character> decode(int overlap, List<List<Character>> complexNumbers,
                                   Set<Character> decoder) {
        List<Character> decoded = new ArrayList<>();

        for(List<Character> number : complexNumbers) {
            if(number.containsAll(decoder) && number.size() - decoder.size() == overlap) {
                List<Character> leftover = new ArrayList<>(number);
                subtract(leftover, decoder);
                return leftover;
            }
        }
        return null;
    }

}
