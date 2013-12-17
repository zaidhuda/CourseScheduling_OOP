package courseschedule.gui;

import courseschedule.*;
import courseschedule.util.*;

import java.util.*;

/**
 * Created by Diaz
 * Package: courseschedule.gui
 * Time: 6:44 PM
 * Date: 12/15/13.
 */
public class LecturerList extends DropList {
	public LecturerList(ScheduleBuilder sb, Course course, Lecturer lecturer, int limit) {
		super(sb, course, lecturer, limit);
	}

	public ArrayList<Course> getCourses() {
		Collections.sort(lCourses);
		String[] c = lCourses.toString().replaceAll("[\\[\\]]", "").split(", ");
		return sb.getCourses(c);
	}

	public Lecturer getLecturer() {
		return sb.getLecturerByName(name);
	}
}
