package de.manuelclever.dec13;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Calculate_13 implements Calculator {
    Set<Dot> dots;
    List<Fold> folds;

    @Override
    public long calculatePart1() {
        generateDotsAndFolds();
        fold(1);

        return dots.size();
    }

    private void generateDotsAndFolds() {
        dots = new HashSet<>();
        folds = new ArrayList<>();
        MyFileReader fr = new MyFileReader(13,1);
        BufferedReader br = fr.createBufferedReader();

        try {
            while (true) {
                String line = br.readLine();

                if (line.equals("")) {
                    break;
                }

                String[] p = line.split(",");
                dots.add(new Dot(Integer.parseInt(p[0]), Integer.parseInt(p[1])));
            }

            while (true) {
                String line = br.readLine();

                if(line == null) {
                    break;
                }

                String[] split = line.split("\\s");
                String[] f = split[2].split("=");

                folds.add(new Fold(f[0].charAt(0), Integer.parseInt(f[1])));
            }
        } catch(IOException ignore) {}
    }

    private void fold(int foldInt) {

        for(int i = 0; i < foldInt; i++) {
            Set<Dot> dotsToRemove = new HashSet<>();
            Set<Dot> dotsToAdd = new HashSet<>();
            Fold fold = folds.get(i);

            if(fold.axis == 'x') {

                for(Dot dot : dots) {

                    if(dot.x > fold.pos) {
                        Dot foldedDot = new Dot(fold.pos - (dot.x - fold.pos) , dot.y);
                        dotsToRemove.add(dot);
                        dotsToAdd.add(foldedDot);
                    }
                }
            } else if(fold.axis == 'y'){

                for(Dot dot : dots) {

                    if(dot.y > fold.pos) {
                        Dot foldedDot = new Dot(dot.x, fold.pos - (dot.y - fold.pos) );
                        dotsToRemove.add(dot);
                        dotsToAdd.add(foldedDot);
                    }
                }
            }
            dots.removeAll(dotsToRemove);
            dots.addAll(dotsToAdd);
        }
    }

    @Override
    public long calculatePart2() {
        generateDotsAndFolds();
        fold(folds.size());
        draw();

        return 0;
    }

    private void draw() {
        List<Dot> sortedList = dots.stream()
                .sorted()
                .collect(Collectors.toList());

        int lastX = 0;
        for(int i = 0; i < sortedList.size(); i++) {
            Dot dot = sortedList.get(i);

            for(int x = 1; x < dot.x - lastX; x++) {
                System.out.print(".");
            }
            System.out.print("#");
            lastX = dot.x;

            if (i != sortedList.size() - 1 && dot.y < sortedList.get(i + 1).y) { //if next row
                System.out.println();
                lastX = 0;
            }
        }
        System.out.println();
    }
}

class Fold {
    public char axis;
    public int pos;

    public Fold(char axis, int pos) {
        this.axis = axis;
        this.pos = pos;
    }
}


