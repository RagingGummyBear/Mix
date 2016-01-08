package com.grizzlypenguins.dungeondart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.grizzlypenguins.dungeondart.GameLoop.FindNextStep;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.AStar;
import com.grizzlypenguins.dungeondart.characters.MonsetNextStep;
import com.grizzlypenguins.dungeondart.effects.PowerUpBonusPoints;
import com.grizzlypenguins.dungeondart.effects.PowerUpMovementSpeed;
import com.grizzlypenguins.dungeondart.effects.PowerUpTorchHealth;
import com.grizzlypenguins.dungeondart.effects.TrapLowerTorch;
import com.grizzlypenguins.dungeondart.effects.TrapSlow;
import com.grizzlypenguins.dungeondart.effects.TrapStun;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Darko on 16.11.2015.
 */
public class myFactory {

    Rand rand = Rand.getInstance();
    public static int TILESIZE = 64;
    public static final int TILENUMBER = 9;
    private static myFactory ourInstance = new myFactory();


    //pictures for tiles
    public Bitmap TileMovable;
    public Bitmap TileNotMovable;
    public Bitmap TileStart;
    public Bitmap TileFinish;
    public Bitmap TileNFinish;
    public Bitmap TorchLight;
    public Bitmap arrowL,arrowD,arrowU,arrowR;

    public Bitmap EvilMonster;  //Its used for render Monster's den in create map (no longer is being used for displaying the monster)


    //animation pictures
    public Bitmap Character;
    public HashMap <String,Bitmap> mainCharacterPictures = new HashMap<>();
    public HashMap <String,Bitmap> evilMonster = new HashMap<>();


    //powerUps and traps pictures
    public Bitmap PowerUpR; //speed
    public Bitmap PowerUpB; //torchHealth
    public Bitmap PowerUpG; //higherTorchIntensity
    public Bitmap PowerUpP; //stunMonster
    public Bitmap PowerUpY; //bonus points

    public Bitmap TrapR; //Stun
    public Bitmap TrapY; //Slow
    public Bitmap TrapB; //lowerTorch

    public Paint paint;

    public MonsetNextStep monsetNextStep;  //pathfinding algo
    public FindNextStep findNextStep;
    public Bitmap arrowKey;

    public static myFactory getInstance() {
        return ourInstance;
    }

    private myFactory() {
        paint = new Paint();
        paint.setAntiAlias(false);
        paint.setDither(true);
        paint.setFilterBitmap(false);

    }

    public LevelMap test_map_4()
    {
        int tileNum = 60;
        Tile [][]tiles =new Tile [tileNum][tileNum];
        for(int i=0;i<tileNum;i++)
        {
            for(int y=0;y<tileNum;y++)
            if(i<10 || i>tileNum-10 || y<10 || y>tileNum-10)
            {
                tiles[i][y] =new Tile(0,-1,-1);
            }
            else
            {
                if(i == y || i==12 || i==tileNum-12 || y==12 || y == tileNum -12)
                        tiles[i][y] = new Tile(2,-1,-1);
                else
                {
                    tiles[i][y] =new Tile(0,-1,-1);
                }
            }
        }
        tiles[12][12] = newStartTile();
        tiles[tileNum-12][tileNum-12] = newMonsterDenTile();
        tiles[13][12] = newFinishTile();
        return new LevelMap(tiles,"test_map_4",10004);
    }

    public LevelMap test_map_3()
    {
        int tileNum = 100;
        //(Tile Tiles [][],int tileNumber, int TileSize,String mapName)
        LevelMap temp=new LevelMap(test_Tiles_1(tileNum),"TestMap3",10003);
        for(int i=10;i<90;i++)
        {
            for(int y=10;y<90;y++)
            if(temp.tiles[i][y].getDefine()==0)temp.tiles[i][y].setDefine(1);
        }
        temp.tiles[11][11].setDefine(7);
        return temp;
    }

    public LevelMap test_map_1()
    {
        int tileNum = 120;
        //(Tile Tiles [][],int tileNumber, int TileSize,String mapName)
        return new LevelMap(test_Tiles_1(tileNum),"TestMap1",10001);
    }

    public Tile test_tile_1()
    {
        return new Tile(Rand.getInstance().random.nextInt(4),-1,-1);
    }

