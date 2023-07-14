package com.adventofcode.dec14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\Coding\\Projects\\" +
                "adventofcode\\src\\com\\adventofcode\\dec14\\input.txt"));

        Pattern pBlock = Pattern.compile("\\bmask\\b\\s.\\s[X,1,0]{36}\r\n" +
                "(\\bmem\\b.\\d+.\\s.\\s\\d+(\r\n)?)+");
        Matcher mBlock = pBlock.matcher(input);

        Pattern pMask = Pattern.compile("[X,1,0]{36}");
        Pattern pValue = Pattern.compile("\\bmem\\b.\\d+.\\s.\\s\\d+");

        List<List<String>> allLinesGrouped = new ArrayList<>();
        while(mBlock.find()) {
            String s = mBlock.group(0);
            Matcher mMask = pMask.matcher(s);

            Matcher mValue = pValue.matcher(s);
            List<String> values = new ArrayList<>();
            values.add(mMask.find() ? mMask.group(0).trim() : "-1");
            while(mValue.find()) {
                values.add(mValue.group(0));
            }
            allLinesGrouped.add(values);
        }
//        allLinesGrouped.stream().forEach(System.out::println);

        Map<Long, String> binarySaved = new HashMap();
        for(List<String> list : allLinesGrouped) {

            for (int i = 1; i < list.size(); i++) {
                Pattern pMemoryAl = Pattern.compile("(?<=^\\bmem\\b.)\\d+");
                Matcher mMemoryAl = pMemoryAl.matcher(list.get(i));
                long memoryAl = Long.parseLong(mMemoryAl.find() ? mMemoryAl.group(0) : "-1");

                Pattern pDecimal = Pattern.compile("(?<=[=]\\s)\\d+");
                Matcher mDecimal = pDecimal.matcher(list.get(i));
                long decimal = Long.parseLong(mDecimal.find() ? mDecimal.group(0) : "-1");

                binarySaved.put(memoryAl, createBinary(list.get(0), decimal));
            }
        }
        binarySaved.forEach((k,v) -> System.out.println(k + " : " + v));
        System.out.println(binarySaved.values().stream().map(i -> Long.parseLong(i, 2)).mapToLong(i -> i).sum());
    }

    public static String createBinary(String mask, long decimal) {
        //turn decimal into binary and create binaryCharArray, create maskCharArray
        char[] binaryCharArray = Long.toBinaryString(decimal).toCharArray();
//        System.out.println(Long.toBinaryString(decimal));
        char[] maskCharArray = mask.toCharArray();

        //set binaryCharArray in full chars
        char[] chars = new char[36];
        int iter = binaryCharArray.length -1;
        for(int i = chars.length -1; i >= 0; i--) {

            if(iter >= 0) {
                chars[i] = binaryCharArray[iter];
                iter--;
            } else {
                chars[i] = '0';
            }
        }

        //write mask over charArray
        char[] charsMasked = new char[chars.length];
        for(int i = 0; i < charsMasked.length; i++) {

            if(maskCharArray[i] == 'X') {
                charsMasked[i] = chars[i];
            } else if(maskCharArray[i] == '1') {
                charsMasked[i] = '1';
            } else {
                charsMasked[i] = '0';
            }
        }

        //build String from charMaskedArray
        StringBuilder sB = new StringBuilder();
        for(char c : charsMasked) {
            sB.append(c);
        }
        return sB.toString();
    }

    public static void readBinary() {

    }
}