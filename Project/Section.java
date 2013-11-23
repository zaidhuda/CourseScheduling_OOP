import java.util.concurrent.atomic.AtomicInteger;

public class Section {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
	private int sectionNum, studentLimit=30;
	private Course course;
	private Lecturer lecturer;

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

	public String getSectionNum(){ return Integer.toString(sectionNum); }
	public int getStudentLimit(){ return studentLimit; }
	public Course getCourse(){ return course; }
	public Lecturer getLecturer(){ return lecturer; }

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
		return sectionNum + ", " + course + ", " + lecturer + "\n";
	}
}