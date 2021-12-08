package com.example.mysurface;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import java.util.Random;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    Bitmap image,wall;//переменная для картинки
    Paint paint;
    float iX, iY, tX=0,tY=0,wall_Y = 0,wall_X = 0;
    float dx = 0, dy = 0;
    Resources res;//достп к ресурсам
    MyThread MyThread;// 2 поток
    //контроль столькновения и размеров
    float heightImage,weightImage;
    //определяем в момент поялвения Conves
    float heightScreen,weightScreen;
    boolean isFirstDraw = true;//задать размеры экрана только 1 раз
    //переменные наших квадратов
    Rect wall_Rect, image_Rect;
    //карта
    GameMap gameMap;
    //счёт игры
    int score = 0;

    public MySurfaceView(Context context) {
        super(context);

        //комада которая актевирует наши методы интерфейся
        getHolder().addCallback(this);

        res = getResources();
        image = BitmapFactory.decodeResource(res, R.drawable.pers);
        wall = BitmapFactory.decodeResource(res, R.drawable.wall);
        heightImage = image.getHeight();
        weightImage = image.getWidth();
        //начяльное положение
        iX = 0;
        iY = 0;

        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(5);
        paint.setTextSize(60);
        setAlpha(0);
    }



    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        MyThread = new MyThread(getHolder(),this);
        MyThread.setRunning(true);
        MyThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceChanged){
        MyThread.setRunning(false);
        try{
            MyThread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (isFirstDraw){
            heightScreen = canvas.getHeight();
            weightScreen = canvas.getWidth();
            wall_X  = weightScreen / 2 ;
            Random random = new Random();
            wall_Y = random.nextInt((int)heightScreen-wall.getHeight()-5);

            gameMap = new GameMap((int)weightScreen, (int)heightScreen, res);
            iX = weightScreen / 2;
            iY = 4 * heightScreen / 5;
            isFirstDraw = false;
        }
        //движение стены
        if(wall_X > -wall.getWidth()*2 ){
            wall_X -= 50;
        }else {
            Random random = new Random();
            wall_Y = random.nextInt((int)heightScreen-wall.getHeight()-5);
            wall_X = weightScreen + wall.getWidth();
            if (dy !=0)
                score += 1;
        }


        gameMap.draw(canvas);

        //спомогательная жолтая линия
        canvas.drawLine(0 + heightImage / 2, iY + weightImage / 2, tX, tY, paint);

        //перремещяем картинку
        canvas.drawBitmap(image, 0, iY, paint);

        //стена
        canvas.drawBitmap(wall, wall_X, wall_Y, paint);
        wall_Rect = new Rect((int) wall_X,(int)wall_Y,(int)wall_X+wall.getWidth(),(int)wall_Y+wall.getHeight());
        canvas.drawText(String.format("score = %d",score),
                weightScreen/2,
                60,
                paint);
        //после перерисовки мы должны расчитать
        // на сколько должна сдвинться картинка на след кадре
        //if (tX != 0)
            //функция расчёта
           // delta();
        image_Rect = new Rect((int) iX,(int)iY,
                (int)(iX+weightImage),(int)(iY+heightImage));
        if(image_Rect.intersect(wall_Rect) && dy!=0){
            Random random = new Random();
            wall_Y = random.nextInt((int)heightScreen-wall.getHeight()-5);
            wall_X = weightScreen + wall.getWidth();
            score -= 1;
            dx = 0;
            dy = 0;
        }
        iX += dx;
        iY += dy;
        checkScreen();
    }

    private void checkScreen(){
        if(iY + heightImage >= heightScreen || iY  <= 0){
            dy = -dy;
        }
        if( iX + weightImage >= weightScreen || iX  <= 0){
            dx = -dx;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            tX = event.getX();
            tY = event.getY();
            //елси она тут то она неостоновится
            delta();
        }
        return true;
    }
    //расчёт смищение картинки
    void delta(){
        double ro = Math.sqrt(Math.pow(tX - tY, 2) + Math.pow(tY - iY,2));
        double k = 15;
        dx = (float)(k*(tX-iX)/ro);
        dy = (float)(k*(tY-iY)/ro);
    }
}