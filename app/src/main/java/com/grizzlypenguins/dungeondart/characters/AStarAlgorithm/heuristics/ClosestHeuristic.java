package com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics;

import com.grizzlypenguins.dungeondart.MyPoint;



/**
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile.
 */
public class ClosestHeuristic implements AStarHeuristic {

	public float getEstimatedDistanceToGoal(MyPoint start, MyPoint goal) {
		float dx = goal.x - start.x;
		float dy = goal.y - start.y;
		
		float result = (float) (Math.sqrt((dx*dx)+(dy*dy)));
		
		//Optimization! Changed to distance^2 distance: (but looks more "ugly")
		
		//float result = (float) (dx*dx)+(dy*dy);
		
		
		return result;
	}

}
