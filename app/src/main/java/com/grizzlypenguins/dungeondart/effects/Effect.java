package com.grizzlypenguins.dungeondart.effects;

import android.graphics.Canvas;

/**
 * Created by Darko on 21.11.2015.
 */
public abstract class Effect {

    public String name;
    public int length;
    public boolean active ;

    abstract void render(Canvas c,float x, float y);

    public Effect(int length, String name, boolean active) {
        this.length = length;
        this.name = name;
        this.active = active;
    }

    public void tick()
    {
        if(active)--length;

        if(length<=0) active = false;

    }

    public void destroy()
    {
        this.destroy();
    }

}
