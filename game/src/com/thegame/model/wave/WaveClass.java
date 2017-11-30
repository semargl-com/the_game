package com.thegame.model.wave;

import com.thegame.model.monster.MonsterClass;
import com.thegame.model.monster.MonsterPath;

import java.util.List;

public class WaveClass {

    public MonsterPath monsterPath;
    public List<MonsterClass> monsterClasses;
    public List<Long> pauses;

    public WaveClass(MonsterPath monsterPath, List<MonsterClass> monsterClasses, List<Long> pauses) {
        assert monsterClasses.size() == pauses.size();
        this.monsterPath = monsterPath;
        this.monsterClasses = monsterClasses;
        this.pauses = pauses;
    }
}
