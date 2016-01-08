package com.grizzlypenguins.dungeondart.Animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.grizzlypenguins.dungeondart.CameraControl;
import com.grizzlypenguins.dungeondart.myFactory;

/**
 * Created by Darko on 07.01.2016.
 */
public class MainCharacterMovement implements SimpleAnimation {

    CameraControl cameraControl;
    int animation = 0; //Which picture to use and when to draw it
    int facingSide = 0; // 0 = down, 1 = right, 2 = up, 3 = left, -1 = notmoving
    String drawPicture= "";

    public MainCharacterMovement(CameraControl cc)
    {
        cameraControl = cc;
    }


    @Override
    public void render(Canvas c, float x, float y) {

        // c.drawBitmap(myFactory.getInstance().Character, x, y, myFactory.getInstance().paint);
        facingSide = cameraControl.moving();
        if(cameraControl.moving()>=0)
        {
            animation++;
            if(animation >2 )
            {

                if(animation > 4)
                {
                    if(animation > 6)
                    {
                        if(animation > 8) //draw standStill picture set animation to 1 to prevent bugs
                        {
                            animation = 1;
                            drawPicture = "standStill";
                        }
                        else //draw walkPicture 3
                        {
                           drawPicture = "walkPicture3";
                        }

                    }
                    else //draw walkPicture 2
                    {
                        drawPicture = "walkPicture2";
                    }

                }
                else //draw walkPicture 1
                {
                    drawPicture = "walkPicture1";
                }


            }
            else //draw standStill picture
            {
               drawPicture = "standStill";
            }

        }
        else   //draw standStill picture and reset the animation
        {
            animation = 0;
            drawPicture = "standStill";
            //c.drawBitmap(myFactory.getInstance().mainCharacterPictures.get("standStill"), x, y, myFactory.getInstance().paint);
        }
        switch (facingSide)  // 0 = down, 1 = right, 2 = up, 3 = left, -1 = notmoving
        {
            case 0:
            {
                drawPicture = drawPicture + "D";
                break;
            }
            case 1:
            {
                drawPicture = drawPicture + "R";
                break;
            }
            case 2:
            {
                drawPicture = drawPicture + "U";
                break;
            }
            case 3:
            {
                drawPicture = drawPicture + "L";
                break;
            }
            default:
            {
               if(drawPicture == "standStill") drawPicture=drawPicture+"D";
                break;
            }
        }

        c.drawBitmap(myFactory.getInstance().mainCharacterPictures.get(drawPicture), x, y, myFactory.getInstance().paint);

    }

}
