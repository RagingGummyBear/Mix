package com.grizzlypenguins.dungeondart;

import android.graphics.Canvas;

import java.io.Serializable;

/**
 * Created by Darko on 23.11.2015.
 */
public class TorchLight implements Serializable {

    float intensity;
    public float decrease;
    int timeToDecrease = 100;
    int counter = 2;
    public float life=100;
    float lightIt = (float) 0.5;
    float damage = 1;


    public TorchLight(float decrease,float damage)
    {
        intensity = 3;
        this.decrease = decrease;
        this.damage = damage;
    }

    public void tick()
    {
       if(counter == 0)
       {
           intensity-=decrease;
           counter = timeToDecrease;
           if(intensity<=0){
               intensity = 0;
               life-=damage*0.70;
           }
       }
        else
           --counter;

    }

    public boolean shake_shake()
    {
        intensity+=lightIt;

        if(intensity>=Math.floor(myFactory.TILENUMBER / 2)-2)
        {
            life-=(damage*0.80);
           if(intensity>(myFactory.TILENUMBER/2))
               intensity = (myFactory.TILENUMBER/2);
            return true;
        }
        else
            return false;
    }


    public void render(Canvas c)
    {
        int start = (int) Math.floor(myFactory.TILENUMBER / 2);
        //start ++;
        if(intensity <= 0)
        {
            //c.drawBitmap(myFactory.getInstance().TorchLight, start * myFactory.TILESIZE, start * myFactory.TILESIZE, myFactory.getInstance().paint);
            intensity = 0;
        }
        int end = start;
        //--end;
        int temp = (int)Math.floor(intensity);
        start -= temp;
        end += temp;
        end++;

        if(start<0 || end >= myFactory.TILENUMBER) {
            start = 0 ; end = myFactory.TILENUMBER;
            for (int i = 0; i < myFactory.TILENUMBER; i++) {
                for (int y = 0; y < myFactory.TILENUMBER; y++) {
                    if (i == start) {

                        c.drawBitmap(myFactory.getInstance().TorchLight, y * myFactory.TILESIZE, i * myFactory.TILESIZE, myFactory.getInstance().paint);

                    } else {
                        if (i == end - 1) {

                            c.drawBitmap(myFactory.getInstance().TorchLight, y * myFactory.TILESIZE, i * myFactory.TILESIZE, myFactory.getInstance().paint);

                        } else {

                            c.drawBitmap(myFactory.getInstance().TorchLight, start * myFactory.TILESIZE, i * myFactory.TILESIZE, myFactory.getInstance().paint);
                            c.drawBitmap(myFactory.getInstance().TorchLight, (end - 1) * myFactory.TILESIZE, i * myFactory.TILESIZE, myFactory.getInstance().paint);
                            y = end;

                        }
                    }

                }
            }
            return;
        }


        for(int i=start;i<end;i++)
        {
            for(int y=start;y<end;y++)
            {
                if(i == start)
                {
                    c.drawBitmap(myFactory.getInstance().TorchLight,y*myFactory.TILESIZE,i*myFactory.TILESIZE,myFactory.getInstance().paint);
                }
                else
                {
                    if(i==end-1)
                    {

                        c.drawBitmap(myFactory.getInstance().TorchLight,y*myFactory.TILESIZE,i*myFactory.TILESIZE,myFactory.getInstance().paint);
                    }
                    else
                    {

                        c.drawBitmap(myFactory.getInstance().TorchLight,start*myFactory.TILESIZE,i*myFactory.TILESIZE,myFactory.getInstance().paint);
                        c.drawBitmap(myFactory.getInstance().TorchLight,(end-1)*myFactory.TILESIZE,i*myFactory.TILESIZE,myFactory.getInstance().paint);
                        y = end;

                    }
                }
            }
        }


    }
}
