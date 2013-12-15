import courseschedule.*;
import courseschedule.gui.*;
import courseschedule.util.*;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Frame extends JFrame {
    public static final ScheduleBuilder sb = new ScheduleBuilder();
    public static final ScheduleFiling sf = new ScheduleFiling(sb);

	public void initFrame() {
		setTitle("Course Scheduling App");
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		sf.load();
	}

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(915,590);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }	

	public static void main(String[] args) {
		MainGUI s = new MainGUI();
		// CourseGUI s = new CourseGUI();
		Frame frame = new Frame();

		frame.initFrame();
		frame.setContentPane(s);
		frame.pack();
		frame.setLocationRelativeTo(null);

		s.setFrame(frame);
	}
}