package mycontroller;

import swen30006.driving.Simulation;

/* Singleton class to create moving strategy */
public class MovingStrategyFactory {
	public static MovingStrategyFactory singletonFactory = null;
	
	/**
	 * Get the singleton instance of the factory
	 * @return a Factory instance
	 */
	public static MovingStrategyFactory getInstance() {
		if (singletonFactory == null) {
			singletonFactory = new MovingStrategyFactory();
		}
		return singletonFactory;
	}
	
	/**
	 * Create appropriate moving strategy according to properties file
	 * @param toConserve strategy to use
	 * @return an IMovingStrategy instance
	 */
	public IMovingStrategy createMovingStrategy(
			Simulation.StrategyMode toConserve) {
		IMovingStrategy strategy = null;
		
	
		if (toConserve.equals(Simulation.StrategyMode.FUEL)) {
			strategy = new FuelConserveStrategy();
		} else if (toConserve.equals(Simulation.StrategyMode.HEALTH)) {
			strategy = new HealthConserveStrategy();
		}

		return strategy;
	}
}
