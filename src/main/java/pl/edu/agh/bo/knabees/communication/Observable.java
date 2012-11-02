package pl.edu.agh.bo.knabees.communication;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.bo.knabees.objects.Item;
import pl.edu.agh.bo.knabees.objects.Knapsack;

public class Observable {
	private List<Observer<Item>> itemsObservers = new ArrayList<>();
	private List<Observer<Knapsack>> knapsacksObservers = new ArrayList<>();

	protected void notifyItemsObservers(List<? extends Item> changed) {
		for (Observer<Item> o : itemsObservers) {
			o.notifyMe(changed);
		}
	}

	protected void notifyKnapsacksObservers(List<? extends Knapsack> changed) {
		for (Observer<Knapsack> o : knapsacksObservers) {
			o.notifyMe(changed);
		}
	}

	public boolean addItemsObserver(Observer<Item> o) {
		return addObserver(o, itemsObservers);
	}

	public boolean addKnapsacksObserver(Observer<Knapsack> o) {
		return addObserver(o, knapsacksObservers);
	}

	private <T> boolean addObserver(Observer<T> o, List<? super Observer<T>> observers) {
		if (o == null || observers == null) {
			throw new IllegalArgumentException();
		}
		if (observers.contains(o)) {
			return false;
		} else {
			return observers.add(o);
		}
	}

	public <T> boolean removeObserver(Observer<T> o) {
		return itemsObservers.remove(o) | knapsacksObservers.remove(o);
	}
}
