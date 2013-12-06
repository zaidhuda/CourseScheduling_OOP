import java.util.*;

abstract class Support {
	protected String name;
	protected boolean[][] availability = new boolean[2][6];
	protected ArrayList<String> courses = new ArrayList<String>();

	public void setName(String arg){ name = arg; }
	public String getName(){ return name; }

	public void addCourse(String arg){
		arg = arg.replaceAll("\\s","").toUpperCase();
		if(!courses.contains(arg)){
			if(!(this instanceof Lecturer))
				courses.add(arg);
			else if(courses.size() < 3)
				courses.add(arg);
		}
	}
	public void addCourse(ArrayList<String> args){
		for (String arg : args)
			addCourse(arg);
	}
	public ArrayList<String> getCourses(){ return courses; }

	public void setAvailability(boolean[][] arg){ availability = arg; }
	public void setAvailabilityAt(int day, int time, boolean arg){ availability[day%2][time%6] = arg; }
	public boolean[][] getAvailability(){ return availability; }
	public boolean isAvailableAt(int day, int time){ return availability[day%2][time%6]; }
	public String availability_inWords(){ return Arrays.deepToString(availability); }
	
	public String toString(){
		return getName() + ", " + getCourses();
	}
}