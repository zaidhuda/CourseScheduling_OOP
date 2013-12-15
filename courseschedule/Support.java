package courseschedule;

import java.util.*;

abstract class Support {
	protected String name;
	protected boolean[][] availability = new boolean[2][6];
	//protected ArrayList<String> courses = new ArrayList<>();
	protected ArrayList<Course> courses = new ArrayList<>();

	/**
	 * Receives a string and save to name.
	 *
	 * @param arg the desired name
	 */
	public void setName(String arg) {
		name = arg.toUpperCase();
	}

	/**
	 * Return the name of subclasses.
	 *
	 * @return the name of object
	 */
	public String getName() {
		return name;
	}

	/**
	 * Receives a string and add to courses if it's not already in the array list.
	 * If this is not an instance of Lecturer, add without further validation. Else,
	 * only add if current size of courses less than 3.
	 *
	 * @param arg the desired course code to be added to courses array list
	 */
	public void addCourse(Course arg) {
		if (courses.contains(arg)) {
			return;
		}
		if (!(this instanceof Lecturer))
			courses.add(arg);
		else
			if (courses.size() < 3)
				courses.add(arg);
	}

	/**
	 * Receives an array list of string, iterate through the list and add data
	 * to courses using addCourse.
	 *
	 * @param args array list of the desired course code to be added to courses list
	 */
	public void addCourses(ArrayList<Course> args) {
		for (Course arg : args)
			addCourse(arg);
	}

	public void setCourses(ArrayList<Course> args) {
		courses.clear();
		for (Course c : args) {
			if (this instanceof Lecturer) {
				courses.add(c);
				if (args.indexOf(c) == 2)
					return;
			} else {
				courses.add(c);
			}
		}
	}

	public void setCourse(Course arg) {
		courses.clear();
		courses.add(arg);
	}

	/**
	 * Receives a course code and remove it from courses array list.
	 *
	 * @param arg the course code to be removed from courses array list.
	 */
	public void removeCourse(String arg) {
		arg = arg.replaceAll("\\s", "");
		for (Course c : courses)
			if (arg.equalsIgnoreCase(c.getCode())){
				courses.remove(c);
				break;
			}
	}

	/**
	 * Receives course codes and remove it from courses array list.
	 *
	 * @param args the course codes to be removed from courses array list.
	 */
	public void removeCourses(ArrayList<String> args) {
		for (String str : args)
			str = str.toUpperCase();
		ArrayList<Course> tempCourses = new ArrayList<>();
		for (Course c : courses)
			if (args.contains(c.getCode()))
				tempCourses.add(c);
		courses.removeAll(tempCourses);
	}

	/**
	 * Return courses as an array list.
	 *
	 * @return courses array list
	 */
	public ArrayList<String> getCourses() {
		ArrayList<String> tempList = new ArrayList<>();
		for (Course c : courses)
			tempList.add(c.getCode());
		return tempList;
	}

	/**
	 * Receives a 2x6 array of boolean and saves to availability.
	 *
	 * @param arg a 2D boolean array that represents availability
	 */
	public void setAvailability(boolean[][] arg) {
		availability[0] = Arrays.copyOf(arg[0], 6);
		availability[1] = Arrays.copyOf(arg[1], 6);
	}

	/**
	 * Receives day and time in int and a boolean to set the value of availability at
	 * row day column time to arg.
	 *
	 * @param day    int representing row of availability
	 * @param time   an int representing column of availability
	 * @param isUsed desired value to be set to availability[day][time]
	 */
	public void setAvailabilityAt(int day, int time, boolean isUsed) {
		availability[day % 2][time % 6] = isUsed;
	}

	/**
	 * Return availability as boolean[][].
	 *
	 * @return 2D array of availability
	 */
	public boolean[][] getAvailability() {
		return availability;
	}

	/**
	 * Return value of availability at row day column time.
	 *
	 * @param day  an int representing row of availability
	 * @param time an int representing column of availability
	 *
	 * @return the availability at availability[day][time]
	 */
	public boolean isAvailableAt(int day, int time) {
		return availability[day % 2][time % 6];
	}

	/**
	 * Return a string representation of availability array.
	 *
	 * @return string representation of availability
	 */
	public String getAvailability_inWords() {
		return Arrays.deepToString(availability);
	}

	/**
	 * Returns string array name and courses list.
	 *
	 * @return str String array
	 */
	public String[] detailsArray() {
		String[] str = new String[2];
		str[0] = getName();
		str[1] = getCourses().toString().replaceAll("\\[", "").replaceAll("]", "");
		return str;
	}

	/**
	 * Return name and courses array list as string.
	 *
	 * @return name and courses list in string
	 */
	public String toString() {
		return getName() + ", " + getCourses();
	}
}