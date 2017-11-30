package com.thegame.model.monster;

import com.thegame.util.Log;
import com.thegame.model.base.Coord;
import com.thegame.model.base.Direction;

public class Monster {

    public static final long DYING_TIME = 2000;

    public MonsterClass monsterClass;
    public MonsterState monsterState = MonsterState.Normal;
    public int health;
    public MonsterPath monsterPath;
    public int pathSegmentNumber;
    public double offsetInPathSegment;
    public long actingTime;
    public double progress;

    public Coord coord;
    public Direction direction;

    public Monster(MonsterClass monsterClass, MonsterPath monsterPath, int difficultyLevel) {
        this.monsterClass = monsterClass;
        this.monsterPath = monsterPath;
        health = monsterClass.levelHealth.get(difficultyLevel);
        recalculatePosition();
        Log.debug("Created monster: " + toString());
    }

    public void go(long duration) {
        actingTime += duration;
        if (monsterState == MonsterState.Normal) {
            double distance = duration * monsterClass.speed / 60_000;
            double leftInSegment = coord.distance(monsterPath.segment.get(pathSegmentNumber + 1));
            if (distance > leftInSegment) {
                pathSegmentNumber++;
                offsetInPathSegment = 0;
            } else {
                offsetInPathSegment += distance;
            }
            recalculatePosition();
        }
        else if (monsterState == MonsterState.Dying) {
            if (actingTime >= DYING_TIME) {
                monsterState = MonsterState.Dead;
                actingTime = 0;
            } else {
                progress = actingTime / DYING_TIME;
            }
        }
    }

    public void hit(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            monsterState = MonsterState.Dying;
        }
        Log.debug("Monster hit damage: " + damage + ", health left: " + health);
    }

    private void recalculatePosition() {
        coord = monsterPath.getCoord(pathSegmentNumber, offsetInPathSegment);
        direction = monsterPath.getDirection(pathSegmentNumber);
    }

    @Override
    public String toString() {
        return "{" + monsterClass.name + "/" + monsterState + "/" + health + coord + direction + '}';
    }
}
