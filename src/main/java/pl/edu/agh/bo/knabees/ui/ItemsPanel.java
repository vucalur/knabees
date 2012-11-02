package pl.edu.agh.bo.knabees.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import pl.edu.agh.bo.knabees.objects.Item;
import pl.edu.agh.bo.knabees.utils.Utils;

@SuppressWarnings("serial")
public class ItemsPanel extends JPanel implements Clearable {
	private static final org.apache.log4j.Logger LOG = Logger.getLogger(ItemsPanel.class);

	private JTextField fileStatusField;
	private JFileChooser fileChooser;
	private JButton openFileButton;
	private JList<String> list;
	private DefaultListModel<String> listModel;

	ItemsPanel() {
		initilize();
	}

	private void initilize() {
		setLayout(null);
		JPanel openFilePanel = new JPanel();
		openFilePanel.setBounds(12, 12, 848, 59);
		add(openFilePanel);
		openFilePanel.setLayout(null);

		fileStatusField = new JTextField();
		fileStatusField.setText(Messages.getString("KnabeesGUI.textField.text")); //$NON-NLS-1$
		fileStatusField.setBounds(567, 7, 253, 29);
		openFilePanel.add(fileStatusField);
		fileStatusField.setColumns(10);

		openFileButton = new JButton("Load items");
		openFileButton.setToolTipText("<html>File should be a plain text file, containing lines describing items:<br/>"
				+ "2 positive integers separated with whitespace - item value and size respectively</html>");
		openFileButton.setBounds(12, 8, 168, 27);
		fileChooser = new JFileChooser();
		openFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == openFileButton) {
					int returnVal = fileChooser.showOpenDialog(ItemsPanel.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {

						for (List<Integer> line : Utils.readNumbers(fileChooser.getSelectedFile())) {
							if (line.size() < 2) {
								continue;
							}

							if (line.get(0) <= 0 || line.get(1) <= 0) {
								LOG.log(Priority.WARN, "Not possitive interers will be ignored!");
							} else {
								listModel.addElement(new Item(line.get(0), line.get(1)).toString());
							}
						}

					} else {
						LOG.log(Priority.INFO, "Open command cancelled by user.");
					}
				}
			}
		});
		openFilePanel.add(openFileButton);

		JPanel loadedPanel = new JPanel();
		loadedPanel.setBounds(22, 83, 838, 407);
		add(loadedPanel);
		loadedPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 12, 814, 290);
		loadedPanel.add(scrollPane);

		list = new JList<String>();
		listModel = new javax.swing.DefaultListModel<String>();
		list.setModel(listModel);
		scrollPane.setViewportView(list);

		JButton removeSelected = new JButton("Remove selected");
		removeSelected.setBounds(12, 326, 171, 27);
		removeSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Utils.removeSelection(listModel, list.getSelectedIndices());
			}
		});
		loadedPanel.add(removeSelected);
	}

	@Override
	public void clearData() {
		listModel.clear();
	}

	public List<Item> getItems() {
		List<Item> result = new ArrayList<>();
		for (int i = 0; i < listModel.getSize(); ++i) {
			result.add(Item.parseItem(listModel.get(i)));
		}
		return result;
	}
}
