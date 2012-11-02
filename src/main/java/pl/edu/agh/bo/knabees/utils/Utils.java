package pl.edu.agh.bo.knabees.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class Utils {
	private static final org.apache.log4j.Logger LOG = Logger.getLogger(Utils.class);

	public static <T> void removeSelection(DefaultListModel<T> listModel, int[] selectedIndices) {
		int[] selectedIndicesAscending = selectedIndices;
		Arrays.sort(selectedIndicesAscending);
		for (int i = selectedIndicesAscending.length - 1; i >= 0; --i) {
			listModel.removeElementAt(selectedIndicesAscending[i]);
		}
	}

	public static List<List<Integer>> readNumbers(File file) {
		Path path = file.toPath();
		LOG.log(Priority.INFO, "Opening: " + path.getFileName() + ".");
		List<List<Integer>> lines = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty()) {
					continue;
				}
				List<Integer> lineNumbers = new ArrayList<>();
				for (String token : line.split("\\s+")) {
					try {
						lineNumbers.add(Integer.parseInt(token));
					} catch (NumberFormatException e) {
						LOG.log(Priority.INFO, "Incorrect input: " + token);
					}
				}
				lines.add(lineNumbers);
			}
		} catch (IOException x) {
			LOG.log(Priority.ERROR, "IOException: %s%n", x);
		}

		return lines;
	}
}
