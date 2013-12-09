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

//        sb.generateSections();
//        long seed = System.nanoTime();
//        Collections.shuffle(sb.sections, new Random(seed));
//
//        Collections.sort(sb.sections, sb.courseComparator);
//        for (Section s : sb.sections)
//            s.setLecturer(sb.lecturers, false);
//        for (Section s : sb.sections)
//            s.setVenue(sb.venues);

        for (Section s : sb.sections) {
            s.generateSchedule(true);
            System.out.println(s);
        }

        JFrame frame = new JFrame("Time Table Selector");
        BooleanTable panel = new BooleanTable(sb.sections.get(0).getDay(), sb.sections.get(0).getTime(), sb.getAvailableSlots(sb.sections.get(0).getLecturer(), sb.sections.get(0).getVenue()));
//        BooleanTable panel = new BooleanTable(-1, -1, sb.lecturers.get(4).getAvailability());
        panel.setSize(75);
        panel.obeyConflict(true);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

		sf.save();
    }
}
