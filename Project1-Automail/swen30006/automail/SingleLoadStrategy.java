package automail;

import java.util.LinkedList;

import exceptions.ItemTooHeavyException;

/** A Strategy class used by Loader to load light mail item to a single robot */
public class SingleLoadStrategy implements ILoadStrategy {
	
	@Override
	public boolean loadRobots(MailItem mailItem, LinkedList<Robot> robots) throws ItemTooHeavyException {
		// put the item on the first robot with empty slot
		for (Robot robot : robots) {
			if (robot.handEmpty()) {
				robot.addToHand(mailItem);
				robot.setSingleMoving();
				return true;
			} else if (robot.tubeEmpty()) {
				robot.addToTube(mailItem);
				return true;
			}
		}
		
		return false;
		
	}

}
