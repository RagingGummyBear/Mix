package com.grizzlypenguins.dungeondart.characters.AStarAlgorithm;
import com.grizzlypenguins.dungeondart.MyPoint;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.bresenhamsLine.BresenhamsLine;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics.AStarHeuristic;
import com.grizzlypenguins.dungeondart.characters.AStarAlgorithm.heuristics.DiagonalHeuristic;


import java.util.ArrayList;


public class PathFinder {

	AreaMap map;

	
	public ArrayList<MyPoint> getWaypoints(AreaMap map) {
		this.map = map;


		AStarHeuristic heuristic = new DiagonalHeuristic();


		AStar aStar = new AStar(map, heuristic);


		ArrayList<MyPoint> shortestPath = aStar.calcShortestPath(map.getStartLocationX(), map.getStartLocationY(), map.getGoalLocationX(), map.getGoalLocationY());
		
		//log.addToLog("Printing map of shortest path...");
		//new PrintMap(map, shortestPath);


		ArrayList<MyPoint> waypoints = calculateWayPoints(shortestPath);

		return waypoints;
	}
	
	private ArrayList<MyPoint> calculateWayPoints(ArrayList<MyPoint> shortestPath) {
		ArrayList<MyPoint> waypoints = new ArrayList<MyPoint>();
		
		shortestPath.add(0,map.getStartNode().getPoint());
		shortestPath.add(map.getGoalNode().getPoint());

		MyPoint p1 = shortestPath.get(0);
		int p1Number = 0;
		waypoints.add(p1);

		MyPoint p2 = shortestPath.get(1);
		int p2Number = 1;
		
		while(!p2.equals(shortestPath.get(shortestPath.size()-1))) {
			if(lineClear(p1, p2)) {
				//make p2 the next point in the path
				p2Number++;
				p2 = shortestPath.get(p2Number);
			} else {
				p1Number = p2Number-1;
				p1 = shortestPath.get(p1Number);
				waypoints.add(p1);
				p2Number++;
				p2 = shortestPath.get(p2Number);
			}
		}
		waypoints.add(p2);
		
		return waypoints;
	}
	
	private boolean lineClear(MyPoint a, MyPoint b) {
		ArrayList<MyPoint> pointsOnLine = BresenhamsLine.getPointsOnLine(a, b);
		for(MyPoint p : pointsOnLine) {
			if(map.getNode(p.x, p.y).isObstacle) {
				return false;
			}
		}
		return true;
	}
}
