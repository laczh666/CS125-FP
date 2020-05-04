package com.example.cs125fp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Dragon {
    public int speed = 40;
    int x = 0, y, width, height;
    Bitmap dragon;
    Random random;
    Dragon(Resources res) {

        dragon = BitmapFactory.decodeResource(res, R.drawable.dragon);
        width = dragon.getWidth();
        height = dragon.getHeight();
        dragon = Bitmap.createScaledBitmap(dragon, width, height, false);
        random = new Random();

    }
    Bitmap getDragon() {
        return dragon;
    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
