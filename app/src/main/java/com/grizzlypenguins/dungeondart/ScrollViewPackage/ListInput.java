package com.grizzlypenguins.dungeondart.ScrollViewPackage;
/**
 * Created by Darko on 08.11.2015.
 */
public class ListInput {

    String mapName;
    int mapScore;
    private int ID;
    public ListInput ( String mapName , int mapScore, int id)
    {
        this.ID = id;
        this.mapName= mapName;
        this.mapScore = mapScore;
    }

    public String get_row()
    {
        return mapName+mapScore;
    }

    @Override
    public String toString() {
        return mapName+"  "+ mapScore;

    }
    public int get_ID()
    {
        return ID;
    }
    public String getName()
    {
        return mapName;
    }
}
