package com.thegame.model.monster;

import java.util.List;

public class MonsterClass {

    public List<Integer> levelHealth; // hit-points depends on difficulty level
    public MonsterType type;
    public double speed; // pixels per minute
    public String name;

    public MonsterClass(List<Integer> levelHealth, MonsterType type, double speed, String name) {
        this.levelHealth = levelHealth;
        this.type = type;
        this.speed = speed;
        this.name = name;
    }

}
