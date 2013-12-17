import courseschedule.*;
import courseschedule.gui.*;
import courseschedule.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CourseTableGUI extends JPanel {
	// VARIABLES FOR GENERAL USE
	private Frame frame = new Frame();
	private CustomFont font = new CustomFont();

	// VARIABLES FOR TOP PANEL
	private JPanel topPanel;
	private JLabel textLabel;

	// VARIABLES FOR MID PANELS
	private JPanel middlePanel;
	private ArrayList<TableButton> list = new ArrayList<>();
	private JScrollPane scrollPanel;
	private OffsetFinder of;
	private TableHeader tb;

	// // VARIABLES FOR HEADER
	private String[] header = {"COURSE CODE", "COURSE TITLE", "CREDIT HOUR", "REQUIRED SECTION"};
	private JPanel containerHeader = new JPanel();

	// // VARIABLES FOR TABLE
	private JPanel containerRow = new JPanel();
	private JPanel row = new JPanel();
	private String[][] label = {{"", "", "", ""},
			// { "CSC1102",  "Web Programming",  "3",  "1" },
			// { "CSC1103",  "Object Oriented Programming",  "3",  "2" },
	};

	// VARIABLES FOR BOTTOM PANEL
	private JPanel bottomPanel;
	private RoundedButton backBtn, addBtn;

	public CourseTableGUI() {
		setLayout(new BorderLayout());
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				scrollPanel.setPreferredSize(new Dimension(Frame.frameWidth, Frame.frameHeight-230));
				containerHeader.setSize(new Dimension(Frame.frameWidth, 20));
				of = new OffsetFinder(label, Frame.frameWidth);
				tb.setOffset(of);
				tb.setPreferredSize(new Dimension(Frame.frameWidth, 20));
				tb.repaint();
				for (int i = 0; i < label.length; i++) {
					list.get(i).setOffset(of);
					list.get(i).setPreferredSize(new Dimension(Frame.frameWidth, 40));
					list.get(i).repaint();
				}
				middlePanel.repaint();
				repaint();
			}

			@Override
			public void componentMoved(ComponentEvent e) {}

			@Override
			public void componentShown(ComponentEvent e) {}

			@Override
			public void componentHidden(ComponentEvent e) {}
		});

		// createTopPanel();
		// createMiddlePanel();
		// createBottomPanel();
	}

	public void createTopPanel() {
		topPanel = new JPanel();

		topPanel.setBorder(BorderFactory.createEmptyBorder());
		topPanel.setBackground(CustomColour.lighterblue);

		textLabel = new JLabel("COURSE");

		textLabel.setForeground(CustomColour.silverclouds);
		textLabel.setFont(font.getFontAbel(48));

		topPanel.add(textLabel);
		add(topPanel, BorderLayout.NORTH);
	}

	public void createMiddlePanel() {
		middlePanel = new JPanel();
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.PAGE_AXIS));

		createHeaderPanel();
		createTablePanel();

		middlePanel.add(containerHeader, BorderLayout.NORTH);
		middlePanel.add(containerRow, BorderLayout.SOUTH);

		add(middlePanel, BorderLayout.CENTER);
	}

	public void createHeaderPanel() {
		//containerHeader.setBackground(CustomColour.darkerblue);
		containerHeader.setLayout(new BoxLayout(containerHeader, BoxLayout.X_AXIS));
		//containerHeader.setPreferredSize(new Dimension(1170, 20));
		containerHeader.setPreferredSize(new Dimension(Frame.frameWidth, 20));
		//containerHeader.setMinimumSize(containerHeader.getPreferredSize());
		//containerHeader.setMaximumSize(containerHeader.getPreferredSize());

		of = new OffsetFinder(label, containerHeader);

		tb = new TableHeader(header, of);
		tb.setPreferredSize(new Dimension(Frame.frameWidth, 20));
		containerHeader.add(tb);
	}

	public void createTablePanel() {
		row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
		scrollPanel = new JScrollPane(row);

		for (int i = 0; i < label.length; i++) {
			list.add(new TableButton(label[i], of));
			list.get(i).addActionListener(new ButtonListener());
			list.get(i).setPreferredSize(new Dimension(Frame.frameWidth, 40));
			row.add(list.get(i));
		}

		//scrollPanel.setPreferredSize(new Dimension(1170, 431));
		scrollPanel.setPreferredSize(new Dimension(Frame.frameWidth, Frame.frameHeight-230));
		scrollPanel.setBorder(BorderFactory.createEmptyBorder());
		scrollPanel.getVerticalScrollBar().setUI(new CustomScrollBarUI(1));
		// scrollPanel.getHorizontalScrollBar().setUI(new CustomScrollBarUI(2));
		scrollPanel.setHorizontalScrollBar(null);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(16);

		containerRow.setBackground(CustomColour.silverclouds);
		containerRow.add(scrollPanel);
	}

	public void createBottomPanel() {
		bottomPanel = new JPanel();

		bottomPanel.setBackground(CustomColour.silverclouds);

		backBtn = new RoundedButton("BACK", 0);
		addBtn = new RoundedButton("ADD", 1);

		backBtn.setFont(font.getFontPTSans(15, Font.BOLD, -0.07));
		backBtn.addActionListener(new ButtonListener());

		addBtn.setFont(font.getFontPTSans(15, Font.BOLD, -0.07));
		addBtn.addActionListener(new ButtonListener());

		bottomPanel.add(backBtn);
		bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		bottomPanel.add(addBtn);
		bottomPanel.add(Box.createRigidArea(new Dimension(0, 75)));
		add(bottomPanel, BorderLayout.SOUTH);
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < list.size(); i++)
				if (e.getSource() == list.get(i)) {
					CourseGUI c = new CourseGUI(Frame.sb.courses.get(i));
					c.setFrame(frame);
					frame.setContentPane(c);
				}

			if (e.getSource() == backBtn) {
				MainGUI m = new MainGUI();
				m.setFrame(frame);
				frame.setContentPane(m);
			}

			if (e.getSource() == addBtn) {
				CourseGUI c = new CourseGUI(new Course(""));
				c.setFrame(frame);
				frame.setContentPane(c);
			}

			frame.revalidate();
			frame.repaint();
		}
	}

	public void setFrame(Frame frame) {
		this.frame = frame;

		createTopPanel();
		if (!Frame.sb.courses.isEmpty()) {
			label = Frame.sb.getCourses();
			createMiddlePanel();
		}
		createBottomPanel();
	}
}