package mycontroller;

import java.util.List;

import utilities.Coordinate;

/* A strategy class to conserve health */
public class HealthConserveStrategy implements IMovingStrategy {
	
	// a car will collect more health if it falls below health threshold
	public static final float HEALTH_THRES = 80;
	
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
		// only collect health when needed
		float oldHealth = b.getHealth();
		float newHealth = a.getHealth();
		
		if (oldHealth < HEALTH_THRES && newHealth > oldHealth) {
			// health was lower than threshold with previous path
			return true;
		} 
		// may not worth the effort to go and collect health
		return a.getFuel() > b.getFuel();
	}

}
