import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Test {
	private	Item[] items;
	private Knapsack knapsack;

	public Test(Scanner sc) {
		if(!readInput(sc)) {
			System.out.println("Wrong input!");
			return;
		}

		BeesAlgorithm bA = new BeesAlgorithm(knapsack, items, 500);
		bA.run();
		boolean solution[] = bA.getSolution();

		int dim = knapsack.getDimension();

		System.out.println("Limits:");
		for(int i=0;i<dim;++i) {
			System.out.print(knapsack.getLimit(i)+"\t");
		}
		System.out.println("\nThe Choosen Ones:");
		double[] weights = new double[dim];
		double big_price = 0;
		for(int i=0;i<items.length;++i) {
			if(!solution[i]) continue;
			for(int j=0; j<dim; ++j) {
				System.out.print(items[i].getWeight(j)+"\t");
				weights[j]+=items[i].getWeight(j);
			}
			System.out.println("\t("+items[i].getValue()+")");
			big_price += items[i].getValue();
		}
		System.out.println("Overall:");
		for(int j=0;j<dim;++j) {
			System.out.print(weights[j]+"\t");
		}
		System.out.println("\t("+big_price+")");
	}

	public boolean readInput(Scanner s) {
		try {
			int m = s.nextInt();
			int n = s.nextInt();
			double[] limits = new double[m];
			for(int i=0; i<m; ++i)
				limits[i] = s.nextDouble();
			knapsack = new Knapsack(limits);
			items = new Item[n];
			for(int j=0; j<n; ++j) {
				double price = s.nextDouble();
				double[] weights = new double[m];
				for(int i=0; i<m; ++i)
					weights[i] = s.nextDouble();
				items[j] = new Item(price, weights);
			}
		} catch(Exception e) { return false; }
		return true;
	}

	public static void main(String[] args) {
		if(args.length > 0)
			try {
				new Test(new Scanner(new File(args[0])));
			} catch(IOException e) {
				System.out.println("IO Error");
			}
		else {
			new Test(new Scanner(System.in));
		}

/*		int dimensions = 5;
		int itemsNumber = 100;

		float limits[] = new float[dimensions];
		for (int i = 0; i < dimensions; i++) {
			limits[i] = (float) (5 * Math.random());
		}

		Item items[] = new Item[itemsNumber];
		for (int i = 0; i < itemsNumber; i++) {
			float weight[] = new float[dimensions];
			for (int j = 0; j < dimensions; j++)
				weight[j] = (float) (100 * Math.random());
			items[i] = new Item((float) (25 * Math.random()), weight);
		}

		Knapsack knapsack = new Knapsack(limits);
		BeesAlgorithm bA = new BeesAlgorithm(knapsack, items, 500);
		bA.run();
		boolean solution[] = bA.getSolution();
		// System.out.println("Test");
		int tablica1[][] = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		int tablica2[][] = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		System.out.println(tablica1.hashCode() + " " + tablica2.length);

		boolean x[]=new boolean[]{false, false, true,  true, false};
		boolean y[]=new boolean[]{true, false, true,  true, false};
		boolean z[]=new boolean[]{true, false, true,  true, false};
		
		Map<Integer, Integer> solutionsCount = new HashMap<Integer, Integer>();
		solutionsCount.put(Arrays.hashCode(x), 12);
		solutionsCount.put(Arrays.hashCode(y), 33);
		System.out.println(solutionsCount.get(Arrays.hashCode(z)));
	*/
	}

}
