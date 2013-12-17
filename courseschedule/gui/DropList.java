package courseschedule.gui;

import courseschedule.*;
import courseschedule.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Created by Diaz
 * Package: courseschedule.gui
 * Time: 5:59 PM
 * Date: 12/15/13.
 */
public class DropList extends JPanel {
	protected ArrayList<TableButton> list = new ArrayList<>();
	protected ArrayList<String> lCourses = new ArrayList<>();
	protected ScheduleBuilder sb;
	protected String[][] label;
	protected int limit;
	protected FieldButton theInvoker = new FieldButton("");
	protected JPanel theContainer;
	protected TimePicker theTimePicker;
	protected Lecturer lecturer;
	protected Venue venue;
	protected String name = "";
	private JScrollPane scrollPanel;

	public DropList(ScheduleBuilder sb, ArrayList<String> lCourses, int limit) {
		this.sb = sb;
		this.lCourses = lCourses;
		this.limit = (limit != -1) ? limit : sb.courses.size() + 1;
		label = sb.getCodeandTitle();
		if (label.length > 0)
			makeParts();
	}

	public DropList(ScheduleBuilder sb, Course course, Lecturer lecturer, int limit) {
		this.sb = sb;
		this.lecturer = lecturer;
		name = lecturer.getName();
		this.limit = (limit != -1) ? limit : sb.courses.size();
		ArrayList<Lecturer> lecturers = sb.getAssignedLecturers(course);
		String[][] str = new String[lecturers.size()][];
		for (int i = 0; i < lecturers.size(); i++) {
			String[] arr = lecturers.get(i).detailsArray();
			str[i] = arr;
		}
		label = str;
		if (label.length > 0)
			makeParts();
	}

	public DropList(ScheduleBuilder sb, Course course, Venue venue, int limit) {
		this.sb = sb;
		this.venue = venue;
		name = venue.getName();
		this.limit = (limit != -1) ? limit : sb.courses.size();
		ArrayList<Venue> venues = sb.getAssignedVenues(course);
		String[][] str = new String[venues.size()][];
		for (int i = 0; i < venues.size(); i++) {
			String[] arr = venues.get(i).detailsArray();
			str[i] = arr;
		}
		label = str;
		if (label.length > 0)
			makeParts();
	}

	public void makeParts() {
		setLayout(new BorderLayout());
		JPanel middlePanel = new JPanel();
		SpringLayout spring = new SpringLayout();
		middlePanel.setLayout(spring);
		JPanel containerRow = new JPanel();
		middlePanel.add(containerRow);

		add(middlePanel, BorderLayout.CENTER);

		JPanel row = new JPanel();
		scrollPanel = new JScrollPane(row);
		scrollPanel.setBorder(BorderFactory.createEmptyBorder());
		scrollPanel.setPreferredSize(new Dimension(604, 250));
		scrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI(1));
		scrollPanel.setHorizontalScrollBar(null);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(16);

		OffsetFinder of = new OffsetFinder(label, scrollPanel);

		row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
		for (int i = 0; i < label.length; i++) {
			list.add(new TableButton(label[i], of));
			if (lCourses.contains(label[i][0]) || name.equals(label[i][0])) {
				list.get(i).setColor(CustomColour.silvergray);
			}
			if (limit != 1)
				list.get(i).addActionListener(new ButtonListener());
			else
				list.get(i).addActionListener(new SingleButtonListener());
			row.add(list.get(i));
		}

		containerRow.add(scrollPanel);
		add(containerRow, BorderLayout.NORTH);
	}

	public void setPreferredSize(int newHeight){
		scrollPanel.setPreferredSize(new Dimension(604, newHeight));
	}

	public void setButton(FieldButton btn) {
		this.theInvoker = btn;
	}

	public void setTheContainer(JPanel theContainer) {
		this.theContainer = theContainer;
	}

	public void setTheTimePicker(TimePicker theTimePicker) {
		this.theTimePicker = theTimePicker;
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < list.size(); i++)
				if (e.getSource() == list.get(i)) {
					String code = label[i][0];
					if (lCourses.contains(code)) {
						lCourses.remove(code);
						list.get(i).setColor(list.get(i).getBackground());
					} else
						if (lCourses.size() < limit) {
							lCourses.add(code);
							list.get(i).setColor(CustomColour.silvergray);
						}
					Collections.sort(lCourses);
					theInvoker.setText(lCourses.toString().replaceAll("[\\[\\]]", ""));
				}
		}
	}

	private class SingleButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (TableButton btn : list) {
				btn.setColor(CustomColour.silverclouds);
			}
			TableButton btn = (TableButton) e.getSource();
			btn.setColor(CustomColour.silvergray);
			name = label[list.indexOf(btn)][0];
			theInvoker.setText(name);
		}
	}
}
