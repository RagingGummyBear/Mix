package com.grizzlypenguins.dungeondart.characters;

import android.graphics.Point;

import com.grizzlypenguins.dungeondart.MyPoint;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.AStar;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.AreaMap;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics.AStarHeuristic;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics.ClosestHeuristic;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics.DiagonalHeuristic;
import com.grizzlypenguins.dungeondart.myFactory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Darko on 01.12.2015.
 */
public class MonsetNextStep implements Runnable {


    Algoritmi algoritmi = new Algoritmi();

    AreaMap map;
    AStar aStar;
    AStarHeuristic heuristic = new ClosestHeuristic();

    int [][] maze;
    MyPoint playerLocation;
    MyPoint monsterLocation;
    public MyPoint nextStep= new MyPoint(0,0);

    public boolean finished = true ;
    public boolean running = false;

    Thread thread;

    public MonsetNextStep(int[][] maze, MyPoint playerLocation, MyPoint monsterLocation) {
        this.maze = maze;
        this.playerLocation = playerLocation;
        this.monsterLocation = monsterLocation;
        map = new AreaMap(maze[0].length, maze.length, maze);
        aStar = new AStar(map, heuristic);
    }

    @Override
    public void run() {

        ArrayList<MyPoint> shortestPath = aStar.calcShortestPath(monsterLocation.x, monsterLocation.y, playerLocation.x, playerLocation.y);
        for(int i=0;i<shortestPath.size();i++)
        {
            System.out.println("x:  "+shortestPath.get(i).x+" y: "+shortestPath.get(i).y);
        }

        //System.out.println("MonsterHunt running");
        nextStep = monsterLocation;
        nextStep.x = shortestPath.get(0).x;
        nextStep.y =  shortestPath.get(0).y;
        nextStep.x -= monsterLocation.x;
        nextStep.y -= monsterLocation.y;
        finished = true;
        running=false;
        stop();
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    synchronized public void start()
    {
        try {

            thread = new Thread(this);
            thread.run();
            running = true;
            finished = false;

        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
    }

    synchronized public void stop()
    {
        try {
            System.out.print("Wow");
            finished = true;
            running=false;

            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
