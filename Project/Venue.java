import java.util.concurrent.atomic.AtomicInteger;

public class Venue {
    private static AtomicInteger nextId = new AtomicInteger();
    final int id;
	private String name, type="";

	public Venue(String arg){
		setName(arg);
		id = nextId.incrementAndGet();
	}

	public void setName(String arg){ name = arg; }
	public void setType(String arg){ type = arg; }

	public String getName(){ return name; }
	public String getType(){ return type; }

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof Venue))
	        return false;

	    Venue that = (Venue) other;

	    // Custom equality check here.
	    return this.name.equals(that.name)
	    	&& this.type.equals(that.type);
	}

	public String toString(){
		return name;
	}
}