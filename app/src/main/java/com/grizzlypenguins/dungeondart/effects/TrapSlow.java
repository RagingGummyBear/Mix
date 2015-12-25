package com.grizzlypenguins.dungeondart.effects;

import android.graphics.Canvas;

import com.grizzlypenguins.dungeondart.myFactory;

/**
 * Created by Darko on 30.11.2015.
 */
public class TrapSlow extends Effect {

    public TrapSlow(int length, String name, boolean active) {
        super(length, name, active);
    }

    @Override
    void render(Canvas c, float x, float y) {
        if(!this.active)
        {
            c.drawBitmap(myFactory.getInstance().TrapY,x,y,myFactory.getInstance().paint);
        }

    }
}
