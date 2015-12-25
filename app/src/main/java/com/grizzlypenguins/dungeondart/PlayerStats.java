package com.grizzlypenguins.dungeondart;

/**
 * Created by Darko on 16.11.2015.
 */
public class PlayerStats {

    public static String userName;

    public int times_Played = 0;
    public int times_Died = 0;
    public int numberCompletedMaps = 0;

    public int powerUpsUsed = 0;
    public int trapsActivated = 0;

    public String fastest_Map = "No maps finished :(";
    public String highestScoreMap = "No maps finished :(";

    public Scores scores;

    public PlayerStats(PlayerStats ps) {

        this.userName = ps.userName;
        this.times_Played = ps.times_Played;
        this.times_Died = ps.times_Died;
        this.numberCompletedMaps = ps.numberCompletedMaps;
        this.powerUpsUsed = ps.powerUpsUsed;
        this.trapsActivated = ps.trapsActivated;
        this.fastest_Map = ps.fastest_Map;
        this.highestScoreMap = ps.highestScoreMap;
        this.scores = ps.scores;

    }

    public PlayerStats(String n)
    {
        userName = n;
        scores = new Scores(userName);

    }

    public void add_score(String m,MapScore ms) throws Exception {
        scores.addScore(m,ms);
    }

}
