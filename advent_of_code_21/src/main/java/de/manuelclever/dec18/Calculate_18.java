package de.manuelclever.dec18;

import de.manuelclever.Calculator;
import de.manuelclever.MyFileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculate_18 implements Calculator {

    @Override
    public long calculatePart1() {
        MyFileReader fr = new MyFileReader(18,1);
        List<String> additionList = fr.getStringList();

        Pair pair = createPairFromString(additionList.get(0));
        pair.reduce();
        for(int i = 1; i < additionList.size(); i++) {
            Pair newPair = createPairFromString(additionList.get(i));
            pair = pair.createNew(newPair);
            pair.reduce();
        }
        System.out.println(pair);
        return pair.getMagnitude();
    }

    private Pair createPairFromString(String number) {
        StringBuilder sb = new StringBuilder(number);
        Pattern pattern = Pattern.compile("\\[(-)?\\d+,(-)?\\d+\\]");
        Matcher matcher = pattern.matcher(sb);

        Map<Integer, Pair> pairs = new HashMap<>(); // keys are all negativ
        int i = -1;
        while(matcher.find()) {

            Pair newPair;
            if(i == -1) {
                newPair = new Pair(matcher.group(0));
            } else {
                newPair = new Pair(matcher.group(0), pairs);
            }

            pairs.put(i, newPair);
            sb.replace(matcher.start(), matcher.end(), Integer.toString(i));
            matcher = pattern.matcher(sb);
            i--;
        }
        return pairs.get(pairs.size() * -1);
    }

    @Override
    public long calculatePart2() {
        return 0;
    }
}
