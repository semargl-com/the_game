package com.thegame.view;

import com.thegame.util.Log;
import com.thegame.model.Model;
import com.thegame.model.State;
import com.thegame.model.monster.Monster;
import com.thegame.model.weapon.Weapon;

public class View {

    protected Model model;

    public View(Model model) {
        this.model = model;
    }

    public void draw() {
        consoleDraw();
    }

    public void consoleDraw() {
        State state = model.state;
        Log.debug("------ Health: " + state.health + ", Money: " + state.money + ", Wave: " + state.waveNumber + " -------");
        for (Monster monster : state.monsters) {
            Log.debug(monster.toString());
        }
        for (Weapon weapon : state.weapons.values()) {
            Log.debug(weapon.toString());
        }
    }
}
