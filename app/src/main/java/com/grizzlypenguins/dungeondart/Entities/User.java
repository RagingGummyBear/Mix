package com.grizzlypenguins.dungeondart.Entities;

import java.util.Date;

/**
 * Created by Nikola on 28-Nov-15.
 */

public class User {

    // fields
    private int _id;
    private String _username;
    private String _joindate;

    // default constructor
    public User() {

    }

    // constructor with all three arguments
    public User(int id, String name, String joindate) {
        _id = id;
        _username = name;
        _joindate = joindate;
    }

    // getter-setter pairs
    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getUsername() {
        return _username;
    }

    public void setUsername(String username) {
        _username = username;
    }

    public String getJoindate() {
        return _joindate;
    }

    public void setJoindate(String joindate) {
        _joindate = joindate;
    }

}