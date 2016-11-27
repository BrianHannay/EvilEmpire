package com.gwevil.evilempire;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    Thread drawingThread;
    private boolean shouldStopDrawing;
    public int width, height;
    World w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init(savedInstanceState);

        //load the images into static cache
        new Images(this.getApplicationContext());
        SurfaceView drawingSurface = (SurfaceView) findViewById(R.id.surface);
        drawingSurface.getHolder().addCallback(this);
        w = new World(this, R.raw.level1);
    }

    private void init(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    //when the surface is created, set up a drawing thread. This thread is also responsible for
    //calling update()
    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        shouldStopDrawing = false;

        //determine width and height
        Canvas canvas = holder.lockCanvas();
        width=canvas.getWidth();
        height=canvas.getWidth();
        holder.unlockCanvasAndPost(canvas);

        drawingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    synchronized (holder) {
                        Canvas canvas = holder.lockCanvas();
                        if (canvas != null) {
                            update();
                            clearCanvas(canvas);
                            draw(canvas);
                            holder.unlockCanvasAndPost(canvas);
                        }
                        try {
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (shouldStopDrawing) {
                            break;
                        }
                    }
                }
            }
        });
        drawingThread.start();
    }

    private void clearCanvas(Canvas canvas) {
        Paint painter = new Paint();
        painter.setStyle(Paint.Style.FILL);
        canvas.drawColor(Color.BLACK);
        canvas.drawRect(new Rect(0,0,width, height), painter);
    }

    static ArrayList<Updatable> updated = new ArrayList<>();
    protected void addUpdatable(Updatable newOne){
        updated.add(newOne);
    }

    protected void removeUpdatable(Updatable alreadyAdded){
        updated.remove(alreadyAdded);
    }
    private void update() {
        for (Updatable update: updated) {
            update.update();
        }
    }

    ArrayList<Drawable> drawn = new ArrayList<>();
    protected void addDrawable(Drawable newOne){
        drawn.add(newOne);
    }

    protected void removeDrawable(Drawable alreadyAdded){
        drawn.remove(alreadyAdded);
    }
    private void draw(Canvas canvas) {
        for (Drawable draw: drawn) {
            draw.draw(canvas);
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = width;
        this.height = height;

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                shouldStopDrawing = true;
                drawingThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
