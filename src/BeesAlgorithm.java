import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeesAlgorithm {
	private Knapsack knapsack;
	private Item[] items;
	private boolean solution[];
	private int dimensions;
	private int itemsAmount;
	private int df[];
	private double probabilityOfElement[];
	private Map<Integer, Integer> solutionsCount = new HashMap<Integer, Integer>();

	// consts
	int nBee = 600; // scoutBees
	int nSite = 100; // sites
	// int nBest=10;
	int ngh = 5; // initial size of neighbourhood to search (size of patch)
	// patch includes Site
	int nep = 40;// number of bees recruted for the selected sites

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

	public BeesAlgorithm(Knapsack knapsack, Item[] items, int maxIterations) {
		dimensions = knapsack.getDimension();
		itemsAmount = items.length;
		this.knapsack = knapsack;
		this.items = items;
		this.solution = new boolean[itemsAmount];
		probabilityOfElement = new double[itemsAmount];
		df = new int[itemsAmount];
		this.maxIterations = maxIterations;
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

	private double[] evaluateProbability(double alpha, double beta, double gamma) {
		double maxC = maxC();
		double maxSPrim = maxSPrim();
		double fj = 1.0f;
		double maxDF = 1.0f;
		double DUZA_SUMA = 2.0f;
		double probability[] = new double[itemsAmount];
		for (int j = 0; j < itemsAmount; j++)
			probability[j] = (double) (fj * Math.pow(maxDF / df[j], gamma)
					* Math.pow(items[j].getValue() / maxC, alpha) / Math.pow(
					DUZA_SUMA / maxSPrim, beta));
		return probability;
	}

	private double maxSPrim() {
		return 0.0f;//TODO
	}

	private double maxC() {
		double maximum = items[0].getValue();
		for (int i = 1; i < itemsAmount; i++)
			if (items[i].getValue() > maximum)
				maximum = items[i].getValue();
		return maximum;
	}

	// increment number of visited solution
	private void countSolution(boolean[] solution) {
		Integer hashCode = Arrays.hashCode(solution);
		Integer count = solutionsCount.containsKey(hashCode) ? solutionsCount
				.get(hashCode) : 0;
		solutionsCount.put(hashCode, ++count);
		for(int i=0;i<solution.length;++i) {
			df[i] += solution[i] ? 1 : 0;
		}
	}
	
	private Set<Integer> evaluateJk(Set<Integer> Jx) {
		
		return null;
	}

	// może jednak trochę zachłana?
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
