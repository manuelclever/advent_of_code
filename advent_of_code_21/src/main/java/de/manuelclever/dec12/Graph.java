package de.manuelclever.dec12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    Map<String, List<String>> graph;

    public Graph() {
        this.graph = new HashMap<>();
    }

    public void addVertex(String vertex) {
        if(!graph.containsKey(vertex)) {
            graph.put(vertex, new ArrayList<>());
        }
    }

    public void addEdge(String vertex1, String vertex2) {

        if(!graph.containsKey(vertex1)) {
            addVertex(vertex1);
        }
        if(!graph.containsKey(vertex2)) {
            addVertex(vertex2);
        }

        graph.get(vertex1).add(vertex2);
        graph.get(vertex2).add(vertex1);
    }

    public List<String> getNeighbours(String vertex) {
        return graph.get(vertex);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Map.Entry<String, List<String>> entry : graph.entrySet()) {
            sb.append(entry.getKey()).append(": ");

            for(String conn : entry.getValue()) {
                sb.append(conn).append(",");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
