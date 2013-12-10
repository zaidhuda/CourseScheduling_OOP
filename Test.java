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

import courseschedule.*;
import javax.swing.*;

public class Test {
    public static final ScheduleBuilder sb = new ScheduleBuilder();
    public static final ScheduleFiling sf = new ScheduleFiling(sb);

    public static void main(String[] args) {
        sf.load();

	    //sb.generateSections(true);
	    //for (Section s : sb.sections) {
         //   //s.generateSchedule(true);
         //   System.out.println(s);
	    //}
	    //sb.sections.get(0).setDayAndTime(1,2);
	    sb.forceReSchedule(1, 1, sb.sections.get(0), sb.sections);
	    //System.out.println(sb.sections.get(0));

	    //System.out.println(sb.getAssignedLecturers(sb.sections.get(1)));
	    //System.out.println(sb.getAssignedVenues(sb.sections.get(1)));
	    System.out.println(sb.getClassAt(1, 1, sb.lecturers.get(1), sb.sections));
	    System.out.println(sb.getClassAt(1, 1, sb.venues.get(1), sb.sections));
	    Section s = sb.sections.get(5);
	    //System.out.println(s.getLecturer());
	    JFrame frame = new JFrame("Time Table Selector");
	    TimePicker panel = new TimePicker(s.getDay(), s.getTime(), sb.getAvailableSlots(s.getLecturer(),s.getVenue()));
	    //TimePicker panel = new TimePicker(sb.lecturers.get(4).getAvailability());
        panel.setSize(75);
        panel.obeyConflict(true);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		sf.save();
    }
}
