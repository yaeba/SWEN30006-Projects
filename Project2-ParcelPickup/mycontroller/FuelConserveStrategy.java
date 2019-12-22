package mycontroller;

import java.util.List;

import utilities.Coordinate;

/* A strategy class to conserve fuel usage */
public class FuelConserveStrategy implements IMovingStrategy {

	@Override
	public List<Coordinate> pickDestination(Navigator navigator, 
			int targetParcels, int parcelsFound) {
		int parcelNeeded = targetParcels - parcelsFound;
		List<Coordinate> parcelSeen = navigator.containParcels();
		
		if (parcelNeeded > 0 && parcelSeen.size() > 0) {
			// if we need more parcels and we have seen one, go for it
			return parcelSeen;
		} else if (parcelNeeded <= 0) {
			// we have enough parcels, just go for the goal
			return Navigator.returnGoals();
		}
		// pick a tile to continue exploring
		return navigator.exploreMap();
	}


	@Override
	public boolean isBetterPath(Node a, Node b) {
		// a path is better when it has lower fuel consumption (ie shorter)
		return a.getFuel() > b.getFuel();
	}

}
