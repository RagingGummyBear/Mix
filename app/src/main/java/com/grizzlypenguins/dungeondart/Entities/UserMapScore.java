package com.grizzlypenguins.dungeondart.Entities;

/**
 * Created by Darko on 02.12.2015.
 */
public class UserMapScore {

    // table fields
    private int _id;

    // foreign keys
    private int _userid;
    private int _mapid;

    private int score;
    private String time;

    // migrated from Stat class
    private int noPowerUps;
    private int noPlays;

    // false = 0, true = 1
    private int win;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }
    public int get_mapid() {
        return _mapid;
    }

    public void set_mapid(int _mapid) {
        this._mapid = _mapid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int get_userid() {

        return _userid;
    }

    public int getNoPowerUps() {
        return noPowerUps;
    }

    public void setNoPowerUps(int number) {
        noPowerUps = number;
    }

    public int getNoPlays() {
        return noPlays;
    }

    public void setNoPlays(int number) {
        noPlays = number;
    }

    public void set_userid(int _userid) {
        this._userid = _userid;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int number) {
        win = number;
    }

    public UserMapScore() {

    }
}
