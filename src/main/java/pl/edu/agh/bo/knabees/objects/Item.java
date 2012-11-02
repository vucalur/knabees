package pl.edu.agh.bo.knabees.objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class Item {
	private static final org.apache.log4j.Logger LOG = Logger.getLogger(Item.class);

	private static final Pattern STRING_REPRESENTATION_PATTERN = Pattern.compile("^value\\:\\s+(\\d+)\\,\\s+size\\:\\s+(\\d+)");

	private final int value;
	private final int size;

	public Item(int value, int size) {
		this.value = value;
		this.size = size;
	}

	public Item(String value, String size) throws NumberFormatException {
		this(Integer.parseInt(value), Integer.parseInt(size));
	}

	public int getValue() {
		return value;
	}

	public int getSize() {
		return size;
	}

	public static Item parseItem(String textForm) {
		textForm = textForm.trim();
		Matcher matcher = STRING_REPRESENTATION_PATTERN.matcher(textForm);
		if (!matcher.matches()) {
			throw new IllegalArgumentException(textForm + " doesn't match the pattern: " + STRING_REPRESENTATION_PATTERN);
		}
		return new Item(matcher.group(1), matcher.group(2));
	}

	@Override
	public String toString() {
		return "value: " + value + ", " + "size: " + size;
	}
}
