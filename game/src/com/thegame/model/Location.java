package com.thegame.model;

import com.thegame.model.base.Coord;
import com.thegame.model.monster.MonsterPath;
import com.thegame.model.wave.LevelWaves;
import com.thegame.model.weapon.WeaponClass;

import java.util.List;

public class Location {
    public Coord size;
    public int startHealth;
    public int startMoney;
    public List<Coord> fireCoords; // points where allowed to build weapons
    public List<MonsterPath> monsterPaths;
    public List<LevelWaves> levelWaves;
    public List<WeaponClass> weaponClasses;
}
