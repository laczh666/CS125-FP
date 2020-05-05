package com.example.cs125fp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Goeff {
    boolean goup = false;
    boolean godown = false;
    int x, y, width, height;
    Bitmap goeff, dead;
    Goeff(int screeny, Resources res) {
        goeff = BitmapFactory.decodeResource(res, R.drawable.maincharacter1);
        dead = BitmapFactory.decodeResource(res, R.drawable.maincharacter3);
        width = goeff.getWidth();
        height = goeff.getHeight();
        goeff = Bitmap.createScaledBitmap(goeff, width, height, false);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);
        y = screeny / 2;
        x = 100;
    }
    Bitmap getGoeff () {
        return goeff;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getDead () {
        return dead;
    }


}
