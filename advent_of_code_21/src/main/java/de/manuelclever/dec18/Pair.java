package de.manuelclever.dec18;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pair {
    Pair parent;
    Pair pairLeft;
    Pair pairRight;
    int value;

    public Pair(int value) {
        this.value = value;
    }

    public Pair(Pair pairLeft, Pair pairRight) {
        this.value = -1;
        this.pairLeft = pairLeft;
        this.pairRight = pairRight;

        pairLeft.setParent(this);
        pairRight.setParent(this);
    }

    public Pair(String pair) {
        this.value = -1;
        String reg = "(\\d+),(\\d+)";
        Matcher matcher = Pattern.compile(reg).matcher(pair);

        if(matcher.find()) {
            pairLeft = new Pair(Integer.parseInt(matcher.group(1)));
            pairRight = new Pair(Integer.parseInt(matcher.group(2)));
            pairLeft.setParent(this);
            pairRight.setParent(this);
        }
    }

    public Pair(String pair, Map<Integer, Pair> pairList) {
        this.value = -1;

        String regSeveral = "(-)?(\\d+),(-)?(\\d+)";
        Matcher matcherSeveral = Pattern.compile(regSeveral).matcher(pair);

        if(matcherSeveral.find()) {
            int intLeft = Integer.parseInt(matcherSeveral.group(2));
            int intRight = Integer.parseInt(matcherSeveral.group(4));

            intLeft = matcherSeveral.group(1) == null ? intLeft : intLeft * -1;
            intRight = matcherSeveral.group(3) == null ? intRight : intRight * -1 ;

            //if smaller 0, then its a reference to Pair on Map, else create new Pair of simple Value
            pairLeft = intLeft < 0 ? pairList.get(intLeft) : new Pair(intLeft);
            pairRight = intRight < 0 ? pairList.get(intRight) : new Pair(intRight);

            pairLeft.setParent(this);
            pairRight.setParent(this);

        }
    }

    public void setParent(Pair parent) {
        this.parent = parent;
    }

    public void setPair(Pair pairLeft, Pair pairRight) {
        this.value = -1;
        this.pairLeft = pairLeft;
        this.pairRight = pairRight;

        pairLeft.setParent(this);
        pairRight.setParent(this);
    }

    public void setValue(int value) {
        this.value = value;
        this.pairLeft = null;
        this.pairRight = null;
    }

    public void addValue(int value) {
        if(!hasPair()) {
            this.value += value;
        } else {
            setValue(value);
        }
    }

    public boolean hasPair() {
        return pairLeft != null && pairRight != null && value == -1;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public boolean hasChild(Pair child) {

        if(hasPair()) {
            if (pairLeft == child || pairRight == child) {
                return true;
            }
            if(pairLeft.hasPair() && pairRight.hasPair()) {
                return pairLeft.hasChild(child) || pairRight.hasChild(child);
            } else if(pairLeft.hasPair()) {
                return pairLeft.hasChild(child);
            } else if (pairRight.hasPair()) {
                return pairRight.hasChild(child);
            }
        }
        return false;
    }

    public Pair createNew(Pair pairRight) {
        return new Pair(this, pairRight);
    }

    public void reduce() {
        while(reduce(-1)){} // strange while loop. Is there another way to write this?
    }

    public Boolean reduce(int nested) {
        nested++;
        if(!this.hasPair()) { // no pair -> has value, test split
            if(value > 9) {
                setPair(new Pair(value / 2), new Pair(value % 2 == 0 ? value / 2 : value / 2 + 1));
                return true;
            }
        } else { // pair, test explode

            if (nested == 3) { // explode

                // we are in a 3x nested Pair. If it has another pair in it, this child will be nested 4x times and
                // has to explode. Explode from left to right, so check left child first, then right.
                if(pairLeft.hasPair()) {
                    pairLeftChangeNearestLeftValue();
                    pairLeftChangeNearestRightValue();

                    pairLeft.setValue(0);
                    return true;
                } else if(pairRight.hasPair()) {
                    pairRightChangeNearestLeftValue();
                    pairRightChangeNearestRightValue();

                    pairRight.setValue(0);
                    return true;
                }
            }

            if (pairLeft.reduce(nested)) {
                return true;
            }
            return pairRight.reduce(nested);
        }
        return false;
    }

    private void pairLeftChangeNearestLeftValue() {
        if(hasParent()) {
            List<Pair> pairs = new ArrayList<>();
            pairs.add(this);
            parent.addValueAtParentLeft(pairLeft.pairLeft.value, pairs);
        }
    }

    private void pairLeftChangeNearestRightValue() {
        if(pairRight.hasPair()) {
            pairRight.addValueLeftest(pairLeft.pairRight.value);
        } else {
            pairRight.addValue(pairLeft.pairRight.value);
        }
    }

    private void pairRightChangeNearestLeftValue() {
        if(pairLeft.hasPair()) {
            pairLeft.addValueRightest(pairRight.pairLeft.value);
        } else {
            pairLeft.addValue(pairRight.pairLeft.value);
        }
    }

    private void pairRightChangeNearestRightValue() {
        if(hasParent()) {
            List<Pair> pairs = new ArrayList<>();
            pairs.add(this);
            parent.addValueAtParentRight(pairRight.pairRight.value, pairs);
        }
    }

    private void addValueAtParentLeft(int value, List<Pair> pairs) {
        if(hasPair()) {

            if(pairLeft == pairs.get(0) || pairLeft.hasChild(pairs.get(0))) {
                if(hasParent()) {
                    pairs.add(this);
                    parent.addValueAtParentLeft(value, pairs);
                }
            } else if(pairRight == pairs.get(0) || pairRight.hasChild(pairs.get(0))) {
                pairLeft.addValueRightest(value);
            } else if (!pairs.contains(pairRight)) {
                    pairRight.addValueLeftest(value);
            } else if(hasParent()) {
                    pairs.add(this);
                    parent.addValueAtParentLeft(value, pairs);
                }
            }
    }

    private void addValueAtParentRight(int value, List<Pair> pairs) {
        if(hasPair()) {

            if(pairRight == pairs.get(0) || pairRight.hasChild(pairs.get(0))) {
                if(hasParent()) {
                    pairs.add(this);
                    parent.addValueAtParentRight(value, pairs);
                }
            } else if(pairLeft == pairs.get(0) || pairLeft.hasChild(pairs.get(0))) {
                pairRight.addValueLeftest(value);
            } else if (!pairs.contains(pairLeft)) {
                pairLeft.addValueRightest(value);
            } else if(hasParent()) {
                pairs.add(this);
                parent.addValueAtParentRight(value, pairs);
            }
        }
    }

    private void addValueLeftest(int value) {
        if(hasPair()) {
            pairLeft.addValueLeftest(value);
        } else {
            addValue(value);
        }
    }

    private void addValueRightest(int value) {
        if(hasPair()) {
            pairRight.addValueRightest(value);
        } else {
            addValue(value);
        }
    }

    public long getMagnitude() {
        if(hasPair()) {
            return 3 * pairLeft.getMagnitude() + 2 * pairRight.getMagnitude();
        }
        return value;
    }

    public String allParentsToString() {
        if(hasParent()) {
            return parent.allParentsToString();
        }
        return toString();
    }

    @Override
    public String toString() {
        if(hasPair()) {
            return "[" + pairLeft + "," + pairRight + "]";
        }
        return Integer.toString(value);
    }
}
