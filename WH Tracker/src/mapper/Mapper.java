package mapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Mapper {
	private HashMap<String, Solarsystem> systems;
	private GUI gui;

	public Mapper() {
		gui = new GUI(this);
		load();
		gui.show();//FIXME This should be good to remove
	}

	/**
	 * Used to save a file to filename
	 * @param filename File to save to
	 * @param contents Contents of the list that are getting saved
	 */
	protected void save(String filename, String contents) {
		File out = new File("./saves/" + filename + ".txt");
		BufferedWriter writer;
		try {
			String[] lines = contents.split("\n");
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(out), "utf-8"));

			for (int i = 1; i < lines.length; i++) {
				writer.append(lines[i]);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Adds a string to the list after retriving all the relevent info.
	 * @param s
	 * @throws InvalidSystemException
	 */
	protected void add(String s) throws InvalidSystemException {
		if (s.equals("clear")) {
			clearSave();
			return;
		}
		//Get the current date/time in eve time
		DateTimeFormatter dateFormat = DateTimeFormat
				.forPattern("yyyy/MM/dd HH:mm:ss");
		DateTime now = new DateTime(DateTimeZone.UTC);
		String[] values = new String[5];
		values[0] = dateFormat.print(now);
		values[1] = s;
		if (!systems.containsKey(s))
			throw new InvalidSystemException("No Such System" + s);
		Solarsystem sys = systems.get(s);
		values[2] = Long.toString((sys.getID()));
		values[3] = Long.toString(sys.getCon().getID());
		values[4] = Long.toString(sys.getRegion().getID());
		gui.append(values);
		write(values);
	}
	/**
	 * Clears the save file
	 */
	private void clearSave() {
		File out = new File("./saves/Save.txt");
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(out), "utf-8"));
			writer.write("");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		gui.clear();
	}
	/**
	 * Writes the current data to autosave file
	 * @param towrite
	 */
	private void write(String[] towrite) {
		File out = new File("./saves/Save.txt");
		String contents = "";
		try {
			BufferedReader data = new BufferedReader(new FileReader(out));
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
		BufferedWriter writer;
		try {
			String sep = "\t";
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(out), "utf-8"));

			writer.write(towrite[0] + sep + towrite[1] + sep + towrite[2] + sep
					+ sep + towrite[3] + sep + sep + towrite[4] + "\n");
			writer.append(contents);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Loads all data at the start of the program
	 */
	private void load() {
		Loader l = new Loader();
		gui.append(l.load());
		l.loadRegions();
		l.loadCons();
		systems = l.loadSystems();
	}
	/**
	 * Used to get the data for the find request from the GUI
	 * @param string
	 * @return
	 * @throws InvalidSystemException
	 */
	protected String[] find(String string)throws InvalidSystemException {
		String[] values = new String[4];
		values[0] = string;
		if (!systems.containsKey(string))
			throw new InvalidSystemException("No Such System" + string);
		Solarsystem sys = systems.get(string);
		values[1] = Long.toString((sys.getID()));
		values[2] = Long.toString(sys.getCon().getID());
		values[3] = Long.toString(sys.getRegion().getID());
		return values;	
	}

}
