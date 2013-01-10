package pl.edu.agh.bo.knabees.communication;

/**
 * Immutable !!!! <br />
 * Whenever editing, do not remove this crucial property
 * 
 * Represents data sent to observers in each iteration
 */
public final class IterationData {
	private final int iterationNum;
	private final double totalTakenValue;
	private final boolean[] itemsTaken;
	private final double[] solutionsValues;
	private final int takenItemsCount;

	public static class Builder {
		private int iterationNum;
		private double totalTakenValue;
		private boolean[] itemsTaken = {};
		private double[] solutionsValues = {};

		public Builder(int iterationNumber, double totalTakenValue) {
			this.iterationNum = iterationNumber;
			this.totalTakenValue = totalTakenValue;
		}

		public Builder itemsTaken(boolean[] value) {
			this.itemsTaken = value;
			return this;
		}

		public Builder solutionsValues(double[] value) {
			this.solutionsValues = value;
			return this;
		}

		public IterationData build() {
			return new IterationData(this);
		}
	}

	private IterationData(Builder builder) {
		this.iterationNum = builder.iterationNum;
		this.totalTakenValue = builder.totalTakenValue;
		this.itemsTaken = builder.itemsTaken;
		this.solutionsValues = builder.solutionsValues;

		int takenItemsCount = 0;
		for (boolean isTaken : itemsTaken) {
			if (isTaken) {
				takenItemsCount++;
			}
		}
		this.takenItemsCount = takenItemsCount;
	}

	public int getIterationNum() {
		return iterationNum;
	}

	public double getTotalTakenValue() {
		return totalTakenValue;
	}

	public int getTakenItemsCount() {
		return takenItemsCount;
	}

	public boolean[] getItemsTaken() {
		return itemsTaken;
	}

	public double[] getSolutionsValues() {
		return solutionsValues;
	}
}
