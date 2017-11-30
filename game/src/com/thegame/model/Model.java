package com.thegame.model;

import com.thegame.model.base.Coord;
import com.thegame.model.monster.MonsterClass;
import com.thegame.model.monster.MonsterPath;
import com.thegame.model.monster.MonsterType;
import com.thegame.model.wave.LevelWaves;
import com.thegame.model.wave.WaveClass;
import com.thegame.model.weapon.Weapon;
import com.thegame.model.weapon.WeaponClass;
import com.thegame.model.weapon.WeaponSpec;

import java.util.Arrays;
import java.util.HashSet;

public class Model {

    public State state;

    public static Model createDemoModel() {
        Location location = initLocation();
        Model model = new Model();
        model.state = new State(location, 0);
        Weapon w1 = new Weapon(location.weaponClasses.get(0), location.fireCoords.get(0));
        Weapon w2 = new Weapon(location.weaponClasses.get(0), location.fireCoords.get(1));
        Weapon w3 = new Weapon(location.weaponClasses.get(0), location.fireCoords.get(2));
        model.state.weapons.put(w1.coord, w1);
        model.state.weapons.put(w2.coord, w2);
        model.state.weapons.put(w3.coord, w3);
        return model;
    }

    public static Location initLocation() {
        Location location = new Location();
        location.size = new Coord(500, 500);
        location.startHealth = 200;
        location.fireCoords = Arrays.asList(
                new Coord(56, 103), new Coord(172, 103), new Coord(382, 103),
                new Coord(264, 207), new Coord(200, 260), new Coord(164, 332),
                new Coord(266, 394), new Coord(412, 326));
        location.monsterPaths = Arrays.asList(new MonsterPath(Arrays.asList(
                new Coord(0, 33), new Coord(237, 33), new Coord(279, 75),
                new Coord(277, 118), new Coord(92, 332), new Coord(92, 380),
                new Coord(959, 467), new Coord(368, 395), new Coord(300, 300),
                new Coord(400, 170), new Coord(499, 170))),
                null);
        location.levelWaves = Arrays.asList(new LevelWaves(Arrays.asList(
                new WaveClass(location.monsterPaths.get(0),
                        Arrays.asList(MONSTER_ZOMBIE, MONSTER_ZOMBIE, MONSTER_ZOMBIE),
                        Arrays.asList(3000L, 3000L, 5000L)),
                new WaveClass(location.monsterPaths.get(0),
                        Arrays.asList(MONSTER_ZOMBIE, MONSTER_ZOMBIE, MONSTER_ZOMBIE, MONSTER_ZOMBIE, MONSTER_ZOMBIE),
                        Arrays.asList(1000L, 1000L, 2000L, 1000L, 5000L))
        )), null);
        location.weaponClasses = Arrays.asList(WEAPON_MACHINEGUN, null);
        return location;
    }

    public static MonsterClass MONSTER_ZOMBIE = new MonsterClass(
            Arrays.asList(50, 60, 75, 85, 100),
            MonsterType.Land,
            1000,
            "Zombie");

    public static WeaponClass WEAPON_MACHINEGUN = new WeaponClass(
            new HashSet<>(Arrays.asList(MonsterType.Land, MonsterType.Flying)),
            Arrays.asList(
                    new WeaponSpec(200, 3, 150, 150, 0, 0, 3.14),
                    new WeaponSpec(40, 3, 200, 175, 0, 0, 3.14),
                    new WeaponSpec(45, 3, 200, 200, 0, 0, 3.14),
                    new WeaponSpec(50, 4, 200, 200, 0, 0, 3.14),
                    new WeaponSpec(55, 4, 250, 225, 0, 0, 3.14)
            ),
            "Machinegun");
}
