package de.manuelclever.dec04;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private List<List<Integer>> matrix;
    private int currentRow;

    public Matrix() {
        this.matrix = new ArrayList<>();
        this.currentRow = -1;
    }

    public void nextRow(){
        currentRow += 1;
        matrix.add(new ArrayList<>());
    }

    public void addEntry(int entry) {
        matrix.get(currentRow).add(entry);
    }

    public void crossNumber(int drawn) {

        for(int row = 0; row < matrix.size(); row++) {
            for(int column = 0; column < matrix.size(); column++) {
                if(matrix.get(row).get(column) == drawn) {
                    matrix.get(row).set(column, -1);
                }
            }
        }
    }

    public int checkForBingo() {
        int row = checkForBingoRow();
        int column = checkForBingoColumn();

        if(row == -1 && column == -1) {
            return -1;
        } else if(row != -1) {
            return row;
        } else {
            return column;
        }
    }

    private int checkForBingoRow() {
        int r = 0;

        for(List<Integer> row : matrix) {
            int c = 0;

            for(Integer value : row) {
                if(value == -1) {
                    c++;
                }
            }

            if(c == matrix.size()) {
                return r;
            }
            r++;
        }
        return -1;
    }

    private int checkForBingoColumn() {

        for(int column = 0; column < matrix.size(); column++) {
            int c = 0;

            for(int row = 0; row < matrix.size(); row++) {
                if(matrix.get(row).get(column) == -1) {
                    c++;
                }
            }

            if(c == matrix.size()) {
                return column;
            }
        }
        return -1;
    }

    public int calculateSum () {
        int sum = 0;

        for(List<Integer> row : matrix) {

            for(Integer value : row) {
                if(value != -1) {
                    sum += value;
                }
            }
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        matrix.forEach(row -> {
            row.forEach(i -> sb.append(i).append(","));
            sb.append("\n");
        });
        return sb.toString();
    }
}
