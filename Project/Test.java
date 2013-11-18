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

		course = new Course("CSC1100", "Elements of Programming", 3);
		courses.add(course);
		lecturer = new Lecturer("Dr Suriani");
		lecturers.add(lecturer);
		lecturer = new Lecturer("Dr Saremah");
		lecturers.add(lecturer);
		section = new Section(course, lecturers.get(0), 1, 30);
		sections.add(section);
		section = new Section(course, lecturers.get(1), 2, 30);
		sections.add(section);
		venue = new Venue("Lab 6");
		venues.add(venue);
		venues.add(new Venue("Lab 7"));
		schedule = new Schedule(sections.get(0), venues.get(0), 1);
		schedules.add(schedule);
		schedule = new Schedule(sections.get(1), venues.get(1), 2);
		schedules.add(schedule);
		
		System.out.println(schedules);
		System.out.println(lecturers.get(findLecturer("Dr Saremah")));
		System.out.println(venues.get(findVenue("Lab 6")));
		System.out.println(courses.get(findCourse("CSC1100")));
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