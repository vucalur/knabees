package pl.edu.agh.bo.knabees.ui;

import javax.naming.OperationNotSupportedException;

import pl.edu.agh.bo.knabees.alg.BeesAlgorithm;

public interface DataHolder {
	void clearData();

	void loadData(BeesAlgorithm.Builder data) throws OperationNotSupportedException;
}
