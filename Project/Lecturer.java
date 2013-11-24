import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

public class Lecturer {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
    public ArrayList<Integer> availability = new ArrayList<Integer>(java.util.Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
	private String name;
	private ArrayList<String> specialization = new ArrayList<String>();

	public Lecturer(String name, String specialization){
		setName(name);
		addSpecialization(specialization);
		id = nextId.incrementAndGet();
	}

	public void setName(String arg){ name = arg; }
	public void addSpecialization(String arg){ specialization.add(arg); }

	public String getName(){ return name; }
	public ArrayList<String> getSpecialization(){ return specialization; }

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Lecturer))
	        return false;

	    Lecturer that = (Lecturer) other;

	    // Custom equality check here.
	    return this.name.equals(that.name);
	}

	public String toString(){
		return name;
	}
}