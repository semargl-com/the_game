package com.thegame.model.base;

public class Coord {
    public double x;
    public double y;

    public Coord() {}

    public Coord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Coord p) {
        return Math.hypot(x - p.x, y - p.y);
    }

    public double angle(Coord p) {
        return Math.atan2(y - p.y, x - p.x) - Math.PI / 2;
    }

    public Coord getOffsetCoord(Direction direction, double offset) {
        return new Coord(x + (offset * direction.xCoef()), y + (offset * direction.yCoef()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }

    @Override
    public int hashCode() {
        return (int)y * 10_000 + (int)(x);
    }

    @Override
    public String toString() {
        return "(" + (int)x + "," + (int)y + ')';
    }
}
