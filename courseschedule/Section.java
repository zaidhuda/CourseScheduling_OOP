/*
+---------------------------------------------------------------------+
|                Section                                              |
+---------------------------------------------------------------------+
|-sectionNum:int                                                      |
|-studentLimit:int                                                    |
|-time:int                                                            |
|-day:int                                                             |
|-time_inWords:String                                                 |
|-day_inWords:String                                                  |
|-course:course                                                       |
|-lecturer:Lecturer                                                   |
|-venue:Venue                                                         |
|+userDefined:boolean                                                 |
+---------------------------------------------------------------------+
|-setTime_inWords(arg:int):void                                       |
|-setDay_inWords(arg:int):void                                        |
|+setDay(arg:int):void                                                |
|+setTime(arg:int):void                                               |
|+setSectionNum(arg:int):void                                         |
|+setStudentLimit(arg:int):void                                       |
|+setCourse(arg:Course):void                                          |
|+setLecturer(arg:Lecturer):void                                      |
|+setLecturer(lecturers:ArrayList<Lecturer>,random:boolean):void      |
|+setVenue(arg:Venue):void                                            |
|+setVenue(venues:ArrayList<Venue>):void                              |
|+getDay():int                                                        |
|+getTime():int                                                       |
|+getStudentLimit():int                                               |
|+getSectionNum():String                                              |
|+getDay_inWords():String                                             |
|+getTime_inWords():String                                            |
|+getCourse():Course                                                  |
|+getLecturer():Lecturer                                              |
|+getVenue():Venue                                                    |
|+generateSchedule(sections:ArrayList<Section>,random:boolean):boolean|
|+equals(other:Object):boolean                                        |
|+toString():String                                                   |
+---------------------------------------------------------------------+
*/

package courseschedule;

import java.util.ArrayList;

public class Section {
    private int sectionNum, studentLimit = 30, time = -1, day = -1;
    private String note = null;
    private Course course = null;
    private Lecturer lecturer = null;
    private Venue venue = null;

    // Section must have course and lecturer
    public Section(int sectionNum, Course course, Lecturer lecturer, Venue venue) {
        setCourse(course);
        setLecturer(lecturer);
        setVenue(venue);
        setSectionNum(sectionNum);
    }

    public Section(int sectionNum, Course course, Lecturer lecturer, Venue venue, int studentLimit) {
        setSectionNum(sectionNum);
        setCourse(course);
        setLecturer(lecturer);
        setVenue(venue);
        setStudentLimit(studentLimit);
    }

    public void setDayAndTime(int newDay, int newTime) {
	    System.out.println(newDay + " " + newTime);
	    try {
	        if (!(newDay == -1 && newTime == -1) && !(newDay == day && newTime == time)){
		        if (venue == null) {
		            venue = new Venue();
		        } else if (lecturer == null) {
		            lecturer = new Lecturer();
		        }
	            boolean lecturerAvailable = lecturer.isAvailableAt(newDay, newTime),
			            venueAvailable = venue.isAvailableAt(newDay, newTime);
	            if ((lecturerAvailable && venueAvailable) || (day == -1 && time == -1)){
		            if (!(day == -1 && time == -1)){
	                    venue.setAvailabilityAt(day, time, true);
	                    lecturer.setAvailabilityAt(day, time, true);
		            }
		            day = newDay;
		            time = newTime;
	                venue.setAvailabilityAt(day, time, false);
	                lecturer.setAvailabilityAt(day, time, false);
		            System.out.println("hish.");
	            } else {
	                System.out.println("Cannot change schedule.");
	            }
	        } else {
		        day = newDay;
		        time = newTime;
	        }
        } catch (Exception e) {
            System.out.println("failed!");
        }
//        day = newDay;
//        time = newTime;
    }

    public int getDay() {
        return day;
    }

    public String getDay_inWords() {
        switch (day) {
            case 0:
                return "Mon/Wed";
            case 1:
                return "Tue/Thu";
            default:
                return null;
        }
    }

    public int getTime() {
        return time;
    }

    public String getTime_inWords() {
        switch (time) {
            case 0:
                return "08:30 - 09:50";
            case 1:
                return "10:00 - 11:20";
            case 2:
                return "11:30 - 12:50";
            case 3:
                return "14:00 - 15:20";
            case 4:
                return "15:30 - 16:50";
            case 5:
                return "17:00 - 18:20";
            default:
                return null;
        }
    }

