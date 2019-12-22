package automail;

/** A Moving class describing the individual moving behaviour of robot */
public class SingleMoving implements IMoving {
	private Robot robot;
	
	public SingleMoving(Robot robot) {
		this.robot = robot;
	}
	
	@Override
	public void moveTowards(int destination) {
		// robot moves one step at a time
		int current_floor = robot.getCurrentFloor();
		if (current_floor < destination) {
			robot.setCurrentFloor(++current_floor);
		} else {
			robot.setCurrentFloor(--current_floor);
		}
	}

}
