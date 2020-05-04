package com.example.cs125fp;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private MainControl mainControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        mainControl = new MainControl(this, point.x, point.y);

        setContentView(mainControl);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mainControl.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainControl.resume();
    }
}
