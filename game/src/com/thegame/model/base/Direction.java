package com.thegame.model.base;

public class Direction {

    public static final double DEFAULT_THRESHOLD = 0.02;

    public static final double UP = 0;
    public static final double RIGHT = Math.PI / 2;
    public static final double DOWN = -Math.PI;
    public static final double LEFT = -Math.PI / 2;
    public static final double UP_RIGHT = Math.PI / 4;
    public static final double DOWN_RIGHT = -Math.PI * 5 / 4;
    public static final double DOWN_LEFT = -Math.PI * 3 / 4;
    public static final double UP_LEFT = -Math.PI / 4;

    public double angle;

    public Direction() {}

    public Direction(double angle) {
        this.angle = angle;
    }

    public Direction(Coord from, Coord to) {
        this.angle = Math.atan2(from.y - to.y, from.x - to.x) - Math.PI / 2;
    }

    public boolean almostEquals(Coord from, Coord to) {
        return almostEquals(new Direction(from, to).angle, DEFAULT_THRESHOLD);
    }

    public boolean almostEquals(double angle) {
        return almostEquals(angle, DEFAULT_THRESHOLD);
    }

    public boolean almostEquals(double angle, double threshold) {
        return Math.abs(this.angle - angle) < threshold; // TODO case when over Pi
    }

    public void rotate(double targetAngle, double angleStep) {
        double angleDistance = angle - targetAngle;
        if (angleDistance < 0)
            angleDistance = -angleDistance;
        if (angleDistance <= angleStep) {
            angle = targetAngle;
            return;
        }
        if (targetAngle < angle) {
            angle -= angleStep;
        } else {
            angle += angleStep;
        }
    }

    public double xCoef() {
        if (angle == Direction.UP || angle == Direction.DOWN)
            return 0;
        if (angle == Direction.LEFT)
            return -1;
        if (angle == Direction.RIGHT)
            return 1;
        double a;
        if (angle < -Math.PI) // down to right
            a = -(angle + Math.PI);
        else if (angle < (-Math.PI / 2)) // left to down
            a = -(angle + Math.PI);
        else if (angle < 0)
            a = angle;
        else
            a = angle;
        return Math.sin(a);
    }

    public double yCoef() {
        if (angle == Direction.LEFT || angle == Direction.RIGHT)
            return 0;
        if (angle == Direction.UP)
            return -1;
        if (angle == Direction.DOWN)
            return 1;
        double a;
        if (angle < -Math.PI) // down to right
            a = angle + Math.PI * 3 / 2;
            //a = -(angle + Math.PI / 2);
        else if (angle < (-Math.PI / 2)) // left to down
            a = -(angle + Math.PI / 2);
        else if (angle < 0)
            a = -(Math.PI / 2 + angle);
        else
            a = -(Math.PI / 2 - angle);
        return Math.sin(a);
    }

    @Override
    public String toString() {
        return "<" + angle + '>';
    }
}
