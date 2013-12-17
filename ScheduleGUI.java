import courseschedule.*;
import courseschedule.gui.*;
import courseschedule.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScheduleGUI extends JPanel {
	// VARIABLES FOR GENERAL USE
	private CustomFont font = new CustomFont();
	private Frame frame = new Frame();

	// VARIABLES FOR TOP PANEL
	private JPanel topPanel;
	private JLabel textLabel;

	// VARIABLES FOR MID PANELS
	private final JPanel midLowerPanelContainer = new JPanel();
	LecturerList LecturerLister = null;
	VenueList VenueLister = null;
	private JPanel middlePanel, midUpperPanel, midMiddlePanel, midLeftPanel, midRightPanel;
	private TimePicker midLowerPanel;
	private Section section;
	private Lecturer lecturer;
	private Venue venue;

	// MIDLEFTPANEL'S 
	private FieldButton[] mltextField = new FieldButton[1];
	private CustomLabel[] mltextLabel = {new CustomLabel("LECTURER")};

	// MIDRIGHTPANEL'S
	private FieldButton[] mrtextField = new FieldButton[1];
	private CustomLabel[] mrtextLabel = {new CustomLabel("VENUE")};

	// VARIABLES FOR BOTTOM PANEL
	private JPanel bottomPanel;
	private RoundedButton backBtn, saveBtn;

	public ScheduleGUI(Section section) {
		this.section = section;
		setLayout(new BorderLayout());
	}

	public void createTopPanel() {
		topPanel = new JPanel();

		topPanel.setBackground(CustomColour.getNightBlue());
		topPanel.setPreferredSize(new Dimension(900, 70));
		topPanel.setMinimumSize(getPreferredSize());
		topPanel.setMaximumSize(getPreferredSize());

		textLabel = new JLabel("SCHEDULE");

		textLabel.setForeground(CustomColour.getSilverClouds());
		textLabel.setFont(font.getFontAbel(48));

		topPanel.add(textLabel);
		add(topPanel, BorderLayout.NORTH);
	}

	public void createMiddlePanel() {
		middlePanel = new JPanel();
		midUpperPanel = new JPanel();
		midMiddlePanel = new JPanel();
		int[][] slots = ScheduleBuilder.getAvailableSlots(section.getLecturer(), section.getVenue());
		midLowerPanel = new TimePicker(section.getDay(), section.getTime(), slots);
		midLeftPanel = new JPanel();
		midRightPanel = new JPanel();

		midLeftPanel.setBackground(CustomColour.getSilverClouds());
		midLeftPanel.setLayout(new BoxLayout(midLeftPanel, BoxLayout.Y_AXIS));

		for (int i = 0; i < 1; i++) {
			mltextField[i] = new FieldButton(section.detailsArray()[6]);
			//mltextField[i].setText(section.detailsArray()[6]);
			if (!Frame.sb.getAssignedLecturers(section.getCourse()).isEmpty()) {
				LecturerLister.setButton(mltextField[0]);
				LecturerLister.setTheTimePicker(midLowerPanel);
				mltextField[i].addActionListener(new ButtonListener());
			}
			midLeftPanel.add(mltextLabel[i]);
			midLeftPanel.add(mltextField[i]);
			midLeftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		midRightPanel.setBackground(CustomColour.getSilverClouds());
		midRightPanel.setLayout(new BoxLayout(midRightPanel, BoxLayout.Y_AXIS));

		for (int i = 0; i < 1; i++) {
			mrtextField[i] = new FieldButton(section.detailsArray()[7]);
			//mltextField[i].setText(section.detailsArray()[7]);
			if (!Frame.sb.getAssignedVenues(section.getCourse()).isEmpty()) {
				VenueLister.setButton(mrtextField[0]);
				VenueLister.setTheTimePicker(midLowerPanel);
				mrtextField[i].addActionListener(new ButtonListener());
			}
			midRightPanel.add(mrtextLabel[i]);
			midRightPanel.add(mrtextField[i]);
			midRightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		midUpperPanel.setLayout(new BoxLayout(midUpperPanel, BoxLayout.PAGE_AXIS));
		CustomLabel[] textLabel = new CustomLabel[2];
		JTextField[] textField = new JTextField[2];

		for (int i = 0; i < 2; i++) {
			JPanel rowPane = new JPanel();
			JLabel[] leftLabel = new JLabel[2];
			rowPane.setLayout(new BoxLayout(rowPane, BoxLayout.LINE_AXIS));

			for (int j = 0; j < 4; j++) {
				if (i == 0) {
					switch (j) {
						case 0:
							leftLabel[j] = new JLabel("SECTION");
							leftLabel[j].setPreferredSize(new Dimension(146, 18));
							leftLabel[j].setMaximumSize(leftLabel[j].getPreferredSize());
							leftLabel[j].setMinimumSize(leftLabel[j].getPreferredSize());
							leftLabel[j].setHorizontalAlignment(JLabel.CENTER);
							leftLabel[j].setFont(font.getFontAbel(20, -0.05));
							leftLabel[j].setForeground(CustomColour.getSilver());
							rowPane.add(leftLabel[j]);
							break;
						case 1:
							textLabel[j - 1] = new CustomLabel("COURSE TITLE");
							rowPane.add(textLabel[j - 1]);
							rowPane.add(Box.createRigidArea(new Dimension(30, 0)));
							break;
						case 2:
							rowPane.add(Box.createRigidArea(textLabel[j - 2].getPreferredSize()));
							break;
						case 3:
							rowPane.add(Box.createRigidArea(leftLabel[j - 3].getPreferredSize()));
							break;
					}
				}
				if (i == 1) {
					switch (j) {
						case 0:
							int frameWidth = (int)frame.getPreferredSize().getWidth();
							int offset = 605;

							leftLabel[j] = new JLabel(section.detailsArray()[j + 1]);
							leftLabel[j].setPreferredSize(new Dimension(((frameWidth-offset)/2)-140, 20));
							leftLabel[j].setMaximumSize(leftLabel[j].getPreferredSize());
							leftLabel[j].setMinimumSize(leftLabel[j].getPreferredSize());
							leftLabel[j].setHorizontalAlignment(JLabel.CENTER);
							leftLabel[j].setFont(font.getFontAbel(25, -0.05));
							leftLabel[j].setForeground(CustomColour.getSilver());
							rowPane.add(leftLabel[j]);
							break;
						case 1:
							textLabel[j] = new CustomLabel(section.detailsArray()[j + 1]);
							textLabel[j].setFont(font.getFontAbel(25, -0.05));
							textLabel[j].setPreferredSize(new Dimension(605, 26));
							rowPane.add(textLabel[j]);
							// rowPane.add(Box.createRigidArea(new Dimension(30, 0)));
							break;
						// case 2:
						// 	rowPane.add(Box.createRigidArea(textLabel[j - 1].getPreferredSize()));
						// 	break;
						case 3:
							rowPane.add(Box.createRigidArea(leftLabel[j - 3].getPreferredSize()));
							break;
					}
				}
			}

			midUpperPanel.add(rowPane);
		}

		midMiddlePanel.setBackground(CustomColour.getSilverClouds());
		midMiddlePanel.setLayout(new BoxLayout(midMiddlePanel, BoxLayout.X_AXIS));
		midMiddlePanel.setAlignmentX(CENTER_ALIGNMENT);
		midMiddlePanel.add(midLeftPanel);
		midMiddlePanel.add(Box.createRigidArea(new Dimension(30, 0)));
		midMiddlePanel.add(midRightPanel);

		midLowerPanel.setAlignmentX(CENTER_ALIGNMENT);

		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.PAGE_AXIS));
		middlePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		middlePanel.add(midUpperPanel);
		middlePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		middlePanel.add(midMiddlePanel);
		//middlePanel.add(midLowerPanel);

		midLowerPanelContainer.add(midLowerPanel);
		if (Frame.sb.getAssignedLecturers(section.getCourse()) != null) {
			midLowerPanelContainer.add(LecturerLister);
			LecturerLister.setVisible(false);
		}
		if (Frame.sb.getAssignedVenues(section.getCourse()) != null) {
			midLowerPanelContainer.add(VenueLister);
			VenueLister.setVisible(false);
		}
		middlePanel.add(midLowerPanelContainer);

		add(middlePanel, BorderLayout.CENTER);
	}

	public void createBottomPanel() {
		bottomPanel = new JPanel();
		backBtn = new RoundedButton("BACK", 0);
		saveBtn = new RoundedButton("SAVE", 1);

		backBtn.setFont(font.getFontPTSans(15, Font.BOLD, -0.07));
		backBtn.addActionListener(new ButtonListener());

		saveBtn.setFont(font.getFontPTSans(15, Font.BOLD, -0.07));
		saveBtn.addActionListener(new ButtonListener());

		bottomPanel.add(backBtn);
		bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		bottomPanel.add(saveBtn);
		bottomPanel.add(Box.createRigidArea(new Dimension(0, 75)));
		add(bottomPanel, BorderLayout.SOUTH);
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == mltextField[0] || e.getSource() == mrtextField[0]){
				if (LecturerLister.isVisible() || VenueLister.isVisible()){
					midLowerPanel.reset(section.getDay(), section.getTime(), ScheduleBuilder.getAvailableSlots(LecturerLister.getLecturer(), VenueLister.getVenue()));
					midLowerPanel.setVisible(true);
				}
				if (e.getSource() == mltextField[0] ){
					if (LecturerLister.isVisible()) {
						LecturerLister.setVisible(false);
						VenueLister.setVisible(false);
					} else {
						LecturerLister.setVisible(true);
						LecturerLister.setPreferredSize(Frame.frameHeight-300);
						LecturerLister.setSize(getPreferredSize());
						midLowerPanel.setVisible(false);
						VenueLister.setVisible(false);
					}
				}
				if (e.getSource() == mrtextField[0]){
					if (VenueLister.isVisible()) {
						VenueLister.setVisible(false);
						LecturerLister.setVisible(false);
					} else {
						VenueLister.setVisible(true);
						VenueLister.setPreferredSize(Frame.frameHeight-300);
						VenueLister.setSize(getPreferredSize());
						midLowerPanel.setVisible(false);
						LecturerLister.setVisible(false);
					}
				}
				midLowerPanelContainer.revalidate();
				midLowerPanelContainer.repaint();
			}

			if (e.getSource() == saveBtn) {
				int day = midLowerPanel.getDay(), time = midLowerPanel.getTime();
				section.setLecturer(LecturerLister.getLecturer());
				section.setVenue(VenueLister.getVenue());
				if (midLowerPanel.hasConflict()) {
					Section s = Frame.sb.findSection(day, time, LecturerLister.getLecturer(), VenueLister.getVenue());
					if (s != null)
						s.generateSchedule(true);
					System.out.println(s);
				}
				section.setDayAndTime(day, time);
				e.setSource(backBtn);
			}

			if (e.getSource() == backBtn) {
				ScheduleTableGUI s = new ScheduleTableGUI();
				s.setFrame(frame);
				frame.setContentPane(s);
			}

			frame.revalidate();
			frame.repaint();
		}
	}

	public void setFrame(Frame frame) {
		this.frame = frame;

		lecturer = section.getLecturer();
		venue = section.getVenue();
		if (Frame.sb.getAssignedLecturers(section.getCourse()) != null)
			LecturerLister = new LecturerList(Frame.sb, section.getCourse(), section.getLecturer(), 1);
		if (Frame.sb.getAssignedVenues(section.getCourse()) != null)
			VenueLister = new VenueList(Frame.sb, section.getCourse(), section.getVenue(), 1);

		createTopPanel();
		createMiddlePanel();
		createBottomPanel();
	}
}