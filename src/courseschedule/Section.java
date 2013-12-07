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
    private String note;
    private Course course = null;
    private Lecturer lecturer = null;
    private Venue venue = null;

    // Section must have course and lecturer
    public Section(Course course, Lecturer lecturer, int sectionNum){
        setCourse(course);
        setLecturer(lecturer);
        setSectionNum(sectionNum);
    }

    public void setDay(int arg){ day = arg; }
    public int getDay(){ return day; }
    public String getDay_inWords(){
        switch(day) {
            case 0: return "Mon/Wed";
            case 1: return "Tue/Thu";
            default: return null;
        }
    }

    public void setTime(int arg){ time = arg; }
    public int getTime(){ return time; }
    public String getTime_inWords(){
        switch(time){
            case 0: return "08:30 - 09:50";
            case 1: return "10:00 - 11:20";
            case 2: return "11:30 - 12:50";
            case 3: return "14:00 - 15:20";
            case 4: return "15:30 - 16:50";
            case 5: return "17:00 - 18:20";
            default: return null;
        }
    }

    public void setSectionNum(int arg){
        sectionNum = arg;
    }

    public String getSectionNum(){
        return Integer.toString(sectionNum);
    }

    public int getStudentLimit(){
        return studentLimit;
    }

    public void setStudentLimit(int arg){
        studentLimit = arg;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCourse(Course arg){
        course = arg;
    }

    public Course getCourse(){
        return course;
    }

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
        catch(Exception ignored){}
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
            ArrayList<String> courses;
            ArrayList<Venue> tempVenues = new ArrayList<Venue>();

            // Checking if venue can be used for a subject
            for (Venue v : venues) {
                courses = v.getCourses();
                if (courses.contains(code)){
                    // Adding usable venue for a subject to ArrayList
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

    public boolean generateSchedule(boolean random){
        boolean lecturerFree, venueUsable;

        if(day != -1 || time != -1)
            setUsing(day, time, false);

        int count = 0;

        do{
            if(random){
                if(count<12){
                    day = (int) (Math.random() * 2);
                    time = (int) (Math.random() * (3) + 1);
                }
                else
                    if (!setDayAndTime(count-12))
                        return false;
            }
            else{
                if (!setDayAndTime(count))
                    return false;
            }

            // Check if lecturer and venue are available for same time
            lecturerFree = lecturer.isAvailableAt(day, time);
            venueUsable = venue.isAvailableAt(day, time);
            count++;

        }while(!lecturerFree || !venueUsable);

        setUsing(day, time, true);

        if(studentLimit > venue.getStudentLimit())
            setNote("Student exceeded venue capacity.");

        return true;
    }

    private boolean setDayAndTime(int i){
        if(i<12) {
            day = i%2;
            if(i<6)
                time = (i%6)/2;
            else
                time = i/2;
        }
        else {
            setDay(-1);
            setTime(-1);
            return false;
        }

        return true;
    }

    private void setUsing(int day, int time, boolean used){
        lecturer.setAvailabilityAt(day, time, !used);
        venue.setAvailabilityAt(day, time, !used);
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
        return getSectionNum() + ", " + getCourse().getCode() + ", " + getLecturer().getName() + ", " + getVenue().getName() + ", " + getDay_inWords() + ", " + getTime_inWords() + ", " + getNote();
    }
}