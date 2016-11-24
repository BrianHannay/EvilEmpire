package com.gwevil.evilempire;

/**
 * Created by Lindsay on 11/23/2016.
 */

public class World {
    Player player;
    public World(MainActivity mainActivity) {
        player = new Player();
        mainActivity.addUpdatable(player);
        mainActivity.addDrawable(player);
    }
}
