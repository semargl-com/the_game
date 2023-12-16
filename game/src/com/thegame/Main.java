package com.thegame;

import com.thegame.controllers.Controller;
import com.thegame.model.Model;
import com.thegame.util.Log;
import com.thegame.view.View;

public class Main {

    public static void main(String[] args) {
        game();
        /* hi there two*/
        /* hi there one */
    }

    public static void game() {
        // Create MVC
        Model model = Model.createDemoModel();
        View view = new View(model);
        Controller controller = new Controller(model);

        do {
            model.state.next();
            view.draw();
            if (!controller.sleep(1000)) {
                Log.error("Break");
                break;
            }
        } while(!model.state.finished);
        Log.debug(model.state.victory ? "You win" : "You lose");

        // another commit
    }
}
