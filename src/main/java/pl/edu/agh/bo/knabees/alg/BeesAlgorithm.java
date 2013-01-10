package pl.edu.agh.bo.knabees.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pl.edu.agh.bo.knabees.communication.FinishedData;
import pl.edu.agh.bo.knabees.communication.IterationData;
import pl.edu.agh.bo.knabees.communication.Observable;
import pl.edu.agh.bo.knabees.model.Item;
import pl.edu.agh.bo.knabees.model.Knapsack;

public class BeesAlgorithm extends Observable<IterationData> {
	private Knapsack knapsack;
	private Item[] items;
	private boolean solution[];
	private int dimensions;
	private int itemsAmount;

	// consts
	private int nBee;// scoutBees
	private int nSite; // sites
	private int nBest = 2;
	private int ngh;// initial size of neighbourhood to search (size of
					// patch)
	// patch includes Site
	private int nep;// number of bees recruited for the selected sites

	// consts-end
	int maxIterations;

	public static class Builder {
		// required
		private Knapsack knapsack;
		private Item[] items;
		private int maxIterations = 50;

		// optional
		private int nBee = 30;
		private int nSite = 5;
		private int ngh = 2;
		private int nep = 2;

		public Builder(Knapsack knapsack, Item[] items) {
			this.knapsack = knapsack;
			this.items = items;
		}

		public Builder(Knapsack knapsack, List<Item> items) {
			this(knapsack, items.toArray(new Item[items.size()]));
		}

		public Builder maxIterations(int value) {
			maxIterations = value;
			return this;
		}

		public Builder nBee(int value) {
			nBee = value;
			return this;
		}

		public Builder nSite(int value) {
			nSite = value;
			return this;
		}

		public Builder ngh(int value) {
			ngh = value;
			return this;
		}

		public Builder nep(int value) {
			nep = value;
			return this;
		}

		public BeesAlgorithm build() {
			return new BeesAlgorithm(this);
		}

		public Knapsack getKnapsack() {
			return knapsack;
		}

		public Item[] getItems() {
			return items;
		}

		public int getMaxIterations() {
			return maxIterations;
		}

		public int getNBee() {
			return nBee;
		}

		public int getNSite() {
			return nSite;
		}

		public int getNgh() {
			return ngh;
		}

		public int getNep() {
			return nep;
		}
	}

	private BeesAlgorithm(Builder builder) {
		this.knapsack = builder.knapsack;
		this.items = builder.items;
		dimensions = knapsack.getDimension();
		itemsAmount = builder.items.length;
		this.maxIterations = builder.maxIterations;
		this.nBee = builder.nBee;
		this.nSite = builder.nSite;
		this.ngh = builder.ngh;
		this.nep = builder.nep;
	}

	private double itemsWeightSum(boolean matrix[], int dimension) {
		double result = 0.0f;
		for (int i = 0; i < itemsAmount; i++)
			if (matrix[i])
				result += items[i].getWeight(dimension);
		return result;
	}

	// objective function
	private double itemsValue(boolean matrix[]) {
		double value = 0;
		for (int i = 0; i < matrix.length; i++)
			if (matrix[i])
				value += items[i].getValue();
		return value;
	}

	private boolean checkSolution(boolean matrix[]) {
		for (int i = 0; i < knapsack.getDimension(); i++)
			if (itemsWeightSum(matrix, i) > knapsack.getLimit(i))
				return false;
		return true;
	}

	public int rws(boolean items[]) {
		double probabilitySum = 0, p = 0;
		double randomNumber;
		for (int i = 0; i < itemsAmount; i++)
			probabilitySum += items[i] ? 1 : 0;
		randomNumber = Math.random() * probabilitySum;
		// System.out.println(probabilitySum);
		for (int i = 0; i < itemsAmount; i++) {
			p += items[i] ? 1 : 0;

			if (p >= randomNumber)
				return i;
		}
		return -1;
	}

	private boolean setNotEmpty(boolean S[]) {
		for (boolean b : S) {
			if (b)
				return true;
		}
		return false;
	}

	private boolean[] safeSet(boolean S[]) {
		boolean[] retSet = new boolean[itemsAmount];
		boolean[] tempSet = Arrays.copyOf(S, S.length);
		for (int i = 0; i < itemsAmount; i++) {
			if (!tempSet[i]) {
				tempSet[i] = true;
				if (checkSolution(tempSet)) {
					retSet[i] = true;
				}
				tempSet[i] = false;
			}
		}
		return retSet;
	}

