package com.adventofcode.dec11;

import java.util.Comparator;

public class CharacterCloneable implements Cloneable, Comparator {
    char c;

    public CharacterCloneable(char c) {
        this.c = c;
    }

    public char getChar() {
        return c;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            return (CharacterCloneable) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == getClass()) {
            return this.getChar() == ((CharacterCloneable) obj).getChar();
        } else {
            return false;
        }
    }
}
