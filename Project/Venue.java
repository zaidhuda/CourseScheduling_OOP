public class Venue {
	private String name;

	public Venue(String arg){ setName(arg); }

	public String getName(){ return name; }

	public void setName(String arg){ name=arg; }

	public String toString(){
		return "Venue: " + name + "\n";
	}
}