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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {
	public static ArrayList<Course> courses = new ArrayList<>();
	public static ArrayList<Lecturer> lecturers = new ArrayList<>();
	public static ArrayList<Venue> venues = new ArrayList<>();
	public static ArrayList<Section> sections = new ArrayList<>();

	public static CourseComparator courseComparator = new CourseComparator();

	public static void main(String[] args) throws Exception {

        boolean[][] availability = new boolean[2][6];
        Arrays.fill(availability[0], true);
        Arrays.fill(availability[1], true);

		courses.add(new Course("CSC1100", "Elements of Programming", 3, 6));
		courses.add(new Course("CsC1102", "Web Programming Fundamental", 3, 1));
		courses.add(new Course("CSC1103", "Object Oriented Programming", 3, 7));

		lecturers.add(new Lecturer("Dr Suriani", "CSC1100", availability));
		lecturers.add(new Lecturer("Dr Amelia", "CSC1100", availability));
		lecturers.add(new Lecturer("Dr Rizal", "CSC1102", availability));
		lecturers.add(new Lecturer("Dr Azlin", "CSC1103", availability));
		lecturers.add(new Lecturer("Dr Norsaremah", new ArrayList<>(java.util.Arrays.asList("CSC1100", "CSC1102", "CSC1103", "CSC1104"))));

		venues.add(new Venue("Lab 3", availability));
		venues.add(new Venue("Lab 6", availability));
		venues.get(0).addCourse("CSC1102");
		venues.get(1).addCourse("CSC1100");
		venues.get(1).addCourse("CSC1103");
        venues.get(1).removeCourse("lalalala");
        System.out.println(venues.get(1));
        // System.out.println(venues);
		// create sections based on specialization
		generateSections();

		for (Section s : sections) {
			s.setVenue(venues);
			// System.out.println(s);
		}

		for (Section s : sections) {
			s.setLecturer(lecturers, false);
			// System.out.println(s);
		}
		sections.get(4).setStudentLimit(50);

//		long seed = System.nanoTime();
//		Collections.shuffle(sections, new Random(seed));

        for (Section s : sections) {
            s.generateSchedule(false);
            System.out.println(s);
        }

        for (Section s : sections) {
            s.generateSchedule(true);
            System.out.println(s);
        }

//		Collections.sort(sections, courseComparator);
//
//		for (Section s : sections) {
//			System.out.println(s);
//		}

		// System.out.println(courses.get(0).compareTo(courses.get(1)));

		save();
		// FetchData fd = new FetchData(courses);
	}

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

	public static void save(){

		File file = new File("text.txt");
		String content;

		try (FileOutputStream fop = new FileOutputStream(file)){

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fop.write("// Courses\n".getBytes());
			fop.flush();

			// get content of courses
			for (Course c : courses) {
				content = c.getCode();
				content += ";" + c.getTitle();
				content += ";" + c.getCredit();
				content += ";" + c.getRequiredSections() + "\n";

				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
			}

			fop.write("// Lecturers\n".getBytes());
			fop.flush();

			// get content of lecturers
			for (Lecturer l : lecturers) {
				content = l.getName();
				content += ";" + l.getSpecializations();
				content += ";" + l.getAvailability_inWords() + "\n";

				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
			}

			fop.write("// Venues\n".getBytes());
			fop.flush();

			// get content of venues
			for (Venue v : venues) {
				content = v.getName();
				content += ";" + v.getCourses();
				content += ";" + v.getAvailability_inWords() + "\n";

				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
			}

			fop.write("// Sections\n".getBytes());
			fop.flush();

			// get content of sections
			for (Section s : sections) {
				content = s.getDay() + ";" + s.getTime();
				content += ";" + s.getStudentLimit();
				content += ";" + s.getSectionNum();
				content += ";" + s.getCourse().getCode();
				content += ";" + s.getLecturer().getName();
				content += ";" + s.getVenue().getName() + "\n";

				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
			}
			
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void add(Object o) throws Exception {
		boolean exist = false;
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

    }

	public static void remove(Object o) throws Exception {
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
    }

	public static void generateSections(){
		for (Course course : courses)
			for (int i=0;i<course.getRequiredSections();++i)
				sections.add(new Section(i+1, course, new Lecturer("TO BE DETERMINED")));
	}
}