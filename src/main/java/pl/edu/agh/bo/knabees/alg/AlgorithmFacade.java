package pl.edu.agh.bo.knabees.alg;

import java.util.List;

import pl.edu.agh.bo.knabees.communication.Observable;
import pl.edu.agh.bo.knabees.communication.ValueCommand;
import pl.edu.agh.bo.knabees.objects.Item;
import pl.edu.agh.bo.knabees.objects.Knapsack;

public class AlgorithmFacade extends Observable {
	private final List<Knapsack> knapsacks;
	private final List<Item> items;

	public AlgorithmFacade(List<Knapsack> knapsacks, List<Item> items) {
		this.knapsacks = knapsacks;
		this.items = items;
	}
	

	public <T> void calculate(ValueCommand<T> callback) {
		// TODO : logic goes here

		// TODO : remember to notify Observers from time to time

		// optionally we can provide an action to perform, after the calculation
		// is completed
		if (callback != null) {
			callback.execute(null); // FIXME: we can provide non-null argument
		}
	}
}
