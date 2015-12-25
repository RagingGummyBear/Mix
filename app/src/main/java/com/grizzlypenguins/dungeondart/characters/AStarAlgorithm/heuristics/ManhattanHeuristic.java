package com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics;

import com.grizzlypenguins.dungeondart.MyPoint;

/**
 * Created by Darko on 02.12.2015.
 */
public class ManhattanHeuristic implements AStarHeuristic{



    @Override
    public float getEstimatedDistanceToGoal(MyPoint start, MyPoint goal) {

        float dx = Math.abs(start.x - goal.x);
        float dy =  Math.abs(start.y - goal.y);
        float temp = 1 * (dx + dy);
        float p = (1/10000);
       temp *= (1.0 + p);

        return temp;

    }



}
