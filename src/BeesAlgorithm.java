import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class BeesAlgorithm {
	private Knapsack knapsack;
	private Item[] items;
	private boolean solution[];
	private int dimensions;
	private int itemsAmount;
	// private Map<Integer, Integer> solutionsCount = new HashMap<Integer,
	// Integer>();

	// consts
	int nBee = 30; // scoutBees
	int nSite = 5; // sites
	int nBest = 2;
	int ngh = 2; // initial size of neighbourhood to search (size of patch)
	// patch includes Site
	int nep = 2;// number of bees recruited for the selected sites

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

	Random rand;

	private int df(int j) {
		return visitedCount[j];
	}

	public BeesAlgorithm(Knapsack knapsack, Item[] items, int maxIterations) {
		dimensions = knapsack.getDimension();
		itemsAmount = items.length;
		this.knapsack = knapsack;
		this.items = items;
//		this.maxIterations = maxIterations;//TODO
		this.maxIterations = 20;
		visitedCount = new int[itemsAmount];
		rand = new Random();
		rand.setSeed((long) (Math.random() * 1000));

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

	private double evaluateProbability(int j, double[] fj, double alpha,
			double beta, double gamma) {
		double maxC = maxC();
		double maxSPrim = maxSPrim();
		double maxDF = maxDF();
		double duzaSuma = 0.0;

		for (int i = 0; i < dimensions; i++) {
			duzaSuma += (items[j].getWeight(i) / (b(i) * b(i)));
		}
		double test=
		/*return*/ (double) (fj[j] * Math.pow(maxDF / df(j), gamma)
				* Math.pow(items[j].getValue() / maxC, alpha) / Math.pow(
				duzaSuma / maxSPrim, beta));
//		System.out.println(test);
//		return test;
		return 1;//TODO
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

	private double maxC() { //TODO
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

	public int rws(double[] fj, boolean items[], double alpha, double beta, double gamma) {
		double probabilitySum = 0, p = 0;
		double randomNumber;
		for (int i = 0; i < itemsAmount; i++)
			probabilitySum += items[i] ? evaluateProbability(i, fj, alpha,
					beta, gamma) : 0;
		randomNumber = Math.random() * probabilitySum;
		//System.out.println(probabilitySum);
		for (int i = 0; i < itemsAmount; i++) {
			p += items[i] ? evaluateProbability(i, fj, alpha, beta, gamma) : 0;
			
			if (p >= randomNumber)
				return i;
		}
		return -1;
	}

	public int rws2(double[] fj, boolean items[], double alpha, double beta, double gamma) {
		double probabilitySum = 0, p = 0;
		double randomNumber;
		for (int i = 0; i < itemsAmount; i++)
			probabilitySum += items[i] ? 1.0/evaluateProbability(i, fj, alpha,
					beta, gamma) : 0;
		randomNumber = Math.random() * probabilitySum;
		for (int i = 0; i < itemsAmount; i++) {
			p += items[i] ? 1.0 / evaluateProbability(i, fj, alpha, beta, gamma)
					: 0;
			if (p >= randomNumber)
				return i;
		}
		return -1;
	}

	private boolean setNotEmpty(boolean S[]) {
		for (boolean b : S) {
			if (b)
				return true;
		}
		return false;
	}

	private boolean[] safeSet(boolean S[]) {
		boolean[] retSet = new boolean[itemsAmount];
		boolean[] tempSet = Arrays.copyOf(S, S.length);
		for (int i = 0; i < itemsAmount; i++) {
			if(!tempSet[i]) {
				tempSet[i] = true;
				if (checkSolution(tempSet)) {
					retSet[i] = true;
				}
				tempSet[i] = false;
			}
		}
		return retSet;
	}

	public boolean[] generateSolution() {
		double[] fj = new double[itemsAmount];
		boolean x[] = new boolean[itemsAmount];
		boolean S[] = new boolean[itemsAmount];
		int t;
		for (int i = 0; i < itemsAmount; i++) {
			if (rand.nextInt() % 3 == 0)
				fj[i] = probability0;
			else
				fj[i] = probability1;
		}
		S = safeSet(x);
		while (setNotEmpty(S)) {
			t = rws(fj, S, alpha, beta, gamma);
			x[t] = true;
			S = safeSet(x);
		}
		return x;
	}

	boolean[] evaluateJk(boolean[] Jx, int k) {
		boolean[] S = Arrays.copyOf(Jx, itemsAmount);
		double fj[] = new double[itemsAmount];
		for (int i = 0; i < itemsAmount; i++) {
			fj[i] = 1;
		}
		boolean Jk[] = new boolean[itemsAmount];
		int t;
		while (powerOfSet(Jk) < k) {
			t = rws2(fj, S, alphaBis, betaBis, gammaBis);
			if(t==-1)
				break;
			Jk[t] = true;
			S[t] = false;
		}
		return Jk;

	}

	private int powerOfSet(boolean[] S) {
		int count = 0;
		for (int i = 0; i < itemsAmount; i++)
			if (S[i])
				count++;
		return count;
	}

	public boolean[] solutionFromNeighbourhood(boolean[] solutionJx, int k) {
		double pj[] = new double[itemsAmount];
		double pjprim[] = new double[itemsAmount];
		double prob0prim[] = new double[itemsAmount];
		double prob1prim[] = new double[itemsAmount];
		for (int i = 0; i < itemsAmount; i++) {
			prob0prim[i] = probability0Prim;
			prob1prim[i] = probability1Prim;
		}

		boolean[] x = Arrays.copyOf(solutionJx, itemsAmount);
		boolean[] Jk = evaluateJk(solutionJx, k);
		for (int i = 0; i < itemsAmount; i++)
			if (Jk[i])
				x[i] = false;

		/*for (int j = 0; j < itemsAmount; j++) {
			if (Jk[j])
				pj[j] = evaluateProbability(j, prob0prim, alphaPrim, betaPrim,
						gammaPrim);
			else
				pjprim[j] = evaluateProbability(j, prob1prim, alphaPrim,
						betaPrim, gammaPrim);
		}*/

		boolean[] S = safeSet(x);
		while (setNotEmpty(S)) {
			int t = rws(prob0prim, S, alphaPrim, betaPrim, gammaPrim);
			x[t] = true;
			if(!checkSolution(x)) throw new RuntimeException();
			S = safeSet(x);
		}
		return x;

	}

	public void run() {
		ArrayList<boolean[]> solutions = new ArrayList<>();

		// initial solution (random)
		for (int i = 0; i < nBee; i++) {
			solutions.add(randomizeSolution());
		}
		
		for (int nIteration = 1; nIteration <= maxIterations; nIteration++) {
			Collections.sort(solutions, new Comparator<boolean[]>() {
				@Override
				public int compare(boolean[] s1, boolean[] s2) {
					double c = itemsValue(s1) - itemsValue(s2);
					return c>0?-1:(c<0?1:0);
				}
			});
			System.out.print("Posortowane:");
			for(int f=0;f<solutions.size();++f)
				System.out.print(" " + itemsValue(solutions.get(f)));
			System.out.println();
			ArrayList<boolean[]> newSolutions = new ArrayList<boolean[]>();
			if(solution == null || itemsValue(solutions.get(0))>itemsValue(solution))
				solution = solutions.get(0);

			for(int i=0;i<nSite;++i) {
				newSolutions.add(solutions.get(i));
				for(int j=0;j<nep;++j) {
					newSolutions.add(solutionFromNeighbourhood(solutions.get(i), ngh));
				}
			}//maybe we should delete identical solutions from solution list, shouldn't we?
			for(int i=0;i<nBee-nSite*nep;++i) {
				newSolutions.add(generateSolution());				
			}
			solutions = newSolutions;
			System.out.println(nIteration);
		}
	}

	public boolean[] getSolution() {
		return solution;
	}

}
