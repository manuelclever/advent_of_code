package de.manuelclever;

import de.manuelclever.dec01.Calculate_01;
import de.manuelclever.dec02.Calculate_02;
import de.manuelclever.dec03.Calculate_03;
import de.manuelclever.dec04.Calculate_04;
import de.manuelclever.dec05.Calculate_05;
import de.manuelclever.dec06.Calculate_06;
import de.manuelclever.dec07.Calculate_07;
import de.manuelclever.dec08.Calculate_08;
import de.manuelclever.dec09.Calculate_09;
import de.manuelclever.dec10.Calculate_10;
import de.manuelclever.dec11.Calculate_11;
import de.manuelclever.dec12.Calculate_12;
import de.manuelclever.dec13.Calculate_13;
import de.manuelclever.dec14.Calculate_14;
import de.manuelclever.dec15.Calculate_15;
import de.manuelclever.dec16.Calculate_16;
import de.manuelclever.dec17.Calculate_17;
import de.manuelclever.dec18.Calculate_18;
import de.manuelclever.dec19.Calculate_19;

public class Factory {

    public static Calculator createCalculator(int day) throws Exception {

        switch (day) {
            case 1 -> {return new Calculate_01();}
            case 2 -> {return new Calculate_02();}
            case 3 -> {return new Calculate_03();}
            case 4 -> {return new Calculate_04();}
            case 5 -> {return new Calculate_05();}
            case 6 -> {return new Calculate_06();}
            case 7 -> {return new Calculate_07();}
            case 8 -> {return new Calculate_08();}
            case 9 -> {return new Calculate_09();}
            case 10 -> {return new Calculate_10();}
            case 11 -> {return new Calculate_11();}
            case 12 -> {return new Calculate_12();}
            case 13 -> {return new Calculate_13();}
            case 14 -> {return new Calculate_14();}
            case 15 -> {return new Calculate_15();}
            case 16 -> {return new Calculate_16();}
            case 17 -> {return new Calculate_17();}
            case 18 -> {return new Calculate_18();}
            case 19 -> {return new Calculate_19();}
            default -> throw new Exception("No such class");
        }

    }
}
