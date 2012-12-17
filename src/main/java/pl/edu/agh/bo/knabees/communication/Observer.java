package pl.edu.agh.bo.knabees.communication;

/**
 * @param <T>
 *            Type of Data being send to observer
 */
public interface Observer<T> {
	void notifyMe(T data);
}
