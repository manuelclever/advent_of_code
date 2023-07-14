package de.manuelclever.dec17;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculate_17 implements Calculator {
    int[] startPos;
    int[] targetX;
    int[] targetY;

    int[] currentVelocity;


    @Override
    public long calculatePart1() {
        start();
        Map<int[], Integer> successVelo = calculateSuccessfulVelocities();

        int maxYPos = Integer.MIN_VALUE;
        for(Map.Entry<int[], Integer> entry : successVelo.entrySet()) {
            maxYPos = Integer.max(maxYPos, entry.getValue());
        }

        return maxYPos;
    }

    private void start() {
        MyFileReader fr = new MyFileReader(17,1);
        String line = fr.getString();

        startPos = new int[]{0,0};
        targetX = new int[2];
        targetY = new int[2];

        String group = "([-]?[0-9]+)";
        String reg = "x=" + group + ".." + group + ",\\sy=" + group + ".." + group;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(line);

        if(matcher.find()) {
            targetX[0] = matcher.group(1).contains("-") ? -1 * parseInt(matcher.group(1)) : parseInt(matcher.group(1));
            targetX[1] = matcher.group(2).contains("-") ? -1 * parseInt(matcher.group(2)) : parseInt(matcher.group(2));
            targetY[0] = matcher.group(3).contains("-") ? -1 * parseInt(matcher.group(3)) : parseInt(matcher.group(3));
            targetY[1] = matcher.group(4).contains("-") ? -1 * parseInt(matcher.group(4)) : parseInt(matcher.group(4));
        }
    }

    private void step(int[] pos) {
        pos[0] += currentVelocity[0];
        pos[1] += currentVelocity[1];

        if(currentVelocity[0] != 0) {
            currentVelocity[0] -= 1;
        }
        currentVelocity[1] -= 1;
    }

    private boolean isInTargetArea(int[] pos) {
        return pos[0] >= targetX[0] && pos[0] <= targetX[1] &&
                pos[1] >= targetY[0] && pos[1] <= targetY[1];
    }

    private  boolean overshotTargetArea(int[] pos) {
        return pos[0] > targetX[1] ||
                pos[1] < targetY[0];
    }

    private int parseInt(String group) {
        return  Integer.parseInt(group.replace("-",""));
    }

    private Map<int[], Integer> calculateSuccessfulVelocities() {
        Map<int[], Integer> successVelo = new HashMap<>();
        Iterator<int[]> iter = new VelocityIterator(targetX, targetY);

        while(iter.hasNext()) {
            int[] currentPos = new int[]{startPos[0], startPos[1]};
            currentVelocity = iter.next();
            int maxYPos = Integer.MIN_VALUE;

            while(!overshotTargetArea(currentPos)) {
                step(currentPos);
                maxYPos = Integer.max(maxYPos, currentPos[1]);

                if(isInTargetArea(currentPos)) {
                    successVelo.put(currentPos, maxYPos);
                    break;
                }
            }
        }
        return successVelo;
    }

    @Override
    public long calculatePart2() {
        start();
        Map<int[], Integer> successVelo = calculateSuccessfulVelocities();

        return successVelo.size();
    }
}
