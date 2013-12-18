package courseschedule.util;

import courseschedule.*;

import java.util.*;

/**
 * Created by Diaz
 * Package: ${PACKAGE_NAME}
 * Time: 12:50 AM
 * Date: 12/8/13.
 */
public class ScheduleBuilder {
	private final int ROW = 2;
	private final int COL = 6;
	public ArrayList<Course> courses = new ArrayList<>();
	public ArrayList<Lecturer> lecturers = new ArrayList<>();
	public ArrayList<Venue> venues = new ArrayList<>();
	public ArrayList<Section> sections = new ArrayList<>();
	public final CourseComparator courseComparator = new CourseComparator();
	public final VenueComparator venueComparator = new VenueComparator();

	/**
	 * Generate a certain number of sections for every courses depending on their required sections.
	 * If generateSchedule is true, this method will try to generate schedule of the sections, given
	 * that there exist at least one venue and lecturer for the course or it will only generate the
	 * time.
	 *
	 * @param generateSchedule boolean to determine to generate schedule or not.
	 */
	public void generateSections(boolean generateSchedule) {

		for (Section s : sections) {
			if (s.getDay() != -1 && s.getTime() != -1) {
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
			for (Section s : sections) {
				if (venues != null)
					s.setVenue(venues, true);
				if (lecturers != null)
					s.setLecturer(lecturers, false);
				s.generateSchedule(true);
			}
		}

	}

	/**
	 *
	 * @param generateSchedule
	 * @param course
	 * @param required
	 * @param count
	 */
	public void generateSection(boolean generateSchedule, Course course, int required, int count) {
		for (int i = count; i < required; ++i) {
			sections.add(new Section(i + 1, course, new Lecturer(), new Venue()));
		}

		if (generateSchedule) {
			for (Section s : sections) {
				if (venues != null)
					s.setVenue(venues, true);
				if (lecturers != null)
					s.setLecturer(lecturers, false);
				s.generateSchedule(true);
			}
		}
	}

	private static class CourseComparator implements Comparator<Section> {
		public int compare(Section o1, Section o2) {
			final int diff = o1.getCourse().getCode().compareTo(o2.getCourse().getCode());
			if (diff == 0) {
				return o1.getSectionNum().compareTo(o2.getSectionNum());
			} else
				return diff;
		}
	}

	public static class VenueComparator implements Comparator<Venue>{
		public  int compare(Venue v1, Venue v2){
			return v1.getName().compareTo(v2.getName());
		}
	}

	public void add(Course o) {
		boolean exist = false;
		for (Course c : courses)
			if (o.getCode().equals(c.getCode())) {
				exist = true;
				break;
			}
		if (!exist) {
			courses.add(o);
		} else {
			for (Course c : courses) {
				if (!c.equals(o) && o.getCode().equals(c.getCode())) {
					courses.remove(o);
					break;
				}
			}
		}
	}

	public void remove(Course o) {
		ArrayList<Section> tempSections = new ArrayList<>();
		for (Section s : getSectionOf(o)){
			tempSections.add(s);
			int day = s.getDay();
			int time = s.getTime();
			if (day != -1 && time != -1){
				s.getLecturer().setAvailabilityAt(day, time, true);
				s.getVenue().setAvailabilityAt(day, time, true);
			}
		}
		for (Lecturer l : getAssignedLecturers(o))
			l.removeCourse(o.getCode());
		for (Venue v : getAssignedVenues(o))
			v.removeCourse(o.getCode());
		sections.removeAll(tempSections);
		courses.remove(o);
	}

	public void add(Lecturer o) {
		if (o.getName().equals("TBD"))
			return;
		boolean exist = false;
		for (Lecturer l : lecturers)
			if (l.equals(o)) {
				exist = true;
				break;
			}
		if (!exist) {
			lecturers.add(o);
		} else {
			for (Section s : sections)
				if (s.getLecturer().equals(o))
					if (!o.getCourses().contains(s.getCourse().getCode()))
						s.setLecturer(new Lecturer());
		}
	}

	public Lecturer getLecturerByName(String name) {
		for (Lecturer l : lecturers)
			if (l.getName().equals(name))
				return l;
		return null;
	}

	public void remove(Lecturer o) {
		for (Section s : sections)
			if (s.getLecturer().equals(o))
				s.setLecturer(new Lecturer());
		lecturers.remove(o);
	}

	public void add(Venue o) {
		if (o.getName().equals("TBD"))
			return;
		boolean exist = false;
		for (Venue v : venues)
			if (v.equals(o)) {
				exist = true;
				break;
			}
		if (!exist) {
			venues.add(o);
		} else {
			for (Section s : sections)
				if (s.getVenue().equals(o))
					if (!o.getCourses().contains(s.getCourse().getCode()))
						s.setVenue(new Venue());
		}
	}

	public Venue getVenueByName(String name) {
		for (Venue v : venues)
			if (v.getName().equals(name))
				return v;
		return null;
	}

	public void remove(Venue o) {
		for (Section s : sections)
			if (s.getVenue().equals(o))
				s.setVenue(new Venue());
		venues.remove(o);
	}

	private ArrayList<String> getCourseCodes() {
		ArrayList<String> tempCodes = new ArrayList<>();
		for (Course c : courses) {
			tempCodes.add(c.getCode());
		}
		return tempCodes;
	}

	public ArrayList<Course> getCourses(String[] codes) {
		ArrayList<Course> tempCourses = new ArrayList<>();
		ArrayList<String> tempCodes = new ArrayList<>(Arrays.asList(codes));
		for (Course c : courses) {
			if (tempCodes.contains(c.getCode()))
				tempCourses.add(c);
		}
		return tempCourses;
	}

	/**
	 * String arrays used to build data tables.
	 * @return
	 */
	public String[][] getCourses() {
		String[][] str = new String[courses.size()][];
		for (int i = 0; i < courses.size(); i++) {
			String[] arr = courses.get(i).detailsArray();
			str[i] = arr;
		}
		return str;
	}

	public String[][] getCodeAndTitle() {
		String[][] str = new String[courses.size()][];
		for (int i = 0; i < courses.size(); i++) {
			String[] arr = courses.get(i).getCodeandTitle();
			str[i] = arr;
		}
		return str;
	}

	public String[][] getLecturers() {
		String[][] str = new String[lecturers.size()][];
		for (int i = 0; i < lecturers.size(); i++) {
			String[] arr = lecturers.get(i).detailsArray();
			str[i] = arr;
		}
		return str;
	}

	public String[][] getVenues() {
		Collections.sort(venues, venueComparator);
		String[][] str = new String[venues.size()][];
		for (int i = 0; i < venues.size(); i++) {
			String[] arr = venues.get(i).detailsArray();
			str[i] = arr;
		}
		return str;
	}

	public String[][] getSections() {
		Collections.sort(sections, courseComparator);
		String[][] str = new String[sections.size()][];
		for (int i = 0; i < sections.size(); i++) {
			String[] arr = sections.get(i).detailsArray();
			str[i] = arr;
		}
		return str;
	}

	/**
	 * Get the the available time slots of lecturer and venue
	 * @param lecturer  lecturer to compare to venue
	 * @param venue     venue to compare to lecturer
	 * @return          return 2D int arrays representing availability of both using int
	 */
	public static int[][] getAvailableSlots(Lecturer lecturer, Venue venue) {
		final int MAX_ROW = 2, MAX_COL = 6;
		int[][] newAvailability = new int[MAX_ROW][MAX_COL];
		if (lecturer == null)
			lecturer = new Lecturer();
		if (venue == null)
			venue = new Venue();

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

	/**
	 * Get corresponding section at day and time by lecturer
	 * @param day           the day
	 * @param time          the time
	 * @param theLecturer   the lecturer
	 * @return              returns the section if found or null if such doesn't exist
	 */
	public Section getClassAt(int day, int time, Lecturer theLecturer) {
		for (Section s : sections) {
			boolean sameTime = (day == s.getDay() && time == s.getTime()),
					sameLect = s.getLecturer().equals(theLecturer);
			if (sameTime && sameLect) {
				return s;
			}
		}
		return null;
	}

	/**
	 * Get corresponding section at day and time by venue
	 * @param day           the day
	 * @param time          the time
	 * @param theVenue      the venue
	 * @return              returns the section if found or null if such doesn't exist
	 */
	public Section getClassAt(int day, int time, Venue theVenue) {
		for (Section s : sections) {
			boolean sameTime = (day == s.getDay() && time == s.getTime()),
					sameVenue = s.getVenue().equals(theVenue);
			if (sameTime && sameVenue) {
				return s;
			}
		}
		return null;
	}

	/**
	 * Get the section at day, time with the lecturer at the venue
	 * @param day           the day
	 * @param time          the time
	 * @param theLecturer   the lecturer
	 * @param theVenue      the venue
	 * @return              returns the section if found or null if not found
	 */
	public Section findSection(int day, int time, Lecturer theLecturer, Venue theVenue) {
		for (Section s : sections) {
			if (s.getDay() == day && s.getTime() == time)
				if (s.getLecturer().equals(theLecturer) || s.getVenue().equals(theVenue))
					return s;
		}
		return null;
	}

	/**
	 * Fix clashes when lecturer changes free times.
	 * @param lecturer  the lecturer
	 * @param conflicts boolean representing time of conflict
	 */
	public void fixClash(Lecturer lecturer, boolean[][] conflicts) {
		if (lecturers.contains(lecturer))
			for (int i = 0; i < ROW; i++)
				for (int j = 0; j < COL; j++)
					if (conflicts[i][j]) {
						Section s = getClassAt(i, j, lecturer);
						if (s != null)
							s.generateSchedule(true);
					}
	}

	/**
	 * Fix clashes when venue changes free times.
	 * @param venue  the lecturer
	 * @param conflicts boolean representing time of conflict
	 */
	public void fixClash(Venue venue, boolean[][] conflicts) {
		if (venues.contains(venue))
			for (int i = 0; i < ROW; i++)
				for (int j = 0; j < COL; j++)
					if (conflicts[i][j]) {
						Section s = getClassAt(i, j, venue);
						if (s != null)
							s.generateSchedule(true);
					}
	}

	/**
	 * Fix number of sections of a course. If the required section is less than current
	 * sections of the course, remove excessive sections in reverse order. Else, generate
	 * required section to meet the demand.
	 * @param course the respective course
	 */
	public void fixCourseSections(Course course) {
		int newRequired = course.getRequiredSections();
		ArrayList<Section> tempSections = getSectionOf(course);
		int count = tempSections.size();
		if (count != newRequired) {
			if (newRequired < count) {
				Collections.reverse(tempSections);
				for (Section s : tempSections){
					sections.remove(s);
					int day = s.getDay(), time = s.getTime();
					if (day != -1 && time != -1){
						s.getLecturer().setAvailabilityAt(day, time, true);
						s.getVenue().setAvailabilityAt(day, time, true);
					}
				}
			} else {
				generateSection(true, course, newRequired, count);
			}
		}
	}

	/**
	 * Fix all number of sections of all courses.
	 */
	public void fixCourseSections() {
		Collections.sort(sections, courseComparator);
		for (Course c : courses) {
			fixCourseSections(c);
		}
	}

	public void fixCoursesChange(Support o, ArrayList<Course> courses){
		o.getCoursesList().removeAll(courses);
		for (Course c : o.getCoursesList())
			for (Section s : getSectionOf(c)){
				if (o.equals(s.getLecturer()) || o.equals(s.getVenue()))
					o.setAvailabilityAt(s.getDay(), s.getTime(), true);
			}
		System.out.println(o.getCoursesList().toString());
	}

	/**
	 * Don't know la
	 * @param newDay
	 * @param newTime
	 * @param theSection
	 */
	public void forceReSchedule(int newDay, int newTime, Section theSection) {
		Lecturer theLecturer = theSection.getLecturer();
		Venue theVenue = theSection.getVenue();
		for (Section s : sections) {
			if (!s.equals(theSection) && (newDay == s.getDay() && newTime == s.getTime())) {
				Lecturer l = s.getLecturer();
				Venue v = s.getVenue();

				if (l.equals(theLecturer))
					s.getLecturer().setAvailabilityAt(newDay, newTime, true);
				if (v.equals(theVenue))
					s.getVenue().setAvailabilityAt(newDay, newTime, true);

				s.setDayAndTime(-1, -1);
				theSection.setDayAndTime(newDay, newTime);
				s.generateSchedule(true);
				return;
			}
		}
	}

	/**
	 * Get all assigned lecturers of a course.
	 * @param course    the respective course
	 * @return          all assigned lecturers
	 */
	public ArrayList<Lecturer> getAssignedLecturers(Course course) {
		String courseCode = course.getCode();
		ArrayList<Lecturer> tempLecturers = new ArrayList<>();
		for (Lecturer l : lecturers)
			if (l.getCourses().contains(courseCode))
				tempLecturers.add(l);
		return tempLecturers;
	}

	/**
	 * Get all assigned lecturers of a venue
	 * @param course    guess what..
	 * @return          the assigned venue
	 */
	public ArrayList<Venue> getAssignedVenues(Course course) {
		String courseCode = course.getCode();
		ArrayList<Venue> tempVenues = new ArrayList<>();
		for (Venue v : venues)
			if (v.getCourses().contains(courseCode))
				tempVenues.add(v);
		return tempVenues;
	}

	/**
	 * Get all sections of a course.
	 * @param course    the respective course
	 * @return          all the sections of that course
	 */
	public ArrayList<Section> getSectionOf(Course course) {
		ArrayList<Section> tempSections = new ArrayList<>();
		for (Section s : sections)
			if (s.getCourse().equals(course))
				tempSections.add(s);
		return tempSections;
	}
}