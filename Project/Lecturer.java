import java.util.concurrent.atomic.AtomicInteger;

public class Lecturer {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
	private String name, specialization;

	public Lecturer(String arg){
		setName(arg);
		id = nextId.incrementAndGet();
	}

	public void setName(String arg){ name = arg; }
	public void setSpecialization(String arg){ specialization = arg; }

	public String getName(){ return name; }
	public String getSpecialization(){ return specialization; }

	public String toString(){
		return "Lecturer: " + name + "\n";
	}
}