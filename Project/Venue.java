import java.util.concurrent.atomic.AtomicInteger;

public class Venue {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
	private String name, type;

	public Venue(String arg){
		setName(arg);
		id = nextId.incrementAndGet();
	}

	public void setName(String arg){ name = arg; }
	public void setType(String arg){ type = arg; }

	public String getName(){ return name; }
	public String getType(){ return type; }

	public String toString(){
		return "Venue: " + name + "\n";
	}
}