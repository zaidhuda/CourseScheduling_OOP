// +--------------------------------------------------+
// |                   Lecturer          			  |
// +--------------------------------------------------+
// |-name:String             						  |
// |-specialization:ArrayList<String>                 |
// |+availability:ArrayList<Integer>                  |
// +--------------------------------------------------+
// |+setName(arg: String):void                        |
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

public class Lecturer {
	private String name;
	private boolean[][] availability = new boolean[2][6];
	private ArrayList<String> specialization = new ArrayList<String>();

	public Lecturer(String name){
		setName(name);
		Arrays.fill(availability[0], true);
		Arrays.fill(availability[1], true);
	}

	public Lecturer(String name, ArrayList<String> specialization){
		setName(name);
		addSpecialization(specialization);
		Arrays.fill(availability[0], true);
		Arrays.fill(availability[1], true);
	}

	public Lecturer(String name, String specialization){
		setName(name);
		addSpecialization(specialization);
		Arrays.fill(availability[0], true);
		Arrays.fill(availability[1], true);
	}

	public void setName(String arg){ name = arg; }
	public void addSpecialization(String arg){
		if(specialization.size()<3)
			specialization.add(arg.toUpperCase());
		else ;// add action
	}
	public void addSpecialization(ArrayList<String> args){
		for (String arg : args){
			if(specialization.size()<3)
				specialization.add(arg.toUpperCase());
			else
				break;
		}
	}
	public void setAvailability(boolean[][] arg){ availability = arg; }
	public void setAvailabilityAt(int day, int time, boolean arg){ availability[day%2][time%6] = arg; }

	public String getName(){ return name; }
	public ArrayList<String> getSpecialization(){ return specialization; }
	public boolean[][] getAvailability(){ return availability; }
	public boolean isAvailableAt(int day, int time){ return availability[day%2][time%6]; }

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Lecturer))
	        return false;

	    Lecturer that = (Lecturer) other;

	    return this.name.equalsIgnoreCase(that.name);
	}

	public String toString(){
		return getName() + ", " + getSpecialization();
	}
}