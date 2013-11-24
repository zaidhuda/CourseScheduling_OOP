import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

public class Venue {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
    public ArrayList<Integer> availability = new ArrayList<Integer>(java.util.Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
	private String name, type="";
	private ArrayList<String> courses = new ArrayList<String>();

	public Venue(String name, String type){
		setName(name);
		setType(type);
		id = nextId.incrementAndGet();
	}

	public void setName(String arg){ name = arg; }
	public void setType(String arg){ type = arg; }
	public void addCourse(String arg){ courses.add(arg); }

	public String getName(){ return name; }
	public String getType(){ return type; }
	public ArrayList<String> getCourses(){ return courses; }

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Venue))
	        return false;

	    Venue that = (Venue) other;

	    // Custom equality check here.
	    return this.name.equals(that.name)
	    	&& this.type.equals(that.type);
	}

	public String toString(){
		return name;
	}
}