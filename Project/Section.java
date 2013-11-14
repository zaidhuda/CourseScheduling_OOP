public class Section {
	private int sectionNum, studentLimit=30;
	private Schedule time;
	private Course course;
	private Lecturer lecturer;
	private Venue venue;

	public Section(
			Course course,
			Lecturer lecturer,
			Venue venue,
			Schedule time,
			int sectionNum,
			int studentLimit
		){
		setCourse(course);
		setLecturer(lecturer);
		setVenue(venue);
		setTime(time);
		setSectionNum(sectionNum);
		setStudentLimit(studentLimit);
	}

	public String getSectionNum()	{ return Integer.toString(sectionNum); }
	public int getStudentLimit()	{ return studentLimit; }
	public Schedule getTime()		{ return time; }
	public Course getCourse()		{ return course; }
	public Lecturer getLecturer()	{ return lecturer; }
	public Venue getVenue()			{ return venue; }

	public void setSectionNum(int arg)		{ sectionNum = arg; }
	public void setStudentLimit(int arg)	{ studentLimit = arg; }
	public void setTime(Schedule arg)		{ time = arg; }
	public void setCourse(Course arg)		{ course = arg; }
	public void setLecturer(Lecturer arg)	{ lecturer = arg; }
	public void setVenue(Venue arg)			{ venue = arg; }

	public String toString(){
		return course + "" + lecturer + "" + venue  + "" + time + "Student Limit: " + studentLimit;
	}
}