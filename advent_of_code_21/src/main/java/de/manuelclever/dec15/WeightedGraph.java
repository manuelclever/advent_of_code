package de.manuelclever.dec15;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class WeightedGraph implements Iterable<Edge> {
    Map<Point,List<Edge>> graph;

    public WeightedGraph() {
        graph = new HashMap<>();
    }

    public void addEdge(Point p, Edge e) {
        if(!graph.containsKey(p)) {
            graph.put(p, new ArrayList<>(List.of(e)));
        } else {
            graph.get(p).add(e);
        }
    }

    @NotNull
    @Override
    public Iterator<Edge> iterator() {
        return new WeightedGraphIterator(graph);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Map.Entry<Point, List<Edge>> entry : graph.entrySet()) {

            sb.append(entry.getKey()).append(":\n");
            for(Edge e : entry.getValue()) {
                sb.append("\t").append(e.destination).append(", ").append(e.weight).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

class Edge {
    Point source;
    Point destination;
    int weight;

    public Edge(Point source, Point destination, int weight) {
        this. source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        return destination.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == this.getClass()) {
            Edge o = (Edge) obj;
            return o.destination.equals(this.destination);
        }
        return false;
    }
}
