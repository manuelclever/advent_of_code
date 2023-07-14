package de.manuelclever.dec13;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class Dot implements Comparable<Dot> {
    public int x;
    public int y;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return x * 478 + y * 146 ;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj.getClass() == this.getClass()) {
            Dot o = (Dot) obj;
            return o.x == this.x && o.y == this.y;
        }
        return false;
    }

    @Override
    public int compareTo(@NotNull Dot o) {
        if(this.y > o.y) {
            return 1;
        } else if(this.y < o.y) {
            return -1;
        } else {
            return Integer.compare(this.x, o.x);
        }
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
