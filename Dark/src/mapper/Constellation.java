package mapper;

	
public class Constellation {
	private final Region region;
	private final long ID;
	private final String name;
	public Constellation(Region r, long i, String n){
		region = r;
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
