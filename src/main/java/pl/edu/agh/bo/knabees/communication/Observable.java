package pl.edu.agh.bo.knabees.communication;

import java.util.ArrayList;
import java.util.List;

/**
 * Class extending {@link Observable} must construct proper data for observers
 * (data is shared, hence it should be immutable) and invoke
 * notifyAllObservers()
 * 
 * @param <T>
 *            Type of Data being send to observers
 */
public abstract class Observable<T> {
	private List<Observer<T>> observers = new ArrayList<Observer<T>>();

	protected void notifyAllObservers(T data) {
		for (Observer<T> observer : observers) {
			observer.notifyMe(data);
		}
	}

	public boolean addItemsObserver(Observer<T> observer) {
		return observers.add(observer);
	}

	public boolean removeObserver(Observer<T> observer) {
		return observers.remove(observer);
	}
}
