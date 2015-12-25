package com.grizzlypenguins.dungeondart;

import java.io.Serializable;

/**
 * Created by Darko on 22.11.2015.
 */
public class MyPoint implements Serializable {
    public int x=0;
    public int y= 0;
    public MyPoint(int x,int y){
        this.x= x;
        this.y=y;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new MyPoint(x,y);
    }
}
