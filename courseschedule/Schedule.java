package courseschedule;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Diaz
 * Package: ${PACKAGE_NAME}
 * Time: 12:50 AM
 * Date: 12/8/13.
 */
public class Schedule {
    public static ArrayList<Course> courses = new ArrayList<>();
    public static ArrayList<Lecturer> lecturers = new ArrayList<>();
    public static ArrayList<Venue> venues = new ArrayList<>();
    public static ArrayList<Section> sections = new ArrayList<>();
    public CourseComparator courseComparator = new CourseComparator();

    public static class CourseComparator implements Comparator<Section> {
        public int compare(Section o1, Section o2) {
            int diff = o1.getCourse().getCode().compareTo(o2.getCourse().getCode());
            if(diff == 0){
                return o1.getSectionNum().compareTo(o2.getSectionNum());
            }
            else
                return diff;
        }
    }

    public static void add(Object o) {
        boolean exist = false;
        try {
            if(o instanceof Lecturer){
                Lecturer that = (Lecturer) o;
                for (Lecturer l : lecturers)
                    if(l.equals(that)){
                        exist = true;
                        break;
                    }
                if(!exist)
                    lecturers.add(that);
            }
            else if (o instanceof Course){
                Course that = (Course) o;
                for (Course c : courses)
                    if(c.equals(that)){
                        exist = true;
                        break;
                    }
                if(!exist)
                    courses.add(that);
            }
            else if (o instanceof Venue){
                Venue that = (Venue) o;
                for (Venue v : venues)
                    if(v.equals(that)){
                        exist = true;
                        break;
                    }
                if(!exist)
                    venues.add(that);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void remove(Object o) {
        try {
            if(o instanceof Lecturer){
                // for (Section s : sections) {
                // 	if(s.getLecturer().equals(o))
                // 		s.setLecturer(new Lecturer("TO BE DETERMINED"));
                // }
                // lecturers.remove(o);
                Lecturer lect = (Lecturer) o;
                lect.setName("TO BE DETERMINED");
            }
            else if (o instanceof Course) {
                ArrayList<Section> tempSections = new ArrayList<>();
                for (Section s : sections) {
                    if(s.getCourse().equals(o)){
                        tempSections.add(s);
                    }
                }
                for (Section s : tempSections) {
                    sections.remove(s);
                }
                courses.remove(o);
            }
            else if (o instanceof Venue) {
                // for (Section s : sections) {
                // 	if(s.getVenue().equals(o))
                // 		s.setVenue(new Venue("TO BE DETERMINED", ""));
                // }
                // venues.remove(o);
                Venue ven = (Venue) o;
                ven.setName("TO BE DETERMINED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateSections(){
        for (Course course : courses)
            for (int i=0;i<course.getRequiredSections();++i)
                sections.add(new Section(i+1, course, new Lecturer("TO BE DETERMINED")));
    }
}
