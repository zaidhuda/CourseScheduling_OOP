package courseschedule.gui;

import courseschedule.*;
import courseschedule.gui.*;
import courseschedule.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.*;

public class CoursesList extends JPanel {
	private ArrayList<TableButton> list = new ArrayList<>();
	private ArrayList<String> lCourses = new ArrayList<>();
	private ScheduleBuilder sb;

	private final JFrame frame = new JFrame();
	private String[][] label;

	private String codes;
	int limit;

	public CoursesList(ScheduleBuilder sb, ArrayList<String> lCourses, int limit) {
		this.sb = sb;
		this.lCourses = lCourses;
		this.limit = limit;
		setLayout(new BorderLayout());
		JPanel middlePanel = new JPanel();
		SpringLayout spring = new SpringLayout();
		middlePanel.setLayout(spring);
		JPanel containerRow = new JPanel();
		middlePanel.add(containerRow);

		add(middlePanel, BorderLayout.CENTER);

		JPanel row = new JPanel();
		JScrollPane scrollPanel = new JScrollPane(row);
		scrollPanel.setPreferredSize(new Dimension(604, 250));
		scrollPanel.setBorder(BorderFactory.createEmptyBorder());
		scrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI(1));
		scrollPanel.setHorizontalScrollBar(null);

		label = sb.getCodeandTitle();
		OffsetFinder of = new OffsetFinder(label, scrollPanel);

		row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
		for(int i=0; i<label.length; i++) {
			list.add(new TableButton(label[i], of.getOffset()));
			if (lCourses.contains(label[i][0])){
				list.get(i).setBackground(Color.darkGray);
			}
			list.get(i).addActionListener(new ButtonListener());
			row.add(list.get(i));
		}

		containerRow.add(scrollPanel);
		add(containerRow, BorderLayout.NORTH);
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<list.size(); i++)
				if(e.getSource() == list.get(i)) {
					String code = label[i][0];
					if(lCourses.contains(code)){
						lCourses.remove(code);
					} else {
						lCourses.add(code);
					}
				}
		}
	}

	public ArrayList<Course> getCourses(){
		Collections.sort(lCourses);
		String[] c = lCourses.toString().replaceAll("\\[", "").replaceAll("]", "").split(", ");
		return sb.getCourses(c);
	}
}