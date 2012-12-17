package pl.edu.agh.bo.knabees.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Knapsack {
	private static final org.apache.log4j.Logger logger = Logger.getLogger(Knapsack.class);
	private double limits[];

	public Knapsack(double limits[]) {
		this.limits = limits;
	}

	public Knapsack(List<Double> limits) {
		this.limits = new double[limits.size()];
		Double[] limitsTmp = (Double[]) limits.toArray(new Double[limits.size()]);
		for (int i = 0; i < limitsTmp.length; i++) {
			this.limits[i] = limitsTmp[i];
		}
	}

	public double getLimit(int dimension) {
		return limits[dimension];
	}

	public int getDimension() {
		return limits.length;
	}

	public static Knapsack parseKnapsack(String textForm) {
		try (Scanner s = new Scanner(textForm.trim())) {
			s.useDelimiter("[^\\p{Digit}\\.]");
			List<Double> limits = new ArrayList<>();
			while (true) {
				if (s.hasNextInt()) {
					limits.add((double) s.nextInt());
				} else if (s.hasNextDouble()) {
					limits.add(s.nextDouble());
				} else if (s.hasNext()) {
					s.next();
					continue;
				} else {
					break;
				}
			}
			return new Knapsack(limits);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (double limit : limits) {
			sb.append(limit).append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
}
