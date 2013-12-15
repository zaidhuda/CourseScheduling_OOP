package courseschedule.gui;

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
	protected FieldButton btn = new FieldButton("");

	public DropList(ScheduleBuilder sb, ArrayList<String> lCourses, int limit) {
		this.sb = sb;
		this.lCourses = lCourses;
		this.limit = (limit != -1)? limit : sb.courses.size();
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
			if (this.lCourses.contains(label[i][0])){
				list.get(i).setBackground(CustomColour.silvergray);
			}
			list.get(i).addActionListener(new ButtonListener());
			row.add(list.get(i));
		}

		containerRow.add(scrollPanel);
		add(containerRow, BorderLayout.NORTH);
	}

	public void setButton(FieldButton btn){
		this.btn = btn;
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<list.size(); i++)
				if(e.getSource() == list.get(i)) {
					String code = label[i][0];
					if(lCourses.contains(code)){
						lCourses.remove(code);
						list.get(i).setBackground(CustomColour.silverclouds);
					} else if(lCourses.size() < limit) {
						lCourses.add(code);
						list.get(i).setBackground(CustomColour.silvergray);
					}
					Collections.sort(lCourses);
					btn.setLabel(lCourses.toString().replaceAll("[\\[\\]]",""));
				}
		}
	}
}
