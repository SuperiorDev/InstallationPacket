package com.srp.installpacket.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DataReader {

	private File topLevelItems;

	/**
	 * Creates a Reader
	 */
	public DataReader(File file) {
		if (file == null) {
			throw new IllegalArgumentException("File cannot be null");
		}
		this.topLevelItems = file;
	}

	/**
	 * Reads the file and provides a list of available manuals
	 * 
	 */
	public HashMap<String, String> readMasterFile() {
		HashMap<String, String> manuals = new HashMap<String, String>();
		try (Scanner scanner = new Scanner(this.topLevelItems)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] fields = line.split(",");
				String part = fields[0];
				String location = fields[1];
				manuals.put(part, location);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Master file not found");
		}
		return manuals;
	}

	/**
	 * Reads the file and provides a list
	 * 
	 */
	public List<String> readTopLevelFile(File file) {
		List<String> manuals = new ArrayList<String>();
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				;
				manuals.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return manuals;
	}

}
