package com.grizzlypenguins.dungeondart;

import java.io.Serializable;

/**
 * Created by Darko on 29.11.2015.
 */
public class PlayerScoring implements Serializable {
    int powerUpBonus = 0;
    public int score = 0;
    long startTime;
    int msec = 0;
    int sec = 0;
    int min = 0;

    public PlayerScoring ()
    {

    }
    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }

    public void tick()
    {
        msec++;
        if(msec>=50)
        {
            sec++;
            if(sec>59)
            {
                min++;
                sec = 0;
            }
            msec = 0;
        }
    }

    public String getTime()
    {
        return String.format("min: "+ min +"sec: " + sec + "msec"+ msec);
    }

    public int calculateScore(double multiplyer,long currentTime,MyPoint playerLocation,MyPoint monsterLocation,float torchHealth ,boolean playerAlive )
    {
        currentTime/=1000000;
        startTime = currentTime - startTime;
        int temp=0;
        while(startTime>1000)
        {
            startTime-=1000;
            sec++;
            if(sec>59)
            {
                sec = 0;
                min++;
            }


        }
        msec = (int) startTime;
        //Presmetka za kolku polinja se razdalecheni player i monster
        score += (int)(torchHealth * 20*multiplyer);
        if(playerAlive) return score+powerUpBonus;
        score = 0;
        return score;
    }

    public void addPowerUpBonus(int num)
    {
        powerUpBonus += num;
    }

}
