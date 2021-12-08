package com.example.mysurface;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class GameMap {
    int sizeTexture = 32;
    int mapArray[][];
    Bitmap textures[];

    public GameMap(int width, int height, Resources resources){
        Random random = new Random();
        mapArray = new int[height / sizeTexture][(width / sizeTexture)+2];
        for (int i = 0; i < mapArray.length; i++){
            for (int j =0; j < mapArray[i].length; j++){
                if( i > mapArray[i].length / 2 + 10 - random.nextInt(7)&&
                    i < mapArray[i].length / 2 + 10 + random.nextInt(7)
                ){
                    mapArray[i][j] = 0;
                }else{
                    mapArray[i][j] = random.nextInt(3)+1;
                }
            }


//            for (int j = 0; j < mapArray[i].length; j++){
//                if (j> mapArray[i].length/2 - 5 - random.nextInt(7)&&
//                    j< mapArray[i].length/2 + 5 + random.nextInt(7)){
//                    mapArray[i][j] = 0;
//                }else{
//                mapArray[i][j] = random.nextInt(3)+1;}
//            }
        }
        textures = new Bitmap[4];
        textures[0] = BitmapFactory.decodeResource(resources,R.drawable.grass);
        textures[1] = BitmapFactory.decodeResource(resources,R.drawable.water);
        textures[2] = BitmapFactory.decodeResource(resources,R.drawable.rock);
        textures[3] = BitmapFactory.decodeResource(resources,R.drawable.lava);
    }

    public void draw(Canvas canvas){
        float x = 0,y = 0;
        Paint paint = new Paint();
        for (int i = 0; i < mapArray.length; i++){
            for (int j = 0; j < mapArray[i].length; j++){
                    canvas.drawBitmap(textures[mapArray[i][j]], x, y, paint);
                    x += sizeTexture;
        }
                    y += sizeTexture;
                    x = 0;
    }
        changMap();
    }

    //изменение карты
    private void  changMap(){

        for (int i = 0 ; i < mapArray.length; i++){
            for (int j = 0; j < mapArray[i].length-1 ; j++){
                mapArray[i][j] = mapArray[i][j+1];
                Random random = new Random();
                if( j == mapArray[i].length-2){
                    if (i > mapArray[i].length / 2 - 10 - random.nextInt(7)&&
                            i < mapArray[i].length / 2 + 10 + random.nextInt(7)){
                        mapArray[i][j] = 0;
                    }else{
                        mapArray[i][j] = random.nextInt(3)+1;}
                }
            }

        }

    }

}
