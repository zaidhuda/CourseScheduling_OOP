import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

public class Course {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
	private String code, title = null;
	private int credit=0, requiredSections=1;
	
	public Course(String code){
		constructor(code, null, 0, 1);
		id = nextId.incrementAndGet();
	}
	
	public Course(String code, String title){
		constructor(code, title, 0, 1);
		id = nextId.incrementAndGet();
	}
	
	public Course(String code, String title, int credit){
		constructor(code, title, credit, 1);
		id = nextId.incrementAndGet();
	}
	
	public Course(String code, String title, int credit, int requiredSections){
		constructor(code, title, credit, requiredSections);
		id = nextId.incrementAndGet();
	}

	private void constructor(String code, String title, int credit, int requiredSections){
		setCode(code);
		setTitle(title);
		setCredit(credit);
		setRequiredSections(requiredSections);
	}
	
	// setter methods
	public void setCredit(int arg){ credit = arg; }
	public void setRequiredSections(int arg){ requiredSections = arg; }
	public void setTitle(String arg){ title = arg; }
	public void setCode(String arg){
		if(!arg.equals(arg.split(" "))){
			String[] split = arg.split(" ");
			arg = "";
			for (String str : split)
				arg+=str;
		}
		code = arg.toUpperCase();
	}

	// getter methods
	public int getCredit(){ return credit; }
	public int getRequiredSections(){ return requiredSections; }
	public String getCode(){ return code; }
	public String getTitle(){ return title; }

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Course))
	        return false;

	    Course that = (Course) other;

	    return this.code.equalsIgnoreCase(that.code);
	}

	public String toString(){
		String course = getCode() + " " + getTitle() + " " + getCredit();
		return course;
	}
}