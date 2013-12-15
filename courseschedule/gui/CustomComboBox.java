package courseschedule.gui;

import javax.swing.*;
import java.awt.*;

public class CustomComboBox extends JComboBox {
	private CustomFont font = new CustomFont();

	public CustomComboBox(String[] s) {
		super(s);
		setRenderer(new ComboBoxRenderer());
		setBackground(CustomColour.silvergray);
		setForeground(CustomColour.silverclouds);
		setFont(font.getFontPTSans(15, -0.05));
		setMaximumRowCount(3);
	}

	public class ComboBoxRenderer extends JLabel implements ListCellRenderer {
		// private JLabel label = new JLabel();

		// public ComboBoxRenderer() {
		// 	JPanel panel = new JPanel();
		// 	panel.add(label);
		// 	add(panel);
		// }

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			// String[] item = (String[]) value;
			// label.setText(item[0]);

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			return this;
		}
	}
}