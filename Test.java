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

import java.util.*;

public class Test {
    public static ScheduleBuilder sb = new ScheduleBuilder();
    public static final ScheduleFiling sf = new ScheduleFiling(sb);

    public static void main(String[] args) {

//        boolean[][] availability = new boolean[2][6];
//        Arrays.fill(availability[0], true);
//        Arrays.fill(availability[1], true);
//
//        courses.add(new Course("CSC1100", "Elements of Programming", 3, 3));
//        courses.add(new Course("CsC1102", "Web Programming Fundamental", 3, 1));
//        courses.add(new Course("CSC1103", "Object Oriented Programming", 3, 2));
//
//        lecturers.add(new Lecturer("Dr Suriani", "CSC1100", availability));
//        lecturers.add(new Lecturer("Dr Amelia", "CSC1100", availability));
//        lecturers.add(new Lecturer("Dr Rizal", "CSC1102", availability));
//        lecturers.add(new Lecturer("Dr Azlin", "CSC1103", availability));
//        lecturers.add(new Lecturer("Dr Norsaremah", new ArrayList<>(java.util.Arrays.asList("CSC1100", "CSC1102", "CSC1103", "CSC1104"))));
//
//        venues.add(new Venue("Lab 3", availability));
//        venues.add(new Venue("Lab 6", availability));
//        venues.get(0).addCourse("CSC1102");
//        venues.get(1).addCourse("CSC1100");
//        venues.get(1).addCourse("CSC1103");
//        venues.get(1).removeCourse("lalalala");
        sf.load();
//        System.out.println(Arrays.deepToString(lecturers.get(1).getAvailability()));
//        System.out.println(Arrays.deepToString(venues.get(1).getAvailability()));
//        System.out.println(Arrays.deepToString(getAvailableSlots(lecturers.get(1), venues.get(1))));

        sb.generateSections();

//        long seed = System.nanoTime();
//        Collections.shuffle(sb.sections, new Random(seed));
//
//        Collections.sort(sb.sections, sb.courseComparator);
        for (Section s : sb.sections)
            s.setLecturer(sb.lecturers, false);
        for (Section s : sb.sections)
            s.setVenue(sb.venues);

        for (Section s : sb.sections) {
            s.generateSchedule(true);
            System.out.println(s);
        }

		  sf.save();
//        FetchData fd = new FetchData(courses);
    }
}
