package mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
//TODO Look into a total redo of this
//This is really a mess. Ill comment what I can but it does need a cleanup
public class Loader {
	private HashMap<Long, Region> regions = new HashMap<Long, Region>();
	private HashMap<Long, Constellation> constellations = new HashMap<Long, Constellation>();
	private HashMap<String, Solarsystem> systems = new HashMap<String, Solarsystem>();
	/**
	 * Starts the initial load
	 * @return Returns the contents of the save file.
	 */
	String load() {
		File in = new File("./saves/Save.txt");
		String contents = "";
		try {
			BufferedReader data = new BufferedReader(new FileReader(in));
			String line = "";
			while (true) {
				line = data.readLine();
				if (line == null)
					break;
				contents = contents + line + "\n";
			}
			data.close();
		} catch (IOException e) {
		}
		return (contents);

	}
	/**
	 * Loads all the regions from file
	 * @return
	 */
	HashMap<Long, Region> loadRegions() {
		InputStream file = getClass().getResourceAsStream(
				"/files/WHRegions.txt");
		BufferedReader data;
		String line = "temp";
		try {
			data = new BufferedReader(new InputStreamReader(file, "utf-8"));
			while (true) {
				line = data.readLine();
				if (line == null)
					break;
				String[] values = line.split("\t");
				Region r = new Region(Long.parseLong(values[0]), values[1]);
				regions.put(Long.parseLong(values[0]), r);

			}
			data.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return regions;

	}
	/**
	 * Loads all the Constellations from file
	 * @return
	 */
	HashMap<Long, Constellation> loadCons() {
		InputStream file = getClass().getResourceAsStream(
				"/files/WHConstellations.txt");
		BufferedReader data;
		String line = "temp";
		try {
			data = new BufferedReader(new InputStreamReader(file, "utf-8"));
			while (true) {
				line = data.readLine();
				if (line == null)
					break;
				String[] values = line.split("\t");
				long region = Long.parseLong(values[0]);
				Constellation c = new Constellation(regions.get(region),
						Long.parseLong(values[1]), values[2]);
				constellations.put(Long.parseLong(values[1]), c);

			}
			data.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return constellations;
	}
	/**
	 * Loads all the Systems from file
	 * @return
	 */
	HashMap<String, Solarsystem> loadSystems() {
		InputStream file = getClass().getResourceAsStream(
				"/files/WHSystems.txt");
		BufferedReader data;
		String line = "temp";
		try {
			data = new BufferedReader(new InputStreamReader(file, "utf-8"));
			while (true) {
				line = data.readLine();
				if (line == null)
					break;
				String[] values = line.split("\t");
				long region = Long.parseLong(values[0]);
				long con = Long.parseLong(values[1]);
				Solarsystem s = new Solarsystem(regions.get(region),
						constellations.get(con), Long.parseLong(values[2]),
						values[3]);
				systems.put(values[3], s);

			}
			data.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return systems;
	}
}
