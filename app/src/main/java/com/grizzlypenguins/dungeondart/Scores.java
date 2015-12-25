package com.grizzlypenguins.dungeondart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Darko on 16.11.2015.
 */
public class Scores {

    public final static  int maxScores = 10;
    public String userName;
    HashMap <String,ArrayList<MapScore>> scores = new HashMap<String,ArrayList<MapScore>>();

    public Scores(String userName,HashMap<String,ArrayList<MapScore>> hm)
    {
        this.userName = userName;

        if(hm!=null)
            scores = hm;

        refreshScores();

    }
    public  Scores(String n)
    {
        this.userName = n;
    }



    public ArrayList<Integer> get5scores (String map){
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for(int i = 0; i<5 ; i++)
        {
            temp.add(scores.get(map).get(i).score);
        }
        return temp;
    }


    public void addScore(String map,MapScore ms) throws Exception {
        if(this.scores.get(map)!=null)
        this.scores.get(map).add(ms);
        else
        {
            throw new Exception("Need to initialize the ArrayList");
        }
        this.refreshScores(map);
    }


     void refreshScores()
    {
        for(String key : scores.keySet()){
            Collections.sort(scores.get(key));
            if(scores.get(key).size()-maxScores>0)
            {
                for(int i=0;i<scores.get(key).size()-maxScores;i++)
                    scores.get(key).remove(scores.get(key).size()-1);
            }
        }

    }
    void refreshScores(String Map)
    {
        Collections.sort(scores.get(Map));
        if(scores.get(Map).size()-maxScores>0)
        {
            for(int i=0;i<scores.get(Map).size()-maxScores;i++)
                scores.get(Map).remove(scores.get(Map).size()-1);
        }
    }


}
