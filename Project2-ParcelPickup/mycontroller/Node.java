package mycontroller;

import tiles.HealthTrap;
import tiles.LavaTrap;
import tiles.MapTile;
import tiles.WaterTrap;
import utilities.Coordinate;

/* A class to store information of path processed by BFS */
public class Node {
	private Coordinate coor;
	private float health;
	private float fuel;
	private Node parent;
	
	public Node(Coordinate coor, 
			float health, float fuel, Node parent) {
		this.coor = coor;
		this.health = health;
		this.fuel = fuel;
		this.parent = parent;
	}
	
	public Coordinate getCoordinate() {
		return coor;
	}
	
	public float getHealth() {
		return health;
	}
	
	public float getFuel() {
		return fuel;
	}
	
	public Node getParent() {
		return parent;
	}
	
	/**
	 * Given a coordinate and tile, compute the resultant health and fuel
	 * @param nextCoor
	 * @param nextTile
	 * @return A new node with evaluated information
	 */
	public Node evaluateNode(Coordinate nextCoor, MapTile nextTile) {
		// apply health and fuel penalty
		float nextHealth = getHealth();
		float nextFuel = getFuel();
		if (nextTile instanceof WaterTrap) {
			nextHealth += WaterTrap.Yield;
		} else if (nextTile instanceof LavaTrap) {
			nextHealth -= LavaTrap.HealthDelta;
		} else if (nextTile instanceof HealthTrap) {
			nextHealth += HealthTrap.HealthDelta;
		}

		nextFuel--;
		return new Node(nextCoor, nextHealth, nextFuel, this);
	}
}