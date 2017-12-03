package com.thegame.model.weapon;

import com.thegame.model.base.Coord;
import com.thegame.model.enemy.Enemy;
import com.thegame.model.base.Direction;
import com.thegame.model.enemy.EnemyState;

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
    public Enemy targetEnemy;
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

    public void go(long duration, List<Enemy> enemies) {
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
            targetEnemy = findMonster(enemies);
            if (targetEnemy != null) {
                if (direction.almostEquals(coord, targetEnemy.coord)) {
                    weaponState = WeaponState.Firing;
                } else {
                    weaponState = WeaponState.Aiming;
                }
                actingTime = 0;
            }
        }
        else if (weaponState == WeaponState.Aiming) {
            double angleStep = spec.rotationSpeed * actingTime / 1000;
            direction.rotate(coord.angle(targetEnemy.coord), angleStep);
            if (direction.almostEquals(coord, targetEnemy.coord)) {
                weaponState = WeaponState.Firing;
            }
            actingTime = 0;
        }
        else if (weaponState == WeaponState.Firing) {
            if (spec.bulletSpeed == 0) {
                targetEnemy.hit(spec.power);
                weaponState = WeaponState.Recharging;
                actingTime = 0;
            }
            direction.angle = coord.angle(targetEnemy.coord);
        }
        else if (weaponState == WeaponState.Recharging) {
            if (checkMonster(targetEnemy)) {
                if (actingTime >= spec.rechargeTime) {
                    weaponState = WeaponState.Firing;
                } else {
                    progress = actingTime / spec.rechargeTime;
                }
                direction.angle = coord.angle(targetEnemy.coord);
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

    private Enemy findMonster(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (checkMonster(enemy) && weaponClass.attackEnemyTypes.contains(enemy.enemyClass.type)) {
                return enemy;
            }
        }
        return null;
    }

    private boolean checkMonster(Enemy enemy) {
        return enemy != null && coord.distance(enemy.coord) < spec.distance &&
            (enemy.enemyState != EnemyState.Dying && enemy.enemyState != EnemyState.Dead);
    }

    @Override
    public String toString() {
        return "(" + weaponClass.name + "/" + weaponState + "/" + coord + direction + "/attacking:" + targetEnemy + ")";
    }

}
