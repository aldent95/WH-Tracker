package mapper;

public class system {
	private final Region region;
	private final Constellation con;
	private final long ID;
	private final String name;
	public system(Region r, Constellation c, long i, String n){
		region  =r;
		con = c;
		ID = i;
		name = n;
	}
	/**
	 * @return the region
	 */
	Region getRegion() {
		return region;
	}
	/**
	 * @return the con
	 */
	Constellation getCon() {
		return con;
	}
	/**
	 * @return the iD
	 */
	long getID() {
		return ID;
	}
	/**
	 * @return the name
	 */
	String getName() {
		return name;
	}
	
}
