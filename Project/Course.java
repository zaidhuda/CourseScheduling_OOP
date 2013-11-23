import java.util.concurrent.atomic.AtomicInteger;

public class Course {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
	private String code, title, venueType="";
	private int credit, requiredSections=2;
	
	public Course(String code, String title, int credit, int requiredSections){
		setCode(code);
		setTitle(title);
		setCredit(credit);
		setRequiredSections(requiredSections);
		id = nextId.incrementAndGet();
	}
	
	// setter methods
	public void setCode(String arg){ code = arg; }
	public void setTitle(String arg){ title = arg; }
	public void setVenueType(String arg){ venueType = arg; }
	public void setCredit(int arg){ credit = arg; }
	public void setRequiredSections(int arg){ requiredSections = arg; }

	// getter methods
	public String getCode(){ return code; }
	public String getTitle(){ return title; }
	public String getVenueType(){ return venueType; }
	public int getCredit(){ return credit; }
	public int getRequiredSections(){ return requiredSections; }

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Course))
	        return false;

	    Course that = (Course) other;

	    // Custom equality check here.
	    return this.code.equals(that.code);
	}

	public String toString(){
		String course = code + " " + title + " " + credit;
		return course;
	}
}