package pl.edu.agh.bo.knabees.alg;

public class Knapsack {
	private double limits[];

	public Knapsack(double limits[]) {
		this.limits = limits;
	}

	public double getLimit(int dimension) {
		return limits[dimension];
	}

	public int getDimension() {
		return limits.length;
	}
}
