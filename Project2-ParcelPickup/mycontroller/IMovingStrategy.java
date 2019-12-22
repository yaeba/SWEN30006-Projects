package mycontroller;

import java.util.List;

import utilities.Coordinate;

public interface IMovingStrategy {
	/**
	 * Pick destination(s) to visit next
	 * @param navigator
	 * @param targetParcels How many parcels are required
	 * @param parcelsFound How many have we found
	 * @return A list of destinations' coordinates
	 */
	public List<Coordinate> pickDestination(Navigator navigator, 
			int targetParcels, int parcelsFound);
	
	
	/**
	 * Compare between paths in breadth-first search
	 * @param a new path
	 * @param b old path
	 * @return is new path better or not
	 */
	public boolean isBetterPath(Node a, Node b);
}
