package com.grizzlypenguins.dungeondart.effects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.grizzlypenguins.dungeondart.GameLoop.MyGameLoop;
import com.grizzlypenguins.dungeondart.myFactory;

/**
 * Created by Darko on 29.11.2015.
 */
public class TrapStun extends Effect{


    public TrapStun(int length, String name, boolean active) {
        super(length, name, active);
    }

    @Override
    void render(Canvas c, float x, float y) {
        if(!this.active)
        {
            c.drawBitmap(myFactory.getInstance().TrapR,x,y,myFactory.getInstance().paint);
        }

    }
}
