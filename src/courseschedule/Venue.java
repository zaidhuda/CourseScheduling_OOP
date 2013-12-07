/*
+------------------------------------------+
|			Venue					         |
+------------------------------------------+
|-name:String 							 |
|-type:String                              |
|-courses:ArrayList<String>                |
|+availability:ArrayList<Integer>          |
+------------------------------------------+
|+setName(arg:String):void                 |
|+setType(arg:String):void                 |
|+addCourse(arg:String):void               |
|+addCourse(args:ArrayList<String>):void   |
|+addAvailability(arg:int):void            |
|+removeAvailability(arg:int):void         |
|+getName():String                         |
|+getType():String                         |
|+getCourses():ArrayList<String>           |
|+getAvailability():ArrayList<Integer>     |
|+equals(other:Object):boolean             |
|+toString():String                        |
+------------------------------------------+
*/

package courseschedule;

import java.util.*;

public class Venue extends Support {
	private int studentLimit = 30;

	public Venue(String name){
		setName(name);
		Arrays.fill(availability[0], true);
		Arrays.fill(availability[1], true);
	}

	public void setStudentLimit(int arg){
        studentLimit = arg;
    }

	public int getStudentLimit(){
        return studentLimit;
    }

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Venue))
			return false;

		Venue that = (Venue) other;

		return this.name.equalsIgnoreCase(that.name);
	}
}