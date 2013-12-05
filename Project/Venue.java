//+------------------------------------------+
//|			Venue					         |
//+------------------------------------------+
//|-name:String 							 |
//|-type:String                              |
//|-courses:ArrayList<String>                |
//|+availability:ArrayList<Integer>          |
//+------------------------------------------+
//|+setName(arg:String):void                 |
//|+setType(arg:String):void                 |
//|+addCourse(arg:String):void               |
//|+addCourse(args:ArrayList<String>):void   |
//|+addAvailability(arg:int):void            |
//|+removeAvailability(arg:int):void         |
//|+getName():String                         |
//|+getType():String                         |
//|+getCourses():ArrayList<String>           |
//|+getAvailability():ArrayList<Integer>     |
//|+equals(other:Object):boolean             |
//|+toString():String                        |
//+------------------------------------------+


import java.util.*;

public class Venue {
	private String name, type = "";
	private boolean[][] availability = new boolean[2][6];
	private ArrayList<String> courses = new ArrayList<String>();

	public Venue(String name, String type){
		setName(name);
		setType(type);
		Arrays.fill(availability[0], true);
		Arrays.fill(availability[1], true);
	}

	public void setName(String arg){ name = arg; }
	public void setType(String arg){ type = arg; }
	public void addCourse(String arg){ courses.add(arg.toUpperCase()); }
	public void addCourse(ArrayList<String> args){for (String arg : args) courses.add(arg.toUpperCase()); }
	public void setAvailability(boolean[][] arg){ availability = arg; }
	public void setAvailabilityAt(int day, int time, boolean arg){ availability[day%2][time%6] = arg; }
	
	public String getName(){ return name; }
	public String getType(){ return type; }
	public ArrayList<String> getCourses(){ return courses; }
	public boolean[][] getAvailability(){ return availability; }
	public boolean isAvailableAt(int day, int time){ return availability[day%2][time%6]; }

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Venue))
			return false;

		Venue that = (Venue) other;

		return this.name.equalsIgnoreCase(that.name)
			&& this.type.equalsIgnoreCase(that.type);
	}

	public String toString(){
		return getName() + ", " + getCourses();
	}
}