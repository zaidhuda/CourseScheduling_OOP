import courseschedule.gui.*;
import courseschedule.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI extends JPanel {
	private CustomFont font = new CustomFont();
	private Frame frame = new Frame();

	public MainGUI() {
		setLayout(new BorderLayout());

		Panel1 headerPanel = new Panel1();
		Panel2 menuPanelTop = new Panel2();
		Panel3 menuPanelBottom = new Panel3();
		JPanel menuPanel = new JPanel();

		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
		menuPanel.add(headerPanel);
		menuPanel.add(menuPanelTop);
		menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		menuPanel.add(menuPanelBottom);

		add(menuPanel, BorderLayout.CENTER);
	}

	private class Panel1 extends JPanel {
		private JLabel label;

		public Panel1() {
			setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

			label = new JLabel("Main Menu");
			label.setFont(font.getFontSegoe(48));
			label.setForeground(CustomColour.silver);

			add(label);
			add(Box.createRigidArea(new Dimension(700, 120)));
		}
	}

	private class Panel2 extends JPanel {
		private SquareButton[] btn = new SquareButton[4];
		private JLabel[] imgLabel = new JLabel[4];

		public Panel2() {
			setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			setBackground(CustomColour.silverclouds);
			setButton();
			setTile();

			for (int i = 0; i < 2; i++) {
				add(btn[i]);
				add(Box.createRigidArea(new Dimension(5, 0)));
				add(imgLabel[i]);
				add(Box.createRigidArea(new Dimension(5, 0)));
			}
		}

		public void setButton() {
			btn[0] = new SquareButton("COURSE", "add course to be generated.");
			btn[1] = new SquareButton("LECTURER", "assign lecturer to course.");

			btn[0].setCustomColor(CustomColour.lightergreen);
			btn[1].setCustomColor(CustomColour.lighterblue);

			for (int i = 0; i < 2; i++) {
				btn[i].addActionListener(new ButtonListener());
				btn[i].setFontUpperLabel(font.getFontPTSans(15, Font.BOLD, -0.05));
				btn[i].setFontLowerLabel(font.getFontPTSans(14, -0.07));
			}
		}

		public void setTile() {
			imgLabel[0] = new JLabel(new ImageIcon("Image\\1.png"));
			imgLabel[1] = new JLabel(new ImageIcon("Image\\2.png"));

			for (int i = 0; i < 2; i++) {
				imgLabel[i].setOpaque(true);
				imgLabel[i].setBackground(CustomColour.nightblue);
				imgLabel[i].setPreferredSize(new Dimension(170, 196));
				imgLabel[i].setMinimumSize(new Dimension(170, 196));
				imgLabel[i].setMaximumSize(new Dimension(170, 196));
			}
		}

		private class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btn[0]) {
					CourseTableGUI c = new CourseTableGUI();
					c.setFrame(frame);
					frame.setContentPane(c);
				}

				if (e.getSource() == btn[1]) {
					LecturerTableGUI l = new LecturerTableGUI();
					l.setFrame(frame);
					frame.setContentPane(l);
				}
				frame.revalidate();
				frame.repaint();
			}
		}
	}

	private class Panel3 extends JPanel {
		private SquareButton[] btn = new SquareButton[4];
		private JLabel[] imgLabel = new JLabel[4];

		public Panel3() {
			setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			setBackground(CustomColour.silverclouds);
			setButton();
			setTile();

			for (int i = 0; i < 2; i++) {
				add(imgLabel[i]);
				add(Box.createRigidArea(new Dimension(5, 0)));
				add(btn[i]);
				add(Box.createRigidArea(new Dimension(5, 0)));
			}
		}

		public void setButton() {
			btn[0] = new SquareButton("VENUE", "venues to be assigned.");
			btn[1] = new SquareButton("GENERATE", "generate the schedule.");

			btn[0].setCustomColor(CustomColour.lighterred);
			btn[1].setCustomColor(CustomColour.turqoise);

			for (int i = 0; i < 2; i++) {
				btn[i].addActionListener(new ButtonListener());
				// btn[i].setFontUpperLabel(font.getFontSegoe(14, Font.BOLD,-0.09));
				btn[i].setFontUpperLabel(font.getFontPTSans(15, Font.BOLD, -0.05));
				// btn[i].setFontLowerLabel(font.getFontSegoe(13,-0.03));
				btn[i].setFontLowerLabel(font.getFontPTSans(14, -0.07));
			}
		}

		public void setTile() {
			imgLabel[0] = new JLabel(new ImageIcon("Image\\3.png"));
			imgLabel[1] = new JLabel(new ImageIcon("Image\\4.png"));

			for (int i = 0; i < 2; i++) {
				imgLabel[i].setOpaque(true);
				imgLabel[i].setBackground(CustomColour.nightblue);
				imgLabel[i].setPreferredSize(new Dimension(170, 196));
				imgLabel[i].setMinimumSize(new Dimension(170, 196));
				imgLabel[i].setMaximumSize(new Dimension(170, 196));
			}
		}

		private class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btn[0]) {
					VenueTableGUI v = new VenueTableGUI();
					v.setFrame(frame);
					frame.setContentPane(v);
				}

				if (e.getSource() == btn[1]) {
					ScheduleTableGUI st = new ScheduleTableGUI();
					st.setFrame(frame);
					frame.setContentPane(st);
				}

				frame.revalidate();
				frame.repaint();
			}
		}
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
		if (Frame.sb.sections.isEmpty())
			Frame.sb.generateSections(true);
		else
			Frame.sb.fixCourseSections();
	}
}