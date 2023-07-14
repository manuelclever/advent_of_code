package de.manuelclever.dec12;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Calculate_12 implements Calculator {
    Graph graph;
    Set<String> paths;

    @Override
    public long calculatePart1() {
        createGraph();

        paths = new HashSet<>();
        List<String> startEdges = graph.getNeighbours("start");

        for(String vertex : startEdges) {
            StringBuilder sb = new StringBuilder("start,");
            Set<String> visited = new HashSet<>();
            visited.add("start");

            if (isAllLowercase(vertex)) {
                visited.add(vertex);
            }
            sb.append(vertex).append(",");
            buildPath(vertex, sb, visited);
        }
        return paths.size();
    }

    private void createGraph() {
        MyFileReader fr = new MyFileReader(12,1);
        List<String> rawInput = fr.getStringList();
        graph = new Graph();

        for(String line : rawInput) {
            String[] edge = line.split("-");
            graph.addEdge(edge[0], edge[1]);
        }
    }

    private void buildPath(String vertex, StringBuilder sb, Set<String> visited) {
        List<String> vertexes = graph.getNeighbours(vertex);

        for(String neighbour : vertexes) {

            if(!visited.contains(neighbour)) {
                StringBuilder newSB = new StringBuilder(sb);
                Set<String> newVisited = new HashSet<>(visited);

                if (neighbour.equals("end")) {
                    paths.add(sb.append("end").toString());
                } else {
                    if (isAllLowercase(neighbour)) {
                        newVisited.add(neighbour);
                    }
                    newSB.append(neighbour).append(",");
                    buildPath(neighbour, newSB, newVisited);
                }
            }
        }
    }

    private boolean isAllLowercase(String vertex) {
        String reg = "[a-z]+";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(vertex);

        return matcher.matches();
    }

    @Override
    public long calculatePart2() {
        createGraph();

        paths = new HashSet<>();
        List<String> startEdges = graph.getNeighbours("start");

        for(String vertex : startEdges) {
            StringBuilder sb = new StringBuilder("start,");
            Set<String> visited = new HashSet<>();
            visited.add("start");
            int smallCaveCounter = 0;

            if (isAllLowercase(vertex)) {
                newSplit(vertex, sb, visited, smallCaveCounter);
                smallCaveCounter++;
            }

            sb.append(vertex).append(",");
            buildPathPart2(vertex, sb, visited, smallCaveCounter);
        }
        return paths.size();
    }

    private void buildPathPart2(String vertex, StringBuilder sb, Set<String> visited, int smallCaveCounter) {
        List<String> vertexes = graph.getNeighbours(vertex);

        for(String neighbour : vertexes) {

            if(!visited.contains(neighbour)) {
                StringBuilder splitOneSB = new StringBuilder(sb);
                Set<String> splitOneVisited = new HashSet<>(visited);
                int splitOneSmallCaveCounter = smallCaveCounter;

                if (neighbour.equals("end")) {
                    paths.add(sb.append("end").toString());
                } else {
                    if (isAllLowercase(neighbour)) {

                        if(splitOneSmallCaveCounter < 1) {
                            newSplit(neighbour,splitOneSB, splitOneVisited, splitOneSmallCaveCounter);
                            splitOneSmallCaveCounter++;
                        } else {
                            splitOneVisited.add(neighbour);
                        }
                    }
                    splitOneSB.append(neighbour).append(",");
                    buildPathPart2(neighbour, splitOneSB, splitOneVisited, splitOneSmallCaveCounter);
                }
            }
        }
    }

    private void newSplit(String vertex, StringBuilder sb, Set<String> visited, int smallCaveCounter) {
        StringBuilder splitSB = new StringBuilder(sb);
        Set<String> splitVisited = new HashSet<>(visited);

        splitVisited.add(vertex);
        splitSB.append(vertex).append(",");
        buildPathPart2(vertex, splitSB, splitVisited, 0);
    }
}
