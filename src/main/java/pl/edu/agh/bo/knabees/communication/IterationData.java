package pl.edu.agh.bo.knabees.communication;

/**
 * Immutable !!!! <br />
 * Whenever editing, do not remove this crucial property
 * 
 * Represents data sent to observers on
 */
public final class IterationData {
	private final int iterationNum;
	private final double totalTakenValue;
	private final boolean[] itemsTaken;
	private final double[] solutionsValues;

	public static class Builder {
		private int iterationNum;
		private double totalTakenValue;
		private boolean[] itemsTaken;
		private double[] solutionsValues;

		public Builder(int iterationNumber, double totalTakenValue,
				boolean[] itemsTaken, double[] solutionsValues) {
			this.iterationNum = iterationNumber;
			this.totalTakenValue = totalTakenValue;
			this.itemsTaken = itemsTaken;
			this.solutionsValues = solutionsValues;
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
	}

	public int getIterationNum() {
		return iterationNum;
	}

	public double calculateTotalTakenValue() {
		return totalTakenValue;
	}

	public boolean[] getItemsTaken() {
		return itemsTaken;
	}

	public double[] calculateSolutionsValues() {
		return solutionsValues;
	}
}
