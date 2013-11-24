import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

public class Course {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
    private ArrayList<Integer> availability = new ArrayList<Integer>(java.util.Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
	private String code, title, venueType="";
	private int credit=0, requiredSections=1;
	
	public Course(String code, String title){
		setCode(code);
		setTitle(title);
		id = nextId.incrementAndGet();
	}
	
	public Course(String code, String title, int credit){
		setCode(code);
		setTitle(title);
		setCredit(credit);
		id = nextId.incrementAndGet();
	}
	
	public Course(String code, String title, int credit, int requiredSections){
		setCode(code);
		setTitle(title);
		setCredit(credit);
		setRequiredSections(requiredSections);
		id = nextId.incrementAndGet();
	}
	
	// setter methods
	public void setCredit(int arg){ credit = arg; }
	public void setRequiredSections(int arg){ requiredSections = arg; }
	public void setCode(String arg){ code = arg; }
	public void setTitle(String arg){ title = arg; }
	public void setVenueType(String arg){ venueType = arg; }
	public void addAvailability(int arg){ availability.add(arg); }

	// getter methods
	public int getCredit(){ return credit; }
	public int getRequiredSections(){ return requiredSections; }
	public String getCode(){ return code; }
	public String getTitle(){ return title; }
	public String getVenueType(){ return venueType; }
	public ArrayList<Integer> getAvailability(){ return availability; }

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Course))
	        return false;

	    Course that = (Course) other;
	    
	    return this.code.equals(that.code);
	}

	public String toString(){
		String course = code + " " + title + " " + credit;
		return course;
	}
}