import java.util.concurrent.atomic.AtomicInteger;

public class Lecturer {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
	private String name;

	public Lecturer(String arg){
		setName(arg);
		id = nextId.incrementAndGet();
	}

	public String getName(){ return name; }

	public void setName(String arg){ name = arg; }

	public String toString(){
		return "Lecturer: " + name + "\n";
	}
}