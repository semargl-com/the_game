package com.thegame.model.wave;

import com.thegame.model.enemy.EnemyClass;
import com.thegame.model.enemy.EnemyPath;

import java.util.List;

public class WaveClass {

    public EnemyPath enemyPath;
    public List<EnemyClass> enemyClasses;
    public List<Long> pauses;

    public WaveClass(EnemyPath enemyPath, List<EnemyClass> enemyClasses, List<Long> pauses) {
        assert enemyClasses.size() == pauses.size();
        this.enemyPath = enemyPath;
        this.enemyClasses = enemyClasses;
        this.pauses = pauses;
    }
}
