package mycontroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import tiles.*;
import utilities.Coordinate;
import world.Car;
import world.World;

/* A class for keeping track of all tiles seen and computing best path */
public class Navigator {
	public static final Coordinate[] DIRECTIONS = {
			new Coordinate(1, 0),
			new Coordinate(-1, 0),
			new Coordinate(0, -1),
			new Coordinate(0, 1)
	};
	
	
	// collection of all seen tiles
	private HashMap<Coordinate, MapTile> seenTiles;
	private Car car;
	
	// constructor
	public Navigator(Car car) {
		seenTiles = new HashMap<>();
		this.car = car;
	}
	
	// copy constructor
	public Navigator(Car car, HashMap<Coordinate, MapTile> seenTiles) {
		this.car = car;
		this.seenTiles = new HashMap<>();
		this.seenTiles.putAll(seenTiles);
	}
	
	
	/**
	 * Method to update previously seen tiles
	 */
	public void updateSeenTiles() {
		HashMap<Coordinate, MapTile> view = car.getView();
		for (Map.Entry<Coordinate, MapTile> entry : view.entrySet()) {
			Coordinate coor = entry.getKey();
			MapTile tile = entry.getValue();
			if (tile.isType(MapTile.Type.EMPTY)) {
				// do not add empty tiles
				continue;
			}
			
			seenTiles.put(coor, tile);
		}
	}
	
	/**
	 * Return number of all seen tiles
	 * @return int
	 */
	public int totalSeenTiles() {
		return seenTiles.size();
	}
	
	
	/**
	 * Return a list of goals' coordinates
	 * @return
	 */
	public static List<Coordinate> returnGoals() {
		List<Coordinate> res = new ArrayList<>();
		HashMap<Coordinate, MapTile> global = World.getMap();
		for (Coordinate coor : global.keySet()) {
			if (global.get(coor).isType(MapTile.Type.FINISH)) {
				res.add(coor);
			}
		}
		return res;
	}

	/**
	 * Return a list of parcels seen
	 * @return list of coordinate
	 */
	public List<Coordinate> containParcels() {
		ArrayList<Coordinate> res = new ArrayList<>();
		for (Map.Entry<Coordinate, MapTile> entry : seenTiles.entrySet()) {
			Coordinate coor = entry.getKey();
			MapTile tile = entry.getValue();
			if (tile instanceof ParcelTrap) {
				res.add(coor);
			}
		}
		return res;
		
	}
	
	/**
	 * Return a list of seen ice tiles
	 * @return list of coordinate
	 */
	public List<Coordinate> containIceTiles() {
		ArrayList<Coordinate> res = new ArrayList<>();
		for (Map.Entry<Coordinate, MapTile> entry : seenTiles.entrySet()) {
			Coordinate coor = entry.getKey();
			MapTile tile = entry.getValue();
			if (tile instanceof HealthTrap) {
				res.add(coor);
			}
		}
		return res;
	}
	
	/**
	 * Use breadth-first search to compute best paths to destinations 
	 * according to different strategies
	 * @param dest All destinations' coordinates
	 * @param movingStrategy Moving Strategy
	 * @return Collection of all best paths
	 */
	public List<List<Coordinate>> computePaths(List<Coordinate> dest, 
			IMovingStrategy movingStrategy) {
		
		HashMap<Coordinate, Node> visited = new HashMap<>();
		
		float initialFuel = car.getFuel();
		float initialHealth = car.getHealth();
		Coordinate initialCoor = new Coordinate(car.getPosition());

		Node initialNode = new Node(initialCoor, initialHealth, initialFuel, null);
		
		// use breadth-first search
		LinkedList<Node> nextToVisit = new LinkedList<>();
		nextToVisit.add(initialNode);

		while (!nextToVisit.isEmpty()) {
			Node visit = nextToVisit.remove();
			
	
			Coordinate coor = visit.getCoordinate();
			MapTile tile = null;
			
			
			if (!seenTiles.containsKey(coor)) {
				tile = World.getMap().get(coor);
			} else {
				tile = seenTiles.get(coor);
			}
			float health = visit.getHealth();
			float fuel = visit.getFuel();
			
			if (visited.containsKey(coor)) {
				// visited before, check if now is better
				Node lastVisit = visited.get(coor);
				if (!movingStrategy.isBetterPath(visit, lastVisit)) {
					// last time was better, ignore
					continue;
				}
				visited.put(coor, visit);
			}
			if (tile.isType(MapTile.Type.WALL)) {
				// do not go into wall
				continue;
			} else if (health < 0.5 || fuel < 0.5) {
				// skip paths which lead to losing
				continue;
			} 

			
			// add children of next generation
			for (Coordinate dir : DIRECTIONS) {

				Coordinate nextCoor = new Coordinate(coor.x+dir.x, coor.y+dir.y);

				Node newNode = visit.evaluateNode(nextCoor, seenTiles.get(nextCoor));
				
				// add to list of to-travel
				nextToVisit.add(newNode);
			
			}
			// mark as visited
			visited.put(coor, visit);
			
		}
		
		List<List<Coordinate>> res = new ArrayList<>();
		
		for (Coordinate d : dest) {
			if (visited.containsKey(d)) {
				// we knew the best path to a destination
				List<Coordinate> bestPath = backtrackPath(visited.get(d));
				if (bestPath.size() > 0) {
					res.add(bestPath);
				}
			}
		}
		
		return res;
	}
	
	
	private List<Coordinate> backtrackPath(Node node) {
		// backtrack node to create a path to current node
		ArrayList<Coordinate> path = new ArrayList<>();
		Node iter = node;
		
		while (iter.getParent() != null) {
			path.add(iter.getCoordinate());
			iter = iter.getParent();
		}
		Collections.reverse(path);
		return path;
	}
	
	/**
	 * Find coordinates that are shortest unexplored tiles
	 * @param movingStrategy moving strategy to influence path taken
	 * @return list of coordinates of destinations
	 */
	public List<Coordinate> exploreMap() {
		List<Coordinate> res = new ArrayList<>();
		
		HashMap<Coordinate, MapTile> globalMap = World.getMap();
		
		for (Coordinate coor : globalMap.keySet()) {
			if (seenTiles.containsKey(coor) || globalMap.get(coor).isType(MapTile.Type.EMPTY)
					|| globalMap.get(coor).isType(MapTile.Type.WALL)) {
				continue;
			}
			
			res.add(coor);
		}
		return res;
	}

}
