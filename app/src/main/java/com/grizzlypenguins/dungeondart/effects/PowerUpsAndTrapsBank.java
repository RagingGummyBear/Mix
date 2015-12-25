package com.grizzlypenguins.dungeondart.effects;

import android.graphics.Canvas;

import com.grizzlypenguins.dungeondart.myFactory;

/**
 * Created by Darko on 29.11.2015.
 *
 */
public class PowerUpsAndTrapsBank {
    private static PowerUpsAndTrapsBank ourInstance = new PowerUpsAndTrapsBank();

    public static PowerUpsAndTrapsBank getInstance() {
        return ourInstance;
    }

    public PowerUpMovementSpeed powerUpR;
    public PowerUpBonusPoints powerUpY;
    public PowerUpTorchHealth powerUpG;



    private TrapStun trapStun;
    private TrapSlow trapSlow;
    private TrapLowerTorch trapLowerTorch;



    private PowerUpsAndTrapsBank() {

        powerUpR = myFactory.getInstance().newPowerUpMovementSpeed();
        powerUpY = myFactory.getInstance().newPowerUpBonusPoints();
        powerUpG = myFactory.getInstance().newPowerUpTorchHealth();

        trapSlow = myFactory.getInstance().newTrapSlow();
        trapStun = myFactory.getInstance().newTrapStun();
        trapLowerTorch = myFactory.getInstance().newTrapLowerTorch();

    }

    public void tick()
    {

        powerUpR.tick();
        powerUpG.tick();
        powerUpY.tick();

    }

    public void renderPowerUp(Canvas c,float x, float y,int define)
    {

        switch(define)
        {
            case 0:
            {
                break;
            }
            case 1:
            {
                powerUpR.render(c,x,y);
             break;
            }
            case 2:
            {
                powerUpY.render(c,x,y);
                break;
            }
            case 3:
            {
                powerUpG.render(c,x,y);
                break;
            }
            default:
            {
                break;
            }
        }
    }

    public void renderTrap(Canvas c,float x, float y,int define)
    {

        switch(define)
        {
            case 0:
            {
                break;
            }
            case 1:
            {
                trapStun.render(c,x,y);
                break;
            }
            case 2:
            {
                trapSlow.render(c,x,y);
                break;
            }
            case 3:
            {
                trapLowerTorch.render(c,x,y);
                break;
            }
            default:
            {
                break;
            }
        }
    }

    public Effect get_PowerUp(int define)
    {

        switch(define)
        {
            case 0:
            {
                return null;

            }
            case 1:
            {
                return myFactory.getInstance().newPowerUpMovementSpeed();
            }
            case 2:
            {
                return myFactory.getInstance().newPowerUpBonusPoints();
            }
            case 3:
            {
                return myFactory.getInstance().newPowerUpTorchHealth();
            }
            default:
            {
                return null;

            }
        }
    }

    public Effect get_Trap(int define) {

        switch(define)
        {
            case 0:
            {
                return null;
            }
            case 1:
            {
                return myFactory.getInstance().newTrapStun();
            }
            case 2:
            {
                return myFactory.getInstance().newTrapSlow();
            }
            case 3:
            {
                return myFactory.getInstance().newTrapLowerTorch();
            }
            default:
            {
                return null;

            }
        }
    }
}
