/*
+------------------------------------------+
|			Venue					         |
+------------------------------------------+
|-name:String 							 |
|-type:String                              |
|-courses:ArrayList<String>                |
|+availability:ArrayList<Integer>          |
+------------------------------------------+
|+setName(arg:String):void                 |
|+setType(arg:String):void                 |
|+addCourse(arg:String):void               |
|+addCourse(args:ArrayList<String>):void   |
|+addAvailability(arg:int):void            |
|+removeAvailability(arg:int):void         |
|+getName():String                         |
|+getType():String                         |
|+getCourses():ArrayList<String>           |
|+getAvailability():ArrayList<Integer>     |
|+equals(other:Object):boolean             |
|+toString():String                        |
+------------------------------------------+
*/

package courseschedule;

import java.util.*;

public class Venue extends Support {
	private int studentLimit = 30;

	public Venue() {
		setName("TBD");
		Arrays.fill(availability[0], true);
		Arrays.fill(availability[1], true);
	}

	/**
	 * Construct a new instance of Venue and fill availability with true.
	 *
	 * @param name name of the new Venue object
	 */
	public Venue(String name) {
		setName(name);
		Arrays.fill(availability[0], true);
		Arrays.fill(availability[1], true);
	}

	public Venue(String name, boolean[][] availability) {
		setName(name);
		setAvailability(availability);
	}

	/**
	 * Receives an integer representing maximum capacity of the venue.
	 *
	 * @param arg the maximum capacity of the venue
	 */
	public void setStudentLimit(int arg) {
		studentLimit = arg;
	}

	/**
	 * Returns maximum capacity of the venue.
	 *
	 * @return maximum capacity of the venue.
	 */
	public int getStudentLimit() {
		return studentLimit;
	}

	/**
	 * Receives an object and if the object is an instance of Venue, compare their names
	 * ignoring cases to determine if they are equals.
	 *
	 * @param other expecting an object of type Venue to compare otherwise, return false without comparing
	 *
	 * @return true if both Venue are equals otherwise false
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Venue))
			return false;

		Venue that = (Venue) other;

		return this.name.equalsIgnoreCase(that.name);
	}

	public String getDetails() {
		return getName() + ";" + getCourses() + ";" + getAvailability_inWords() + ";" + getStudentLimit();
	}
}