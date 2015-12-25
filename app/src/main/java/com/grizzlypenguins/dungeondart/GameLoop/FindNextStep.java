package com.grizzlypenguins.dungeondart.GameLoop;

import android.util.Log;

import com.grizzlypenguins.dungeondart.MyPoint;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.AStar;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.AreaMap;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics.AStarHeuristic;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics.ClosestHeuristic;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics.DiagonalHeuristic;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics.ManhattanHeuristic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Darko on 01.12.2015.
 */
public class FindNextStep implements Runnable {
    AreaMap map;
    AStar aStar;
    AStarHeuristic heuristic = new ManhattanHeuristic();

    int [][] maze;
    public boolean finished = true;

    public  MyPoint playerLocation;
    public MyPoint monsterLocation;

    public MyPoint nextStep= new MyPoint(0,0);

    public FindNextStep(int[][] maze, MyPoint playerLocation, MyPoint monsterLocation)
    {
        this.maze = maze;
        this.playerLocation = playerLocation;
        this.monsterLocation = monsterLocation;
        map = new AreaMap(maze[0].length, maze.length, maze);

    }

    @Override
    public void run() {
        aStar = new AStar(map,heuristic);
        finished = false;
       // Log.v("search", "Start" );
        ArrayList<MyPoint> shortestPath = null;
       if(aStar!=null)
       {
           shortestPath = aStar.calcShortestPath(monsterLocation.x, monsterLocation.y, playerLocation.x, playerLocation.y);
       }
       if(shortestPath!=null)
        {
            if(shortestPath.size()>0) {
                nextStep.x = shortestPath.get(0).x - monsterLocation.x;
                nextStep.y = shortestPath.get(0).y - monsterLocation.y;
               // Log.v("search", "Monster's x:  " + nextStep.x + "  y: " + nextStep.y);
            }

        }
        else Log.v("search", "Its a null" );
       // Log.v("search", "End" );
        finished = true;
    }

    public synchronized boolean isFinished()
    {
        return finished;
    }
    public synchronized void start()
    {
        if(finished)
        {
            this.run();
        }
    }

}
