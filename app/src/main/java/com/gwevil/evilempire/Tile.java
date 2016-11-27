package com.gwevil.evilempire;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Lindsay on 11/23/2016.
 */

public class Tile implements Drawable {
    Tile(int x, int y, Bitmap image){
        this.x = x;
        this.y = y;
        this.image = image;
    }
    int x, y;
    private Bitmap image;
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
}
