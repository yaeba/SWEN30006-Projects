package automail;

import java.util.LinkedList;

import exceptions.ItemTooHeavyException;

/**
 * a LoadStrategy interface is used by Loader to load a mail item to appropriate robot(s)
 */
public interface ILoadStrategy {
	
	/**
     * Load a mail item appropriately to one or more robots
     * @param mailItem the mailItem to get loaded
     * @param robots a list of all the robots currently awaiting
     * @return boolean indicating if the mailItem has been successfully loaded
     */
	public boolean loadRobots(MailItem mailItem, LinkedList<Robot> robots) throws ItemTooHeavyException;

}
