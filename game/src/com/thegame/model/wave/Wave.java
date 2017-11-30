package com.thegame.model.wave;

public class Wave {

    public WaveClass waveClass;
    public int lastMonsterNumber = -1;
    public long lastMonsterTime;

    public Wave(WaveClass waveClass) {
        this.waveClass = waveClass;
    }
}
