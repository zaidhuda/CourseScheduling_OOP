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

		courses.add(new Course("CSC1100", "Elements of Programming", 3));
		courses.add(new Course("CSC1102", "Web Programming Fundamental", 3));
		courses.add(new Course("CSC1103", "Object Oriented Programming", 3));

		lecturers.add(new Lecturer("Dr Suriani"));
		lecturers.add(new Lecturer("Dr Amelia"));
		lecturers.add(new Lecturer("Dr Rizal"));
		lecturers.add(new Lecturer("Dr Azlin"));
		lecturers.add(new Lecturer("Dr Norsaremah"));

		venues.add(new Venue("Lab 3"));
		venues.add(new Venue("Lab 6"));

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

		System.out.println(courses);
		System.out.println(courses.contains(new Course("CSC1100", "Elements of Programming", 3)));
		System.out.println(lecturers);
		System.out.println(lecturers.contains(new Lecturer("Dr Amelia")));
		System.out.println(venues);
		System.out.println(venues.contains(new Venue("Lab 3")));
	}

	public static int findLecturer(String n){
		Iterator itr = lecturers.iterator();
		String name;
		for(int i=0;itr.hasNext();++i){
			try{
				name = lecturers.get(i).getName().toLowerCase();
				if (name.equals(n.toLowerCase()))
					return i;
			}
			catch(Exception e){break;}
		}
		return -1;
	}

	public static int findVenue(String n){
		Iterator itr = venues.iterator();
		String name;
		for(int i=0;itr.hasNext();++i){
			try{
				name = venues.get(i).getName().toLowerCase();
				if (name.equals(n.toLowerCase()))
					return i;
			}
			catch(Exception e){break;}
		}
		return -1;
	}

	public static int findCourse(String c){
		Iterator itr = courses.iterator();
		String code;
		for(int i=0;itr.hasNext();++i){
			try{
				code = courses.get(i).getCode().toLowerCase();
				if (code.equals(c.toLowerCase()))
					return i;
			}
			catch(Exception e){break;}
		}
		return -1;
	}
}