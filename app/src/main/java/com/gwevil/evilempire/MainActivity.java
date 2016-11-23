package com.gwevil.evilempire;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    Thread drawingThread;
    private boolean shouldStopDrawing;
    private int width, height;
    private int origWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SurfaceView drawingSurface = (SurfaceView) findViewById(R.id.surface);
        drawingSurface.getHolder().addCallback(this);
    }


    //when the surface is created, set up a drawing thread. This thread is also responsible for
    //calling update()
    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        shouldStopDrawing = false;
        drawingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    synchronized (holder) {
                        Canvas canvas = holder.lockCanvas();
                        if (canvas != null) {
                            update();
                            clearCanvas(canvas);
                            doDrawing(canvas);
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

    private void update() {
        testx = (testx <= 0? width : testx - 1);
        testy = (testy <= 0? height: testy - 1);
    }

    private void doDrawing(Canvas canvas) {
        //todo change test to actual drawing script
        testDrawing(canvas);
    }

    int testx = width, testy = width;
    private void testDrawing(Canvas canvas) {
        Paint painter = new Paint();
        painter.setColor(Color.RED);
        painter.setStyle(Paint.Style.FILL);
        canvas.drawRect(new Rect(0,0,testx, testy), painter);
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
