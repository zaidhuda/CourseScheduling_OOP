
import courseschedule.*;
import courseschedule.util.*;

import java.util.*;

/**
 * Created by Diaz
 * Package: courseschedule.gui
 * Time: 6:44 PM
 * Date: 12/15/13.
 */
public class VenueList extends DropList {
	public VenueList(ScheduleBuilder sb, Course course, Venue venue, int limit) {
		super(sb, course, venue, limit);
	}

	public ArrayList<Course> getCourses() {
		Collections.sort(lCourses);
		String[] c = lCourses.toString().replaceAll("\\[", "").replaceAll("]", "").split(", ");
		return sb.getCourses(c);
	}

	public Venue getVenue() {
		return sb.getVenueByName(name);
	}
}
