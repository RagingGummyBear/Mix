package com.grizzlypenguins.dungeondart;

import android.graphics.Canvas;
import android.util.Log;

import com.grizzlypenguins.dungeondart.Animation.MainCharacterMovement;
import com.grizzlypenguins.dungeondart.Animation.SpiderMonsterAnimation;
import com.grizzlypenguins.dungeondart.CameraControl;
import com.grizzlypenguins.dungeondart.Difficulty;
import com.grizzlypenguins.dungeondart.GameLoop.FindNextStep;
import com.grizzlypenguins.dungeondart.LevelMap;
import com.grizzlypenguins.dungeondart.characters.EvilMonster;
import com.grizzlypenguins.dungeondart.characters.MainCharacter;
import com.grizzlypenguins.dungeondart.effects.Effect;
import com.grizzlypenguins.dungeondart.effects.PowerUpsAndTrapsBank;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Darko on 22.11.2015.
 */
public class PackedLevel implements Serializable {

    public PlayerScoring playerScoring;
    public Difficulty difficulty;
    public LevelMap levelMap;
    public CameraControl cameraControl;
    public MainCharacter mainCharacter;
    public TorchLight torchLight;
    public EvilMonster evilMonster;


    public boolean gameFinished = false;

    public PackedLevel(Difficulty difficulty, LevelMap levelMap, CameraControl cameraControl, MainCharacter mainCharacter,TorchLight torchLight,EvilMonster evilMonster) {
        this.difficulty = difficulty;
        this.levelMap = levelMap;
        this.cameraControl = cameraControl;
        this.mainCharacter = mainCharacter;
        this.torchLight = torchLight;
        this.evilMonster = evilMonster;
        playerScoring = new PlayerScoring();
        myFactory.getInstance().findNextStep = new FindNextStep(myFactory.getInstance().get_MovementMap(levelMap.tiles),cameraControl.player_position,evilMonster.location);
        mainCharacter.mainCharacterMovement = new MainCharacterMovement(cameraControl);
        evilMonster.monsterAnim = new SpiderMonsterAnimation(evilMonster);
    }

   public void tick() throws Exception {


        evilMonster.tick();
        torchLight.tick();
        evilMonster.playerLocation = cameraControl.player_position;

          // gameFinished = true;
           //mainCharacter.alive = false;
        mainCharacter.tick();
        Tile temp = cameraControl.tick();
       if(temp !=null )
       {
           Effect powerUpEffect = PowerUpsAndTrapsBank.getInstance().get_PowerUp(temp.use_powerUp());
           Effect trapEffect = PowerUpsAndTrapsBank.getInstance().get_Trap(temp.use_trap());
           if(powerUpEffect!=null)
           {
               powerUpEffect.active = true;
               mainCharacter.effects.add(powerUpEffect);

           }
           if(trapEffect != null)
           {
               trapEffect.active = true;
               mainCharacter.effects.add(trapEffect);
           }

       }
       this.useEffects(mainCharacter.effects);

        if(cameraControl.moved)
        {
            cameraControl.tiles = levelMap.getShowingTiles(cameraControl.player_position);
        }
       cameraControl.calculateShadow((int) Math.floor(torchLight.intensity));

      if(temp!=null) if(temp.getDefine() == 5){
           gameFinished = true;
       }

    }

    public void render (Canvas c)
    {
        try{
            cameraControl.preMonsterRender();
            levelMap.tiles[evilMonster.location.x][evilMonster.location.y].monster = true;
            cameraControl.render(c,evilMonster.monsterAnim);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        //evilMonster.render(c,0,0);
        mainCharacter.render(c);
        torchLight.render(c);
    }

    public void shake_shake()
    {
        torchLight.shake_shake();
    }

    private void useEffects(ArrayList<Effect> lista)
    {
        for(int i=0;i<lista.size();i++)
        {
            use_effect(lista.get(i).name);
            lista.get(i).tick();
            if(!lista.get(i).active)
            {
                deactivate_effect(lista.get(i).name);
                lista.remove(i);
            }
        }
    }

    private void deactivate_effect(String s)
    {
        switch (s)
        {
            case "PlayerMovementSpeed" :
            {
                cameraControl.boost = 0;
                break;
            }
            case "PlayerStun":
            {
                mainCharacter.stunned = false;
                cameraControl.playerMovement = cameraControl.speed;
                break;
            }
            case "PlayerSlow":
            {
                cameraControl.boost = 0;
                mainCharacter.slowed = false;
                break;
            }
            default: break;
        }

    }


    private void use_effect(String s)
    {
        switch (s)
        {
            case "PlayerMovementSpeed" :
            {
                cameraControl.boost = 2;
                break;
            }
            case "PowerUpBonusPoints":
            {
                playerScoring.addPowerUpBonus(20);
                break;
            }
            case "PlayerStun":
            {
                mainCharacter.stunned = true;
                cameraControl.playerMovement = 10000000;
                break;
            }
            case "PlayerSlow":
            {
                mainCharacter.slowed = true;
                cameraControl.boost = -4;
                break;
            }
            case "TrapLowerTorch":
            {
                torchLight.intensity--;
                break;
            }
            case "PowerUpTorchHealth":
            {
                torchLight.life+=20;
                break;
            }
            default: break;
        }
    }

    public void finishGame()
    {
        long temp = System.nanoTime();
        playerScoring.calculateScore(difficulty.multiplier,temp,cameraControl.player_position,new MyPoint(1,1),torchLight.life, mainCharacter.alive);

    }
}
