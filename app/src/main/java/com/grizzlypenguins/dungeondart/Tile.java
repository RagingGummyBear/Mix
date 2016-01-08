package com.grizzlypenguins.dungeondart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.grizzlypenguins.dungeondart.Animation.SimpleAnimation;
import com.grizzlypenguins.dungeondart.effects.PowerUpMovementSpeed;
import com.grizzlypenguins.dungeondart.effects.PowerUpsAndTrapsBank;

import java.io.Serializable;

/**
 * Created by Darko on 17.11.2015.
 */

public class Tile implements Serializable {

    // table fields
    private int _id;
    private int define;  //defines the tile with : 0 wall,1 movable,2 start,3 finish, 4 choosenStart,5 working exit, 6 not working exit, 7 monsterDen
    public int x,y;
    public int powerUp = 0; // powerUp = 0 , no powerup on that tile
    public int trap = 0;   // trap = 0, no traps on that Tile trap<0 used trap

    public boolean shadow = false;
    public boolean monster = false;

    /*
        added getters and setters
     */

    public int getId() {
        return _id;
    }

    public void setId(int id){
        _id = id;
    }

    public int getDefine() {
        return define;
    }

    public void setDefine(int def) {
        define = def;
    }

    public int getX() {
        return x;
    }

    public void setX(int X) {
        x = X;
    }

    public int getY() {
        return y;
    }

    public void setY(int Y) {
        y = Y;
    }

    public void setPowerup(int powerup) {
        powerUp = powerup;
    }

    public void setTrap(int i) {
        trap = i;
    }

    /*
        end of added getters and setters
     */


    /*
        added default constructor
     */

    public Tile() {

    }

    /*
        end of default constructor
     */

    public Tile(int move, int pu, int t)
    {
        powerUp = pu;
        trap = t;
        define = move;
    }

    public int getPowerUp()
    {
        return  powerUp;

    }

    public int use_powerUp()
    {
        int temp = powerUp;
        powerUp = -1;
        return temp;
    }

    public int get_trap()
    {
        return trap;
    }
    public int use_trap()
    {
        int temp = trap;
        trap =-1;
        return temp;
    }

    public void render(Canvas c,float x, float y,SimpleAnimation monsterAnim) {
        if (shadow) {

            return;
            /*
            Paint mpaint = myFactory.getInstance().paint;
            mpaint.setColor(Color.BLACK);
            c.drawRect(x, y, myFactory.TILESIZE, myFactory.TILESIZE, mpaint);
            */

        }
        if(c==null)
        {
            Log.v("Tile", "The canvas is null");
            return;
        }
        switch (define) {

            case 0: {
                c.drawBitmap(myFactory.getInstance().TileNotMovable, x, y, myFactory.getInstance().paint);
                break;
            }
            case 1: {
                c.drawBitmap(myFactory.getInstance().TileMovable, x, y, myFactory.getInstance().paint);
                break;
            }
            case 2:
            {
                c.drawBitmap(myFactory.getInstance().TileStart,x,y,myFactory.getInstance().paint);
                break;
            }
            case 3:
            {
                c.drawBitmap(myFactory.getInstance().TileFinish,x,y,myFactory.getInstance().paint);
                break;
            }
            case 4: {
                c.drawBitmap(myFactory.getInstance().TileStart, x, y, myFactory.getInstance().paint);
                break;
            }
            case 5: {
                c.drawBitmap(myFactory.getInstance().TileFinish, x, y, myFactory.getInstance().paint);
                break;
            }
            case 6: {
                c.drawBitmap(myFactory.getInstance().TileNFinish, x, y, myFactory.getInstance().paint);
                break;
            }
            case 7:{

                c.drawBitmap(myFactory.getInstance().TileMovable, x, y, myFactory.getInstance().paint);
                c.drawBitmap(myFactory.getInstance().EvilMonster,x,y,myFactory.getInstance().paint);
                break;
            }
            default: {
                c.drawBitmap(myFactory.getInstance().TileMovable, x, y, myFactory.getInstance().paint);
                break;
            }
        }

        PowerUpsAndTrapsBank.getInstance().renderTrap(c,x,y,trap);
        PowerUpsAndTrapsBank.getInstance().renderPowerUp(c,x,y,powerUp);
        if(monster)
        {
           if(monsterAnim != null) monsterAnim.render(c,x,y);
            //c.drawBitmap(myFactory.getInstance().EvilMonster,x,y,myFactory.getInstance().paint);
        }
    }


}
