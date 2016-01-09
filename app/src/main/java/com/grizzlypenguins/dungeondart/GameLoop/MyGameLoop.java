package com.grizzlypenguins.dungeondart.GameLoop;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.grizzlypenguins.dungeondart.Activities.GamePanel;

/**
 * Created by Darko on 22.11.2015.
 *
 * This class is a form of timer. Its used to generate different thread for displaying the gameObjects to remove some screen stuttering.
 * As well as to invoke the tick() method in the GamePanel class.
 */

public class MyGameLoop extends Thread {

    private GamePanel view; //Where he should call the render or the tick function
    private boolean running = false;  //Whenever this thread should still work or not
    boolean once=true;
    boolean finished = true;

    //render is the Thread used for dealing with the graphics
    Runnable render = new Runnable() {
        @Override
        public void run() {
           if(finished)  //Checks if the previous thread has finished to prevent memory leak
               render();
        }
    };

    Thread renderSecondary = new Thread(render);
    public MyGameLoop(GamePanel view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    //Main function. Function that determines when the tick() and the render() should be called. Needs refactoring!!!
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
    //Invokes the tick() in gamePanel. Its the processing part of the Application.
    void tick()
    {

        view.tick();
    }
    //Invokes the render() in gamePanel. Its the graphical part of the Application.
    synchronized void  render()
    {
        finished = false;
        Canvas c = null;
        try {
            c = view.getHolder().lockCanvas();  //gets the canvas from the GamePanel ( since GamePanel is SurfaceView )
            //  c = view.getHolder().lockCanvas();
            synchronized ( view.getHolder()) {
                if( c != null ){
                    view.draw(c);  // draw == render
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