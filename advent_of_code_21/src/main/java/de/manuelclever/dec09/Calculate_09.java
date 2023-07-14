package de.manuelclever.dec09;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.*;

public class Calculate_09 implements Calculator {
    List<List<Integer>> heightMap;
    Map<Integer, Map<Integer, Boolean>> checkMap;

    @Override
    public long calculatePart1() {
        generateHeightMap();

        int riskScale = 0;
        for(int y  = 0; y < heightMap.size(); y++) {

            for(int x = 0; x < heightMap.get(y).size(); x++) {

                if(heightOfAdjacentHigher(y, x)) {
                    riskScale += 1 + heightMap.get(y).get(x);
                }
            }
        }
        return riskScale;
    }

    private void generateHeightMap() {
        MyFileReader fr = new MyFileReader(9,1);
        List<String> rawInput = fr.getStringList();

        heightMap = new ArrayList<>();

        for(int i = 0; i < rawInput.size(); i++) {
            heightMap.add(new ArrayList<>());
        }

        for(int y = 0; y < rawInput.size(); y++) {
            String line = rawInput.get(y);
            for(int x = 0; x < line.length(); x++) {
                heightMap.get(y).add(Integer.parseInt(String.valueOf(line.charAt(x))));
            }
        }
    }

    private boolean heightOfAdjacentHigher(int yHeight, int xHeight) {
        int left = xHeight - 1; int right = xHeight + 1;
        int top = yHeight - 1; int bottom = yHeight + 1;
        int height = heightMap.get(yHeight).get(xHeight);

        if( (withingEdges(top, xHeight) && height >= heightMap.get(top).get(xHeight)) ||
                (withingEdges(yHeight, left) && height >= heightMap.get(yHeight).get(left)) ||
                (withingEdges(yHeight, right) && height >= heightMap.get(yHeight).get(right)) ||
                (withingEdges(bottom, xHeight) && height >= heightMap.get(bottom).get(xHeight))) {
            return false;
        }
        return true;
    }

    private boolean withingEdges(int y, int x) {
        return y >= 0 && y < heightMap.size() &&
                x >= 0 && x < heightMap.get(y).size();
    }

    @Override
    public long calculatePart2() {
        generateHeightMap();

        List<Integer> basinSizes = new ArrayList<>();
        for(int y  = 0; y < heightMap.size(); y++) {

            for(int x = 0; x < heightMap.get(y).size(); x++) {

                if(heightOfAdjacentHigher(y, x)) {
                    //generate empty map before method call, because basinSize() is recursive
                    checkMap = getEmptyCheckMap();
                    basinSizes.add(basinSize(y, x, 0));
                }
            }
        }
        basinSizes.sort(Integer::compare);
        System.out.println(basinSizes.get(basinSizes.size() - 1) + ", " + basinSizes.get(basinSizes.size()-2) +
                ", " + basinSizes.get(basinSizes.size()-3));
        for(Integer i : basinSizes) {
            System.out.print(i + ", ");
        }
        return (long) basinSizes.get(basinSizes.size() - 1) * basinSizes.get(basinSizes.size()-2) * basinSizes.get(basinSizes.size()-3);
    }

    private Map<Integer, Map<Integer, Boolean>> getEmptyCheckMap() {
        Map<Integer, Map<Integer, Boolean>> heightMapChecked = new HashMap<>();

        for(int i = 0; i < heightMap.get(0).size(); i++) {
            heightMapChecked.put(i, new HashMap<>());
        }
        return heightMapChecked;
    }

    private int basinSize(int yHeight, int xHeight, int size) {
        int left = xHeight - 1; int right = xHeight + 1;
        int top = yHeight - 1; int bottom = yHeight + 1;
        int height = heightMap.get(yHeight).get(xHeight);

        if(checkMap.get(yHeight).get(xHeight) == null) {
            checkMap.get(yHeight).put(xHeight, true);

            size++;
            size = nextIsOneAwayOrSame(height, top, xHeight) ? basinSize(top, xHeight, size) : size;
            size = nextIsOneAwayOrSame(height, yHeight, left) ? basinSize(yHeight, left, size) : size;
            size = nextIsOneAwayOrSame(height, yHeight, right) ? basinSize(yHeight, right, size) : size;
            size = nextIsOneAwayOrSame(height, bottom, xHeight) ? basinSize(bottom, xHeight, size) : size;
        }
        return size;
    }

    private boolean nextIsOneAwayOrSame(int height, int y, int x) {

        if(withingEdges(y, x)) {
            int heightNeighbor =  heightMap.get(y).get(x);

            if(heightNeighbor < 9) {
                return height - heightNeighbor == -1 ||
                        height - heightNeighbor == 0 ||
                        height - heightNeighbor == 1;
            }
        }
        return false;
    }
}
