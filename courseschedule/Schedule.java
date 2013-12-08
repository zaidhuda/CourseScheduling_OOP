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
    public final CourseComparator courseComparator = new CourseComparator();

    public static class CourseComparator implements Comparator<Section> {
        public int compare(Section o1, Section o2) {
            final int diff = o1.getCourse().getCode().compareTo(o2.getCourse().getCode());
            if(diff == 0){
                return o1.getSectionNum().compareTo(o2.getSectionNum());
            }
            else
                return diff;
        }
    }

    public static void add(Course o) {
        boolean exist = false;
        for (Course c : courses)
            if(c.equals(o)){
                exist = true;
                break;
            }
        if(!exist)
            courses.add(o);
    }

    public static void add(Lecturer o) {
        boolean exist = false;
        for (Lecturer l : lecturers)
            if(l.equals(o)){
                exist = true;
                break;
            }
        if(!exist)
            lecturers.add(o);
    }

    public static void add(Venue o) {
        boolean exist = false;
        for (Venue v : venues)
            if(v.equals(o)){
                exist = true;
                break;
            }
        if(!exist)
            venues.add(o);
    }

    public static void remove(Course o) {
        ArrayList<Section> tempSections = new ArrayList<>();
        for (Section s : sections) {
            if(s.getCourse().equals(o)){
                tempSections.add(s);
            }
        }
        sections.removeAll(tempSections);
        courses.remove(o);
    }

    public static void remove(Lecturer o) {
        o.setName("TO BE DETERMINED");
    }

    public static void remove(Venue o) {
        o.setName("TO BE DETERMINED");
    }

    public static void generateSections(){
        sections.clear();
        for (Course course : courses)
            for (int i=0;i<course.getRequiredSections();++i){
                sections.add(new Section(i + 1, course, new Lecturer(), new Venue()));
            }
    }
}
