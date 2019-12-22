package automail;

import java.util.LinkedList;
import java.util.stream.Collectors;

import exceptions.ItemTooHeavyException;

/** A Strategy class used by Loader to load heavy mail item to team of robots */
public class TeamLoadStrategy implements ILoadStrategy {

	
	@Override
	public boolean loadRobots(MailItem mailItem, LinkedList<Robot> robots) throws ItemTooHeavyException {
		// get all the robots with free hands
		LinkedList<Robot> robotsAvail = availableRobots(robots);
		int weight = mailItem.weight;
		
		assert(weight > Robot.INDIVIDUAL_MAX_WEIGHT);

		TeamLoadStrategy loadStrategy = chooseTeamStrategy(weight);
		int required = loadStrategy.robotRequired(weight);
		
		// check if robots available is sufficient
		if (robotsAvail.size() < required) {
			return false;
		}
		
		for (int i=0; i<required; i++) {
			robotsAvail.get(i).addToHand(mailItem);
			robotsAvail.get(i).setTeamMoving(loadStrategy.getStepPeriod());
		}

		return true;
	}
	
	/**
	 * Return number of robots required to carry the item (depending on strategy)
	 * @param weight weight of the mail item
	 * @return int number of robots
	 */
	public int robotRequired(int weight) {
		// meant to be overriden
		return 0;
	}
	
	/**
	 * 
	 * @return int time needed in order to move up or down a floor
	 */
	public int getStepPeriod() {
		// meant to be overriden
		return 0;
	}
	
	private LinkedList<Robot> availableRobots(LinkedList<Robot> robots) {
		// return a list of robots with free hands
		return robots
				.stream()
				.filter(e -> e.handEmpty())
				.collect(Collectors.toCollection(LinkedList::new));
	}
	
	private TeamLoadStrategy chooseTeamStrategy(int weight) throws ItemTooHeavyException {
		// choose a loading strategy
		// added for flexibility, ie client can define more classes for another team behaviour
		if (weight <= Robot.PAIR_MAX_WEIGHT) {
			return new PairLoadStrategy(); 
		} else if (weight <= Robot.TRIPLE_MAX_WEIGHT){
			return new TripleLoadStrategy();
		} else {
			throw new ItemTooHeavyException();
		}
	}

}
