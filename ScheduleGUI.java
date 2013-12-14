import courseschedule.*;
import courseschedule.gui.*;
import courseschedule.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ScheduleGUI extends JPanel {
	// VARIABLES FOR GENERAL USE
    public static ScheduleBuilder sb = null;
	private CustomColour color = new CustomColour();
	private CustomFont font = new CustomFont();
	private Frame frame = new Frame();

	// VARIABLES FOR TOP PANEL
	private JPanel topPanel;
	private JLabel textLabel;

	// VARIABLES FOR MID PANELS
	private JPanel middlePanel, midUpperPanel, midMiddlePanel, midLeftPanel, midRightPanel;
	private TimePicker midLowerPanel;
	private Section section;

	// MIDLEFTPANEL'S 
	private CustomField[] mltextField = new CustomField[1];
	private CustomLabel[] mltextLabel = {new CustomLabel("LECTURER")};

	// MIDRIGHTPANEL'S
	private CustomField[] mrtextField = new CustomField[1];
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

		topPanel.setBackground(color.getNightBlue());
		topPanel.setPreferredSize(new Dimension(900, 70));
		topPanel.setMinimumSize(getPreferredSize());
		topPanel.setMaximumSize(getPreferredSize());

		textLabel = new JLabel("SCHEDULE");

		textLabel.setForeground(color.getSilverClouds());
		textLabel.setFont(font.getFontAbel(48));

		topPanel.add(textLabel);
		add(topPanel, BorderLayout.NORTH);
	}

	public void createMiddlePanel() {
		middlePanel = new JPanel();
		midUpperPanel = new JPanel();
		midMiddlePanel = new JPanel();
		int[][] slots = sb.getAvailableSlots(section.getLecturer(), section.getVenue());
		midLowerPanel = new TimePicker(section.getDay(),section.getTime(),slots);
		midLeftPanel = new JPanel();
		midRightPanel = new JPanel();
		System.out.println(section.getDay() + " " + section.getTime());

		midLeftPanel.setBackground(color.getSilverClouds());
		midLeftPanel.setLayout(new BoxLayout(midLeftPanel, BoxLayout.Y_AXIS));
		
		for(int i=0; i<1; i++) {
			mltextField[i] = new CustomField();
			mltextField[i].setText(section.detailsArray()[6]);
			midLeftPanel.add(mltextLabel[i]);
			midLeftPanel.add(mltextField[i]);
			midLeftPanel.add(Box.createRigidArea(new Dimension(0,10)));
		}

		midRightPanel.setBackground(color.getSilverClouds());
		midRightPanel.setLayout(new BoxLayout(midRightPanel, BoxLayout.Y_AXIS));
		
		for(int i=0; i<1; i++) {
			mrtextField[i] = new CustomField();
			mrtextField[i].setText(section.detailsArray()[7]);
			midRightPanel.add(mrtextLabel[i]);
			midRightPanel.add(mrtextField[i]);
			midRightPanel.add(Box.createRigidArea(new Dimension(0,10)));
		}

		midUpperPanel.setLayout(new BoxLayout(midUpperPanel, BoxLayout.PAGE_AXIS));
		CustomLabel[] textLabel = new CustomLabel[2];
		JTextField[] textField = new JTextField[2];

		for(int i=0; i<2; i++) {
			JPanel rowPane = new JPanel();
			JLabel[] leftLabel = new JLabel[2];
			rowPane.setLayout(new BoxLayout(rowPane, BoxLayout.LINE_AXIS));

			for(int j=0; j<4; j++) {
				if(i==0) {
					switch(j) {
						case 0: 
							leftLabel[j] = new JLabel("SECTION");
							leftLabel[j].setPreferredSize(new Dimension(146,18));
							leftLabel[j].setMaximumSize(leftLabel[j].getPreferredSize());
							leftLabel[j].setMinimumSize(leftLabel[j].getPreferredSize());
							leftLabel[j].setHorizontalAlignment(JLabel.CENTER);
							leftLabel[j].setFont(font.getFontAbel(20,-0.05));
							leftLabel[j].setForeground(color.getSilver());
							rowPane.add(leftLabel[j]); 
							break;
						case 1:
							textLabel[j-1] = new CustomLabel("COURSE TITLE");
							rowPane.add(textLabel[j-1]); 
							rowPane.add(Box.createRigidArea(new Dimension(30,0)));
							break;
						case 2:
							rowPane.add(Box.createRigidArea(textLabel[j-2].getPreferredSize())); 
							break;
						case 3:
							rowPane.add(Box.createRigidArea(leftLabel[j-3].getPreferredSize())); 
							break;
					}
				}
				if(i==1) {
					switch(j) {
						case 0: 
							leftLabel[j] = new JLabel(section.detailsArray()[j+1]);
							leftLabel[j].setPreferredSize(new Dimension(146,20));
							leftLabel[j].setMaximumSize(leftLabel[j].getPreferredSize());
							leftLabel[j].setMinimumSize(leftLabel[j].getPreferredSize());
							leftLabel[j].setHorizontalAlignment(JLabel.CENTER);
							leftLabel[j].setFont(font.getFontAbel(25,-0.05));
							leftLabel[j].setForeground(color.getSilver());
							rowPane.add(leftLabel[j]); 
							break;
						case 1:
							textLabel[j] = new CustomLabel(section.detailsArray()[j+1]);
							textLabel[j].setFont(font.getFontAbel(25,-0.05));
							textLabel[j].setPreferredSize(new Dimension(287,26));
							rowPane.add(textLabel[j]); 
							rowPane.add(Box.createRigidArea(new Dimension(30,0)));
							break;
						case 2:
							rowPane.add(Box.createRigidArea(textLabel[j-1].getPreferredSize())); 
							break;
						case 3:
							rowPane.add(Box.createRigidArea(leftLabel[j-3].getPreferredSize())); 
							break;
					}
				}
			}

			midUpperPanel.add(rowPane);
		}

		midMiddlePanel.setBackground(color.getSilverClouds());
		midMiddlePanel.setLayout(new BoxLayout(midMiddlePanel, BoxLayout.X_AXIS));
		midMiddlePanel.setAlignmentX(CENTER_ALIGNMENT);
		midMiddlePanel.add(midLeftPanel);
		midMiddlePanel.add(Box.createRigidArea(new Dimension(30,0)));
		midMiddlePanel.add(midRightPanel);

		midLowerPanel.setAlignmentX(CENTER_ALIGNMENT);

		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.PAGE_AXIS));
		middlePanel.add(Box.createRigidArea(new Dimension(0,10)));
		middlePanel.add(midUpperPanel);
		middlePanel.add(Box.createRigidArea(new Dimension(0,10)));
		middlePanel.add(midMiddlePanel);
		middlePanel.add(midLowerPanel);

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
		bottomPanel.add(Box.createRigidArea(new Dimension(10,0)));
		bottomPanel.add(saveBtn);
		bottomPanel.add(Box.createRigidArea(new Dimension(0,75)));
		add(bottomPanel, BorderLayout.SOUTH);
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == backBtn) {
				ScheduleTableGUI s = new ScheduleTableGUI();
				s.setFrame(frame);
				frame.setContentPane(s);
				frame.pack();
			}

			if(e.getSource() == saveBtn) {
				// Some save commands
			}
		}
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
		this.sb = frame.sb;

		createTopPanel();
		createMiddlePanel();
		createBottomPanel();
	}
}