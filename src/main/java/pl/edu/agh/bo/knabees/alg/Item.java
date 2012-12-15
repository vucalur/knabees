package pl.edu.agh.bo.knabees.alg;

public class Item {
	private double value;
	private double weights[];

	public Item(double value, double weights[]) {
		this.value = value;
		this.weights = weights;
	}

	public double getValue() {
		return value;
	}

	public double getWeight(int dimension) {
		return weights[dimension];
	}

	public int getDimension() {
		return weights.length;
	}
}
