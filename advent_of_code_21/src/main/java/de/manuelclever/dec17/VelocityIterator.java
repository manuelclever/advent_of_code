package de.manuelclever.dec17;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VelocityIterator implements Iterator<int[]> {
    // xMax = targetX[1]
    // xMin = 1
    // yMax = (targetX[0] - targetY[0]) / 2
    // yMin = targetY[0]

    private List<int[]> queue;

    public VelocityIterator(int[] targetX, int[] targetY) {
        final int X_MAX = targetX[1];
        final int X_MIN = 1;
        final int Y_MAX = (targetX[0] - targetY[0]) / 2;
        final int Y_MIN = targetY[0];
        queue = new ArrayList<>();

        for(int y = Y_MIN; y <= Y_MAX; y++) {

            for(int x = X_MIN; x <= X_MAX; x++) {
                queue.add(new int[]{x,y});
            }
        }
    }

    @Override
    public boolean hasNext() {
        return queue != null && queue.size() > 0;
    }

    @Override
    public int[] next() {
        int[] next = queue.get(0);
        queue.remove(0);

        return next;
    }
}
