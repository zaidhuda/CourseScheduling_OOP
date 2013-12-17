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

	public void generateSections(boolean generateSchedule) {

		for (Section s : sections) {
			if (s.getDay() != -1 || s.getTime() != -1) {
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

	public void generateSection(boolean generateSchedule, Course course, int required, int count) {
		for (int i = count; i < required + count; ++i) {
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
		for (Section s : sections)
			if (s.getCourse().equals(o)){
				tempSections.add(s);
				int day = s.getDay();
				int time = s.getTime();
				s.getLecturer().setAvailabilityAt(day, time, true);
				s.getVenue().setAvailabilityAt(day, time, true);
			}
		for (Lecturer l : lecturers)
			if (l.getCourses().contains(o.getCode()))
				l.removeCourse(o.getCode());
		for (Venue v : venues)
			if (v.getCourses().contains(o.getCode()))
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

	public String[][] getCourses() {
		String[][] str = new String[courses.size()][];
		for (int i = 0; i < courses.size(); i++) {
			String[] arr = courses.get(i).detailsArray();
			str[i] = arr;
		}
		return str;
	}

	public String[][] getCodeandTitle() {
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

	public static int[][] getAvailableSlots(Lecturer lecturer, Venue venue) {
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

	public Section findSection(int day, int time, Lecturer theLecturer, Venue theVenue) {
		for (Section s : sections) {
			if (s.getDay() == day && s.getTime() == time)
				if (s.getLecturer().equals(theLecturer) || s.getVenue().equals(theVenue))
					return s;
		}
		return null;
	}

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

	public void fixCourseSections(Course course) {
		int index = 0, newRequired = course.getRequiredSections();
		ArrayList<Section> tempSections = getSectionOf(course);
		int count = tempSections.size();
		if (count != newRequired) {
			for (Section s : tempSections)
				s.setSectionNum(++index);
			if (newRequired < tempSections.size()) {
				sections.removeAll(tempSections);
				for (int i = count; i > newRequired; i--) {
					int newIndex = i - 1;
					Section s = tempSections.get(newIndex);
					int day = s.getDay(), time = s.getTime();
					if (s.getLecturer() != null)
						s.getLecturer().setAvailabilityAt(day, time, true);
					if (s.getVenue() != null)
						s.getVenue().setAvailabilityAt(day, time, true);
					tempSections.remove(newIndex);
				}
				for (Section s : tempSections)
					sections.add(s);
			} else {
				generateSection(true, course, newRequired - count, count);
			}
		}
	}

	public void fixCourseSections() {
		Collections.sort(sections, courseComparator);
		for (Course c : courses) {
			fixCourseSections(c);
		}
	}

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

	public ArrayList<Lecturer> getAssignedLecturers(Course course) {
		String courseCode = course.getCode();
		ArrayList<Lecturer> tempLecturers = new ArrayList<>();
		for (Lecturer l : lecturers)
			if (l.getCourses().contains(courseCode))
				tempLecturers.add(l);
		return tempLecturers;
	}

	public ArrayList<Venue> getAssignedVenues(Course course) {
		String courseCode = course.getCode();
		ArrayList<Venue> tempVenues = new ArrayList<>();
		for (Venue v : venues)
			if (v.getCourses().contains(courseCode))
				tempVenues.add(v);
		return tempVenues;
	}

	public ArrayList<Section> getSectionOf(Course course) {
		ArrayList<Section> tempSections = new ArrayList<>();
		for (Section s : sections)
			if (s.getCourse().equals(course))
				tempSections.add(s);
		return tempSections;
	}
}