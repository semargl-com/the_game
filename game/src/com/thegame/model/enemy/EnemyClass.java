package com.thegame.model.enemy;

import java.util.List;

public class EnemyClass {

    public List<Integer> levelHealth; // hit-points depends on difficulty level
    public EnemyType type;
    public double speed; // pixels per minute
    public String name;

    public EnemyClass(List<Integer> levelHealth, EnemyType type, double speed, String name) {
        this.levelHealth = levelHealth;
        this.type = type;
        this.speed = speed;
        this.name = name;
    }

}
