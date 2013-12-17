import courseschedule.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Frame extends JFrame {
	public static final ScheduleBuilder sb = new ScheduleBuilder();
	public static final ScheduleFiling sf = new ScheduleFiling(sb);
	public static int frameWidth = 1170;
	public static int frameHeight = 690;

	public void initFrame() {
		setTitle("Course Scheduling App");
		setMinimumSize(new Dimension(800, 600));
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1185, 690);
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	public static void main(String[] args) {
		MainGUI s = new MainGUI();
		final Frame frame = new Frame();

		frame.initFrame();
		frame.setContentPane(s);
		frame.pack();

		frame.setLocationRelativeTo(null);
		if (sb.courses.isEmpty() || sb.lecturers.isEmpty() || sb.venues.isEmpty()){
			File file = new File("schedule.txt");
			if (file.exists() && JOptionPane.showConfirmDialog(null, "A Save file is found. Load?", "Load", JOptionPane.YES_NO_OPTION) == 0)
				sf.load();
		}

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				if (!sb.courses.isEmpty() || !sb.lecturers.isEmpty() || !sb.venues.isEmpty()){
					final int option = JOptionPane.showConfirmDialog(null, "You are about to exit. Save data?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
					switch (option){
						case 0: sf.save();
						case 1: System.exit(0);
							frame.dispose();
					}
				} else {
					System.exit(0);
					frame.dispose();
				}
			}
		});

		frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				frameWidth = frame.getWidth()-15;
				frameHeight = frame.getHeight();
				frame.invalidate();
			}

			@Override
			public void componentMoved(ComponentEvent e) {}

			@Override
			public void componentShown(ComponentEvent e) {}

			@Override
			public void componentHidden(ComponentEvent e) {}
		});

		s.setFrame(frame);
	}
}