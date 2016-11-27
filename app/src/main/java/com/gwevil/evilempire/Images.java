package com.gwevil.evilempire;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lindsay on 11/23/2016.
 */

public class Images {
    public enum Image {
        PLAYER,
    };

    public static HashMap<Image, Bitmap> imageCache = new HashMap<>();

    Context context = null;
    Images(Context applicationContext) {
        context = applicationContext;

        //cache all images below.
        cacheImage(Image.PLAYER, R.drawable.player);
    }
    private void cacheImage(Image which, int id){
        //decode and cache the bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        imageCache.put(which, bitmap);
    }
}
