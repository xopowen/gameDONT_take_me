package com.example.mysurface;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Sprites {
    Bitmap SpritesImage;
    int spritesList[][],speed;
    int sprite_X,sprite_Y;
    Bitmap Sprite_now;
    int move_X = 0,move_Y = 0;

    public Sprites(Bitmap SpritesImage_out){
    SpritesImage = SpritesImage_out;
    sprite_X = SpritesImage.getWidth() / 5;//110
    sprite_Y = SpritesImage.getHeight() / 3;//100
                            //300/100                                  550/110
    spritesList = new int[SpritesImage.getHeight()/sprite_X][SpritesImage.getWidth()/sprite_Y];
    Sprite_now = Bitmap.createBitmap(SpritesImage, 0, 0,(int)sprite_X, (int)sprite_Y);

    }

    public void controlMove(){

        move_X+=1;
        if (move_X == 4){
            move_X = 0;
            move_Y+=1;
            if (move_Y == 2){
                move_Y = 0;
            }
        }

        Sprite_now = Bitmap.createBitmap(SpritesImage,
                move_X * sprite_X,
                move_Y * sprite_Y,
                sprite_X ,
                sprite_Y );

    }
    public void Speed(){
        speed += 1;
    }
    public void draw(int x,int y,float heightImage,float widthImage,Canvas canvas){
        Paint paint = new Paint();
        Speed();
        if (speed >= 3){
            speed = 0;
            controlMove();
        }
        //Sprite_now.setHeight((int)heightImage);
        //Sprite_now.setWidth((int)widthImage);
        canvas.drawBitmap(Sprite_now, x, y, paint);
    }


}
