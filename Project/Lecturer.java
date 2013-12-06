// +--------------------------------------------------+
// |                   Lecturer          			  |
// +--------------------------------------------------+
// |-name:String             						  |
// |-specialization:ArrayList<String>                 |
// |+availability:ArrayList<Integer>                  |
// +--------------------------------------------------+
// |+setName(arg:String):void                         |
// |+addSpecialization(arg:String):void               |
// |+addSpecialization(args:ArrayList<String>):void   |
// |+addAvailability(arg:int):void                    |
// |+removeAvailability(arg:int):void                 |
// |+getName():String                                 |
// |+getSpecialization():ArrayList<String>            |
// |+getAvailability():ArrayList<Integer>             |
// |+equals(other:Object):boolean                     |
// |+toString():String                                |
// +--------------------------------------------------+

import java.util.*;

public class Lecturer extends Support {

	public Lecturer(String name){
		setName(name);
		Arrays.fill(availability[0], true);
		Arrays.fill(availability[1], true);
	}

	public Lecturer(String name, String specialization){
		setName(name);
		addSpecialization(specialization);
		Arrays.fill(availability[0], true);
		Arrays.fill(availability[1], true);
	}

	public Lecturer(String name, ArrayList<String> specialization){
		setName(name);
		addSpecialization(specialization);
		Arrays.fill(availability[0], true);
		Arrays.fill(availability[1], true);
	}

	public Lecturer(String name, boolean[][] availability, String specialization){
		setName(name);
		addSpecialization(specialization);
		setAvailability(availability);
	}

	public Lecturer(String name, boolean[][] availability, ArrayList<String> specialization){
		setName(name);
		addSpecialization(specialization);
		setAvailability(availability);
	}

	public void addSpecialization(String arg){ addCourse(arg); }
	public void addSpecialization(ArrayList<String> args){ addCourse(args); }
	public ArrayList<String> getSpecializations(){ return getCourses(); }

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Lecturer))
	        return false;

	    Lecturer that = (Lecturer) other;

	    return this.name.equalsIgnoreCase(that.name);
	}
}