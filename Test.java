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

import java.io.*;
import java.util.*;

public class Test extends Schedule{

	public static void main(String[] args) {

//        boolean[][] availability = new boolean[2][6];
//        Arrays.fill(availability[0], true);
//        Arrays.fill(availability[1], true);
//
//        courses.add(new Course("CSC1100", "Elements of Programming", 3, 3));
//        courses.add(new Course("CsC1102", "Web Programming Fundamental", 3, 1));
//        courses.add(new Course("CSC1103", "Object Oriented Programming", 3, 2));
//
//        lecturers.add(new Lecturer("Dr Suriani", "CSC1100", availability));
//        lecturers.add(new Lecturer("Dr Amelia", "CSC1100", availability));
//        lecturers.add(new Lecturer("Dr Rizal", "CSC1102", availability));
//        lecturers.add(new Lecturer("Dr Azlin", "CSC1103", availability));
//        lecturers.add(new Lecturer("Dr Norsaremah", new ArrayList<>(java.util.Arrays.asList("CSC1100", "CSC1102", "CSC1103", "CSC1104"))));
//
//        venues.add(new Venue("Lab 3", availability));
//        venues.add(new Venue("Lab 6", availability));
//        venues.get(0).addCourse("CSC1102");
//        venues.get(1).addCourse("CSC1100");
//        venues.get(1).addCourse("CSC1103");
//        venues.get(1).removeCourse("lalalala");
        load();
//        generateSections();
//
//		for (Section s : sections) {
//			s.setVenue(venues);
//			// System.out.println(s);
//		}
//
//		for (Section s : sections) {
//			s.setLecturer(lecturers, false);
//			// System.out.println(s);
//		}
//        sections.get(4).setStudentLimit(50);

//		long seed = System.nanoTime();
//		Collections.shuffle(sections, new Random(seed));

        for (Section s : sections) {
//            s.generateSchedule(false);
            System.out.println(s);
        }

//        for (Section s : sections) {
//            s.generateSchedule(true);
//            System.out.println(s);
//        }

//		save();
		// FetchData fd = new FetchData(courses);
	}

    public static void load(){
        final int COURSE = 0, LECTURER = 1, VENUE = 2, SECTION = 3;
        try (BufferedReader br = new BufferedReader(new FileReader("schedule.txt"))) {
            StringBuilder stringBuilder;
            String fileLine = br.readLine();
            int type = -1;

            while (fileLine != null) {
                stringBuilder = new StringBuilder(fileLine);
                String newLine = stringBuilder.toString();
                String[] splits = newLine.split(";");

                if(splits.length>1) readFile(type, splits);

                if ("courses".equalsIgnoreCase(newLine.replaceAll("// ", ""))) type = COURSE;
                else if ("lecturers".equalsIgnoreCase(newLine.replaceAll("// ", ""))) type = LECTURER;
                else if ("venues".equalsIgnoreCase(newLine.replaceAll("// ", ""))) type = VENUE;
                else if ("sections".equalsIgnoreCase(newLine.replaceAll("// ", ""))) type = SECTION;

                fileLine = br.readLine();
            }
        } catch (Exception ignored) {}
    }

    private static void readFile(int type, String[] splits){
        final int COURSE = 0, LECTURER = 1, VENUE = 2, SECTION = 3;
        switch (type) {
            case COURSE: {
                Course course = new Course(splits[0], splits[1], Integer.parseInt(splits[2]), Integer.parseInt(splits[3]));
                add(course);
                break;
            }
            case LECTURER: { // TODO try combine with venue below
                boolean[][] bool = new boolean[2][6];
                String[] c = splits[1].replaceAll("\\[", "").replaceAll("]", "").split(", ");
                String[] b = splits[2].replaceAll("\\[", "").replaceAll("]", "").split(", ");
                for (int i = 0; i < 2; i++)
                    for (int j = 0; j < 6; j++)
                        bool[i][j] = Boolean.parseBoolean(b[(i * 6) + j]);
                Lecturer lecturer = new Lecturer(splits[0]);
                lecturer.addCourses(new ArrayList<>(Arrays.asList(c)));
                lecturer.setAvailability(bool);
                add(lecturer);
                break;
            }
            case VENUE: {
                boolean[][] bool = new boolean[2][6];
                String[] c = splits[1].replaceAll("\\[", "").replaceAll("]", "").split(", ");
                String[] b = splits[2].replaceAll("\\[", "").replaceAll("]", "").split(", ");
                for (int i = 0; i < 2; i++)
                    for (int j = 0; j < 6; j++)
                        bool[i][j] = Boolean.parseBoolean(b[(i * 6) + j]);
                Venue venue = new Venue(splits[0], bool);
                venue.addCourses(new ArrayList<>(Arrays.asList(c)));
                venue.setStudentLimit(Integer.parseInt(splits[3]));
                add(venue);
                break;
            }
            case SECTION: {
                Course course = new Course("");
                Lecturer lecturer = new Lecturer();
                Venue venue = new Venue();
                for (Course c : courses) {
                    if (splits[4].equalsIgnoreCase(c.getCode())) {
                        course = c;
                        break;
                    }
                }
                for (Lecturer l : lecturers) {
                    if (splits[5].equalsIgnoreCase(l.getName())) {
                        lecturer = l;
                        break;
                    }
                }
                for (Venue v : venues) {
                    if (splits[6].equalsIgnoreCase(v.getName())) {
                        venue = v;
                        break;
                    }
                }
                Section section = new Section(Integer.parseInt(splits[0]), course, lecturer, venue, Integer.parseInt(splits[3]));
                section.setDay(Integer.parseInt(splits[1]));
                section.setTime(Integer.parseInt(splits[2]));
                sections.add(section);
                break;
            }
        }
    }

	public static void save() {

		File file = new File("schedule.txt");
		String content;

		try (FileOutputStream fop = new FileOutputStream(file)){

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fop.write("// Courses\n".getBytes(), 0, "// Courses\n".length());
			fop.flush();

			// get content of courses
			for (Course c : courses) {
				content = c.getDetails() + "\n";

				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
			}

			fop.write("// Lecturers\n".getBytes());
			fop.flush();

			// get content of lecturers
			for (Lecturer l : lecturers) {
				content = l.getDetails() + "\n";

				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
			}

			fop.write("// Venues\n".getBytes());
			fop.flush();

			// get content of venues
			for (Venue v : venues) {
				content = v.getDetails() + "\n";

				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
			}

			fop.write("// Sections\n".getBytes());
			fop.flush();

			// get content of sections
			for (Section s : sections) {
				content = s.getDetails() + "\n";

				byte[] contentInBytes = content.getBytes();
				fop.write(contentInBytes);
				fop.flush();
			}
			
			fop.close();

			System.out.println("Done");
		}
        catch (IOException ignored) {}
    }
}