    public Tile[][] test_Tiles_1(int tileNumber)
    {
        Tile temp [] [] = new Tile[tileNumber][tileNumber];

        for(int i=0;i<tileNumber;i++)
        {
            for(int y=0; y < tileNumber ; y++)
            {
              //public Tile(int move, int pu, int t)
               if(i>10 && i <tileNumber-10 && y>10 && y<tileNumber-10) temp[i][y] = new Tile(Rand.getInstance().random.nextInt(4),Rand.getInstance().random.nextInt(6),Rand.getInstance().random.nextInt(6));
                else
               {
                   temp[i][y] = new Tile(0,-1,-1);
               }
            }
        }
        return temp;
    }

    //defines the tile with : 0 wall,1 movable,2 start,3 finish, 4 choosenStart,5 working exit, 6 not working exit, 7 monsterDen
    public Tile newStartTile()
    {
        return new Tile(2,-1,-1);
    }

    public Tile newFinishTile()
    {
        return new Tile(3,-1,-1);
    }
    public Tile newMovableTile()
    {
        return new Tile(1,-1,-1);
    }
    public Tile newPowerUpTile()
    {
        return new Tile(1,Rand.getInstance().random.nextInt(3),-1);
    }
    public Tile newTrapTile()
    {
        return new Tile(1,-1,Rand.getInstance().random.nextInt(3));
    }
    public Tile newTrapAndPowerUpTile()
    {
       return new Tile(1,Rand.getInstance().random.nextInt(4),Rand.getInstance().random.nextInt(4));
    }

    public Tile newMonsterDenTile()
    {
        return new Tile(7,-1,-1);
    }

    public Tile newWallTile()
    {
        return new Tile(0,-1,-1);
    }

    public LevelMap test_map_2(int i)
    {
        int tileNum = i;
        //(Tile Tiles [][],int tileNumber, int TileSize,String mapName)
        return new LevelMap(test_Tiles_2(tileNum),"TestMap2",10002);
    }

    public Tile[][] test_Tiles_2(int tileNumber)
    {
        Tile temp [] [] = new Tile[tileNumber][tileNumber];

        for(int i=0;i<tileNumber;i++)
        {
            for(int y=0; y < tileNumber ; y++)
            {
                if(i>10 && i < tileNumber-10 && y>10 && y<tileNumber-10)
                {
                    temp[i][y] = newMovableTile();
                }
                else  temp[i][y] = newWallTile();
                //public Tile(int move, int pu, int t)
            }
        }

        int x,y;
        tileNumber -= 20;
        while(true) {
            x = Rand.random.nextInt((tileNumber- 20) + 1) + 20;
            y = Rand.random.nextInt((tileNumber - 20) + 1) + 20;
            if (temp[x][y].getDefine() < 3) {
                temp[x][y] = newStartTile();
                break;
            }
        }  while(true) {
        x = Rand.random.nextInt((tileNumber- 20) + 1) + 20;
        y = Rand.random.nextInt((tileNumber - 20) + 1) + 20;
        if (temp[x][y].getDefine() < 3) {
            temp[x][y] = newFinishTile();
            break;
        }
    }  while(true) {
        x = Rand.random.nextInt((tileNumber- 20) + 1) + 20;
        y = Rand.random.nextInt((tileNumber - 20) + 1) + 20;
        if (temp[x][y].getDefine() < 3) {
            temp[x][y] = newMonsterDenTile();
            break;
        }
    }
        return temp;
    }





    // function used from stackoverflow for scaling bitmaps

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        //bm.recycle();

