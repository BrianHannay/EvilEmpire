package com.gwevil.evilempire;


/**
 * Created by Lindsay on 11/23/2016.
 */

public class World {
    Player player;

    MainActivity ma;
    public World(MainActivity mainActivity, int which) {
        ma = mainActivity;
        player = new Player(this);
        mainActivity.addUpdatable(player);
        mainActivity.addDrawable(player);
        loadWorld(which);
    }

    public int getWidth() {
        return ma.width;
    }

    public int getHeight() {
        return ma.height;
    }
}
