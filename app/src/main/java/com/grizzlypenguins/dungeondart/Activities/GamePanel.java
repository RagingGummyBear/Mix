package com.grizzlypenguins.dungeondart.Activities;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.grizzlypenguins.dungeondart.Activities.uiScalingClasses.ScaleGamePlayActivity;
import com.grizzlypenguins.dungeondart.GameLoop.MyGameLoop;
import com.grizzlypenguins.dungeondart.PackedLevel;
import com.grizzlypenguins.dungeondart.PlayerScoring;
import com.grizzlypenguins.dungeondart.R;
import com.grizzlypenguins.dungeondart.Tile;
import com.grizzlypenguins.dungeondart.effects.PowerUpsAndTrapsBank;
import com.grizzlypenguins.dungeondart.myFactory;

import javax.security.auth.callback.Callback;

/**
 * Created by Darko on 22.11.2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public PackedLevel level;
    MyGameLoop myGameLoop;
    boolean runnable= false;
    float canvasZoom = 0;
    boolean gameFinished = false;



    public GamePanel(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize();
    }
    public GamePanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public GamePanel(Context context) {
        super(context);
        initialize();
    }

    void initialize()
    {


       if(myGameLoop == null) myGameLoop = new MyGameLoop(this);
       // this.setFocusable(true);
        runnable = true;

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if(runnable)
        {
            double surfaceSize = 0;
            if(this.getHeight()<this.getWidth())
            {
                surfaceSize = getHeight();
            }
            else
            {
                surfaceSize = getWidth();
            }

            double scale =  surfaceSize/(myFactory.TILENUMBER * myFactory.TILESIZE);
            canvasZoom = (float) scale;
            this.setMinimumHeight((int) scale);
            this.setMinimumWidth((int) scale);
            this.start();
            runnable = false;

        }

        return super.onTouchEvent(event);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }
    void start()
    {

        myGameLoop.setRunning(true);
        myGameLoop.start();  //illegal state
        level.playerScoring.setStartTime(System.nanoTime()/1000000);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        myGameLoop.setRunning(false);
        this.runnable = true;

    }

    public void stop()
    {
        myGameLoop.setRunning(false);
        this.runnable = true;
    }

    public void tick(){
        try {
            level.tick();

        }
        catch (Exception e) {
          //  System.out.println(e.getMessage());
            this.myGameLoop.setRunning(false);
        }
        PowerUpsAndTrapsBank.getInstance().tick();

    }

    public void shake_shake()
    {
        level.shake_shake();
    }

    public void pressedButton(String s)
    {
        switch (s)
        {
            case "right" :
            {
                level.cameraControl.move_right  = true;
                break;
            }
            case "left" :
            {
                level.cameraControl.move_left  = true;
                Log.v("GamePlanel","moveLeft button pressed");
                break;
            }
            case "up" :
            {
                level.cameraControl.move_up  = true;
                break;
            }
            case "down" :
            {
                level.cameraControl.move_down  = true;
                break;
            }
            default:
            {
                level.cameraControl.reset_movement();
                break;
            }

        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.scale(canvasZoom, canvasZoom, 1, 1);
        canvas.save();

//        test.render(canvas, 0, 0);

        if(level != null)
        {
          level.render(canvas);
        }
        canvas.restore();
     if(level!=null)   if(level.gameFinished)this.finishGame();
    }

    public void finishGame()
    {
        myGameLoop.setRunning(false);
        level.finishGame();
        gameFinished = true;
        //GamePlayActivity.finishGame();
    }


}
