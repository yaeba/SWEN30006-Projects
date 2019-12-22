package automail;

/** A Strategy class used to load an item to three robots */
public class TripleLoadStrategy extends TeamLoadStrategy {
	
	/** Time needed for robot to move one step */
	public static final int TRIPLE_STEP_PERIOD = 3;

	@Override
	public int robotRequired(int weight) {
		return 3;
	}
	
	@Override
	public int getStepPeriod() {
		return TRIPLE_STEP_PERIOD;
	}
	
	
}
