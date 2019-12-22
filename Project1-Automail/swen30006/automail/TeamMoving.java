package automail;

/** A Moving class describing the team moving behaviour of robot */
public class TeamMoving implements IMoving {

	private Robot robot;
	
	/** Record of when the robot last moved */
	private int lastMoved;
	
	/** Time needed for one step */
	private int stepPeriod;

	public TeamMoving(Robot robot, int stepPeriod) {
		this.robot = robot;
		this.lastMoved = Clock.Time();
		this.stepPeriod = stepPeriod;
	}
	
	@Override
	public void moveTowards(int destination) {
		int current_floor = robot.getCurrentFloor();

		if (Clock.Time() - lastMoved < stepPeriod) {
			// should not move yet
			return;
		}
		// move the robot and update time counter
		if (current_floor < destination) {
			robot.setCurrentFloor(++current_floor);
		} else {
			robot.setCurrentFloor(--current_floor);
		}
		lastMoved = Clock.Time();
	}

}
