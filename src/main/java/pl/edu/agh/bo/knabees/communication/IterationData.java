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

	public static class Builder {
		private int iterationNum;

		private double totalTakenValue;

		public Builder(int iterationNumber, double totalTakenValue) {
			this.iterationNum = iterationNumber;
			this.totalTakenValue = totalTakenValue;
		}

		public IterationData build() {
			return new IterationData(this);
		}
	}

	private IterationData(Builder builder) {
		this.iterationNum = builder.iterationNum;
		this.totalTakenValue = builder.totalTakenValue;
	}

	public int getIterationNum() {
		return iterationNum;
	}

	public double calculateTotalTakenValue() {
		return totalTakenValue;
	}
}
