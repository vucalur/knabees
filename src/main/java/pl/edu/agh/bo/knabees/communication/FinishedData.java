package pl.edu.agh.bo.knabees.communication;

/**
 * Represents data sent to observers when algorithm finishes
 */
public final class FinishedData {
	private final boolean[] isItemTaken;

	public FinishedData(boolean[] isItemTaken) {
		this.isItemTaken = isItemTaken;
	}

	public boolean[] getIsItemTaken() {
		return isItemTaken;
	}
}
