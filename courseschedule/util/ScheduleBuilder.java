package courseschedule.util;

import courseschedule.Course;
import courseschedule.Lecturer;
import courseschedule.Section;
import courseschedule.Venue;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Diaz
 * Package: ${PACKAGE_NAME}
 * Time: 12:50 AM
 * Date: 12/8/13.
 */
public class ScheduleBuilder {

    public ArrayList<Course> courses = new ArrayList<>();
    public ArrayList<Lecturer> lecturers = new ArrayList<>();
    public ArrayList<Venue> venues = new ArrayList<>();
    public ArrayList<Section> sections = new ArrayList<>();
    public final CourseComparator courseComparator = new CourseComparator();

    public void generateSections(boolean generateSchedule) {

        for (Section s : sections){
	        if (s.getDay() != -1 || s.getTime() != -1){
                s.getVenue().setAvailabilityAt(s.getDay(), s.getTime(), true);
                s.getLecturer().setAvailabilityAt(s.getDay(), s.getTime(), true);
            }
        }

        sections.clear();

        for (Course course : courses)
            for (int i = 0; i < course.getRequiredSections(); ++i) {
                sections.add(new Section(i + 1, course, new Lecturer(), new Venue()));
            }

	    if (generateSchedule) {
		    for (Section s : sections){
		        if (venues != null)
				    s.setVenue(venues, true);
			    if (lecturers != null)
					s.setLecturer(lecturers, false);
			    s.generateSchedule(true);
		    }
	    }

    }

    private class CourseComparator implements Comparator<Section> {
        public int compare(Section o1, Section o2) {
            final int diff = o1.getCourse().getCode().compareTo(o2.getCourse().getCode());
            if (diff == 0) {
                return o1.getSectionNum().compareTo(o2.getSectionNum());
            } else
                return diff;
        }
    }

    public void add(Course o) {
        boolean exist = false;
        for (Course c : courses)
            if (c.equals(o)) {
                exist = true;
                break;
            }
        if (!exist) courses.add(o);
    }

    public void add(Lecturer o) {
        boolean exist = false;
        for (Lecturer l : lecturers)
            if (l.equals(o)) {
                exist = true;
                break;
            }
        if (!exist) lecturers.add(o);
    }

    public void add(Venue o) {
        boolean exist = false;
        for (Venue v : venues)
            if (v.equals(o)) {
                exist = true;
                break;
            }
        if (!exist) venues.add(o);
    }

    public void remove(Course o) {
        ArrayList<Section> tempSections = new ArrayList<>();
        for (Section s : sections)
            if (s.getCourse().equals(o))
                tempSections.add(s);
	    for (Lecturer l : lecturers)
		    if (l.getCourses().contains(o.getCode()))
			    l.removeCourse(o.getCode());
	    for (Venue v : venues)
		    if (v.getCourses().contains(o.getCode()))
			    v.removeCourse(o.getCode());
        sections.removeAll(tempSections);
        courses.remove(o);
    }

    public void remove(Lecturer o) {
        for (Section s : sections)
            if (s.getLecturer().equals(o))
                s.setLecturer(new Lecturer());
        lecturers.remove(o);
    }

    public void remove(Venue o) {
        for (Section s : sections)
            if (s.getVenue().equals(o))
                s.setVenue(new Venue());
        venues.remove(o);
    }

    public int[][] getAvailableSlots(Lecturer lecturer, Venue venue) {
        final int MAX_ROW = 2, MAX_COL = 6;
        int[][] newAvailability = new int[MAX_ROW][MAX_COL];

        for (int i = 0; i < MAX_ROW; ++i) {
            for (int j = 0; j < MAX_COL; ++j) {
                if (lecturer.isAvailableAt(i, j))
                    newAvailability[i][j] = 1;
                if (venue.isAvailableAt(i, j))
                    newAvailability[i][j] = 2;
	            if (lecturer.isAvailableAt(i, j) && venue.isAvailableAt(i, j))
		            newAvailability[i][j] = 0;
                if (!lecturer.isAvailableAt(i, j) && !venue.isAvailableAt(i, j))
                    newAvailability[i][j] = 3;
            }
        }
        return newAvailability;
    }

	public Section getClassAt(int day, int time, Lecturer theLecturer, ArrayList<Section> sections){
		for(Section s : sections){
			boolean sameTime = (day == s.getDay() && time == s.getTime()),
					sameLect = s.getLecturer().equals(theLecturer);
			if(sameTime && sameLect){
				return s;
			}
		}
		return null;
	}

	public Section getClassAt(int day, int time, Venue theVenue, ArrayList<Section> sections){
		for(Section s : sections){
			boolean sameTime = (day == s.getDay() && time == s.getTime()),
					sameVenue = s.getVenue().equals(theVenue);
			if(sameTime && sameVenue){
				return s;
			}
		}
		return null;
	}

	public void forceReSchedule(int day, int time, Section theSection, ArrayList<Section> sections){
		Lecturer theLecturer = theSection.getLecturer();
		Venue theVenue = theSection.getVenue();
		for (Section s : sections){
			int t = s.getTime(), d = s.getDay();
			if (!s.equals(theSection) && (time == t && d == day)) {
				Lecturer l = s.getLecturer();
				Venue v = s.getVenue();

				if (l.equals(theLecturer))
					s.getLecturer().setAvailabilityAt(day, time, true);
				if (v.equals(theVenue))
					s.getVenue().setAvailabilityAt(day, time, true);

				s.setDayAndTime(-1, -1);
				theSection.setDayAndTime(day, time);
				s.generateSchedule(true);
				return;
			}
		}
	}

	public ArrayList<Lecturer> getAssignedLecturers(Section theSection){
		String courseCode = theSection.getCourse().getCode();
		ArrayList<Lecturer> tempLecturers = new ArrayList<>();
		for (Lecturer l : lecturers)
			if(l.getCourses().contains(courseCode))
				tempLecturers.add(l);
		return tempLecturers;
	}

	public ArrayList<Venue> getAssignedVenues(Section theSection){
		String courseCode = theSection.getCourse().getCode();
		ArrayList<Venue> tempVenues = new ArrayList<>();
		for (Venue v : venues)
			if(v.getCourses().contains(courseCode))
				tempVenues.add(v);
		return tempVenues;
	}
}
