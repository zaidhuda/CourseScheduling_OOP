import java.util.concurrent.atomic.AtomicInteger;

public class Lecturer {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
	private String name, specialization="";

	public Lecturer(String arg){
		setName(arg);
		id = nextId.incrementAndGet();
	}

	public void setName(String arg){ name = arg; }
	public void setSpecialization(String arg){ specialization = arg; }

	public String getName(){ return name; }
	public String getSpecialization(){ return specialization; }

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Lecturer))
	        return false;

	    Lecturer that = (Lecturer) other;

	    // Custom equality check here.
	    return this.name.equals(that.name);
	}

	public String toString(){
		return id + "\nLecturer: " + name;
	}
}