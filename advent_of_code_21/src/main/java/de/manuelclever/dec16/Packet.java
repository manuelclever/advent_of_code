package de.manuelclever.dec16;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Packet {
    int version;
    int type;
    private long value;
    List<Packet> subPackets;

    int lengthBinary;

    public Packet(int version, int type, long value, int valueLength) {
        this.version = version;
        this.type = type;
        this.value = value;

        this.lengthBinary = 6 + valueLength;
    }

    public Packet(int version, int type, int valueLength, List<Packet> subPackets) {
        this.version = version;
        this.type = type;
        this.value = -1;
        this.subPackets = subPackets;

        this.lengthBinary = 6 + valueLength;
        for(Packet subPacket : subPackets) {
            this.lengthBinary += subPacket.lengthBinary;
        }
    }

    public long getValue() {
        if(type == 4) {
            return value;
        } else {
            LongStream longStream = subPackets.stream().mapToLong(Packet::getValue);
            switch (type) {
                case 0 -> {return longStream.sum();}
                case 1 -> {return longStream.reduce((o1, o2) -> o1 * o2).getAsLong();}
                case 2 -> {return longStream.min().getAsLong();}
                case 3 -> {return longStream.max().getAsLong();}
                case 5 -> {return subPackets.get(0).getValue() > subPackets.get(1).getValue() ? 1 : 0;}
                case 6 -> {return subPackets.get(0).getValue() < subPackets.get(1).getValue() ? 1 : 0;}
                case 7 -> {return subPackets.get(0).getValue() == subPackets.get(1).getValue() ? 1 : 0;}
                default -> {return -1;}
            }
        }
    }

    public boolean isFinalPacket() {
        return subPackets == null;
    }

    public int versionSum() {
        if(isFinalPacket()) {
            return version;
        } else {
            int versionSum = version;
            for(Packet subPacket : subPackets) {
                versionSum += subPacket.versionSum();
            }
            return versionSum;
        }
    }
}
