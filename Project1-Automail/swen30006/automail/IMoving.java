package automail;

/**
 * a Moving interface is used by the Robot to decide how it should move.
 */
public interface IMoving {
	
	/**
     * Move the robot towards a destination floor
     * @param destination the floor to move towards
     */
	public void moveTowards(int destination);

}
