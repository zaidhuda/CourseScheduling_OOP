package courseschedule.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TimePicker extends JPanel {
	private int ROW = 2;
	private int COL = 6;
	private int SIZE = 94;
	private int day = -1;
	private int time = -1;
	private int[][] slots = null;
	//

	private boolean[][] availability;
	private boolean[][] oriAvailability;
	private boolean selectMany;
	private boolean hasConflict = false;
	private boolean obeyConflict = true;

	private SButton[] button = null;

	private ButtonListener bl = new ButtonListener();

	private CustomFont font = new CustomFont();

	public TimePicker(boolean[][] availability) {
		this(-1, -1, availability, null);
	}

	public TimePicker(int day, int time, int[][] slots) {
		this(day, time, null, slots);
	}

	public void reset(int day, int time, int[][] slots) {
		this.day = day;
		this.time = time;

		selectMany = false;

		this.slots[0] = Arrays.copyOf(slots[0], COL);
		this.slots[1] = Arrays.copyOf(slots[1], COL);
		removeAll();
		drawTable();
	}

	public TimePicker(int day, int time, boolean[][] availability, int[][] slots) {
		this.day = day;
		this.time = time;

		selectMany = availability != null;

		if (selectMany) {
			this.availability = new boolean[ROW][COL];
			this.availability[0] = Arrays.copyOf(availability[0], COL);
			this.availability[1] = Arrays.copyOf(availability[1], COL);
			oriAvailability = availability;
		} else {
			this.slots = new int[ROW][COL];
			this.slots[0] = Arrays.copyOf(slots[0], COL);
			this.slots[1] = Arrays.copyOf(slots[1], COL);
		}

		drawTable();
	}

	public void drawTable() {

		button = new SButton[ROW * COL];
		String[] str = {"<html><p align=right>MONDAY<br>WEDNESDAY</p></html>", "<html><p align=right>TUESDAY<br>THURSDAY</p></html>", "0830", "1000", "1130", "1400", "1530", "1700"};

		JPanel rowPane = new JPanel();
		rowPane.setLayout(new BoxLayout(rowPane, BoxLayout.LINE_AXIS));
		rowPane.add(Box.createRigidArea(new Dimension((SIZE - 25), 0)));
		for (int j = 0; j < COL; j++) {
			JLabel timeLabel = new JLabel(str[j + 2]);
			timeLabel.setPreferredSize(new Dimension(SIZE, (SIZE - 65)));
			timeLabel.setMaximumSize(timeLabel.getPreferredSize());
			timeLabel.setMinimumSize(timeLabel.getPreferredSize());
			timeLabel.setVerticalAlignment(JLabel.BOTTOM);
			timeLabel.setFont(font.getFontAbel(18, -0.05));
			timeLabel.setForeground(CustomColour.silver);
			rowPane.add(timeLabel);
			if (j == 2)
				rowPane.add(Box.createRigidArea(new Dimension(30, 0)));
			else
				rowPane.add(Box.createRigidArea(new Dimension(3, 0)));
		}
		rowPane.add(Box.createRigidArea(new Dimension((SIZE - 25), 0)));
		add(rowPane);

		for (int i = 0; i < ROW; ++i) {
			rowPane = new JPanel();
			rowPane.setLayout(new BoxLayout(rowPane, BoxLayout.LINE_AXIS));
			JLabel dayLabel = new JLabel(str[i]);
			dayLabel.setPreferredSize(new Dimension((SIZE - 25), SIZE));
			dayLabel.setMaximumSize(dayLabel.getPreferredSize());
			dayLabel.setMinimumSize(dayLabel.getPreferredSize());
			dayLabel.setVerticalAlignment(JLabel.BOTTOM);
			dayLabel.setVerticalTextPosition(JLabel.BOTTOM);
			dayLabel.setForeground(CustomColour.silver);
			// dayLabel.setFont(font.getFontAbel(15)); //SLOWS DOWN PERFORMANCE
			rowPane.add(dayLabel);

			for (int j = 0; j < COL; ++j) {
				int k = (i * COL) + j;
				button[k] = new SButton();
				button[k].putClientProperty("index", k);
				button[k].addActionListener(bl);

				if (selectMany) {
					setColor(button[k], availability[i][j] ? 0 : 3);
				} else {
					setColor(button[k], slots[i][j]);
				}
				button[k].putClientProperty("original", false);

				rowPane.add(button[k]);

				if (j == 2)
					rowPane.add(Box.createRigidArea(new Dimension(30, 0)));
				else
					rowPane.add(Box.createRigidArea(new Dimension(3, 0)));
			}
			rowPane.add(Box.createRigidArea(dayLabel.getPreferredSize()));
			add(rowPane);
			add(Box.createRigidArea(new Dimension(0, 3)));
		}

		if (!selectMany && (day != -1 && time != -1)) {
			button[(day * COL) + time].putClientProperty("original", true);
			button[(day * COL) + time].setBackground(Color.green);
			button[(day * COL) + time].setEnabled(false);
		}

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}

	private void setColor(JButton btn, int i) {
		btn.setEnabled(true);
		switch (i) {
			case 0:
				btn.setBackground(Color.white);
				btn.setText("YES");
				break;
			case 1:
				btn.setBackground(Color.orange);
				btn.setText("NO");
				break;
			case 2:
				btn.setBackground(Color.cyan);
				btn.setText("NO");
				break;
			case 3:
				btn.setBackground(CustomColour.silvergray);
				btn.setText("CLASS");
				break;
		}
		btn.setForeground(Color.GRAY.brighter());
		btn.putClientProperty("availability", i);
	}

	public void setSPACING(int newSpacing) {
		SIZE = newSpacing;
	}

	private class SButton extends JButton {
		public SButton() {
			setFocusPainted(false);
			setBorderPainted(false);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(SIZE, SIZE);
		}

		@Override
		public Dimension getMinimumSize() {
			return getPreferredSize();
		}

		@Override
		public Dimension getMaximumSize() {
			return getPreferredSize();
		}
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton currentButton = (JButton) e.getSource();
			final int index = (int) currentButton.getClientProperty("index");
			day = (index < COL) ? 0 : 1;
			time = index % COL;

			if (selectMany) {
				Color btnBack = currentButton.getBackground();
				if (btnBack.equals(Color.white)) {
					if ((int) currentButton.getClientProperty("availability") == 0)
						setColor(currentButton, 1);
					else
						setColor(currentButton, 3);
					availability[day][time] = !availability[day][time];
				} else
					if (btnBack.equals(Color.orange)) {
						setColor(currentButton, 0);
						availability[day][time] = !availability[day][time];
					} else
						if (obeyConflict()) {
							currentButton.setEnabled(true);
							currentButton.setBackground(Color.white);
							availability[day][time] = !availability[day][time];
						}
			} else
				if (obeyConflict()) {
					for (JButton btn : button) {
						if ((boolean) btn.getClientProperty("original")) {
							btn.setBackground(Color.darkGray);
							btn.setEnabled(true);
						} else {
							setColor(btn, (int) btn.getClientProperty("availability"));
						}
					}
					currentButton.setBackground(Color.green);
					currentButton.setEnabled(false);
				}
		}
	}

	private boolean obeyConflict() {
		hasConflict = false;
		int avail = (int) button[(day * COL) + time].getClientProperty("availability");
		if (avail != 0 && !(boolean) button[(day * COL) + time].getClientProperty("original")) {
			String note = null;
			switch (avail) {
				case 1:
					note = "Venue is in use by other section. Re-Schedule the section?";
					break;
				case 2:
					note = "Lecturer is having another section. Re-Schedule the section?";
					break;
				case 3:
					note = "A Section is set here. Re-Schedule the section?";
					break;
			}
			if (obeyConflict) {
				hasConflict = JOptionPane.showConfirmDialog(null, note, "Warning", JOptionPane.YES_OPTION) == 0;
				return hasConflict;
			}
		}
		hasConflict = false;
		return true;
	}

	public void obeyConflict(boolean obey) {
		obeyConflict = obey;
	}

	public int getDay() {
		return day;
	}

	public int getTime() {
		return time;
	}

	public boolean[][] getAvailability() {
		return availability;
	}

	public boolean[][] getConflict() {
		boolean[][] conflicts = new boolean[ROW][COL];
		for (int i = 0; i < ROW; i++) {
			//Arrays.fill(conflicts[i], true);
			for (int j = 0; j < COL; j++) {
				if (!oriAvailability[i][j] && availability[i][j] != oriAvailability[i][j]) {
					conflicts[i][j] = true;
				}
			}
		}
		return conflicts;
	}

	public boolean hasConflict() {
		return hasConflict;
	}

	public void setSize(int size) {
		if (size >= 30 && size < 150) {
			this.SIZE = size;
			for (JButton btn : button)
				btn.setPreferredSize(new Dimension(SIZE, SIZE));
		}
	}
}