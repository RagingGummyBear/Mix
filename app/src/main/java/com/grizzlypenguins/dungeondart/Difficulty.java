package com.grizzlypenguins.dungeondart;

import java.io.Serializable;

/**
 * Created by Darko on 16.11.2015.
 */
public class Difficulty implements Serializable {

    public int starNumber;
    public int monsterSpeed;
    public int playerSpeed;
    public double multiplier;    // the multiplier is for the score
    public double torchDecrease;
    public float torchLightLifeDecrease;

    public Difficulty(int numb)
    {
        starNumber=numb;
        this.calculateDifficulty();
    }
    public  Difficulty ()
    {
        starNumber = 1;
        this.calculateDifficulty();
    }

    void calculateDifficulty()
    {

        switch (starNumber) {
            case 1:
            {

                monsterSpeed = 7;
                playerSpeed = 6;
                multiplier = 1;  // no bonus points
                torchDecrease = 0.35;
                torchLightLifeDecrease = (float) 4.5;

                break;

            }
            case 2: {

                monsterSpeed = 6;
                playerSpeed = 6;
                multiplier = 1.2;  //bonus 0.2% points
                torchDecrease = 0.5;
                torchLightLifeDecrease = (float) 4.5;
                break;

            }
            case 3: {

                monsterSpeed = 5;
                playerSpeed = 5;
                multiplier = 1.5; //bonus 0.5% points
                torchDecrease = 0.8;
                torchLightLifeDecrease = (float) 4.5;
                break;

            }
            default: {

                monsterSpeed = 10;
                playerSpeed = 5;
                multiplier = 1.2;
                torchDecrease = 1;
                torchLightLifeDecrease = (float) 4.5;
                break;

            }
        }
    }



}
