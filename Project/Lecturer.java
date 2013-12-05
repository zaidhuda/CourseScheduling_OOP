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
	private boolean[][] isAvailable = new boolean[2][6];
	private ArrayList<String> specialization = new ArrayList<String>();

	public Lecturer(String name){
		setName(name);
		Arrays.fill(isAvailable[0], true);
		Arrays.fill(isAvailable[1], true);
	}

	public Lecturer(String name, ArrayList<String> specialization){
		setName(name);
		addSpecialization(specialization);
		Arrays.fill(isAvailable[0], true);
		Arrays.fill(isAvailable[1], true);
	}

	public Lecturer(String name, String specialization){
		setName(name);
		addSpecialization(specialization);
		Arrays.fill(isAvailable[0], true);
		Arrays.fill(isAvailable[1], true);
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
	public void setAvailabilityAt(int day, int time, boolean arg){
		day %= 2;
		time %= 6;
		isAvailable[day][time] = arg;	
	}

	public String getName(){ return name; }
	public ArrayList<String> getSpecialization(){ return specialization; }
	public boolean[][] getAvailability(){ return isAvailable; }
	public boolean isAvailableAt(int day, int time){
		day %= 2;
		time %= 6;
		return isAvailable[day][time];
	}

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