        return resizedBitmap;
    }

    public void resize()
    {
        if(TileMovable!=null)
        {
            TileMovable = getResizedBitmap(TileMovable,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TileNotMovable!=null)
        {
            TileNotMovable = getResizedBitmap(TileNotMovable,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(Character!=null)
        {
            Character = getResizedBitmap(Character,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TorchLight!=null)
        {
            TorchLight = getResizedBitmap(TorchLight,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TileStart!=null)
        {
            TileStart = getResizedBitmap(TileStart,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TileFinish!=null)
        {
            TileFinish = getResizedBitmap(TileFinish,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TileNFinish!=null)
        {
            TileNFinish = getResizedBitmap(TileNFinish,myFactory.TILESIZE,myFactory.TILESIZE);
        }

        if(PowerUpR!=null)
        {
            PowerUpR = getResizedBitmap(PowerUpR,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(PowerUpB!=null)
        {
            PowerUpB = getResizedBitmap(PowerUpB,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(PowerUpG!=null)
        {
            PowerUpG = getResizedBitmap(PowerUpG,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(PowerUpY!=null)
        {
            PowerUpY = getResizedBitmap(PowerUpY,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(PowerUpP!=null)
        {
            PowerUpP = getResizedBitmap(PowerUpP,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TrapR!=null)
        {
            TrapR = getResizedBitmap(TrapR,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(TrapY!=null)
        {
            TrapY = getResizedBitmap(TrapY,myFactory.TILESIZE,myFactory.TILESIZE);
        }

        if(TrapB!=null)
        {
            TrapB = getResizedBitmap(TrapB,myFactory.TILESIZE,myFactory.TILESIZE);
        }
        if(EvilMonster != null)
        {
            EvilMonster = getResizedBitmap(EvilMonster,myFactory.TILESIZE,myFactory.TILESIZE);
        }

        for(String  temp : mainCharacterPictures.keySet())
        {
            if(temp != null)
            {
                mainCharacterPictures.put(temp,getResizedBitmap(mainCharacterPictures.get(temp),TILESIZE,TILESIZE));
            }
        }
        for(String  temp : evilMonster.keySet())
        {
            if(temp != null)
            {
                evilMonster.put(temp,getResizedBitmap(evilMonster.get(temp),TILESIZE,TILESIZE));
            }
        }
    }

    public int [][] get_MovementMap(Tile [][]tiles)
    {
        int temp [][] = new int [tiles.length] [tiles[0].length];

        for(int i=0;i<tiles.length;i++)
        {
            for(int y=0;y<tiles[0].length;y++)
            {
                temp[i][y]= tiles[i][y].getDefine();
            }
        }
        return temp;
    }


    public TrapStun newTrapStun()
    {
        return new TrapStun(100,"PlayerStun",false);
    }
    public TrapSlow newTrapSlow()
    {
        return new TrapSlow(200,"PlayerSlow",false);
    }
    public TrapLowerTorch newTrapLowerTorch()
    {
        return new TrapLowerTorch(1,"TrapLowerTorch",false);
    }


    public PowerUpMovementSpeed newPowerUpMovementSpeed()
    {
        return new PowerUpMovementSpeed(500,"PlayerMovementSpeed",false);
    }
    public PowerUpBonusPoints newPowerUpBonusPoints() {
        return new PowerUpBonusPoints(1,"PowerUpBonusPoints",false);
    }
    public PowerUpTorchHealth newPowerUpTorchHealth() {
        return new PowerUpTorchHealth(1,"PowerUpTorchHealth",false);
    }

    public Tile getTileOfType(String s)
    {
        Tile temp;
        switch (s)
        {
            case "movabletile":
            {
                return this.newMovableTile();
            }
            case "notmovabletile":
            {
                return this.newWallTile();
            }
            case "finishtile":
            {
                return this.newFinishTile();
            }
            case "starttile":
            {
                return  this.newStartTile();
            }
            case "monsterstarttile":
            {
                return this.newMonsterDenTile();
            }
            case "poweruptile":
            {
                return newPowerUpTile();
            }
            case "traptile":
            {
                return newTrapTile();
            }
            case "powerupandtraptile":
            {
                return newTrapAndPowerUpTile();
            }
            default:return null;

        }
    }

    public LevelMap newBlankMovableMap(int width,int height,String mapname)
    {

        width+=20;
        height+=20;
        Tile [][]tiles =new Tile [height][width];
        for(int i=0;i<height;i++)
        {
            for(int y=0;y<width;y++) {
                if (i < 10 || i > height - 10 || y < 10 || y > width - 10) {
                    tiles[i][y] = newWallTile();
                } else {
                    tiles[i][y] = newMovableTile();
                }
                tiles[i][y].setX(i);
                tiles[i][y].setY(y);
                tiles[i][y].trap = -1;
                tiles[i][y].powerUp = -1;
            }
        }
        return new LevelMap(tiles,mapname,2000);

    }
    public LevelMap newBlankNotMovableMap(int width,int height,String mapname)
    {

        width+=20;
        height+=20;
        Tile [][]tiles =new Tile [height][width];
        for(int i=0;i<height;i++)
        {
            for(int y=0;y<width;y++)
            {
                tiles[i][y] = newWallTile();
                tiles[i][y].setX(i);
                tiles[i][y].setY(y);
            }
        }
        return new LevelMap(tiles,mapname,3000);

    }

    public void set_Size(float f)
    {
        if(f/TILENUMBER<64)
        TILESIZE = (int) (f/TILENUMBER);
    }


}
