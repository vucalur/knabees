public class Item {
	private float value;
	private float weights[];
	public Item(float value, int dimensions) {
		this.value = value;
		weights = new float[dimensions];
	}
}
