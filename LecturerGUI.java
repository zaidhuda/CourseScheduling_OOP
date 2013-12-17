import courseschedule.*;
import courseschedule.gui.*;
import courseschedule.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LecturerGUI extends JPanel {
	// VARIABLES FOR GENERAL USE
	private CustomFont font = new CustomFont();
	private Frame frame = new Frame();

	// VARIABLES FOR TOP PANEL
	private JPanel topPanel;
	private JLabel textLabel;

	// VARIABLES FOR MID PANELS
	private final JPanel midLowerPanelContainer = new JPanel();
	CoursesList coursesLister = null;
	private JPanel middlePanel, midUpperPanel, midLeftPanel, midRightPanel;
	private TimePicker midLowerPanel;
	private Lecturer lecturer;

	// MIDLEFTPANEL'S 
	private CustomField[] mltextField = new CustomField[1];
	private CustomLabel[] mltextLabel = {new CustomLabel("LECTURER NAME")};

	// MIDRIGHTPANEL'S
	private FieldButton[] mrtextField = new FieldButton[1];
	private CustomLabel[] mrtextLabel = {new CustomLabel("SPECIALIZATION")};

	// VARIABLES FOR BOTTOM PANEL
	private JPanel bottomPanel;
	private RoundedButton backBtn, removeBtn, addBtn;

	public LecturerGUI(Lecturer lecturer) {
		this.lecturer = lecturer;
		setLayout(new BorderLayout());
	}

	public void createTopPanel() {
		topPanel = new JPanel();

		topPanel.setBackground(CustomColour.nightblue);
		topPanel.setPreferredSize(new Dimension(900, 70));
		topPanel.setMinimumSize(getPreferredSize());
		topPanel.setMaximumSize(getPreferredSize());

		textLabel = new JLabel("LECTURER");

		textLabel.setForeground(CustomColour.silverclouds);
		textLabel.setFont(font.getFontAbel(48));

		topPanel.add(textLabel);
		add(topPanel, BorderLayout.NORTH);
	}

	public void createMiddlePanel() {
		middlePanel = new JPanel();
		midUpperPanel = new JPanel();
		midLowerPanel = new TimePicker(lecturer.getAvailability());
		midLeftPanel = new JPanel();
		midRightPanel = new JPanel();

		midLeftPanel.setBackground(CustomColour.silverclouds);
		midLeftPanel.setLayout(new BoxLayout(midLeftPanel, BoxLayout.Y_AXIS));

		for (int i = 0; i < 1; i++) {
			mltextField[i] = new CustomField();
			mltextField[i].setText(lecturer.getName());
			midLeftPanel.add(mltextLabel[i]);
			midLeftPanel.add(mltextField[i]);
			midLeftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		midRightPanel.setBackground(CustomColour.silverclouds);
		midRightPanel.setLayout(new BoxLayout(midRightPanel, BoxLayout.Y_AXIS));

		for (int i = 0; i < 1; i++) {
			mrtextField[i] = new FieldButton(lecturer.detailsArray()[1]);
			//mrtextField[i].setText(lecturer.detailsArray()[1]);
			if (Frame.sb.getCourses().length > 0) {
				coursesLister.setButton(mrtextField[0]);
				mrtextField[i].addActionListener(new ButtonListener());
			}
			midRightPanel.add(mrtextLabel[i]);
			midRightPanel.add(mrtextField[i]);
			midRightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		midUpperPanel.setBackground(CustomColour.silverclouds);
		midUpperPanel.setLayout(new BoxLayout(midUpperPanel, BoxLayout.X_AXIS));
		midUpperPanel.setAlignmentX(CENTER_ALIGNMENT);
		midUpperPanel.add(midLeftPanel);
		midUpperPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		midUpperPanel.add(midRightPanel);

		midLowerPanel.setAlignmentX(CENTER_ALIGNMENT);

		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.PAGE_AXIS));
		middlePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		middlePanel.add(midUpperPanel);

		midLowerPanelContainer.add(midLowerPanel);
		if (coursesLister != null) {
			midLowerPanelContainer.add(coursesLister);
			coursesLister.setVisible(false);
		}
		middlePanel.add(midLowerPanelContainer);

		add(middlePanel, BorderLayout.CENTER);
	}

	public void createBottomPanel() {
		bottomPanel = new JPanel();
		backBtn = new RoundedButton("BACK", 0);
		removeBtn = new RoundedButton("REMOVE", 0);
		addBtn = new RoundedButton("SAVE", 1);

		backBtn.setFont(font.getFontPTSans(15, Font.BOLD, -0.07));
		backBtn.addActionListener(new ButtonListener());

		removeBtn.setFont(font.getFontPTSans(15, Font.BOLD, -0.07));
		removeBtn.addActionListener(new ButtonListener());

		addBtn.setFont(font.getFontPTSans(15, Font.BOLD, -0.07));
		addBtn.addActionListener(new ButtonListener());

		bottomPanel.add(backBtn);
		bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		bottomPanel.add(removeBtn);
		bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		bottomPanel.add(addBtn);
		bottomPanel.add(Box.createRigidArea(new Dimension(0, 75)));
		add(bottomPanel, BorderLayout.SOUTH);
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mrtextField[0]){
				if (coursesLister.isVisible()) {
					midLowerPanel.setVisible(true);
					coursesLister.setVisible(false);
				} else {
					midLowerPanel.setVisible(false);
					coursesLister.setVisible(true);
				}
				midLowerPanelContainer.revalidate();
				midLowerPanelContainer.repaint();
			}

			if (e.getSource() == removeBtn) {
				Frame.sb.remove(lecturer);
				e.setSource(backBtn);
			}

			if (e.getSource() == addBtn) {
				String name = mltextField[0].getText();

				if (!name.equals("")) {
					boolean[][] availability = midLowerPanel.getAvailability();
					boolean[][] conflicts = midLowerPanel.getConflict();
					lecturer.setAvailability(availability);
					Frame.sb.fixClash(lecturer, conflicts);
					lecturer.setName(name);
					if (coursesLister != null)
						lecturer.setCourses(coursesLister.getCourses());
					Frame.sb.add(lecturer);
				}
				e.setSource(backBtn);
			}

			if (e.getSource() == backBtn) {
				LecturerTableGUI l = new LecturerTableGUI();
				l.setFrame(frame);
				frame.setContentPane(l);
			}

			frame.revalidate();
			frame.repaint();
		}
	}

	public void setFrame(Frame frame) {
		this.frame = frame;

		ArrayList<String> lCourses = new ArrayList<>(lecturer.getCourses());
		if (Frame.sb.getCourses().length > 0)
			coursesLister = new CoursesList(Frame.sb, lCourses, 3);

		createTopPanel();
		createMiddlePanel();
		createBottomPanel();
	}
}