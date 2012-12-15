package pl.edu.agh.bo.knabees.alg;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Test {
	private Item[] items;
	private Knapsack knapsack;

	public Test(Scanner sc) {
		System.out.println("START!");
		if (!readInput(sc)) {
			System.out.println("Wrong input!");
			return;
		}

		BeesAlgorithm bA = new BeesAlgorithm(knapsack, items, 500);
		bA.run();
		boolean solution[] = bA.getSolution();

		int dim = knapsack.getDimension();

		System.out.println("Limits:");
		for (int i = 0; i < dim; ++i) {
			System.out.print(knapsack.getLimit(i) + "\t");
		}
		System.out.println("\nThe Choosen One:");
		double[] weights = new double[dim];
		double big_price = 0;
		for (int i = 0; i < items.length; ++i) {
			if (!solution[i])
				continue;
			for (int j = 0; j < dim; ++j) {
				System.out.print(items[i].getWeight(j) + "\t");
				weights[j] += items[i].getWeight(j);
			}
			System.out.println("\t(" + items[i].getValue() + ")");
			big_price += items[i].getValue();
		}
		System.out.println("Overall:");
		for (int j = 0; j < dim; ++j) {
			System.out.print(weights[j] + "\t");
		}
		System.out.println("\t(" + big_price + ")");
	}

	public boolean readInput(Scanner s) {
		try {
			int m = s.nextInt();
			int n = s.nextInt();
			double[] limits = new double[m];
			for (int i = 0; i < m; ++i)
				limits[i] = s.nextDouble();
			knapsack = new Knapsack(limits);
			items = new Item[n];
			for (int j = 0; j < n; ++j) {
				double price = s.nextDouble();
				double[] weights = new double[m];
				for (int i = 0; i < m; ++i)
					weights[i] = s.nextDouble();
				items[j] = new Item(price, weights);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("Input ok.");
		return true;
	}

	public static void main(String[] args) {
		if (args.length > 0)
			try {
				new Test(new Scanner(new File(args[0])));
			} catch (IOException e) {
				System.out.println("IO Error");
			}
		else {
			new Test(new Scanner(System.in));
		}

	}

}
