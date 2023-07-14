package com.adventofcode.dec15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        //read input and put in Map<Num, lastTurn>
        List<Integer> list = Stream.of("11,0,1,10,5,19".split(","))
                .map(Integer::parseInt).collect(Collectors.toList());
        Map<Integer, Long> lastOccurrence = list.stream()
                .collect(Collectors.toMap( Function.identity(), v -> (long) list.indexOf(v) + 1) );

        int prev = list.get(list.size()-1);
        for(long index = list.size() + 1; index <= 30000000; index++) {
            int nxt = (lastOccurrence.get(prev) != null && lastOccurrence.get(prev) != index -1) ?
                    (int) ((index - 1) - lastOccurrence.get(prev)) : 0;
            lastOccurrence.put(prev, index -1);
            prev = nxt;
        }
        System.out.println(prev);
    }
}
