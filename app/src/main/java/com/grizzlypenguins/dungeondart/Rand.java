package com.grizzlypenguins.dungeondart;

import java.util.Random;

/**
 * Created by Darko on 17.11.2015.
 *
 *
 *
 * random.nextInt(max - min + 1) + min
 */
public class Rand {
    private static Rand ourInstance = new Rand();
    public static Random random = new Random();
    public static Rand getInstance() {
        return ourInstance;
    }

    private Rand() {
    }

}
