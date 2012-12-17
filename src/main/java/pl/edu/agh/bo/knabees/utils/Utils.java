package pl.edu.agh.bo.knabees.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.DefaultListModel;

import org.apache.log4j.Logger;

import pl.edu.agh.bo.knabees.alg.BeesAlgorithm;
import pl.edu.agh.bo.knabees.communication.FileParsingException;
import pl.edu.agh.bo.knabees.objects.Item;
import pl.edu.agh.bo.knabees.objects.Knapsack;

public class Utils {
	private static final org.apache.log4j.Logger logger = Logger.getLogger(Utils.class);

	public static <T> void removeSelection(DefaultListModel<T> listModel, int[] selectedIndices) {
		int[] selectedIndicesAscending = selectedIndices;
		Arrays.sort(selectedIndicesAscending);
		for (int i = selectedIndicesAscending.length - 1; i >= 0; --i) {
			listModel.removeElementAt(selectedIndicesAscending[i]);
		}
	}

	public static BeesAlgorithm.Builder readItemsAndKnapsackData(File file) throws FileParsingException {
		logger.info("Opening: " + file.getPath() + ".");

		try {
			return readItemsAndKnapsackData(new Scanner(file));
		} catch (FileNotFoundException e) {
			throw new FileParsingException();
		}
	}

	public static BeesAlgorithm.Builder readItemsAndKnapsackData(Scanner s) throws FileParsingException {
		BeesAlgorithm.Builder result;
		try {
			int m = s.nextInt();
			int n = s.nextInt();
			double[] limits = new double[m];
			for (int i = 0; i < m; ++i)
				limits[i] = s.nextDouble();
			Item[] items = new Item[n];
			result = new BeesAlgorithm.Builder(new Knapsack(limits), items);
			for (int j = 0; j < n; ++j) {
				double price = s.nextDouble();
				double[] weights = new double[m];
				for (int i = 0; i < m; ++i)
					weights[i] = s.nextDouble();
				items[j] = new Item(price, weights);
			}
		} catch (Exception e) {
			throw new FileParsingException();
		} finally {
			s.close();
		}
		return result;
	}
}
