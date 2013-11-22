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
			int sectionNum,
			int studentLimit
		){
		setCourse(course);
		setLecturer(lecturer);
		setSectionNum(sectionNum);
		setStudentLimit(studentLimit);
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

	public String toString(){
		return course + "" + lecturer + "Student Limit: " + studentLimit + "\n";
	}
}