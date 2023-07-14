package com.adventofcode.dec14;

import com.sun.source.tree.BinaryTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Part2 {

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

        Map<Long, Long> binarySaved = new HashMap();
        for(List<String> list : allLinesGrouped) {

            for (int i = 1; i < list.size(); i++) {
                Pattern pMemoryAl = Pattern.compile("(?<=^\\bmem\\b.)\\d+");
                Matcher mMemoryAl = pMemoryAl.matcher(list.get(i));
                long memoryAl = Long.parseLong(mMemoryAl.find() ? mMemoryAl.group(0) : "-1");

                Pattern pDecimal = Pattern.compile("(?<=[=]\\s)\\d+");
                Matcher mDecimal = pDecimal.matcher(list.get(i));
                long decimal = Long.parseLong(mDecimal.find() ? mDecimal.group(0) : "-1");

                saveInList(binarySaved, list.get(0), memoryAl, decimal);
            }
        }
        binarySaved.forEach((k,v) -> System.out.println(k + " : " + v));
        System.out.println("All occupied memory spaces: " + binarySaved.size());
        System.out.println((Long) binarySaved.values().stream().mapToLong(Long::longValue).sum());
    }

    public static void  saveInList(Map<Long, Long> binarySaved, String mask, long memoryAl, long decimal) {
        //turn memoryDecimal into binary and create binaryCharArray, create maskCharArray
        char[] binaryCharArray = Long.toBinaryString(memoryAl).toCharArray();
//        System.out.println(Long.toBinaryString(memoryAl));
        char[] maskCharArray = mask.toCharArray();

        //extend binaryCharArray to a length of 36
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
        char[] charsMasked = cloneArray(chars);
        List<Integer> allXPositions = new ArrayList<>();
        for(int i = 0; i < charsMasked.length; i++) {

            if(maskCharArray[i] == 'X') {
                charsMasked[i] = '0';
                allXPositions.add(i);
            } else if(maskCharArray[i] == '1') {
                charsMasked[i] = '1';
            }
        }

        //create allMemoryPositions list and set first two addresses
        List<char[]> allMemPositions = new ArrayList<>();
        int lastX = allXPositions.get(allXPositions.size() -1);
        char[] temp = new char[charsMasked.length];
        System.arraycopy(charsMasked, 0, temp, 0, charsMasked.length);
        allMemPositions.add(cloneArray(temp));
        temp[lastX] = '1';
        allMemPositions.add(cloneArray(temp));

        for(int i = charsMasked.length -1; i >= 0 ; i--) {
            if(i != lastX && allXPositions.contains(i)) {
                List<char[]> newMemPositions = new ArrayList<>();
                for(char[] array : allMemPositions) {
                    char[] newArray0 = cloneArray(array);
                    char[] newArray1 = cloneArray(array);
                    newArray0[i] = '0';
                    newArray1[i] = '1';
                    newMemPositions.add(newArray0);
                    newMemPositions.add(newArray1);
                }
                allMemPositions.addAll(newMemPositions);
            }
        }

        for(char[] array : allMemPositions) {
            binarySaved.put(Long.parseLong(arrayToString(array), 2), decimal);
        }
    }

    public static String arrayToString(char[] array) {
        //build String from charMaskedArray
        StringBuilder sB = new StringBuilder();
        for(char c : array) {
            sB.append(c);
        }
        return sB.toString();
    }

    public static char[] cloneArray(char[] array) {
        char[] temp = new char[array.length];
        System.arraycopy(array, 0, temp, 0, array.length);
        return temp;
    }

}