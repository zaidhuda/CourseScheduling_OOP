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
	public void setLecturer(Lecturer arg){ lecturer = arg; }
	public void setVenue(Venue arg){
		try{
			if (arg.getCourses().contains(course.getCode()))
				venue = arg;
			// else
			// 	System.out.println("Failed");
		}
		catch(Exception e){
			// In case no venue for a subject, make dummy venue
			venue = new Venue("NA", "");
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
			venue = new Venue("NA", "");
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
			int tempDay=0, tempTime=0, max=0;
			boolean inC=true, inL=true, inV=true;
			// Checking if the section has a schedule
			if (!(time_inWords == null) && !(day_inWords == null)) {
				if (getDay()==0) {
					if(getTime()<3) tempTime = getTime();
					else tempTime = getTime() + 3;
					// System.out.println(getTime());
				}
				else{
					if(getTime()<3) tempTime = getTime()+3;
					else tempTime = getTime() + 6;
					// System.out.println(getTime());
				}
				// If yes, restore availability for venue and lecturer
				// course.addAvailability(new Integer(tempTime));
				lecturer.addAvailability(new Integer(tempTime));
				venue.addAvailability(new Integer(tempTime));
			}
			// Finding max sections that will be using the venue to fill up
			// morning schedule first
			for (Section s : sections) {
				if(s.venue.equals(venue))
					max++;
			}
			// If only 3 sections will be using, let the schdule be randomed
			// between Mon/Wed and Tue/Thu morning sessions
			if(max<3) max = 5;
			int count = 0;
			do{
				// If random is true, find time that is available in lect and venue
				if(random){
					if(count<12) tempTime = (int) (Math.random() * max);
					else if(count>11 && count<23) tempTime = count-11;
					else if(count>22) return false;
				}
				// If random is false, fill up morning sessions first
				else{
					if(count<12) {
						switch(count){
							case 0: tempTime = 0; break;
							case 1: tempTime = 3; break;
							case 2: tempTime = 1; break;
							case 3: tempTime = 4; break;
							case 4: tempTime = 2; break;
							case 5: tempTime = 5; break;
							case 6: tempTime = 6; break;
							case 7: tempTime = 9; break;
							case 8: tempTime = 7; break;
							case 9: tempTime = 10; break;
							case 10: tempTime = 8; break;
							case 11: tempTime = 11; break;
						}
					}
					else if(count>11) return false;
				}
				// Check if lecturer and venue are available for same time
				inL = lecturer.getAvailability().contains(tempTime);
				inV = venue.getAvailability().contains(tempTime);
				count++;
				// System.out.println(tempTime + " " + (!inC || !inL || !inV) + " " + (count++));
			}while(!inL || !inV);

			// Remove availaibility from lecturer and venue
			lecturer.removeAvailability(new Integer(tempTime));
			venue.removeAvailability(new Integer(tempTime));

			// Mon = 0,1,2,6,7,8
			// Tue = 3,4,5,9,10,11
			if(tempTime>5&&tempTime<9)
				tempTime-=3;
			else if((tempTime>2&&tempTime<6)||(tempTime>8)){
				tempDay=1;
				if(tempTime<6) tempTime-=3;
				if(tempTime>8) tempTime-=6;
			}

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