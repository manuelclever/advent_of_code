package de.manuelclever;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestSolutions
{

    @ParameterizedTest
    @MethodSource("dayPartSolution")
    public void testAllSolutions(long[] dayPartSolution) {
        int day = (int) dayPartSolution[0];
        int part = (int) dayPartSolution[1];
        long solution = dayPartSolution[2];

        try {
            Calculator calculator = Factory.createCalculator(day);

            if(part == 1) {
                Assertions.assertEquals(solution, calculator.calculatePart1());
            } else {
                Assertions.assertEquals(solution, calculator.calculatePart2());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assertions.fail();
        }
    }

    private static Stream<long[]> dayPartSolution() {
        List<long[]> list = new ArrayList<>();

        list.add(new long[]{1,1,1521});
        list.add(new long[]{1,2,1543});
        list.add(new long[]{2,1,2150351});
        list.add(new long[]{2,2,1842742223});
        list.add(new long[]{3,1,4001724});
        list.add(new long[]{3,2,587895});
        list.add(new long[]{4,1,74320});
        list.add(new long[]{4,3,17884});
        list.add(new long[]{5,1,7318});
        list.add(new long[]{5,2,19939});
        list.add(new long[]{6,1,377263});
        list.add(new long[]{6,2,1695929023803L});
        list.add(new long[]{7,1,364898});
        list.add(new long[]{7,2,104149091});
        list.add(new long[]{8,1,440});
//        list.add(new long[]{8,2,0});
        list.add(new long[]{9,1,465});
//        list.add(new long[]{9,2,786780});
        list.add(new long[]{10,1,243939});
        list.add(new long[]{10,2,2421222841L});
        list.add(new long[]{11,1,1675});
        list.add(new long[]{11,2,515});
        list.add(new long[]{12,1,3369});
        list.add(new long[]{12,2,85883});
        list.add(new long[]{13,1,753});
//        list.add(new long[]{13,2,HZLEHJRK});  <- not tested because solution is drawn in console and read manually
        list.add(new long[]{14,1,2112});
        list.add(new long[]{14,2,3243771149914L});
        list.add(new long[]{15,1,739});
        list.add(new long[]{15,2,3040});
        list.add(new long[]{16,1,1007});
        list.add(new long[]{16,2,834151779165L});
        list.add(new long[]{17,1,2701});
        list.add(new long[]{17,2,1070});
//        list.add(new long[]{18,1,0}); <- should work, but doesn't
//        list.add(new long[]{18,2,0});

        return list.stream();
    }
}
