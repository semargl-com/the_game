package com.thegame.model;

import com.thegame.util.Log;
import com.thegame.model.base.Coord;
import com.thegame.model.monster.Monster;
import com.thegame.model.monster.MonsterClass;
import com.thegame.model.monster.MonsterState;
import com.thegame.model.wave.Wave;
import com.thegame.model.weapon.Weapon;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class State {

    public Location location;
    public int difficultyLevel;

    public int health;
    public int money;
    public int waveNumber = -1;
    public long lastTime;
    public long currTime;
    public boolean finished = false;
    public boolean victory = false;

    public Wave wave;
    public Map<Coord, Weapon> weapons = new ConcurrentHashMap<>();
    public List<Monster> monsters = new CopyOnWriteArrayList<>();

    public State(Location location, int difficultyLevel) {
        this.location = location;
        this.difficultyLevel = difficultyLevel;
        health = location.startHealth;
        money = location.startMoney;
    }

    public void next() {
        currTime = System.currentTimeMillis();
        long duration = lastTime != 0 ? (currTime - lastTime) : 0;

        checkWave();

        for (Coord weaponCoord : weapons.keySet()) {
            Weapon weapon = weapons.get(weaponCoord);
            weapon.go(duration, monsters);
        }

        for (Monster monster : monsters) {
            monster.go(duration);
            if (monster.monsterState == MonsterState.Dead)
                monsters.remove(monster);
        }

        checkIsFinished();

        lastTime = currTime;
    }

    private void checkWave() {
        if (waveNumber == -1) {
            nextWave();
        }

        if (wave.lastMonsterNumber == -1) {
            pushNextMonster();
        }
        else {
            long lastMonsterPause = wave.waveClass.pauses.get(wave.lastMonsterNumber);
            if (wave.lastMonsterTime + lastMonsterPause < currTime) {
                if (isLastMonsterInWavePushed()) {
                    if (!isLastWave()) {
                        nextWave();
                    }
                } else {
                    pushNextMonster();
                }
            }
        }
    }

    private void nextWave() {
        waveNumber++;
        Log.debug("nextWave difficultyLevel: " + difficultyLevel + ", waveNumber: " + waveNumber);
        wave = new Wave(location.levelWaves.get(difficultyLevel).waveClasses.get(waveNumber));
    }

    private void pushNextMonster() {
        wave.lastMonsterNumber++;
        MonsterClass monsterClass = wave.waveClass.monsterClasses.get(wave.lastMonsterNumber);
//        Coord coord = wave.waveClass.monsterPath.segment.get(0);
//        Coord nextSegmentCoord = wave.waveClass.monsterPath.segment.get(1);
//        Direction direction = new Direction(coord, nextSegmentCoord);
        Monster newMonster = new Monster(monsterClass, wave.waveClass.monsterPath, difficultyLevel);
        monsters.add(newMonster);
        wave.lastMonsterTime = currTime;
    }

    private void checkIsFinished() {
        if (health <= 0) {
            Log.debug("Finished. health = " + health);
            finished = true;
        }
        else if (isLastWave() && monsters.size() == 0 && isLastMonsterInWavePushed()) {
            Log.debug("Finished. monsters.size = " + monsters.size());
            finished = true;
            victory = true;
        }
    }

    private boolean isLastWave() {
        return waveNumber == location.levelWaves.get(difficultyLevel).waveClasses.size() - 1;
    }

    private boolean isLastMonsterInWavePushed() {
        return wave.lastMonsterNumber == wave.waveClass.monsterClasses.size() - 1;
    }
}
