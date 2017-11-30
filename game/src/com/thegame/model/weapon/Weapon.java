package com.thegame.model.weapon;

import com.thegame.model.base.Coord;
import com.thegame.model.monster.Monster;
import com.thegame.model.base.Direction;
import com.thegame.model.monster.MonsterState;

import java.util.List;

public class Weapon {

    public static final long MAX_LEVEL = 4;
    public static final long BUILD_TIME = 2000;

    public WeaponClass weaponClass;
    public int level;
    public WeaponState weaponState;
    public Coord coord;
    public Direction direction;
    public WeaponSpec spec;
    public Monster targetMonster;
    public long actingTime;
    public double progress;

    public Weapon(WeaponClass weaponClass, Coord coord) {
        this.weaponClass = weaponClass;
        this.coord = coord;
        direction = new Direction();
        weaponState = WeaponState.Building;
        level = -1;
        spec = weaponClass.weaponSpecs.get(0);
    }

    public void go(long duration, List<Monster> monsters) {
        actingTime += duration;
        if (weaponState == WeaponState.Building) {
            if (actingTime >= BUILD_TIME) {
                nextLevel();
                weaponState = WeaponState.Ready;
                actingTime = 0;
            } else {
                progress = actingTime / BUILD_TIME;
            }
        }
        else if (weaponState == WeaponState.Ready) {
            targetMonster = findMonster(monsters);
            if (targetMonster != null) {
                if (direction.almostEquals(coord, targetMonster.coord)) {
                    weaponState = WeaponState.Firing;
                } else {
                    weaponState = WeaponState.Aiming;
                }
                actingTime = 0;
            }
        }
        else if (weaponState == WeaponState.Aiming) {
            double angleStep = spec.rotationSpeed * actingTime / 1000;
            direction.rotate(coord.angle(targetMonster.coord), angleStep);
            if (direction.almostEquals(coord, targetMonster.coord)) {
                weaponState = WeaponState.Firing;
            }
            actingTime = 0;
        }
        else if (weaponState == WeaponState.Firing) {
            if (spec.bulletSpeed == 0) {
                targetMonster.hit(spec.power);
                weaponState = WeaponState.Recharging;
                actingTime = 0;
            }
            direction.angle = coord.angle(targetMonster.coord);
        }
        else if (weaponState == WeaponState.Recharging) {
            if (checkMonster(targetMonster)) {
                if (actingTime >= spec.rechargeTime) {
                    weaponState = WeaponState.Firing;
                } else {
                    progress = actingTime / spec.rechargeTime;
                }
                direction.angle = coord.angle(targetMonster.coord);
            }
            else {
                if (actingTime >= spec.rechargeTime) {
                    weaponState = WeaponState.Ready;
                } else {
                    progress = actingTime / spec.rechargeTime;
                }
            }
        }
    }

    private void nextLevel() {
        if (level == MAX_LEVEL)
            return;
        level++;
        spec = weaponClass.weaponSpecs.get(level);
    }

    private Monster findMonster(List<Monster> monsters) {
        for (Monster monster : monsters) {
            if (checkMonster(monster) && weaponClass.attackMonsterTypes.contains(monster.monsterClass.type)) {
                return monster;
            }
        }
        return null;
    }

    private boolean checkMonster(Monster monster) {
        return monster != null && coord.distance(monster.coord) < spec.distance &&
            (monster.monsterState != MonsterState.Dying && monster.monsterState != MonsterState.Dead);
    }

    @Override
    public String toString() {
        return "(" + weaponClass.name + "/" + weaponState + "/" + coord + direction + "/attacking:" + targetMonster + ")";
    }

}
