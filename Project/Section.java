import java.util.ArrayList;

public class Section {
	private int sectionNum, studentLimit=30, time=0, day=0;
	private String time_inWords = null, day_inWords = null;
	public boolean userDefined = false;
	private Course course = null;
	private Lecturer lecturer = null;
	private Venue venue = null;

	// Section must have course and lecturer
	public Section(Course course, Lecturer lecturer, int sectionNum){
		setCourse(course);
		setLecturer(lecturer);
		setSectionNum(sectionNum);
	}

	public void setDay(int arg){ day = arg; setDay_inWords(arg); }
	public void setTime(int arg){ time = arg; setTime_inWords(arg); }
	public void setSectionNum(int arg){ sectionNum = arg; }
	public void setStudentLimit(int arg){ studentLimit = arg; }
	public void setCourse(Course arg){ course = arg; }
	public void setLecturer(Lecturer arg){
		try{
			if(arg.getSpecialization().contains(course.getCode()))
				lecturer = arg;
			else
				lecturer = new Lecturer("TO BE DETERMINED");
		}
		catch(Exception e){
			// In case no venue for a subject, make dummy venue
			lecturer = new Lecturer("TO BE DETERMINED");
		}	
	}
	public void setLecturer(ArrayList<Lecturer> lecturers, boolean random){
		// main loop, creating sections for each course
		try {
			ArrayList<Lecturer> tempLecturers = new ArrayList<Lecturer>();

			// add lecturers of current course to temporary list
			for (Lecturer lecturer : lecturers)
				if(lecturer.getSpecialization().contains(course.getCode()))
					tempLecturers.add(lecturer);

			// randomly assign lecturer to sections
			if(random){
				int r = (int) (Math.random() * tempLecturers.size());
				setLecturer(tempLecturers.get(r));
			}
			// assign lecturer based on order
			else{
				setLecturer(tempLecturers.get((sectionNum+1)%tempLecturers.size()));
			}

			tempLecturers.clear();
		}
		catch(Exception e){}
	}
	public void setVenue(Venue arg){
		try{
			if (arg.getCourses().contains(course.getCode()))
				venue = arg;
			else
				venue = new Venue("TO BE DETERMINED", "");
		}
		catch(Exception e){
			// In case no venue for a subject, make dummy venue
			venue = new Venue("TO BE DETERMINED", "");
		}
	}
	public void setVenue(ArrayList<Venue> venues){
		try{
			String code = course.getCode();
			ArrayList<String> courses = new ArrayList<>();
			ArrayList<Venue> tempVenues = new ArrayList<>();
			// Checking if venue can be used for a subject
			for (Venue v : venues) {
				courses = v.getCourses();
				if (courses.contains(code)){
					// Adding usable venua for a subject to ArrayList
					tempVenues.add(v);
				}
			}
			// Randomly choosing a venue for a subject
			int r = (int) (Math.random() * tempVenues.size());
			venue = tempVenues.get(r);
			tempVenues.clear();
		}
		catch(Exception e){
			// In case no venue for a subject, make dummy venue
			venue = new Venue("TO BE DETERMINED", "");
		}
	}
	private void setTime_inWords(int arg){
		switch(arg){
			case 0: time_inWords = "08:30 - 09:50"; break;
			case 1: time_inWords = "10:00 - 11:20"; break;
			case 2: time_inWords = "11:30 - 12:50"; break;
			case 3: time_inWords = "14:00 - 15:20"; break;
			case 4: time_inWords = "15:30 - 16:50"; break;
			case 5: time_inWords = "17:00 - 18:20"; break;
			default: time_inWords = null;
		}
	}
	private void setDay_inWords(int arg){
		switch(arg) {
			case 0: day_inWords = "Mon/Wed"; break;
			case 1: day_inWords = "Tue/Thu"; break;
			default: day_inWords = null;
		}
	}

	public int getDay(){ return day; }
	public int getTime(){ return time; }
	public int getStudentLimit(){ return studentLimit; }
	public String getSectionNum(){ return Integer.toString(sectionNum); }
	public String getDay_inWords(){ return day_inWords; }
	public String getTime_inWords(){ return time_inWords; }
	public Course getCourse(){ return course; }
	public Lecturer getLecturer(){ return lecturer; }
	public Venue getVenue(){ return venue; }

	public boolean generateSchedule(ArrayList<Section> sections, boolean random){
		// Generate only if a section is not marked as user defined
		if(!userDefined){
			int tempDay=getDay(), tempTime=0, max=0;
			boolean inC=true, inL=true, inV=true;
			// Checking if the section has a schedule
			if (!(time_inWords == null) && !(day_inWords == null)) {
				if (tempDay==0)
					tempTime = getTime();
					// System.out.println(getTime());
				else
					tempTime = getTime()+6;

				// If yes, restore availability for venue and lecturer
				// course.addAvailability(new Integer(tempTime));
				lecturer.setAvailabilityAt(tempDay, tempTime, true);
				venue.setAvailabilityAt(tempDay, tempTime, true);
			}
			// Finding max sections that will be using the venue to fill up
			// morning schedule first
			for (Section s : sections) {
				if(s.venue.equals(venue))
					max++;
			}
			int count = 0;
			do{
				// If random is true, find time that is available in lect and venue
				if(random){
					if(count<12){
						tempDay = (int) (Math.random() * 2);
						tempTime = (int) (Math.random() * (max%6));
					}
					else if(count>11 && count<23){
						if((count-11)<6)
							tempDay = 0;
						else
							tempDay = 1;
						tempTime = (count-11)%6;
					}
					else if(count>22) return false;
				}
				// If random is false, fill up morning sessions first
				else{
					if(count<12) {
						tempDay = count%2;
						tempTime = (count%6)/2;
					}
					else if(count>11) return false;
				}
				// Check if lecturer and venue are available for same time
				inL = lecturer.isAvailableAt(tempDay, tempTime);
				inV = venue.isAvailableAt(tempDay, tempTime);
				count++;
				// System.out.println(tempDay + " " + tempTime + " " + (inL && inV) + " " + (count++));
			}while(!inL || !inV);

			// Remove availaibility from lecturer and venue
			lecturer.setAvailabilityAt(tempDay, tempTime, false);
			venue.setAvailabilityAt(tempDay, tempTime, false);

			setDay(tempDay);
			setTime(tempTime);
		}
		return true;
	}

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Section))
	        return false;

	    Section that = (Section) other;

	    return this.sectionNum == that.sectionNum
	    	&& this.course.equals(that.course)
	    	&& this.lecturer.equals(that.lecturer);
	}

	public String toString(){
		String rep = getSectionNum() + ", " + getCourse().getCode() + ", " + getLecturer().getName() + ", " + getVenue().getName() + ", " + getDay_inWords() + ", " + getTime_inWords();
		// String rep = getSectionNum() + ", " + getDay_inWords() + ", " + getTime_inWords();
		return rep;
	}
}