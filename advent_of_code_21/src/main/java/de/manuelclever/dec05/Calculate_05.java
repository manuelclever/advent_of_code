package de.manuelclever.dec05;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculate_05 implements Calculator {
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    Map<XY, Integer> seafloor;


    @Override
    public long calculatePart1() {
        return calculate(1);
    }

    private long calculate(int part) {
        MyFileReader fileReader = new MyFileReader(5, 1);
        BufferedReader br = fileReader.createBufferedReader();

        seafloor = new HashMap<>();

        while (true) {
            try {
                String line = br.readLine();
                if (line == null) {
                    break;
                }

                String reg = "([0-9]+)[,]([0-9]+)\\b\\s->\\s\\b([0-9]+)[,]([0-9]+)";
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    x1 = Integer.parseInt(matcher.group(1));
                    y1 = Integer.parseInt(matcher.group(2));
                    x2 = Integer.parseInt(matcher.group(3));
                    y2 = Integer.parseInt(matcher.group(4));

                    if (x1 == x2) {
                        drawVentHorizontal();
                    } else if (y1 == y2) {
                        drawVentVertical();
                    } else if (part == 2 && (x2 - x1) == (y2 - y1)) {
                        drawVentVerticalLR();
                    } else if (part == 2 && (x2 - x1) + (y2 - y1) == 0) {
                        drawVentVerticalRL();
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return 0;
            }
        }
        int c = 0;
        for (Map.Entry<XY, Integer> entry : seafloor.entrySet()) {
            if (entry.getValue() >= 2) {
                c++;
            }
        }
        return c;
    }

    private void drawVentHorizontal() {
        int start = Math.min(y1, y2);
        int difference = y2 - y1 > 0 ? y2 - y1 : y1 - y2;

        for (int i = 0; i <= difference; i++) {
            seafloor.merge(new XY(x1, start + i), 1, Integer::sum);
        }
    }

    private void drawVentVertical() {
        int start = Math.min(x1, x2);
        int difference = x2 - x1 > 0 ? x2 - x1 : x1 - x2;

        for (int i = 0; i <= difference; i++) {
            seafloor.merge(new XY(start + i, y1), 1, Integer::sum);
        }
    }

    private void drawVentVerticalLR() {
        int startX = Math.min(x1, x2);
        int startY = Math.min(y1, y2);
        int difference = x2 - x1 > 0 ? x2 - x1 : x1 - x2;

        for (int i = 0; i <= difference; i++) {
            seafloor.merge(new XY(startX + i, startY + i), 1, Integer::sum);
        }
    }

    private void drawVentVerticalRL() {
        int startX = Math.min(x1, x2);
        int startY = Math.max(y1, y2);
        int difference = x2 - x1 > 0 ? x2 - x1 : x1 - x2;

        for (int i = 0; i <= difference; i++) {
            seafloor.merge(new XY(startX + i, startY - i), 1, Integer::sum);
        }
    }

    @Override
    public long calculatePart2() {
        return calculate(2);
    }
}

class XY {
    public int x;
    public int y;

    public XY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj.getClass() == this.getClass()) {
            XY o = (XY) obj;
            return o.x == this.x && o.y == this.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return x * 3 + y * 7;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
