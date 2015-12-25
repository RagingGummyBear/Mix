package com.grizzlypenguins.dungeondart;

import com.grizzlypenguins.dungeondart.characters.EvilMonster;
import com.grizzlypenguins.dungeondart.characters.MainCharacter;

/**
 * Created by Darko on 17.11.2015.
 */
public class Level implements Runnable{
    Thread thread;
    public boolean running=false;

    public Difficulty difficulty;
    public LevelMap levelMap;
    public CameraControl cameraControl;
    public MainCharacter mainCharacter;
    public PackedLevel packedLevel;
    public TorchLight torchLight;

    //public EvilMonster evilMonster;


    public int screenWidth;
    public int screenHeight;


    public Level(Difficulty d,LevelMap lm,int screenWidth,int screenHeight)
    {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        difficulty = d;
        levelMap = lm;

    }

    @Override
    public void run() {

        System.out.println("Thread running");
        //Difficulty
        difficulty.calculateDifficulty();
        //Creation of LevelMap, with

            levelMap.createMap();


        //Create the character

        mainCharacter = new MainCharacter(levelMap.start,difficulty.playerSpeed);

        //Information for camera

        double cameraZoom;
        int screenSize;
        if(screenHeight > screenWidth)
            screenSize = screenWidth;
                else
            screenSize = screenHeight;

        cameraZoom =  (float)screenSize / (float)(myFactory.TILESIZE * myFactory.TILENUMBER);

        cameraControl = new CameraControl(cameraZoom,cameraZoom,myFactory.TILESIZE,levelMap.start,difficulty.playerSpeed);
        //cameraControl.levelMap = levelMap;
        cameraControl.tiles = levelMap.getShowingTiles(levelMap.start);

        // CreateMonster
        EvilMonster evilMonster = new EvilMonster(myFactory.getInstance().get_MovementMap(levelMap.tiles),levelMap.monsterStart,levelMap.start,difficulty.monsterSpeed);

        // public PackedLevel(Difficulty difficulty, LevelMap levelMap, CameraControl cameraControl, MainCharacter mainCharacter,TorchLight, torchLight)
        torchLight = new TorchLight((float)difficulty.torchDecrease,difficulty.torchLightLifeDecrease);
        packedLevel = new PackedLevel(difficulty,levelMap,cameraControl,mainCharacter,torchLight,evilMonster);

        System.out.println("GENERATING FINISHED");
        running=false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // TODO Auto-generated method stub


    }

    public synchronized void start(){
        thread = new Thread(this);
        running = true;
       // thread.start();
       System.out.println("Thread started from within");
        thread.run();

    }
    synchronized void stop()
    {
        try {
            running=false;
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
