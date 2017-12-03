package com.thegame.model.weapon;

import com.thegame.model.enemy.EnemyType;

import java.util.List;
import java.util.Set;

public class WeaponClass {
    public Set<EnemyType> attackEnemyTypes;
    public List<WeaponSpec> weaponSpecs;
    public String name;

    public WeaponClass(Set<EnemyType> attackEnemyTypes, List<WeaponSpec> weaponSpecs, String name) {
        this.attackEnemyTypes = attackEnemyTypes;
        this.weaponSpecs = weaponSpecs;
        this.name = name;
    }
}
