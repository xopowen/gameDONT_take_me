package com.example.mysurface;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MyThread extends Thread {
    boolean isRunning = false;
    SurfaceHolder surfaceHolder;
    MySurfaceView MySurfaceView;
    long prevTime;

    public MyThread(SurfaceHolder holder, MySurfaceView surfaceView){
        surfaceHolder = holder;
        MySurfaceView = surfaceView;
        prevTime = System.currentTimeMillis();
    }

    @Override
    public void run(){
        Canvas canvas;
        while(isRunning){
            if (!surfaceHolder.getSurface().isValid())
                continue;
            canvas = null;
            long nowTime = System.currentTimeMillis();
            long ellapsed = nowTime - prevTime;
            if (ellapsed > 30){
                prevTime = nowTime;
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder){
                    MySurfaceView.draw(canvas);
                }

            }
            if (canvas != null){
                surfaceHolder.unlockCanvasAndPost(canvas);

            }
        }
    }
    void setRunning(boolean f){
        isRunning = f;
    }

}
