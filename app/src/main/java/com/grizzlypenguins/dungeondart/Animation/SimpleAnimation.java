package com.grizzlypenguins.dungeondart.Animation;

import android.graphics.Canvas;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Darko on 07.01.2016.
 */
public interface SimpleAnimation extends Serializable {

    void render(Canvas c,float x,float y); //x,y = possitions

}
