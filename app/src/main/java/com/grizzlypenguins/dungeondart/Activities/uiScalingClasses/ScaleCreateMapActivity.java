package com.grizzlypenguins.dungeondart.Activities.uiScalingClasses;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Darko on 07.01.2016.
 */
public class ScaleCreateMapActivity implements Serializable {

    public int middleButtonWidth = 0;
    public int middleButtonHeight = 0;

    public int notMiddleButtonWidth = 0;
    public int notMiddleButtonHeight = 0;


    //Margins for The buttons
    public int leftButtonLeftMargin = 0;
    public int rightButtonLeftMargin = 0;
    public int downButtonLeftMargin = 0;
    public int leftButtonTopMargin = 0;


    public int GamePanel = 0;

    public void setAll(float screenWidth, float screenHeight)
    {
        //buttonWidth = (int) (screenHeight * 0.1);
        //buttonHeight = (int) (screenWidth * 0.1);

        double gamePadScreenPercent = 0;
        double separationPercent = 0.05;
        float size = 0;

        if(screenHeight> screenWidth)
        {
            gamePadScreenPercent = screenHeight;
            size = screenWidth;
        }
        else
        {
            gamePadScreenPercent =screenWidth;
            size = screenHeight;
        }

        leftButtonLeftMargin = (int) (size + (gamePadScreenPercent * separationPercent));

        middleButtonWidth = (int) ((gamePadScreenPercent*0.95) - leftButtonLeftMargin);
        Log.v("middleButtonWidth: ", "" + middleButtonWidth);
        //  double buttonDistance = (middleButtonWidth / 2.4) - (middleButtonWidth / 2);
        double buttonDistance = (middleButtonWidth / 3);
        middleButtonWidth -= buttonDistance;

        middleButtonWidth /= 2;

        rightButtonLeftMargin = leftButtonLeftMargin + middleButtonWidth + (int)buttonDistance;

        notMiddleButtonWidth = (int) buttonDistance;

        downButtonLeftMargin = leftButtonLeftMargin+middleButtonWidth;

        double correctionTop = size - (( middleButtonWidth * 2 ) + buttonDistance);

        correctionTop /= 2;

        leftButtonTopMargin = (int) (correctionTop + middleButtonWidth+(middleButtonHeight*1.3));

        middleButtonHeight = (int) buttonDistance;

        notMiddleButtonHeight = middleButtonWidth;

        GamePanel = (int) size;


        Log.v("ScaleGamePlayActivity","rightM: "+rightButtonLeftMargin+" leftM:"+ leftButtonLeftMargin);

    }
}
