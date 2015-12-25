package com.grizzlypenguins.dungeondart.effects;

import android.graphics.Canvas;

import com.grizzlypenguins.dungeondart.myFactory;

/**
 * Created by Darko on 29.11.2015.
 */
public class PowerUpMovementSpeed extends  Effect {

    private float movement = 5;
    private boolean flipflop = true;

    public PowerUpMovementSpeed(int length, String name, boolean active) {
        super(length, name, active);

    }

    public PowerUpMovementSpeed(PowerUpMovementSpeed powerUpR) {
        super(powerUpR.length,powerUpR.name,powerUpR.active);
    }

    @Override
    public void tick()
    {

        if(flipflop)
        {
            movement-=1;
            if(movement < - 5)
            {
                flipflop = false;
                movement = -5;
            }
        }
        else
        {
            movement+=1;
            if(movement > 5)
            {
                movement = 5;
                flipflop = true;
            }
        }
        if(active)--length;

        if(length <= 0) active = false;
    }

    @Override
    void render(Canvas c, float x, float y) {
        y+=movement;
        c.drawBitmap(myFactory.getInstance().PowerUpR, x, y, myFactory.getInstance().paint);
    }

}
