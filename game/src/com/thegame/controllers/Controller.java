package com.thegame.controllers;

import com.thegame.model.Model;
import com.thegame.util.Time;

public class Controller {

    Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public boolean sleep(long time) {
        return Time.sleep(time); // or (time * speed_coef) to fast replay
    }

}
