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

import courseschedule.Course;
import courseschedule.OffsetFinder;
import courseschedule.Section;
import courseschedule.util.ScheduleBuilder;
import courseschedule.util.ScheduleFiling;
import javax.swing.*;
import java.util.Arrays;

public class Test {
    public static final ScheduleBuilder sb = new ScheduleBuilder();
    public static final ScheduleFiling sf = new ScheduleFiling(sb);

    public static void main(String[] args) {
	    //if (JOptionPane.showConfirmDialog(null, "Load?") == 0)
            sf.load();

	    OffsetFinder of = new OffsetFinder(sb.getCourses(), null);
	    System.out.println(Arrays.toString(of.getOffset()));
	    of = new OffsetFinder(sb.getVenues(), null);
	    System.out.println(Arrays.toString(of.getOffset()));
	    of = new OffsetFinder(sb.getLecturers(), null);
	    System.out.println(Arrays.toString(of.getOffset()));

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
	    //System.out.println(sb.lecturers.get(2));

	    //if (JOptionPane.showConfirmDialog(null, "Save?") == 0)
		    //sf.save();
    }
}
