package de.manuelclever.dec15;

public class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return x * 9 + y * 21 + x^3 + y^4;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == this.getClass()) {
            Point o = (Point) obj;
            return o.x == this.x && o.y == y;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }


}
