package com.grizzlypenguins.dungeondart.effects;

import android.graphics.Canvas;

import com.grizzlypenguins.dungeondart.myFactory;

/**
 * Created by Darko on 30.11.2015.
 */
public class PowerUpBonusPoints extends Effect {


    private float movement = 5;
    private boolean flipflop = true;

    public PowerUpBonusPoints(int length, String name, boolean active) {
        super(length, name, active);
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
        c.drawBitmap(myFactory.getInstance().PowerUpY, x, y, myFactory.getInstance().paint);
    }

}
