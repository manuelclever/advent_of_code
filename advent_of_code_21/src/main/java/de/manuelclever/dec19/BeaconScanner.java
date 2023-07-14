package de.manuelclever.dec19;

import java.util.List;

public class BeaconScanner {
    int number;
    private List<Beacon> beacons;

    public BeaconScanner(int number, List<Beacon> beacons) {
        this.number = number;
        this.beacons = beacons;
    }

    public void replaceBeacon(Beacon oldBeacon, Beacon newBeacon) {
        beacons.remove(oldBeacon);
        beacons.add(newBeacon);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[" + number + ": ");
        for(Beacon b : beacons) {
            sb.append("\t" + b);
        }
        sb.append("]");
        return sb.toString();
    }
}
