package com.thegame.model.weapon;

import com.thegame.model.monster.MonsterType;

import java.util.List;
import java.util.Set;

public class WeaponClass {
    public Set<MonsterType> attackMonsterTypes;
    public List<WeaponSpec> weaponSpecs;
    public String name;

    public WeaponClass(Set<MonsterType> attackMonsterTypes, List<WeaponSpec> weaponSpecs, String name) {
        this.attackMonsterTypes = attackMonsterTypes;
        this.weaponSpecs = weaponSpecs;
        this.name = name;
    }
}
