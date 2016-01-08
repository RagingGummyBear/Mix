package com.grizzlypenguins.dungeondart.Animation;

import android.graphics.Canvas;

import com.grizzlypenguins.dungeondart.CameraControl;
import com.grizzlypenguins.dungeondart.characters.EvilMonster;
import com.grizzlypenguins.dungeondart.myFactory;

/**
 * Created by Darko on 08.01.2016.
 */
public class SpiderMonsterAnimation implements SimpleAnimation {


    EvilMonster evilMonster;
    int animation = 0; //Which picture to use and when to draw it
    int facingSide = 0; // 0 = down, 1 = right, 2 = up, 3 = left, -1 = notmoving
    String drawPicture= "";

    public SpiderMonsterAnimation(EvilMonster evil)
    {
        evilMonster = evil;
    }

    @Override
    public void render(Canvas c, float x, float y) {

        facingSide = evilMonster.facingSide;
        if(evilMonster.facingSide>=0)
        {
            animation++;
            if(animation >2 )
            {

                if(animation > 4)
                {
                    if(animation > 6)
                    {

                            animation = 1;
                            drawPicture = "standStill";


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

        c.drawBitmap(myFactory.getInstance().evilMonster.get(drawPicture), x, y, myFactory.getInstance().paint);


    }
}
