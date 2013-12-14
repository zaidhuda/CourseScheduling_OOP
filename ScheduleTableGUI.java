import courseschedule.gui.*;
import courseschedule.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ScheduleTableGUI extends JPanel {
	// VARIABLES FOR GENERAL USE
    public static ScheduleBuilder sb = null;
	private Frame frame = new Frame();
	private CustomFont font = new CustomFont();

	// VARIABLES FOR TOP PANEL
	private JPanel topPanel;
	private JLabel textLabel;

	// VARIABLES FOR MID PANELS
	private JPanel middlePanel;
	private ArrayList<TableButton> list = new ArrayList<TableButton>();
	private JScrollPane scrollPanel;
	private OffsetFinder of;

	// // VARIABLES FOR HEADER
	private String[] header = {"CODE","SECTION","COURSE TITLE", "CR HOUR","DAY","TIME","LECTURER","VENUE","LIMIT"};
	private JPanel containerHeader = new JPanel();

	// // VARIABLES FOR TABLE
	private JPanel containerRow = new JPanel();
	private JPanel row = new JPanel();
	private String[][] label = {
		{ "CSC1100",  "1",  "Elements Of Programming",  "3",  "T-TH",  "10.00 - 11.20am",  "Dr Suriani",  "LAB 6",  "30"},
		// { "CSC1100",  "Elements Of Programming",  "2",  "3",  "T-TH",  "10.00 - 11.20am",  "LAB 6",  "Dr Suriani",  "30"},
		// { "CSC1100",  "Elements Of Programming",  "3",  "3",  "T-TH",  "10.00 - 11.20am",  "LAB 6",  "Dr Suriani",  "30"},
		// { "CSC1100",  "Elements Of Programming",  "4",  "3",  "T-TH",  "10.00 - 11.20am",  "LAB 6",  "Dr Suriani",  "30"},
		// { "CSC1102",  "Web Programming",  "1",  "3",  "T-TH",  "10.00 - 11.20am",  "LAB 6",  "Dr Rizal",  "30"},
		// { "CSC1103",  "Object Oriented Programming",  "1",  "3",  "T-TH",  "10.00 - 11.20am",  "LAB 6",  "Dr Saremah",  "30"},
		// { "CSC1103",  "Object Oriented Programming",  "2",  "3",  "T-TH",  "10.00 - 11.20am",  "LAB 6",  "Dr Azlin",  "30"},
	};

	// VARIABLES FOR BOTTOM PANEL
	private JPanel bottomPanel;
	private RoundedButton backBtn, printBtn;

	public ScheduleTableGUI() {
		setLayout(new BorderLayout());
	}

	public void createTopPanel() {
		topPanel = new JPanel();

		topPanel.setBorder(BorderFactory.createEmptyBorder());
		topPanel.setBackground(CustomColour.getLighterBlue());

		textLabel = new JLabel("SCHEDULE");

		textLabel.setForeground(CustomColour.getSilverClouds());
		textLabel.setFont(font.getFontAbel(48));

		topPanel.add(textLabel);
		add(topPanel, BorderLayout.NORTH);
	}

	public void createMiddlePanel() {
		middlePanel = new JPanel();
		SpringLayout spring = new SpringLayout();
		middlePanel.setLayout(spring);

		createHeaderPanel();
		createTablePanel();

		middlePanel.add(containerHeader);
		middlePanel.add(containerRow);

		spring.putConstraint(SpringLayout.NORTH, containerRow, 0, SpringLayout.SOUTH, containerHeader);
		add(middlePanel, BorderLayout.CENTER);
	}

	public void createHeaderPanel() {
		containerHeader.setBackground(CustomColour.getDarkerBlue());
		containerHeader.setLayout(new BoxLayout(containerHeader, BoxLayout.X_AXIS));
		containerHeader.setPreferredSize(new Dimension(900, 20));
		containerHeader.setMinimumSize(getPreferredSize());
		containerHeader.setMaximumSize(getPreferredSize());

		of = new OffsetFinder(label, containerHeader);

		TableButton tb = new TableButton(header, of.getOffset());
		tb.setPreferredSize(new Dimension(900,20));
		tb.setForeground(CustomColour.getSilverClouds());
		containerHeader.add(tb);
	}

	public void createTablePanel() {
		row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
		scrollPanel = new JScrollPane(row);

		for(int i=0; i<label.length; i++) {
			list.add(new TableButton(label[i], of.getOffset()));
			list.get(i).addActionListener(new ButtonListener());
			row.add(list.get(i));
		}

		scrollPanel.setPreferredSize(new Dimension(900,331));
		scrollPanel.setBorder(BorderFactory.createEmptyBorder());
		scrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI(1));
		// scrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI(2));
		scrollPanel.setHorizontalScrollBar(null);

		containerRow.setBackground(CustomColour.getSilverClouds());
		containerRow.add(scrollPanel);
	}

	public void createBottomPanel() {
		bottomPanel = new JPanel();

		bottomPanel.setBackground(CustomColour.getSilverClouds());

		backBtn = new RoundedButton("BACK", 0);
		printBtn = new RoundedButton("PRINT", 1);

		backBtn.setFont(font.getFontPTSans(15, Font.BOLD, -0.07));
		backBtn.addActionListener(new ButtonListener());

		printBtn.setFont(font.getFontPTSans(15, Font.BOLD, -0.07));
		printBtn.addActionListener(new ButtonListener());

		bottomPanel.add(backBtn);
		bottomPanel.add(Box.createRigidArea(new Dimension(10,0)));
		bottomPanel.add(printBtn);
		bottomPanel.add(Box.createRigidArea(new Dimension(0,75)));
		add(bottomPanel, BorderLayout.SOUTH);
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<list.size(); i++)
				if(e.getSource() == list.get(i)) {
					ScheduleGUI s = new ScheduleGUI(sb.sections.get(i));
					s.setFrame(frame);
					frame.setContentPane(s);
					frame.pack();
				}

			if(e.getSource() == backBtn) {
				MainGUI m = new MainGUI();
				m.setFrame(frame);
				frame.setContentPane(m);
				frame.pack();
			}

			if(e.getSource() == printBtn) {
				// print schedule lists
			}
		}
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
		this.sb = frame.sb;

		if(!sb.sections.isEmpty())
			label = sb.getSections();

		createTopPanel();
		createMiddlePanel();
		createBottomPanel();
	}
}