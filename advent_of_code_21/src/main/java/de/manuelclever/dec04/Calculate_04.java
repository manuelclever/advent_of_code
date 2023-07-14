package de.manuelclever.dec04;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculate_04 implements Calculator {
    String[] drawn;
    List<Matrix> matrices;

    @Override
    public long calculatePart1() {
        createDrawnNumbersAndMatrices();

        for(String s : drawn) {
            int numberDrawn = Integer.parseInt(s);

            for (Matrix matrix : matrices) {
                matrix.crossNumber(numberDrawn);

                if(matrix.checkForBingo() != -1) {
                    return (long) matrix.calculateSum() * numberDrawn;
                }
            }
        }

        return 0;
    }

    private void createDrawnNumbersAndMatrices() {
        MyFileReader fileReader = new MyFileReader(4,1);
        BufferedReader br = fileReader.createBufferedReader();

        try {
            String firstLine = br.readLine();
            drawn = firstLine.split(",");
            br.readLine(); //get empty line out the way

            matrices = readMatrices(br);
        } catch (IOException e) {
            System.out.println("Couldn't read input. Calculation terminated.");
        }
    }

    private List<Matrix> readMatrices(BufferedReader br) {
        List<Matrix> matrices = new ArrayList<>();

        try {
            while(true) {
                createMatrix(br, matrices);
            }
        } catch (IOException e) {
            return matrices;
        }
    }

    private void createMatrix(BufferedReader br, List<Matrix> matrices) throws IOException {
        Matrix matrix = new Matrix();
        while (true) {
            String line = br.readLine();

            if (line == null ) {
                matrices.add(matrix);
                throw new IOException();
            }
            if (line.equals("")) {
                break;
            }

            Scanner scanner = new Scanner(line);

            matrix.nextRow();
            while (scanner.hasNext()) {
                matrix.addEntry(scanner.nextInt());
            }
        }
        matrices.add(matrix);
    }

    @Override
    public long calculatePart2() {
        createDrawnNumbersAndMatrices();

        for(String s : drawn) {
            int numberDrawn = Integer.parseInt(s);

            for (Matrix matrix : matrices) {
                matrix.crossNumber(numberDrawn);
            }

            List<Matrix> newMatrices = new ArrayList<>(matrices);
            for (Matrix matrix : matrices) {

                if (matrix.checkForBingo() != -1) {

                    if (newMatrices.size() != 1) {
                        newMatrices.remove(matrix);
                    } else {
                        return (long) matrix.calculateSum() * numberDrawn;
                    }
                }
            }
            matrices = newMatrices;
        }
        return 0;
    }
}
