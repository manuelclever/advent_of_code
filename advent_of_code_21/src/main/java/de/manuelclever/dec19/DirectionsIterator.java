package de.manuelclever.dec19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DirectionsIterator implements Iterator<int[]> {
    private List<int[]> queue;

    public DirectionsIterator(int[] directions) {
        this.queue = new ArrayList<>();
        generateQueue(directions);
    }

    private void generateQueue(int[] directions) {
        for(int x = -1; x < 2; x+=2) {

            for(int y = -1; y < 2; y+=2)  {

                for(int z = -1; z < 2; z+=2) {
                    System.out.println("===" + x + "," + y + "," + z + "===");
                    int dir0 = x * directions[0];
                    int dir1 = x * directions[1];
                    int dir2 = x * directions[2];
                    
                    
                    
                    queue.add(new int[]{dir0,dir1,dir2});
                    queue.add(new int[]{dir1,dir2,dir0});
                    queue.add(new int[]{dir2,dir0,dir1});
                }
            }
        }
        System.out.println(queue.size());
        queue.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    @Override
    public boolean hasNext() {
        return queue.size() != 0;
    }

    @Override
    public int[] next() {


        return new int[0];
    }
}
