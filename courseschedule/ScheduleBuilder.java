package courseschedule;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Diaz
 * Package: ${PACKAGE_NAME}
 * Time: 12:50 AM
 * Date: 12/8/13.
 */
public class ScheduleBuilder {
    public static ArrayList<Course> courses = new ArrayList<>();
    public static ArrayList<Lecturer> lecturers = new ArrayList<>();
    public static ArrayList<Venue> venues = new ArrayList<>();
    public static ArrayList<Section> sections = new ArrayList<>();
    public final CourseComparator courseComparator = new CourseComparator();

    public ScheduleBuilder(){}

    public static void generateSections() {
        sections.clear();
        for (Course course : courses)
            for (int i = 0; i < course.getRequiredSections(); ++i) {
                sections.add(new Section(i + 1, course, new Lecturer(), new Venue()));
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

    public static void add(Course o) {
        boolean exist = false;
        for (Course c : courses)
            if (c.equals(o)) {
                exist = true;
                break;
            }
        if (!exist) courses.add(o);
    }

    public static void add(Lecturer o) {
        boolean exist = false;
        for (Lecturer l : lecturers)
            if (l.equals(o)) {
                exist = true;
                break;
            }
        if (!exist) lecturers.add(o);
    }

    public static void add(Venue o) {
        boolean exist = false;
        for (Venue v : venues)
            if (v.equals(o)) {
                exist = true;
                break;
            }
        if (!exist) venues.add(o);
    }

    public static void remove(Course o) {
        ArrayList<Section> tempSections = new ArrayList<>();
        for (Section s : sections) {
            if (s.getCourse().equals(o)) {
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

    public static int[][] getAvailableSlots(Lecturer lecturer, Venue venue) {
        int[][] newAvailability = new int[2][6];

        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 6; ++j) {
                if (lecturer.isAvailableAt(i, j) && venue.isAvailableAt(i, j))
                    newAvailability[i][j] = 0;
                else if (lecturer.isAvailableAt(i, j))
                    newAvailability[i][j] = 1;
                else if (venue.isAvailableAt(i, j))
                    newAvailability[i][j] = 2;
                else
                    newAvailability[i][j] = 3;
            }
        }

        return newAvailability;
    }

    public static void load() {
        final int COURSE = 0, LECTURER = 1, VENUE = 2, SECTION = 3;

        File file = new File("schedule.txt");

        if(!file.exists())
            file = new File("temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder;
            String fileLine = br.readLine();
            int type = -1;

            while (fileLine != null) {
                stringBuilder = new StringBuilder(fileLine);
                String newLine = stringBuilder.toString();
                String[] splits = newLine.split(";");

                if (splits.length > 1) lineReader(type, splits);

                if ("courses".equalsIgnoreCase(newLine)) type = COURSE;
                else if ("lecturers".equalsIgnoreCase(newLine)) type = LECTURER;
                else if ("venues".equalsIgnoreCase(newLine)) type = VENUE;
                else if ("sections".equalsIgnoreCase(newLine)) type = SECTION;

                fileLine = br.readLine();
            }
        } catch (Exception ignored) {
        }
    }

    private static void lineReader(int type, String[] splits) {
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

        File file = new File("temp.txt");
        File newFile = new File("schedule.txt");

        try (PrintStream fop = new PrintStream(file)) {

            fop.println("courses");
            for (Course c : courses)
                fop.println(c.getDetails());

            fop.println("lecturers");
            for (Lecturer l : lecturers)
                fop.println(l.getDetails());

            fop.println("venues");
            for (Venue v : venues)
                fop.println(v.getDetails());

            fop.println("sections");
            for (Section s : sections)
                fop.println(s.getDetails());

            fop.close();

            if (newFile.exists())
                newFile.delete();
            file.renameTo(newFile);

            System.out.println("Done");
        } catch (Exception ignored) {
        }
    }
}
