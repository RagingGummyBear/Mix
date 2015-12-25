package com.grizzlypenguins.dungeondart;

import java.sql.Time;

/**
 * Created by Darko on 16.11.2015.
 */
public class MapScore implements Comparable<MapScore>{
    public String name;
    public String time;   // Min/Sec/MSec
    public int score;

    public  MapScore(String n,String t, int s)
    {
        name = n;
        time = t;
        score = s;

    }

    public int getTime()
    {

        String []temp = time.split(":");
        return Integer.parseInt(temp[0])*100000+Integer.parseInt(temp[1])*1000+Integer.parseInt(temp[2]);  //temp[0] - min , temp[1] - sec , temp [0] msec
    }

    @Override
    public int compareTo(MapScore another) {
        if(this.score-another.score==0)
        {
            return this.getTime()-another.getTime();
        }
        return this.score - another.score;

    }
}
