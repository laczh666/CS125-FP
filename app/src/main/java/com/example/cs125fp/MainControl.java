package com.example.cs125fp;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.Random;
//This is our first time dealing with graphics in android.
//We referred to a Youtube tutorial for the framework of this class.
//link: https://www.youtube.com/watch?v=RQoT9BUsl1Q
public class MainControl extends SurfaceView implements Runnable{

    private boolean playing;
    private int screenx, screeny, score = -1;

    private Thread thread;
    private boolean over = false;
    private Goeff goeff;
    private Dragon dragon;
    private Background bga, bgb;
    private Paint paint;
    private Random random;
    public static float scrax, scray;
    private GameActivity activity;
    private boolean touch = false;
    private GameOver gameOver;

    public MainControl(GameActivity activity, int screenx, int screeny) {
        super(activity);
        this.activity = activity;
        this.screenx = screenx;
        this.screeny = screeny;
        bga = new Background(screenx, screeny, getResources());
        bgb = new Background(screenx, screeny, getResources());
        goeff = new Goeff(screeny, getResources());
        gameOver = new GameOver(screenx, screeny, getResources());
        dragon = new Dragon(getResources());
        bgb.x = screenx;
        paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLACK);
        random = new Random();
        scrax = 1920f / screenx;
        scray = 1080f / screeny;
    }

    @Override
    public void run() {
        while (playing){
            update();
            show();
            sleep();

        }

    }

    private void update() {
        bga.x -= 10 * scrax;
        bgb.x -= 10 * scrax;
        if (bga.x + bga.background.getWidth() < 0) {
            bga.x = screenx;
        }

        if (bgb.x + bgb.background.getWidth() < 0) {
            bgb.x = screenx;
        }
        if (goeff.goup) {
            goeff.y -= 35 * scrax;
        }
        if (goeff.godown) {
            goeff.y += 35 * scray;
        }
        if (goeff.y < 0) {
            goeff.y = 0;
        }
        if (goeff.y >= screeny - goeff.height) {
            goeff.y = screeny - goeff.height;
        }
        dragon.x -= dragon.speed;
        if (dragon.x + dragon.width < 0) {
            score++;
            dragon.speed = random.nextInt(65);
            if (dragon.speed <= 40) {
                dragon.speed = 40;
            }
            dragon.x = screenx;
            dragon.y = random.nextInt(screeny - dragon.height);
        }
        if (Rect.intersects(dragon.getCollisionShape(), goeff.getCollisionShape())) {
            over = true;
            return;
        }
    }

    private void show () {
        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(bga.background, bga.x, bga.y, paint);
            canvas.drawBitmap(bgb.background, bgb.x, bgb.y, paint);
            canvas.drawBitmap(dragon.dragon, dragon.x, dragon.y, paint);
            canvas.drawText("Score: "+ score + "", screenx / 2f - 200, 100, paint);
            if (over) {
                playing = false;
                canvas.drawBitmap(goeff.getDead(), goeff.x, goeff.y, paint);
                canvas.drawBitmap(gameOver.gameover, gameOver.x, gameOver.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                if (touch) {
                    restart();
                }
                return;
            }
            canvas.drawBitmap(goeff.getGoeff(), goeff.x, goeff.y, paint);
            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void sleep() {
        try {
            Thread.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void restart() {
        score = 0;
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }
    public boolean onTouchEvent(MotionEvent event) {

        if (over == false) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (event.getX() < screenx / 2) {
                    goeff.goup = true;
                }
                if (event.getX() > screenx / 2) {
                    goeff.godown = true;
                }
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getX() < screenx / 2) {
                    goeff.goup = false;
                }
                if (event.getX() > screenx / 2) {
                    goeff.godown = false;
                }
            }
        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                touch = true;
            }
        }
        return true;
    }
    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            playing= false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
