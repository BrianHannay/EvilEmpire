package com.gwevil.evilempire;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Lindsay on 11/23/2016.
 */
public class Entity implements Updatable, Drawable{

    int x,y;
    World world;
    Bitmap bitmap;

    public Entity(World world, int x, int y, Bitmap bitmap){
        this.bitmap = bitmap;
        this.world = world;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    @Override
    public void update() {

    }
}
