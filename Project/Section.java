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
	public void setSectionNum(int arg){ sectionNum = arg; }
	public void setStudentLimit(int arg){ studentLimit = arg; }
	public void setCourse(Course arg){ course = arg; }
	public void setLecturer(Lecturer arg){ lecturer = arg; }
	public void setVenue(Venue arg){ venue = arg; }

	public String getSectionNum(){ return Integer.toString(sectionNum); }
	public int getTime(){ return time; }
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
		int t=0, d=0;
		try{
			Section current = sections.get(sections.indexOf(new Section(course, lecturer, sectionNum)));

			for (int i=0;i<sections.indexOf(current);++i) {
				Course c = sections.get(i).getCourse();
				Lecturer l = sections.get(i).getLecturer();
				Venue v = sections.get(i).getVenue();
				int t2 = sections.get(i).getTime();

				if( t==t2 && ( course == c || lecturer == l || venue == v )){
					t++;
					if(t==6){
						d++;
						t=0;
					}
				}
			}
		}
		catch(Exception e){return;}

		day = d;
		time = t;
		switch (day) {
			case 0: dayRepresentation = "Monday/Wednesday"; break;
			case 1: dayRepresentation = "Tuesday/Thursday"; break;
		}
		switch(time){
			case 0: timeRepresentation = "08:30 - 09:50"; break;
			case 1: timeRepresentation = "10:00 - 11:20"; break;
			case 2: timeRepresentation = "11:30 - 12:50"; break;
			case 3: timeRepresentation = "14:00 - 15:20"; break;
			case 4: timeRepresentation = "15:30 - 16:50"; break;
			case 5: timeRepresentation = "17:00 - 18:20"; break;
			default: break;
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
		return sectionNum + ", " + course + ", " + lecturer + ", " + venue + "\n" + dayRepresentation + ", " + timeRepresentation;
	}
}