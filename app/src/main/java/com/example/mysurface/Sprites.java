package com.example.mysurface;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Sprites {
    Bitmap SpritesImage;
    int spritesList[][],spreed;
    float sprite_X,sprite_Y;
    Bitmap Sprite_now;
    int move_X = 0,move_Y = 0;

    public Sprites(Resources resources){
    SpritesImage = BitmapFactory.decodeResource(resources,R.drawable.player);
    sprite_X = SpritesImage.getWidth() / 5;
    sprite_Y = SpritesImage.getHeight() / 3;

    spritesList = new int[SpritesImage.getWidth()/(int)sprite_X][SpritesImage.getHeight()/(int)sprite_Y];
    Sprite_now = Bitmap.createBitmap(SpritesImage, move_X*(int)sprite_X, (int)sprite_Y * move_Y,(move_X+1)*(int)sprite_X, (int)sprite_Y * (1+move_Y));

    }

    public void controlMove(){

        move_X++;
        if (move_X == spritesList.length-1){
            move_X = 0;

            move_Y++;
            if (move_Y == spritesList[0].length-1){
                move_Y = 0;
            }
        }
        if ((move_X+1) * (int)sprite_X-1 <= SpritesImage.getWidth()&&(int)sprite_Y * (1+move_Y)<=SpritesImage.getHeight())
        Sprite_now = Bitmap.createBitmap(SpritesImage,
                move_X * (int)sprite_X-1,
                (int)sprite_Y * move_Y,
             (move_X+1)*(int)sprite_X-1,
             (int)sprite_Y * (1+move_Y));

    }
    public void Speed(){
        spreed+=1;
    }
    public void draw(int x,int y,float heightImage,float widthImage,Canvas canvas){
        Paint paint = new Paint();
        Speed();
        if (spreed >= 50){
            spreed = 0;
            controlMove();
        }
        //Sprite_now.setHeight((int)heightImage);
        //Sprite_now.setWidth((int)widthImage);
        canvas.drawBitmap(Sprite_now, x, y, paint);
    }


}
