package de.manuelclever.dec15;

import java.util.*;

public class WeightedGraphIterator implements Iterator<Edge> {
    Map<Point,List<Edge>> graph;
    LinkedList<Edge> queue;
    Set<Point> visited;

    public WeightedGraphIterator(Map<Point, List<Edge>> graph) {
        Point start = new Point(0,0);

        this.graph = graph;
        this.queue = new LinkedList<>(graph.get(start));
        this.visited = new HashSet<>(List.of(start));
    }

    @Override
    public boolean hasNext() {
        return queue.size() > 0;
    }

    @Override
    public Edge next() {
        Edge next = queue.get(0);
        visited.add(next.destination);
        queue.remove(0);

        for(Edge e : graph.get(next.destination)) {
            if(!visited.contains(e.destination)) {
                if(!queue.contains(e) && !visited.contains(e.destination)) {
                    queue.add(e);
                }
            }
        }

        return next;
    }
}
