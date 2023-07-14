package de.manuelclever.dec15;
import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Calculate_15 implements Calculator {
    List<String> rawInput;
    List<String> bigInput;
    WeightedGraph graph;
    List<Point> visited;
    Map<Point,DistanceAndPrevious> shortest;

    @Override
    public long calculatePart1() {
        MyFileReader fr = new MyFileReader(15,1);
        rawInput = fr.getStringList();
        generateGraph(rawInput);

        Point start = new Point(0,0);
        visited = new ArrayList<>(List.of(new Point(0,0)));
        this.shortest = new HashMap<>();
        updateShortest(new Point(0,0), 0, rawInput);

        for(int i = 0; i < 4; i++) { //it works like that, fuck it
            findShortestDistance(rawInput);
        }

        Point end = new Point(rawInput.get(0).length() - 1, rawInput.size() - 1);
        return shortest.get(end).distance;
    }

    private void generateGraph(List<String> input) {

        graph = new WeightedGraph();
        for(int y = 0; y < input.size(); y++) {

            for(int x = 0; x < input.get(y).length(); x++) {

                for(Point neighbour : getAdjacent(x,y, input)) {

                    if(!Objects.equals(neighbour, new Point(0, 0))) {
                        int risk = Integer.parseInt(String.valueOf(input.get(neighbour.y).charAt(neighbour.x)));
                        graph.addEdge(new Point(x, y), new Edge(new Point(x,y),neighbour, risk));
                    }
                }
            }
        }
    }

    private List<Point> getAdjacent(int x ,int y, List<String> input) {
        int top = y+1; int bottom = y-1;
        int left = x-1; int right = x+1;

        List<Point> neighbours = new ArrayList<>();
        if(isWithinCave(x, top, input)) {
            neighbours.add(new Point(x, top));
        }
        if(isWithinCave(x, bottom, input)) {
            neighbours.add(new Point(x, bottom));
        }
        if(isWithinCave(left, y, input)) {
            neighbours.add(new Point(left, y));
        }
        if(isWithinCave(right, y, input)) {
            neighbours.add(new Point(right, y));
        }
        return neighbours;
    }

    private boolean isWithinCave(int x, int y, List<String> input) {
        return y >= 0 && y < input.size() &&
                x >= 0 && x < input.get(y).length();
    }

    private void findShortestDistance(List<String> input) {
        for (Edge next : graph) {
            updateShortest(next.destination, next.weight, input);
        }
    }

    public void updateShortest(Point p, int newDistance, List<String> input) {

        if(shortest.size() > 0) {

            for (Point adjacent : getAdjacent(p.x, p.y, input)) {

                if(shortest.get(adjacent) != null ) {

                    if (!shortest.containsKey(p)) {
                        shortest.put(p, new DistanceAndPrevious((newDistance + shortest.get(adjacent).distance),
                                adjacent));
                    } else if (shortest.get(adjacent).distance + newDistance < shortest.get(p).distance) {
                        shortest.get(p).distance = shortest.get(adjacent).distance + newDistance;
                        shortest.get(p).previous = adjacent;
                    }
                }
            }

        } else {
            shortest.put(p, new DistanceAndPrevious(newDistance, null));
        }
    }

    public void printPath(Point p) {
        System.out.print(p + " " + shortest.get(p).distance + " | ");

        if(!p.equals(new Point(0,0))) {
            printPath(shortest.get(p).previous);
        } else {
            System.out.println();
        }
    }

    @Override
    public long calculatePart2() {
        List<String> bigInput = generateBigInput();
        generateGraph(bigInput);

        Point start = new Point(0,0);
        visited = new ArrayList<>(List.of(new Point(0,0)));
        this.shortest = new HashMap<>();
        updateShortest(new Point(0,0), 0, bigInput);

        for(int i = 0; i < 6; i++) { //it works like that, fuck it
            findShortestDistance(bigInput);
        }

        Point end = new Point(bigInput.get(0).length() - 1, bigInput.size() - 1);
        return shortest.get(end).distance;
    }

    private List<String> generateBigInput() {
        MyFileReader fr = new MyFileReader(15,1);
        BufferedReader br = fr.createBufferedReader();

        List<String> bigInput = new ArrayList<>();
        try {
            while (true) {
                String line = br.readLine();

                if(line != null) {
                    StringBuilder sb = new StringBuilder(line);

                    for (int i = 1; i < 5; i++) {

                        for (int x = 0; x < line.length(); x++) {
                            sb.append(newRisk(line,i,x));
                        }
                    }
                    bigInput.add(sb.toString());
                } else {
                    break;
                }
            }

            int firstSize = bigInput.size();
            for(int i = 1; i < 5; i++) {

                for(int y = 0; y < firstSize; y++) {
                    String line = bigInput.get(y);
                    StringBuilder sb = new StringBuilder();

                    for(int x = 0; x < bigInput.get(y).length(); x++) {
                        sb.append(newRisk(line,i,x));
                    }
                    bigInput.add(sb.toString());
                }

            }
        } catch (IOException ignore){}
        return bigInput;
    }

    private int newRisk(String line, int i, int x) {
        int risk = Integer.parseInt(String.valueOf(line.charAt(x)));
        risk += i;

        if (risk <= 9) {
            return risk;
        } else {
            return risk - 9;
        }
    }
}

class DistanceAndPrevious {
    int distance;
    Point previous;

    public DistanceAndPrevious(int distance, Point previous) {
        this.distance = distance;
        this.previous = previous;
    }
}