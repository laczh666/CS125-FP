package com.example.cs125fp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GameOver {
    int x,y, width, height;
    Bitmap gameover;
    GameOver(int screenx, int screeny, Resources res) {
        gameover = BitmapFactory.decodeResource(res, R.drawable.gameover);
        width = gameover.getWidth();
        height = gameover.getHeight();
        gameover = Bitmap.createScaledBitmap(gameover, width, height, false);
        x = screenx / 2 - 550;
        y = screeny / 2 - 100;
    }
}
