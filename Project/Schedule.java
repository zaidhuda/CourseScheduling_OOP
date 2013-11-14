public class Schedule {
	private int time;
	private String timeRepresentation;

	public Schedule(int arg){ setTime(arg); }

	public String getTime(){ return Integer.toString(time); }

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

	public String toString(){
		return "Time: " + timeRepresentation + "\n";
	}
}