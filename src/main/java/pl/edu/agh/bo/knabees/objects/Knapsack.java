package pl.edu.agh.bo.knabees.objects;

public class Knapsack {
	private final int initialCapacity;
	private int currentCapacity;

	public Knapsack(int initialCapacity) {
		this.currentCapacity = this.initialCapacity = initialCapacity;
	}

	public void increaseCapacity(int increase) {
		currentCapacity += increase;
	}

	public int getInitialCapacity() {
		return initialCapacity;
	}

	public int getCurrentCapacity() {
		return currentCapacity;
	}
}