	public boolean[] generateSolution() {
		double[] fj = new double[itemsAmount];
		boolean x[] = new boolean[itemsAmount];
		boolean S[] = new boolean[itemsAmount];
		int t;
		S = safeSet(x);
		while (setNotEmpty(S)) {
			t = rws(S);
			x[t] = true;
			S = safeSet(x);
		}
		return x;
	}

	boolean[] evaluateJk(boolean[] Jx, int k) {
		boolean[] S = Arrays.copyOf(Jx, itemsAmount);
		double fj[] = new double[itemsAmount];
		for (int i = 0; i < itemsAmount; i++) {
			fj[i] = 1;
		}
		boolean Jk[] = new boolean[itemsAmount];
		int t;
		while (powerOfSet(Jk) < k) {
			t = rws(S);
			if (t == -1)
				break;
			Jk[t] = true;
			S[t] = false;
		}
		return Jk;

	}

	private int powerOfSet(boolean[] S) {
		int count = 0;
		for (int i = 0; i < itemsAmount; i++)
			if (S[i])
				count++;
		return count;
	}

	public boolean[] solutionFromNeighbourhood(boolean[] solutionJx, int k) {
		boolean[] x = Arrays.copyOf(solutionJx, itemsAmount);
		boolean[] Jk = evaluateJk(solutionJx, k);
		for (int i = 0; i < itemsAmount; i++)
			if (Jk[i])
				x[i] = false;

		boolean[] S = safeSet(x);
		while (setNotEmpty(S)) {
			int t = rws(S);
			x[t] = true;
			if (!checkSolution(x))
				throw new RuntimeException();
			S = safeSet(x);
		}
		return x;

	}

	public void run() {
		ArrayList<boolean[]> solutions = new ArrayList<boolean[]>();

		// initial solution (random)
		for (int i = 0; i < nBee; i++) {
			solutions.add(generateSolution());
		}

		for (int nIteration = 1; nIteration <= maxIterations; nIteration++) {
			Collections.sort(solutions, new Comparator<boolean[]>() {
				@Override
				public int compare(boolean[] s1, boolean[] s2) {
					double c = itemsValue(s1) - itemsValue(s2);
					return c > 0 ? -1 : (c < 0 ? 1 : 0);
				}
			});
			double[] solutionsValues = new double[solutions.size()];
			System.out.print("Posortowane:");
			for (int i = 0; i < solutions.size(); ++i) {
				solutionsValues[i] = itemsValue(solutions.get(i));
				System.out.print(" " + solutionsValues[i]);
			}
			System.out.println();
			ArrayList<boolean[]> newSolutions = new ArrayList<boolean[]>();
			if (solution == null || itemsValue(solutions.get(0)) > itemsValue(solution))
				solution = solutions.get(0);

			for (int i = 0; i < nSite; ++i) {
				newSolutions.add(solutions.get(i));
				for (int j = 0; j < nep; ++j) {
					newSolutions.add(solutionFromNeighbourhood(solutions.get(i), ngh));
				}
			}
			for (int i = 0; i < nBee - nSite * (nep + 1); ++i) {
				newSolutions.add(generateSolution());
			}
			solutions = newSolutions;
			System.out.println(nIteration);
			notifyAllObservers(new IterationData.Builder(nIteration, itemsValue(solution)).itemsTaken(solution)
					.solutionsValues(solutionsValues).build());
		}
		// TODO: for tests only!!!

		System.out.println("Limits:");
		for (int i = 0; i < dimensions; ++i) {
			System.out.print(knapsack.getLimit(i) + "\t");
		}
		System.out.println();

		System.out.println("\nThe Choosen Ones:");
		for (int i = 0; i < itemsAmount; i++)
			System.out.print(solution[i] ? "1" : "0");
		System.out.println();

		double[] weights = new double[dimensions];
		double big_price = 0;
		for (int i = 0; i < items.length; ++i) {
			if (!solution[i])
				continue;
			for (int j = 0; j < dimensions; ++j) {
				System.out.print(items[i].getWeight(j) + "\t");
				weights[j] += items[i].getWeight(j);
			}
			System.out.println("\t(" + items[i].getValue() + ")");
			big_price += items[i].getValue();
		}
		System.out.println("Overall:");
		for (int j = 0; j < dimensions; ++j) {
			System.out.print(weights[j] + "\t");
		}
		System.out.println("\t(" + big_price + ")");
		// TODO: end of tests
		notifyAllObserversTaksFinished(new FinishedData(solution));
	}

	public boolean[] getSolution() {
		return solution;
	}

}
