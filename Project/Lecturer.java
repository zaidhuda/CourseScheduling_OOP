import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;

public class Lecturer {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
	private String name;
    public ArrayList<Integer> availability = new ArrayList<Integer>(java.util.Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
	private ArrayList<String> specialization = new ArrayList<String>();

	public Lecturer(String name){
		setName(name);
		id = nextId.incrementAndGet();
	}

	public Lecturer(String name, ArrayList<String> specialization){
		setName(name);
		addSpecialization(specialization);
		id = nextId.incrementAndGet();
	}

	public Lecturer(String name, String specialization){
		setName(name);
		addSpecialization(specialization);
		id = nextId.incrementAndGet();
	}

	public void setName(String arg){ name = arg; }
	public void addSpecialization(String arg){ specialization.add(arg); }
	public void addSpecialization(ArrayList<String> arg){for (String a : arg) specialization.add(a); }
	public void addAvailability(int arg){ availability.add(arg); }

	public String getName(){ return name; }
	public ArrayList<String> getSpecialization(){ return specialization; }
	public ArrayList<Integer> getAvailability(){ return availability; }

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Lecturer))
	        return false;

	    Lecturer that = (Lecturer) other;

	    return this.name.equalsIgnoreCase(that.name);
	}

	public String toString(){
		return name;
	}
}