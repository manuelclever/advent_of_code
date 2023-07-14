package de.manuelclever.dec16;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.ArrayList;
import java.util.List;

public class Calculate_16 implements Calculator {
    String binary;

    @Override
    public long calculatePart1() {
        generateBinary();

        int version = Integer.parseInt(binary.substring(0,3),2);
        int type = Integer.parseInt(binary.substring(3,6),2);
        Packet packet = recursive(version, type, binary.substring(6));

        return packet.versionSum();
    }

    private void generateBinary() {
        MyFileReader fr = new MyFileReader(16,1);
        String line = fr.getString();

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < line.length(); i++) {
            char hex = line.charAt(i);
            sb.append(convertHexToBinary(hex));
        }
        binary = sb.toString();
    }

    private String convertHexToBinary(char hex) {
        switch(hex) {
            case '0' -> {return "0000";}
            case '1' -> {return "0001";}
            case '2' -> {return "0010";}
            case '3' -> {return "0011";}
            case '4' -> {return "0100";}
            case '5' -> {return "0101";}
            case '6' -> {return "0110";}
            case '7' -> {return "0111";}
            case '8' -> {return "1000";}
            case '9' -> {return "1001";}
            case 'A' -> {return "1010";}
            case 'B' -> {return "1011";}
            case 'C' -> {return "1100";}
            case 'D' -> {return "1101";}
            case 'E' -> {return "1110";}
            case 'F' -> {return "1111";}
            default -> {return "ERROR";}
        }
    }

    private Packet recursive(int version, int type, String binary) {

        if(type == 4 ){
            return buildFinalPacket(version, type, binary);
        } else {
            int lengthType = Integer.parseInt(String.valueOf(binary.charAt(0)));

            if(lengthType == 0) { // next 15 = total length
                return buildLengthTypePacket(version, type, binary);
            } else { // next 11 = number of sub packets
                return buildSubPacketTypePacket(version, type, binary);
            }
        }
    }

    private Packet buildFinalPacket(int version, int type, String binary) {
        StringBuilder sb = new StringBuilder();
        int valueLength = 0;

        for(int i = 0; i < binary.length(); i+=5) {
            int group = Integer.parseInt(binary.substring(i, i+1));
            sb.append(binary, i+1, i+5);
            valueLength += 5;

            if(group == 0) {
                break;
            }
        }
        long value = Long.parseLong(sb.toString(), 2);
        return new Packet(version, type, value, valueLength);
    }

    private Packet buildLengthTypePacket(int version, int type, String binary) {
        int totalLength = Integer.parseInt(binary.substring(1, 16), 2);
        binary = binary.substring(16);

        List<Packet> subPackets = new ArrayList<>();
        for(int i = 0; i < totalLength;) {
            int subVersion = Integer.parseInt(binary.substring(0,3),2);
            int subType = Integer.parseInt(binary.substring(3,6),2);

            Packet subPacket = recursive(subVersion, subType, binary.substring(6));

            binary = binary.substring(subPacket.lengthBinary);
            i += subPacket.lengthBinary;

            subPackets.add(subPacket);
        }
        return new Packet(version, type, 16, subPackets);
        // 16 == typePacket + length of totalLength. Should be moved somewhere else for cleaner code
    }

    private Packet buildSubPacketTypePacket(int version, int type, String binary) {
        int subPackets = Integer.parseInt(binary.substring(1, 12), 2);
        binary = binary.substring(12);

        List<Packet> packets = new ArrayList<>();
        for(int i = 0; i < subPackets; i++) {
            int subVersion = Integer.parseInt(binary.substring(0,3),2);
            int subType = Integer.parseInt(binary.substring(3,6),2);

            Packet subPacket = recursive(subVersion, subType, binary.substring(6));
            packets.add(subPacket);

            binary = binary.substring(subPacket.lengthBinary);
        }
        return new Packet(version, type, 12, packets);
        // 12 == typePacket + length of subPackets count. Should be moved somewhere else for cleaner code
    }

    @Override
    public long calculatePart2() {
        generateBinary();

        int version = Integer.parseInt(binary.substring(0,3),2);
        int type = Integer.parseInt(binary.substring(3,6),2);
        Packet packet = recursive(version, type, binary.substring(6));

        return packet.getValue();
    }
}
