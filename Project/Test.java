import java.util.*;
import java.util.ArrayList;

public class Test {
	public static ArrayList<Course> courses = new ArrayList<Course>();
	public static ArrayList<Lecturer> lecturers = new ArrayList<Lecturer>();
	public static ArrayList<Venue> venues = new ArrayList<Venue>();
	public static ArrayList<Schedule> schedules = new ArrayList<Schedule>();
	public static ArrayList<Section> sections = new ArrayList<Section>();

	public static void main(String[] args) {

		Course course;
		Lecturer lecturer;
		Venue venue;
		Schedule schedule;
		Section section;

		courses.add(new Course("CSC1100", "Elements of Programming", 3, 4));
		courses.add(new Course("CSC1102", "Web Programming Fundamental", 3, 1));
		courses.add(new Course("CSC1103", "Object Oriented Programming", 3, 3));

		lecturers.add(new Lecturer("Dr Suriani", "CSC1100"));
		lecturers.add(new Lecturer("Dr Amelia", "CSC1100"));
		lecturers.add(new Lecturer("Dr Rizal", "CSC1102"));
		lecturers.add(new Lecturer("Dr Azlin", "CSC1103"));
		lecturers.add(new Lecturer("Dr Norsaremah", "CSC1103"));

		venues.add(new Venue("Lab 3"));
		venues.add(new Venue("Lab 6"));

		// create sections based on specialization
		generateSections();

		System.out.println(sections);

		// schedule = new Schedule(sections.get(0), venues.get(0), 1);
		// schedules.add(schedule);
		// schedule = new Schedule(sections.get(1), venues.get(1), 2);
		// schedules.add(schedule);
		
		// System.out.println(schedules);
		// System.out.println(lecturers.get(findLecturer("Dr Saremah")));
		// System.out.println(venues.get(findVenue("Lab 6")));
		// System.out.println(courses.get(findCourse("CSC1100")));

		// CourseForm courseform = new CourseForm(courses);
		// MainForm mainform = new MainForm(courses);

		// System.out.println("Courses: "+courses);
		// System.out.println("Lecturers: "+lecturers);
		// System.out.println("Venues: "+venues);
	}

	public static void generateSections(){
		Course course;
		Lecturer lecturer;

		// main loop, creating sections for each course
		for (int i=0;i<courses.size();++i) {
			course = courses.get(i);
			ArrayList<Lecturer> tempLecturers = new ArrayList<Lecturer>();

			// add lecturers of current course to temporary list
			for (int j=0;j<lecturers.size();++j) {
				lecturer = lecturers.get(j);
				if(lecturer.getSpecialization().contains(course.getCode()))
					tempLecturers.add(lecturer);
			}

			int sects = courses.get(i).getRequiredSections();
			// int sects = 10;
			// randomly assign lecturer to sections
			for (int j=0;j<sects;++j) {
				lecturer = tempLecturers.get(j%tempLecturers.size());
				sections.add(new Section(course, lecturer, j+1));
			}
			tempLecturers.clear();
		}
	}
}