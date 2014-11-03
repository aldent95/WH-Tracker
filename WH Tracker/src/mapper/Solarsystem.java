package mapper;

public class Solarsystem {
	private final long region;
	private final long con;
	private final long ID;
	private final String name;
	public Solarsystem(long region2, long con2, long id, String name){
		this.region  =region2;
		con = con2;
		ID = id;
		this.name = name;
	}
	/**
	 * @return the region
	 */
	long getRegion() {
		return region;
	}
	/**
	 * @return the con
	 */
	long getCon() {
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
