package de.manuelclever.dec01;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.ArrayList;
import java.util.List;

public class Calculate_01 implements Calculator {

    @Override
    public long calculatePart1() {
        List<Integer> depths = getDepths();

        int up = 0;
        for(int i = 1; i < depths.size(); i++) {
            if(depths.get(i - 1) < depths.get(i)) {
                up++;
            }
        }
        return up;
    }

    private List<Integer> getDepths() {
        MyFileReader myFileReader = new MyFileReader(1,1);
        List<String> rawInput = myFileReader.getStringList();

        List<Integer> depths = new ArrayList<>();
        for (String inputLine : rawInput) {
            depths.add(Integer.parseInt(inputLine));
        }
        return depths;
    }

    @Override
    public long calculatePart2() {
        List<Integer> depths = getDepths();

        int up = 0;
        try {
            for (int i = 2; i < depths.size(); i++) {
                int sum1 = depths.get(i - 2) + depths.get(i - 1) + depths.get(i);
                int sum2 = depths.get(i - 1) + depths.get(i) + depths.get(i + 1);

                if(sum1 < sum2) {
                    up++;
                }
            }
        } catch (IndexOutOfBoundsException ignore) {}
        return up;
    }
}
