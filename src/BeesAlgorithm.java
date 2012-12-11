
public class BeesAlgorithm {
	private Knapsack knapsack;
	private Item[] items;
	private boolean solution[];
	private int dimensions;
	private int itemsAmount;
	// private Map<Integer, Integer> solutionsCount = new HashMap<Integer,
	// Integer>();

	// consts
	int nBee = 600; // scoutBees
	int nSite = 100; // sites
	// int nBest=10;
	int ngh = 5; // initial size of neighbourhood to search (size of patch)
	// patch includes Site
	int nep = 40;// number of bees recruited for the selected sites

	int alpha = 1;
	int beta = 1;
	int gamma = 2;
	int alphaPrim = 1;
	int betaPrim = 1;
	int gammaPrim = 1;
	int alphaBis = 2;
	int betaBis = 1;
	int gammaBis = 0;
	double probability0 = 0.2f;
	double probability1 = 0.8f;
	double probability0Prim = 0.1f;
	double probability1Prim = 0.9f;
	// consts-end
	int maxIterations; // =500

	int visitedCount[];

	private int df(int j) {
		return visitedCount[j];
	}

	public BeesAlgorithm(Knapsack knapsack, Item[] items, int maxIterations) {
		dimensions = knapsack.getDimension();
		itemsAmount = items.length;
		this.knapsack = knapsack;
		this.items = items;
		this.solution = new boolean[itemsAmount];
		this.maxIterations = maxIterations;
		visitedCount = new int[itemsAmount];
	}

	private double itemsWeightSum(boolean matrix[], int dimension) {
		double result = 0.0f;
		for (int i = 0; i < itemsAmount; i++)
			if (matrix[i])
				result += items[i].getWeight(dimension);
		return result;
	}

	// funkcja celu
	private double itemsValue(boolean matrix[]) {
		double value = 0;
		for (int i = 0; i < matrix.length; i++)
			if (matrix[i])
				value += items[i].getValue();
		return value;
	}

	private boolean checkSolution(boolean matrix[]) {
		for (int i = 0; i < knapsack.getDimension(); i++)
			if (itemsWeightSum(matrix, i) > knapsack.getLimit(i))
				return false;
		return true;
	}

	private double evaluateProbability(int j, double fj, double alpha, double beta,
			double gamma) {
		double maxC = maxC();
		double maxSPrim = maxSPrim();
		double maxDF = maxDF();
		double duzaSuma = 0.0;

		for (int i = 0; i < dimensions; i++) {
			duzaSuma += (items[j].getWeight(i) / (b(i) * b(i)));
		}
		return (double) (fj * Math.pow(maxDF / df(j), gamma)
				* Math.pow(items[j].getValue() / maxC, alpha) / Math.pow(
				duzaSuma / maxSPrim, beta));
	}

	private int maxDF() {
		int max = 0;
		int actual;
		for (int i = 0; i < itemsAmount; i++) {
			actual = df(i);
			max = actual > max ? actual : max;
		}
		return max;
	}

	private double maxSPrim() {
		double max = 0;
		double sum;
		for (int j = 0; j < itemsAmount; j++) {
			sum = 0;
			for (int i = 0; i < dimensions; i++) {
				sum += (items[j].getWeight(i) / (b(i) * b(i)));
			}
			max = sum > max ? sum : max;
		}

		return max;
	}

	private double maxC() {
		double maximum = items[0].getValue();
		for (int i = 1; i < itemsAmount; i++)
			if (items[i].getValue() > maximum)
				maximum = items[i].getValue();
		return maximum;
	}

	double b(int i) {
		double sum = 0;
		for (Item it : items) {
			sum += it.getWeight(i);
		}
		return sum;
	}


	private boolean[] randomizeSolution() {
		int elementNumber;
		boolean solution[] = new boolean[itemsAmount];
		for (int i = 0; i < itemsAmount; i++) {
			elementNumber = (int) (itemsAmount * Math.random());
			solution[elementNumber] = true;
			if (!checkSolution(solution)) {
				solution[elementNumber] = false;
				break;
			}
		}
		return solution;
	}

	public void run() {
		for (int nIteration = 1; nIteration <= maxIterations; nIteration++) {
			//
		}
	}

	public boolean[] getSolution() {
		return solution;
	}
}
