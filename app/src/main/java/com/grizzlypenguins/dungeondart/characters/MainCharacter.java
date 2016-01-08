package com.grizzlypenguins.dungeondart.characters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.grizzlypenguins.dungeondart.Animation.MainCharacterMovement;
import com.grizzlypenguins.dungeondart.CameraControl;
import com.grizzlypenguins.dungeondart.Level;
import com.grizzlypenguins.dungeondart.PackedLevel;
import com.grizzlypenguins.dungeondart.effects.Effect;
import com.grizzlypenguins.dungeondart.MyPoint;
import com.grizzlypenguins.dungeondart.myFactory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Darko on 21.11.2015.
 */
public class MainCharacter implements Serializable {

    public int speed=1;
    public static MyPoint location = new MyPoint(0,0);

    public boolean moves = false;    //se koristi za animacijata
    int num_of_animation = 0;
    boolean standby = false;
    public boolean alive = false;

    public boolean stunned = false;
    public boolean slowed = false;

    public MainCharacterMovement mainCharacterMovement;


    public ArrayList<Effect> effects = new ArrayList<Effect>();


    public MainCharacter (MyPoint location,int speed)
    {
        alive = true;
        this.location = location;
        this.speed = speed;
    }


    public void tick () // da se aktiviraat powerUps-ot t.e. effectite od powerUps i Traps
    {

    }

    public void render(Canvas c) {

        float x = (int) (myFactory.TILENUMBER / 2) * myFactory.TILESIZE;
        float y = x;

        mainCharacterMovement.render(c,x,y);

        if (stunned) {
            c.drawBitmap(myFactory.getInstance().TrapR, x, y, myFactory.getInstance().paint);
        }
        if (slowed) {
            c.drawBitmap(myFactory.getInstance().TrapY, x, y, myFactory.getInstance().paint);
        }

    }

}
