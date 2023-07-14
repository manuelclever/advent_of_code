package de.manuelclever.dec19;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculate_19 implements Calculator {
    Map<Integer, BeaconScanner> scanner;

    @Override
    public long calculatePart1() {
        generateScanners();
        DirectionsIterator iter = new DirectionsIterator(new int[]{2,4,6});

//        for(BeaconScanner bScanner : scanner.values()) {
//            System.out.println(bScanner);
//        }
        return 0;
    }

    private void generateScanners() {
        MyFileReader fr = new MyFileReader(19,1);
        BufferedReader br = fr.createBufferedReader();
        scanner = new HashMap<>();

        outerLoop:
        while(true) {
            try {
                String line = br.readLine();

                if (line == null || line.equals("")) {
                    break;
                } else {
                    Pattern p = Pattern.compile("---\\s\\bscanner\\b\\s([0-9]+)\\s---");
                    Matcher m = p.matcher(line);

                    if (m.find()) {
                        int num = Integer.parseInt(m.group(1));

                        List<Beacon> beacons = new ArrayList<>();
                        while (true) {
                            line = br.readLine();

                            if(line == null || line.equals("")) {
                                break;
                            } else  {
                                String[] pos = line.split(",");
                                try {
                                    beacons.add(new Beacon(num, parseStringToInt(pos[0]), parseStringToInt(pos[1]),
                                            parseStringToInt(pos[2])));
                                } catch(NumberFormatException e) {
                                    e.printStackTrace();
                                    break outerLoop;
                                }
                            }
                        }
                        scanner.put(num, new BeaconScanner(num, beacons));
                    }
                }
            } catch(IOException ignore) {}
        }
    }

    private int parseStringToInt(String s) throws NumberFormatException {
        Pattern p = Pattern.compile("(-)?([0-9]+)");
        Matcher m = p.matcher(s);

        if(m.find()) {
            int num = Integer.parseInt(m.group(2));
            return m.group(1) != null ? num * -1 : num;
        } else {
            throw new NumberFormatException("String " + s + " couldn't be parsed to Integer");
        }
    }

    @Override
    public long calculatePart2() {
        return 0;
    }
}