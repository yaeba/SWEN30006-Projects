package automail;

/** A Strategy class used to load an item to two robots */
public class PairLoadStrategy extends TeamLoadStrategy {
	
	/** Time needed for robot to move one step */
	public static final int PAIR_STEP_PERIOD = 3;
	
	@Override
	public int robotRequired(int weight) {
		return 2;
	}
	
	@Override
	public int getStepPeriod() {
		return PAIR_STEP_PERIOD;
	}
	
}
