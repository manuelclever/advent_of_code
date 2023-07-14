package de.manuelclever;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        execute();
    }

    private static void execute() {
        Scanner scanner = new Scanner(System.in);
        int day;
        int part;

        loop: {
            while (true) {
                System.out.print("Please enter day and part separated by comma (day,part) or 'q' to quit: ");
                while (true) {
                    if (scanner.hasNext()) {
                        String input = scanner.next();

                        if(input.equalsIgnoreCase("q")) {
                            break loop;
                        }

                        String[] inputSplit = input.split(",");
                        try {
                            day = Integer.parseInt(inputSplit[0]);
                            part = Integer.parseInt(inputSplit[1]);

                            if (day < 1 || day > 31 || part < 1 || part > 2) {
                                throw new NumberFormatException();
                            }
                            break;
                        } catch (Exception e) {
                            System.out.print("The input couldn't be parsed. Please enter a day (1-31) and a part (1-2), " +
                                    "separated by a comma: ");
                        }
                    }
                }

                try {
                    Calculator calculator = Factory.createCalculator(day);

                    long startTime;
                    long endTime;
                    long solution;
                    if (part == 1) {
                        startTime = System.currentTimeMillis();
                        solution = calculator.calculatePart1();
                        endTime = System.currentTimeMillis();

                    } else {
                        startTime = System.currentTimeMillis();
                        solution = calculator.calculatePart2();
                        endTime = System.currentTimeMillis();
                    }
                    System.out.println("Solution to day " + day + ", part " + part + ": " + solution);
                    System.out.println("Execution time: " + (endTime - startTime) + " ms");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private static void getInput() {

    }
}
