package com.grizzlypenguins.dungeondart.GameLoop;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.grizzlypenguins.dungeondart.Activities.GamePanel;

/**
 * Created by Darko on 22.11.2015.
 */

public class MyGameLoop extends Thread {

    private GamePanel view;
    private boolean running = false;
    boolean once=true;
    boolean finished = true;
    Runnable render = new Runnable() {
        @Override
        public void run() {
           if(finished) render();
        }
    };
    Thread renderSecondary = new Thread(render);

    public MyGameLoop(GamePanel view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long lastTime=System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        long timer2=timer;
        int frames = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta+= (now - lastTime) / ns;
            lastTime = now;
            while(delta>=1)
            {
                tick();
                delta--;
            }
            if(running)
                if(once) {
                    renderSecondary.run();
                  //  render();


                }
            frames++;

            if(System.currentTimeMillis()-timer>1000)
            {

                timer += 1000;
                frames = 0;
            }

        }


    }

    void tick()
    {

        view.tick();
    }

    synchronized void  render()
    {
        finished = false;
        Canvas c = null;
        try {
            c = view.getHolder().lockCanvas();
            //  c = view.getHolder().lockCanvas();


            synchronized ( view.getHolder()) {
                if( c != null ){
                    view.draw(c);
                }
            }
        } finally {
            if (c != null) {
                view.getHolder().unlockCanvasAndPost(c);
            }
        }
        finished = true;
    }
}