package com.thegame.model.enemy;

import com.thegame.util.Log;
import com.thegame.model.base.Coord;
import com.thegame.model.base.Direction;

public class Enemy {

    public static final long DYING_TIME = 2000;

    public EnemyClass enemyClass;
    public EnemyState enemyState = EnemyState.Normal;
    public int health;
    public EnemyPath enemyPath;
    public int pathSegmentNumber;
    public double offsetInPathSegment;
    public long actingTime;
    public double progress;

    public Coord coord;
    public Direction direction;

    public Enemy(EnemyClass enemyClass, EnemyPath enemyPath, int difficultyLevel) {
        this.enemyClass = enemyClass;
        this.enemyPath = enemyPath;
        health = enemyClass.levelHealth.get(difficultyLevel);
        recalculatePosition();
        Log.debug("Created enemy: " + toString());
    }

    public void go(long duration) {
        actingTime += duration;
        if (enemyState == EnemyState.Normal) {
            double distance = duration * enemyClass.speed / 60_000;
            double leftInSegment = coord.distance(enemyPath.segment.get(pathSegmentNumber + 1));
            if (distance > leftInSegment) {
                pathSegmentNumber++;
                offsetInPathSegment = 0;
            } else {
                offsetInPathSegment += distance;
            }
            recalculatePosition();
        }
        else if (enemyState == EnemyState.Dying) {
            if (actingTime >= DYING_TIME) {
                enemyState = EnemyState.Dead;
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
            enemyState = EnemyState.Dying;
        }
        Log.debug("Enemy hit damage: " + damage + ", health left: " + health);
    }

    private void recalculatePosition() {
        coord = enemyPath.getCoord(pathSegmentNumber, offsetInPathSegment);
        direction = enemyPath.getDirection(pathSegmentNumber);
    }

    @Override
    public String toString() {
        return "{" + enemyClass.name + "/" + enemyState + "/" + health + coord + direction + '}';
    }
}
