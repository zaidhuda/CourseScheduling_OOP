//+---------------------------------------------------------------------+
//|                Section                                              |
//+---------------------------------------------------------------------+
//|-sectionNum:int                                                      |
//|-studentLimit:int                                                    |
//|-time:int                                                            |
//|-day:int                                                             |
//|-time_inWords:String                                                 |
//|-day_inWords:String                                                  |
//|-course:course                                                       |
//|-lecturer:Lecturer                                                   |  
//|-venue:Venue                                                         |
//|+userDefined:boolean                                                 |
//+---------------------------------------------------------------------+
//|-setTime_inWords(arg:int):void                                       |
//|-setDay_inWords(arg:int):void                                        | 
//|+setDay(arg:int):void                                                |
//|+setTime(arg:int):void                                               |
//|+setSectionNum(arg:int):void                                         |
//|+setStudentLimit(arg:int):void                                       |
//|+setCourse(arg:Course):void                                          |
//|+setLecturer(arg:Lecturer):void                                      |
//|+setLecturer(lecturers:ArrayList<Lecturer>,random:boolean):void      |  
//|+setVenue(arg:Venue):void                                            |
//|+setVenue(venues:ArrayList<Venue>):void                              |
//|+getDay():int                                                        |
//|+getTime():int                                                       | 
//|+getStudentLimit():int                                               |
//|+getSectionNum():String                                              |
//|+getDay_inWords():String                                             |
//|+getTime_inWords():String                                            |
//|+getCourse():Course                                                  |
//|+getLecturer():Lecturer                                              | 
//|+getVenue():Venue                                                    |
//|+generateSchedule(sections:ArrayList<Section>,random:boolean):boolean|
//|+equals(other:Object):boolean                                        |   
//|+toString():String                                                   |
//+---------------------------------------------------------------------+

import java.util.ArrayList;

public class Section {
	private int sectionNum, studentLimit=30, time=0, day=0;
	private String time_inWords = null, day_inWords = null, note;
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
	public int getDay(){ return day; }

	public void setTime(int arg){ time = arg; setTime_inWords(arg); }
	public int getTime(){ return time; }

	public void setSectionNum(int arg){ sectionNum = arg; }
	public String getSectionNum(){ return Integer.toString(sectionNum); }

	public int getStudentLimit(){ return studentLimit; }
	public void setStudentLimit(int arg){ studentLimit = arg; }

	public void setCourse(Course arg){ course = arg; }
	public Course getCourse(){ return course; }

	public void setLecturer(Lecturer arg){
		try{
			if(arg.getSpecializations().contains(course.getCode()))
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
				if(lecturer.getSpecializations().contains(course.getCode()))
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
	public Lecturer getLecturer(){ return lecturer; }
	
	public void setVenue(Venue arg){
		try{
			if (arg.getCourses().contains(course.getCode()))
				venue = arg;
			else
				venue = new Venue("TO BE DETERMINED");
		}
		catch(Exception e){
			// In case no venue for a subject, make dummy venue
			venue = new Venue("TO BE DETERMINED");
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
			venue = new Venue("TO BE DETERMINED");
		}
	}
	public Venue getVenue(){ return venue; }

	private void setDay_inWords(int arg){
		switch(arg) {
			case 0: day_inWords = "Mon/Wed"; break;
			case 1: day_inWords = "Tue/Thu"; break;
			default: day_inWords = null;
		}
	}
	public String getDay_inWords(){ return day_inWords; }

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
	public String getTime_inWords(){ return time_inWords; }

	public boolean generateSchedule(ArrayList<Section> sections, boolean random){
		int tempDay=getDay(), tempTime=getTime(), max=0;
		boolean lecturerFree = true, venueUsable = true, spaceAvailable = true;

		// Checking if the section has a schedule
		if (!(time_inWords == null) && !(day_inWords == null)) {
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
					tempTime = (int) (Math.random() * (max/2+1)+1);
				}
				else if(count>11 && count<23){
					tempDay = count%2;
					if(count<17)
						tempTime = (count%6)/2;
					else
						tempTime = (count-11)/2;
					System.out.println(count + " " + tempDay + " " + tempTime);
				}
				else if(count>22) {
					for (boolean b : venue.getAvailability()[0]) {
						
					}
					return false;
				}
			}
			// If random is false, fill up morning sessions first
			else{
				if(count<12) {
					tempDay = count%2;
					if(count<6)
						tempTime = (count%6)/2;
					else
						tempTime = count/2;
				}
				else if(count>11) return false;
			}
			// Check if lecturer and venue are available for same time
			lecturerFree = lecturer.isAvailableAt(tempDay, tempTime);
			venueUsable = venue.isAvailableAt(tempDay, tempTime);
			count++;
		}while(!lecturerFree || !venueUsable);

		// Remove availaibility from lecturer and venue
		lecturer.setAvailabilityAt(tempDay, tempTime, false);
		venue.setAvailabilityAt(tempDay, tempTime, false);

		setDay(tempDay);
		setTime(tempTime);

		if(studentLimit > venue.getStudentLimit())
			note = "Student exceeded venue capacity.";

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
		String rep = getSectionNum() + ", " + getCourse().getCode() + ", " + getLecturer().getName() + ", " + getVenue().getName() + ", " + getDay_inWords() + ", " + getTime_inWords() + ", " + note;
		return rep;
	}
}