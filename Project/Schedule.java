import java.util.concurrent.atomic.AtomicInteger;

public class Schedule {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
	private int time;
	private String timeRepresentation;
	private Section section;
	private Venue venue;

	public Schedule(Section section, Venue venue, int time){ 
		setSection(section);
		setVenue(venue);
		setTime(time);
		id = nextId.incrementAndGet();
		// checkAvaibility(section.getLecturer(), venue);
	}

	// public void checkAvaibility(Lecturer lecturer, Venue venue){
	// 	String lecturerName = lecturer.getName();
	// 	String venueName = venue.getName();
	// 	time = (int) (Math.random() * 5);
	// 	setTime(time);
	// }

	public void setTime(int arg){
		time = arg;
		switch(arg){
			case 0: timeRepresentation = "08:30 - 09:50"; break;
			case 1: timeRepresentation = "10:00 - 11:20"; break;
			case 2: timeRepresentation = "11:30 - 12:50"; break;
			case 3: timeRepresentation = "14:00 - 15:20"; break;
			case 4: timeRepresentation = "15:30 - 16:50"; break;
			case 5: timeRepresentation = "17:00 - 18:20"; break;
			default: break;
		}
	}
	public void setVenue(Venue arg){ venue = arg; }
	public void setSection(Section arg){ section = arg; }

	// public String getTime(){ return Integer.toString(time); }
	public int getTime(){ return time; }
	public Venue getVenue(){ return venue; }
	public Section getSection(){ return section; }

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Schedule))
	        return false;

	    Schedule that = (Schedule) other;

	    // Custom equality check here.
	    return this.venue.equals(that.venue)
	    	&& this.section.equals(that.section);
	}

	public String toString(){
		return section + ", " + venue + ", " + timeRepresentation;
	}
}