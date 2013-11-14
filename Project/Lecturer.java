public class Lecturer {
	private String name;

	public Lecturer(String arg){ setName(arg); }

	public String getName(){ return name; }

	public void setName(String arg){ name=arg; }

	public String toString(){
		return "Lecturer: " + name + "\n";
	}
}