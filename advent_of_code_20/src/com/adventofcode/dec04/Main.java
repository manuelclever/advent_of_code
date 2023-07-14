package com.adventofcode.dec04;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    final static String KEY_BIRTH_YEAR = "byr";
    final static int VAL_BYR_MIN = 1920;
    final static int VAL_BYR_MAX = 2002;

    final static String KEY_ISSUE_YEAR = "iyr";
    final static int VAL_IYR_MIN = 2010;
    final static int VAL_IYR_MAX = 2020;

    final static String KEY_EXPIRATION_YEAR = "eyr";
    final static int VAL_EYR_MIN = 2020;
    final static int VAL_EYR_MAX = 2030;

    final static String KEY_HEIGHT = "hgt";
    final static int VAL_HGT_MIN_CM = 150;
    final static int VAL_HGT_MAX_CM = 193;
    final static int VAL_HGT_MIN_IN = 59;
    final static int VAL_HGT_MAX_IN = 76;

    final static String KEY_HAIR_COLOR = "hcl";
    final static char VAL_HCL_FIRST = '#';
    final static int VAL_HCL_CHARS = 6;
    final static char VAL_HCL_MIN_DIG = '0';
    final static char VAL_HCL_MAX_DIG = '9';
    final static char VAL_HCL_MIN_LET = 'a';
    final static char VAL_HCL_MAX_LET = 'f';

    final static String KEY_EYE_COLOR = "ecl";
    enum VAL_ECL{
        AMB, BLU, BRN, GRY, GRN, HZL, OTH
    }

    final static String KEY_PASSPORT_ID = "pid";
    final static int VAL_PID_DIGITS = 9;

    final static String KEY_COUNTRY_ID = "cid";

    public static void main(String[] args) throws IOException {
        //set source file
        File file = new File("C:\\Users\\NB-Manuel\\OneDrive\\Dokumente\\Coding\\Projects\\" +
                "adventofcode\\src\\com\\adventofcode\\dec04\\input.txt");
        //initialize br and set file
        BufferedReader br = new BufferedReader(new FileReader(file));

        //read line and add to long string
        String tempString = "";
        StringBuilder stringBuilder = new StringBuilder();
        while(true) {

            tempString = br.readLine();
            if(tempString == null) {
                break;
            } else if( tempString.equals("") ) {
                stringBuilder.append(",");
            } else { //if line is not empty
                stringBuilder.append(tempString).append(" ");
            }
        }

        //create array
        String[] stringArray = stringBuilder.toString().split(",");
        int validations = 0;
        for(String s : stringArray) {
            boolean byrValid = false;
            boolean iyrValid = false;
            boolean eyrValid = false;
            boolean hgtValid = false;
            boolean hclValid = false;
            boolean eclValid = false;
            boolean pidValid = false;
            boolean cidValid = false;

            //put info in map
            Map<String, String> infos = new HashMap<>();
            String[] infoArray = s.split(" ");
            for(String info : infoArray) {
                String[] splitInfo = info.split(":");
                String key = splitInfo[0];
                String value = splitInfo[1];
                infos.put(key, value);
            }

            //validation
            String birthYear = "";
            int tempI = 0;
            if(infos.containsKey(KEY_BIRTH_YEAR)) {

                birthYear = infos.get(KEY_BIRTH_YEAR);
                try {
                    tempI = Integer.parseInt(birthYear);
                } catch (NumberFormatException ex) {
                    tempI = Integer.MIN_VALUE;
                }
                if(tempI >= VAL_BYR_MIN && tempI <= VAL_BYR_MAX) {
                    byrValid = true;
                }
            }
            String issueYear = "";
            if(infos.containsKey(KEY_ISSUE_YEAR)) {

                issueYear = infos.get(KEY_ISSUE_YEAR);
                try {
                    tempI = Integer.parseInt(issueYear);
                } catch (NumberFormatException ex) {
                    tempI = Integer.MIN_VALUE;
                }
                if(tempI >= VAL_IYR_MIN && tempI <= VAL_IYR_MAX) {
                    iyrValid = true;
                }
            }
            String expirationYear = "";
            if(infos.containsKey(KEY_EXPIRATION_YEAR)) {

                expirationYear = infos.get(KEY_EXPIRATION_YEAR);
                try {
                    tempI = Integer.parseInt(expirationYear);
                } catch (NumberFormatException ex) {
                    tempI = Integer.MIN_VALUE;
                }
                if(tempI >= VAL_EYR_MIN && tempI <= VAL_EYR_MAX) {
                    eyrValid = true;
                }
            }
            String heightString = "";
            if(infos.containsKey(KEY_HEIGHT)) {
                heightString = infos.get(KEY_HEIGHT);
                int split = 0;
                boolean flag = false; //for when there is no alphabetic char

                //check each char if alphabetic, then save position and split string there
                for(int i = 0; i < heightString.length(); i++) {

                    if(Character.isAlphabetic(heightString.charAt(i))) {
                        split = i;
                        flag = true;
                        break;
                    }
                }

                int height = 0;
                String unit = "";
                if(!flag) {
                    height = Integer.parseInt(heightString);
                } else {
                    height = Integer.parseInt(heightString.substring(0, split));
                    unit = heightString.substring(split);
                }

                if(unit.equals("cm")) {

                    if(height >= VAL_HGT_MIN_CM && height <= VAL_HGT_MAX_CM) {
                        hgtValid = true;
                    }
                } else if(unit.equals("in")) {

                    if(height >= VAL_HGT_MIN_IN && height <= VAL_HGT_MAX_IN) {
                        hgtValid = true;
                    }
                }

            }
            String hairColor = "";
            if(infos.containsKey(KEY_HAIR_COLOR)) {
                hairColor = infos.get(KEY_HAIR_COLOR);

                char[] array = hairColor.toCharArray();
                if( array.length - 1 == VAL_HCL_CHARS && array[0] == VAL_HCL_FIRST) {
                    for(int i = 1; i < array.length; i++) {

                        char c = array[i];
                        if( ( c >= VAL_HCL_MIN_DIG && c <= VAL_HCL_MAX_DIG ) ||
                                ( c >= VAL_HCL_MIN_LET && c <= VAL_HCL_MAX_LET) ) {
                            hclValid = true;
                        } else {
                            hclValid = false;
                            break;
                        }
                    }
                }
            }
            String eyeColor = "";
            if(infos.containsKey(KEY_EYE_COLOR)) {
                eyeColor = infos.get(KEY_EYE_COLOR);

                VAL_ECL[] enums = VAL_ECL.values();
                for(VAL_ECL v : enums) {

                    if(eyeColor.equals(v.name().toLowerCase())) {
                        eclValid = true;
                        break;
                    }
                }
            }
            String passportID = "";
            if(infos.containsKey(KEY_PASSPORT_ID)) {
                passportID = infos.get(KEY_PASSPORT_ID);

                char[] array = passportID.toCharArray();

                if(array.length == 9) {
                    for (int i = 0; i < array.length; i++) {

                        char c = array[i];
                        if (Character.isDigit(c)) {
                            pidValid = true;
                        } else {
                            pidValid = false;
                            break;
                        }
                    }
                }
            }
            String tempS = "";
            if(infos.containsKey(KEY_COUNTRY_ID)) {
                cidValid = true;
            }

//            System.out.print(
//                    "\nCard:\t" + (birthYear.equals("") ? "-----" : birthYear) + "\t" +
//                            (issueYear.equals("") ? "-----" : issueYear) + "\t" +
//                            (expirationYear.equals("") ? "-----" : expirationYear) + "\t" +
//                            (heightString.equals("") ? "-----" : heightString) + "\t" +
//                            (hairColor.equals("") ? "-----" : hairColor) + "\t" +
//                            (eyeColor.equals("") ? "-----" : eyeColor) + "\t" +
//                            (passportID .equals("") ? "-----" : passportID) + "\n" +
//                    "\t\t" + byrValid +
//                    "\t" + iyrValid +
//                    "\t" + eyrValid +
//                    "\t" + hgtValid +
//                    "\t" + hclValid +
//                    "\t" + eclValid +
//                    "\t" + pidValid);
            
            //set validation
            if(byrValid && iyrValid && eyrValid && hgtValid && hclValid && eclValid && pidValid) {
                validations++;
            } else {
            }
        }
        System.out.println("They made it through the checkpoint: " + validations);
    }
}
