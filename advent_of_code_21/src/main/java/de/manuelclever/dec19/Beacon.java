package de.manuelclever.dec19;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Beacon {
    //position relative to scanner
    Map<Integer, int[]> positions;

    public Beacon(int scanner, int x, int y, int z) {
        this.positions = new HashMap<>();
        this.positions.put(scanner, new int[]{x, y, z});
    }

    public int getPos0(int scanner) {
        return positions.get(scanner)[0];
    }

    public int getPos1(int scanner) {
        return positions.get(scanner)[1];
    }

    public int getPos2(int scanner) {
        return positions.get(scanner)[2];
    }

    public Beacon mergeWithBeacons(Beacon... beacons) {
        for (Beacon b2 : beacons) {
            positions.putAll(b2.positions);
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Map.Entry<Integer,int[]> entry : positions.entrySet()) {
            sb.append("\n\t\t" + entry.getKey() + ": " + Arrays.toString(entry.getValue()));
        }
        return sb.toString();
    }
}