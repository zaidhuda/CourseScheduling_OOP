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
		lecturers.add(new Lecturer("Dr Norsaremah", new ArrayList<String>(java.util.Arrays.asList("CSC1100", "CSC1102", "CSC1103", "CSC1104"))));

		venues.add(new Venue("Lab 3", "LAB"));
		venues.add(new Venue("Lab 6", "LAB"));
		venues.get(0).addCourse("CSC1102");
		venues.get(1).addCourse("CSC1100");
		venues.get(1).addCourse("CSC1103");

		// System.out.println(venues);

		// create sections based on specialization
		generateSections();

		for (Section s : sections) {
			s.setVenue(venues);
			// System.out.println(s);
		}
		// try{
		// 	remove(lecturers.get(1));
		// }
		// catch(Exception e){}
		// remove(courses.get(2));
		// remove(venues.get(1));
		sections.get(1).setVenue(venues.get(0));
		System.out.println(sections.get(1));

		for (Section s : sections) {
			if(!s.generateSchedule(sections, true))
				System.out.println("Failed.");
			System.out.println(s);
		}

		for (Section s : sections) {
			if(s.getCourse().equals(new Course("csc1103"))){
				System.out.println(s.getDay_inWords() + ", " + s.getTime_inWords());
			}
		}

		// for (Section s : sections) {
		// 	if(s.getCourse().equals(new Course("csc 1 1 0 2")) || s.getLecturer().equals(new Lecturer("Dr Norsaremah")))
		// 		System.out.println(s);
		// }

		// System.out.println(lecturers);
	}

	public static void remove(Object o){
		try{
			if(o instanceof Lecturer){
				for (Section s : sections) {
					if(s.getLecturer().equals(o))
						s.setLecturer(new Lecturer("NA"));
				}
				lecturers.remove(o);
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
				for (Section s : sections) {
					if(s.getVenue().equals(o))
						s.setVenue(new Venue("NA", ""));
				}
				venues.remove(o);
			}
		}
		catch(Exception e){} // Action
	}

	public static void generateSections(){
		// main loop, creating sections for each course
		for (Course course : courses) {
			ArrayList<Lecturer> tempLecturers = new ArrayList<Lecturer>();

			// add lecturers of current course to temporary list
			for (Lecturer lecturer : lecturers)
				if(lecturer.getSpecialization().contains(course.getCode()))
					tempLecturers.add(lecturer);

			// randomly assign lecturer to sections
			for (int i=0;i<course.getRequiredSections();++i) {
				Lecturer lecturer = tempLecturers.get(i%tempLecturers.size());
				sections.add(new Section(course, lecturer, i+1));
			}
			tempLecturers.clear();
		}
	}
}