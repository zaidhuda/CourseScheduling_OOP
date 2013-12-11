//+-------------------------------------+
//|			Test 				        |
//+-------------------------------------+
//|+courses:ArrayList<Course>           |
//|__________________________           |
//|+lecturers:ArrayList<Lecturer>       |
//|______________________________       |
//|+venues:ArrayList<Venue>             |
//|________________________             |
//|+schedules:ArrayList<Schedule>       |
//|______________________________       |
//|+sections:ArrayList<Section>         |
//|_____________________________        |
//+-------------------------------------+
//|+main(String[] args):void            |
//|_________________________            |
//|+save():void                         |
//|____________                         |
//|+add(o:Object):void                  |
//|_____________________________        |
//|+remove(o:Object ):void              |
//|_______________________              |
//|+generateSections():void             |
//|_______________________              |
//+-------------------------------------+

import courseschedule.OffsetFinder;
import courseschedule.Section;
import courseschedule.util.ScheduleBuilder;
import courseschedule.util.ScheduleFiling;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Test {
    public static final ScheduleBuilder sb = new ScheduleBuilder();
    public static final ScheduleFiling sf = new ScheduleFiling(sb);

    public static void main(String[] args) {
	    //if (JOptionPane.showConfirmDialog(null, "Load?") == 0)
            sf.load();

	    //OffsetFinder of = new OffsetFinder(sb.getCourses(), null);
	    //System.out.println(Arrays.toString(of.getOffset()));
	    //of = new OffsetFinder(sb.getVenues(), null);
	    //System.out.println(Arrays.toString(of.getOffset()));
	    //of = new OffsetFinder(sb.getLecturers(), null);
	    //System.out.println(Arrays.toString(of.getOffset()));

	    //sb.generateSections(true);
	    //for (Section s : sb.sections) {
         //   //s.generateSchedule(true);
         //   System.out.println(s);
	    //}
	    //sb.sections.get(0).setDayAndTime(1,2);
	    //sb.forceReSchedule(1, 1, sb.sections.get(0), sb.sections);
	    //System.out.println(sb.sections.get(0));
	    //sb.remove(sb.courses.get(1));
	    for (Section s : sb.sections) {
	       System.out.println(s);
	    }

	    JFrame frame = new JFrame("Hweh");
	    Panel panel = new Panel(sb.getCourses());
	    panel.setPreferredSize(new Dimension(800, 300));
	    frame.setContentPane(panel);
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    //System.out.println(sb.lecturers.get(2));

	    //if (JOptionPane.showConfirmDialog(null, "Save?") == 0)
		    //sf.save();
    }

	public static class Panel extends JPanel {
		String[][] str;
		double[] offset;
		public Panel(String[][] str){
			super();
			this.str = str;
			System.out.println(this.getWidth());
		}
		public void paint(Graphics g){
			Graphics2D g2d = (Graphics2D)g;

			Font font = new Font(Font.SERIF, Font.PLAIN, 20);
			g2d.setFont(font);
			FontMetrics fm = getFontMetrics(font);

			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

			OffsetFinder of = new OffsetFinder(str, this);
			offset = of.getOffset();
			System.out.println(this.getWidth());

			for (int i=0;i<str.length;i++)
				for (int j=0;j<str[i].length;++j){
					int strHalfWidth = (int) offset[j] - fm.stringWidth(str[i][j])/2;
					g2d.drawString(str[i][j], strHalfWidth, 50*(i+1));
				}
		}
	}
}
