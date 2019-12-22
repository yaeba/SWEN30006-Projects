package automail;

import java.util.LinkedList;
import java.util.stream.Collectors;

import exceptions.ItemTooHeavyException;

/**
 * The Loader is responsible for pairing up the robots and mail items
 */
public class Loader {
	
	/** Robots with something loaded */
	private LinkedList<Robot> loadedRobots = new LinkedList<>();
	
	/** Mail items loaded on robots */
	private LinkedList<MailItem> loadedMailItems = new LinkedList<>();
	
	
	
	/**
     * For each item, try to load it to one or more robots depending on strategy
     * @param items a list of all mail items to get loaded
     * @param robots a list of all the robots currently awaiting
     */
	public void loadRobots(LinkedList<MailItem> items, LinkedList<Robot> robots) throws ItemTooHeavyException {
		for (MailItem item : items) {
			ILoadStrategy loadStrategy = chooseStrategy(item);
			if (loadStrategy.loadRobots(item, robots)) {
				loadedMailItems.add(item);
			} else {
				break;
			}
		}
		updateLoadedRobots(robots);
	}
	
	/**
	 * Get the number of robots successfully loaded with item
	 * @return int number of robots loaded
	 */
	public int numRobotsLoaded() {
		return loadedRobots.size();
	}
	
	/**
	 * Set all robots to dispatch
	 */
	public void dispatchLoadedRobots() {
		for (Robot robot : loadedRobots) {
			robot.dispatch();
		}
	}
	
	/**
	 * Get all the loaded robots
	 * @return LinkedList<Robot> of loaded robots
	 */
	public LinkedList<Robot> getLoadedRobots() {
		return loadedRobots;
	}
	
	/**
	 * Get all the mail items loaded
	 * @return LinkedList<MailItem> of mail items loaded
	 */
	public LinkedList<MailItem> getLoadedMailItems() {
		return loadedMailItems;
	}
	
	private ILoadStrategy chooseStrategy(MailItem item) {
		// choose an appropriate loading strategy for this item
		// client can create more strategy classes and add to here
		if (item.weight > Robot.INDIVIDUAL_MAX_WEIGHT) {
			return new TeamLoadStrategy();
		} else {
			return new SingleLoadStrategy();
		}
	}
	
	private void updateLoadedRobots(LinkedList<Robot> robots) {
		loadedRobots = robots
						.stream()
						.filter(e -> !e.isEmpty())
						.collect(Collectors.toCollection(LinkedList::new));
	}

}
