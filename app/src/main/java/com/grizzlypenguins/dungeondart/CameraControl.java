package com.grizzlypenguins.dungeondart;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import com.grizzlypenguins.dungeondart.Animation.SimpleAnimation;

import java.io.Serializable;

/**
 * Created by Darko on 17.11.2015.
 *
 */
public class CameraControl implements Serializable {


    //scaleX and scaleY are for zooming the surfaceview inorder to display myFactory.TILENUMBER tiles
    double scaleX;
    double scaleY;
   // public double tileSize;
    //LevelMap levelMap;

    //player position
    public MyPoint player_position;
    public int playerMovement=1;
    int speed=1;
    public int boost=0;

    //Displaying Tiles
    public Tile [][] tiles;

    //moving information
    public boolean move_left= false;
    public boolean move_right = false;
    public boolean move_up = false;
    public boolean move_down = false;
    public boolean moved = false;

    //num
    int num = myFactory.TILENUMBER/2;


    public CameraControl(double scaleX, double scaleY,double Ts,MyPoint poz,int playerMovement) {
      //  tileSize = myFactory.TILESIZE;
        speed = playerMovement;
        this.playerMovement = 0;
        player_position = new MyPoint(poz.x,poz.y);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public boolean show_monster(MyPoint p)
    {
        int x = p.x - player_position.x;
        int y = p.y - player_position.y;
        if(x<0)
        {
            x*=-1;
        }
        if(y<0)
        {
            y*=-1;
        }
        if(x<=4 || y<=4)
        {
            return true;
        }
        return false;
    }

    private boolean movableTile(Tile t)
    {
        if(t.getDefine()>0)
            return true;
        else
            return false;
    }

    public int moving() //used to determent the facing side and facing side;
    {
        int temp = -1; // 0 = down, 1 = right, 2 = up, 3 = left
        if(move_up)
        {
            temp = 2;
        }
        else
        {
            if(move_down)
            {
                temp = 0;
            }
            else
            {
                if(move_left)
                {
                    temp = 3;
                }
                else
                {
                    if(move_right)
                    {
                        temp = 1;
                    }
                }
            }
        }

       return temp;
    }

    private Tile move()
    {

        if(move_up || move_down || move_left || move_right) {
            if(playerMovement == 0) {
                playerMovement = speed - boost;
                if (move_left) {
                    if (movableTile(tiles[num - 1][num]))
                    {
                        player_position.x--;
                        moved = true;
                        return tiles[num - 1][num];
                    }
                } else if (move_right) {
                    if (movableTile(tiles[num + 1][num]))
                    {
                        player_position.x++;
                        moved = true;
                        return tiles[num + 1][num];
                    }
                } else if (move_down) {
                    if (movableTile(tiles[num][num + 1]))
                    {
                        player_position.y++;
                        moved = true;
                        return tiles[num][num + 1];
                    }

                } else if (move_up) {
                    if (movableTile(tiles[num][num - 1]))

                    {
                        player_position.y--;
                        moved = true;
                        return tiles[num][num - 1];
                    }

                }

            }
            else {
                //System.out.println(" The speed is: "+playerMovement);
                playerMovement--;
            }
        }
        return null;
    }

    public  void reset_movement()
    {
        move_left= false;
        move_right = false;
        move_up = false;
        move_down = false;
        moved = false;
        playerMovement = 0;
    }

    public Tile tick()
    {

        return this.move();


    }
    public void preMonsterRender()
    {
        if(tiles!=null)
        {
            for(int i=0;i<myFactory.TILENUMBER;i++)
            {
                for(int y=0;y<myFactory.TILENUMBER;y++) {
                    tiles[i][y].monster = false;
                }
            }
        }
    }

    public void render (Canvas c,SimpleAnimation monsterAnim) throws Exception {
       if(moved) {

           moved = false;
       }
        if(tiles == null)
        {
            throw  new Exception("The tiles are null in CameraControl, The location of the player x:" +player_position.x + " y: "+player_position.y);
        }
        for(int i=0;i<myFactory.TILENUMBER;i++)
        {
            for(int y=0;y<myFactory.TILENUMBER;y++) {
               if(c != null) tiles[i][y].render(c, (float) (i * myFactory.TILESIZE), (float) (y * myFactory.TILESIZE),monsterAnim);
                else
                   Log.v("CameraControl","The canvas is null idiot");
            }
        }
    }

    public void calculateShadow(int intensity) throws Exception {

        Math.floor(intensity);
        if(tiles == null)
        {
            throw  new Exception("The tiles are null in CameraControl, The location of the player x:" +player_position.x + " y: "+player_position.y);
        }
        int start = (int) Math.floor(myFactory.TILENUMBER / 2);
        //start++;
        int end = start;
        // end-=2;
        // end++;
        start -= intensity;
        end += intensity;
        if (start < 0 || end >= myFactory.TILENUMBER){
            for (int i = 0; i < myFactory.TILENUMBER; i++) {
                for (int y = 0; y < myFactory.TILENUMBER; y++) {
                    tiles[i][y].shadow = false;
                }

            }
        return;
    }

        for(int i=0;i<myFactory.TILENUMBER;i++)
        {
            for(int y=0;y<myFactory.TILENUMBER;y++)
            {
                if(i<start)
                {
                    tiles[i][y].shadow = true;
                    //System.out.println(" Wat wat 1? ");
                }
                else
                if(i>end)
                {
                    //System.out.println(" Wat wat 2? ");

                    tiles[i][y].shadow = true;
                }

                else
                if(y<start)
                {
                    //System.out.println(" Wat wat 3? ");
                    tiles[i][y].shadow = true;
                }
                else
                if(y>end)
                {
                  //  System.out.println(" Wat wat 4? ");
                    tiles[i][y].shadow = true;
                }
                else
                    tiles[i][y].shadow = false;
               // tiles[i][y].shadow = false;


            }
        }

    }

}
