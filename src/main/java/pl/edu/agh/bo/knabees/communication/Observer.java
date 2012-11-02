package pl.edu.agh.bo.knabees.communication;

import java.util.List;

public interface Observer<T> {
	void notifyMe(List<? extends T> changed);
}
