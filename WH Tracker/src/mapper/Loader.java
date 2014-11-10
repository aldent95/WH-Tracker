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
	private HashMap<String, Long[]> systems = new HashMap<String, Long[]>();
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
	 * Loads all the Systems from file
	 * @return
	 */
	HashMap<String, Long[]> loadSystems() {
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
				Long[] s = new Long[3];
				s[0] = region;
				s[1] = con;
				s[2] = Long.parseLong(values[2]);
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
