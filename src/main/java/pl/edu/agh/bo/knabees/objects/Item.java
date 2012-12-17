package pl.edu.agh.bo.knabees.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Item {
	private static final org.apache.log4j.Logger logger = Logger.getLogger(Item.class);

	private double value;
	private double weights[];

	public Item(double value, double weights[]) {
		this.value = value;
		this.weights = weights;
	}

	public Item(String value, String weights[]) throws NumberFormatException {
		this(Double.parseDouble(value), new double[weights.length]);
		for (int i = 0; i < weights.length; i++) {
			this.weights[i] = Double.parseDouble(weights[i]);
		}
	}

	public Item(double value, List<Double> wieghts) {
		this.value = value;
		this.weights = new double[wieghts.size()];
		Double[] weightsTmp = (Double[]) wieghts.toArray(new Double[wieghts.size()]);
		for (int i = 0; i < weightsTmp.length; i++) {
			this.weights[i] = weightsTmp[i];
		}
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

	public static Item parseItem(String textForm) {
		try (Scanner s = new Scanner(textForm.trim())) {
			s.useDelimiter("[^\\p{Digit}\\.]");
			while (!(s.hasNextDouble() || s.hasNextInt())) {
				s.next();
			}
			double value = s.hasNextDouble() ? s.nextDouble() : (s.hasNextInt() ? s.nextInt() : -1);
			List<Double> weights = new ArrayList<>();
			while (true) {
				if (s.hasNextInt()) {
					weights.add((double) s.nextInt());
				} else if (s.hasNextDouble()) {
					weights.add(s.nextDouble());
				} else if (s.hasNext()) {
					s.next();
					continue;
				} else {
					break;
				}
			}
			return new Item(value, weights);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (double weight : weights) {
			sb.append(weight).append(", ");
		}
		sb.append("]");
		return "value: " + value + ", " + "weights: " + sb;
	}
}
