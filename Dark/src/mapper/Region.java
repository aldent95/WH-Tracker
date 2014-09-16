package mapper;

public class Region {
	private long ID;
	private String name;

	public Region(long id, String n) {
		ID = id;
		name = n;
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