    public void setSectionNum(int arg) {
        sectionNum = arg;
    }

    public String getSectionNum() {
        return Integer.toString(sectionNum);
    }

    public int getStudentLimit() {
        return studentLimit;
    }

    public void setStudentLimit(int arg) {
        studentLimit = arg;
    }

    public void setNote(String arg) {
        note = arg;
    }

    public String getNote() {
        return note;
    }

    public void setCourse(Course arg) {
        course = arg;
    }

    public Course getCourse() {
        return course;
    }

    public void setLecturer(Lecturer arg) {
        try {
            if (arg.getSpecializations().contains(course.getCode()))
                lecturer = arg;
            else
                lecturer = new Lecturer();
        } catch (Exception e) {
            // In case no venue for a subject, make dummy venue
            lecturer = new Lecturer();
        }
    }

    public void setLecturer(ArrayList<Lecturer> lecturers, boolean random) {
        // main loop, creating sections for each course
        ArrayList<Lecturer> tempLecturers = new ArrayList<>();

        // add lecturers of current course to temporary list
        for (Lecturer lecturer : lecturers)
            if (lecturer.getSpecializations().contains(course.getCode()))
                tempLecturers.add(lecturer);

        // randomly assign lecturer to sections
        try {
            if (random) {
                int r = (int) (Math.random() * tempLecturers.size());
                setLecturer(tempLecturers.get(r));
            }
            // assign lecturer based on order
            else setLecturer(tempLecturers.get((sectionNum + 1) % tempLecturers.size()));
        } catch (Exception ignored) {
        }

        tempLecturers.clear();
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setVenue(Venue arg) {
        try {
            if (arg.getCourses().contains(course.getCode()))
                venue = arg;
            else
                venue = new Venue();
        } catch (Exception e) {
            // In case no venue for a subject, make dummy venue
            venue = new Venue();
        }
    }

    public void setVenue(ArrayList<Venue> venues) {
        try {
            String code = course.getCode();
            ArrayList<String> courses;
            ArrayList<Venue> tempVenues = new ArrayList<>();

            for (Venue v : venues) {
                courses = v.getCourses();
                if (courses.contains(code))
                    tempVenues.add(v);
            }

            int r = (int) (Math.random() * tempVenues.size());
            venue = tempVenues.get(r);
            tempVenues.clear();
        } catch (Exception e) {
            venue = new Venue();
        }
    }

    public Venue getVenue() {
        return venue;
    }

    public void generateSchedule(boolean random) {
        if (venue != null && lecturer != null) {
            boolean lecturerOccupied, venueUsed;
            String str = "Success. ";

            if (day != -1 || time != -1)
                setUsing(day, time, false);

            int count = 0;

            do {
                if (random && count < 12) {
                    day = (int) (Math.random() * 2);
                    time = (int) (Math.random() * (3) + 1);
                } else if (!random || count >= 12) {
                    int i = count;
                    if (random) i -= 12;
                    if (!setDayAndTime(i)) {
                        str = "Failed. ";
                        setNote(str);
                        return;
                    }
                }

                lecturerOccupied = !lecturer.isAvailableAt(day, time);
                venueUsed = !venue.isAvailableAt(day, time);
                count++;

            } while (lecturerOccupied || venueUsed);

            setUsing(day, time, true);

            if (studentLimit > venue.getStudentLimit())
                str += "Student exceeds venue capacity.";

            setNote(str);
        }
    }

    private boolean setDayAndTime(int count) {
        if (count < 12) {
            day = count % 2;
            if (count < 6)
                time = (count % 6) / 2;
            else
                time = count / 2;
        } else {
            day = -1;
            time = -1;
            return false;
        }
        return true;
    }

    private void setUsing(int day, int time, boolean isUsed) {
        lecturer.setAvailabilityAt(day, time, !isUsed);
        venue.setAvailabilityAt(day, time, !isUsed);
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

    public String getDetails() {
        return getSectionNum() + ";" + getDay() + ";" + getTime() + ";" + getStudentLimit() + ";" +
                getCourse().getCode() + ";" + getLecturer().getName() + ";" + getVenue().getName() + ";" + getNote();
    }

    public String toString() {
        return getSectionNum() + ", " + getCourse().getCode() + ", " + getLecturer().getName() + ", " + getVenue().getName() + ", " + getDay_inWords() + ", " + getTime_inWords() + ", " + getNote();
    }
}