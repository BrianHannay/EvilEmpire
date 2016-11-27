package com.gwevil.evilempire;

import android.graphics.Canvas;

/**
 * Created by Lindsay on 11/23/2016.
 */
public class Player extends Entity {
    public Player(World world) {
        super(world, 0, 0, Images.imageCache.get(Images.Image.PLAYER));
    }
}
