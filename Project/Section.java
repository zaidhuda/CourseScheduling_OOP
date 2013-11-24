import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

public class Section {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
	private int sectionNum, studentLimit=30, time=0, day=0;
	private String timeRepresentation = null, dayRepresentation = null;
	private Course course;
	private Lecturer lecturer;
	private Venue venue = null;

	public Section(
			Course course,
			Lecturer lecturer,
			int sectionNum
		){
		setCourse(course);
		setLecturer(lecturer);
		setSectionNum(sectionNum);
		id = nextId.incrementAndGet();
	}
	public void setDay(int arg){ day = arg; }
	public void setSectionNum(int arg){ sectionNum = arg; }
	public void setStudentLimit(int arg){ studentLimit = arg; }
	public void setCourse(Course arg){ course = arg; }
	public void setLecturer(Lecturer arg){ lecturer = arg; }
	public void setVenue(Venue arg){ venue = arg; }

	public int getTime(){ return time; }
	public int getDay(){ return day; }
	public String getSectionNum(){ return Integer.toString(sectionNum); }
	public int getStudentLimit(){ return studentLimit; }
	public Course getCourse(){ return course; }
	public Lecturer getLecturer(){ return lecturer; }
	public Venue getVenue(){ return venue; }

	public void setVenue(ArrayList<Venue> venues){
		String code = course.getCode();
		ArrayList<String> courses = new ArrayList<String>();
		for (Venue v : venues) {
			courses = v.getCourses();
			if (courses.contains(code))
				venue = v;
		}
	}

	public void setTime(ArrayList<Section> sections){
		int d=0, t=0, max=0;
		boolean inC=true, inL=true, inV=true;
		// if(max==0)
		// 	max = course.availability.size()-1;

		for (Section s : sections) {
			if(s.venue.equals(venue))
				max++;
		}

		do{
			t = (int) (Math.random() * max);
			inC = course.availability.contains(t);
			inL = lecturer.availability.contains(t);
			inV = venue.availability.contains(t);
			// System.out.println(t + " " + inC + " " + inL + " " + inV + " " + (!inC || !inL || !inV));
		}while(!inC || !inL || !inV);

		course.availability.remove(course.availability.indexOf(t));
		lecturer.availability.remove(lecturer.availability.indexOf(t));
		venue.availability.remove(venue.availability.indexOf(t));

		if(t>5&&t<9){
			t-=3;
		}
		else if((t>2&&t<6)||(t>8)){
			d=1;
			if(t<6) t-=3;
			if(t>8) t-=6;
		}

		day = d;
		time = t;
		switch (day) {
			case 0: dayRepresentation = "Monday/Wednesday"; break;
			case 1: dayRepresentation = "Tuesday/Thursday"; break;
			default: dayRepresentation = null;
		}
		switch(time){
			case 0: timeRepresentation = "08:30 - 09:50"; break;
			case 1: timeRepresentation = "10:00 - 11:20"; break;
			case 2: timeRepresentation = "11:30 - 12:50"; break;
			case 3: timeRepresentation = "14:00 - 15:20"; break;
			case 4: timeRepresentation = "15:30 - 16:50"; break;
			case 5: timeRepresentation = "17:00 - 18:20"; break;
			default: timeRepresentation = null;
		}
	}

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Section))
	        return false;

	    Section that = (Section) other;

	    // Custom equality check here.
	    return this.course.equals(that.course)
	    	&& this.lecturer.equals(that.lecturer)
	    	&& this.sectionNum == that.sectionNum;
	}

	public String toString(){
		// String rep = sectionNum + ", " + course + ", " + lecturer + ", " + venue + ", " + dayRepresentation + ", " + timeRepresentation;
		
		return dayRepresentation + ", " + timeRepresentation + ", " + venue;
	}
}