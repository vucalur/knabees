package pl.edu.agh.bo.knabees.communication;

public interface ValueCommand<T> {
	void execute(T value);
}
