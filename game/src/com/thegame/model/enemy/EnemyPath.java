package com.thegame.model.enemy;

import com.thegame.model.base.Coord;
import com.thegame.model.base.Direction;

import java.util.List;

public class EnemyPath {

    public List<Coord> segment;

    public EnemyPath(List<Coord> segment) {
        this.segment = segment;
    }

    public Coord getCoord(int segmentNumber, double offset) {
        Coord coord = segment.get(segmentNumber);
        Direction direction = getDirection(segmentNumber);
        return coord.getOffsetCoord(direction, offset);
    }

    public Direction getDirection(int segmentNumber) {
        return new Direction(segment.get(segmentNumber), segment.get(segmentNumber + 1));
    }
}
