package com.grizzlypenguins.dungeondart.characters;

import android.graphics.Canvas;
import android.util.Log;

import com.grizzlypenguins.dungeondart.GameLoop.FindNextStep;
import com.grizzlypenguins.dungeondart.effects.Effect;
import com.grizzlypenguins.dungeondart.MyPoint;
import com.grizzlypenguins.dungeondart.myFactory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Darko on 21.11.2015.
 */
public class EvilMonster implements Serializable{

    public int [][]movementMap;
    public MyPoint location = new MyPoint(0,0);
    public MyPoint playerLocation = new MyPoint(0,0);
    private Thread thread;

    private boolean runAlgo = false;

    public int speed;
    int move = 1;

    public boolean showing = false;
    int num_animation = 5;
    public int facingSide = 0;  //0 = right, 1 = down, 2 = right, 3 = up

    public ArrayList<Effect> effects = new ArrayList<Effect>();

    public EvilMonster(MyPoint location, int speed,int [][]map) {

        movementMap = map;
        this.location = location;
        this.speed = speed;
    }

    public EvilMonster(int[][] movementMap, MyPoint location, MyPoint playerLocation, int speed) {
        this.movementMap = movementMap;
        this.location = location;
        this.playerLocation = playerLocation;
        this.speed = speed;
        this.move = speed;
    }

    public boolean tick()
    {

            if (move >= speed) {

                if(myFactory.getInstance().findNextStep.isFinished())
                {

                    this.location.x+=myFactory.getInstance().findNextStep.nextStep.x;
                    this.location.y+=myFactory.getInstance().findNextStep.nextStep.y;
                    myFactory.getInstance().findNextStep.monsterLocation = this.location;
                    myFactory.getInstance().findNextStep.playerLocation = this.playerLocation;

                    //myFactory.getInstance().findNextStep.run();
                   if(myFactory.getInstance().findNextStep.isFinished()) myFactory.getInstance().findNextStep = new FindNextStep(movementMap,playerLocation,location);
                    thread = new Thread(myFactory.getInstance().findNextStep);
                    thread.start();

                }
                move = 0;
                return step();
            }
            move++;
            return false;

    }


    public boolean step ()
    {
      //  location.y+=nextStep.y;
       // location.x+=nextStep.x;
        if(location == playerLocation)
        {
            return true;
        }
        else return false;
    }

    public void render(Canvas c,float x,float y)
    {

    }


}
