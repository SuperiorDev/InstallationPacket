package com.srp.installpacket.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Manager {
	private static final String MASTER_FILE = "C:\\Users\\jeremy.trimble\\Documents\\Projects\\PDFMerger\\TopLevelFileLocations.csv";
	private static final String SAVE_TO = "\\\\ga-fp1\\netsuitedata\\InstallPDFs\\";
	private static final String CSV_LOCATION = "\\\\ga-fp1\\netsuitedata\\InstallCSVs\\";
	private static final String SRP_INSTALL = "\\\\ga-fp1\\Playland\\INSTALLATION GUIDELINES - PDF FILES\\PLAYGROUNDS\\~INST_INSTALLATION INTRODUCTION - SRP - 2017.pdf";
	private static final String GENERAL_MAINTENANCE = "\\\\ga-fp1\\Playland\\INSTALLATION GUIDELINES - PDF FILES\\PLAYGROUNDS\\GENERAL MAINTENANCE CHECKLIST.pdf";
	private String soNumber;
	private List<String> missingManuals;

	public Manager(String soNumber) {
		this.soNumber = soNumber;
		this.missingManuals = new ArrayList<String>();
	}

	public void createInstallPacket(String brand) {

		try {
			PDFMergerUtility merger = new PDFMergerUtility();
			DataReader reader = new DataReader(new File(Manager.MASTER_FILE));
			HashMap<String, String> manualsMaster = reader.readMasterFile();
			PDDocument newDoc = createSaveToFile();
			this.addIntroAndMaintenance(merger, brand);
			List<String> locations = makeLocationAndMissingList(reader, manualsMaster);

			for (String currentLocation : locations) {
				if (currentLocation != null) {
					File newfile = new File(currentLocation);
					merger.addSource(newfile);
					System.out.println("Found");
				}
			}

			merger.setDestinationFileName(Manager.SAVE_TO + this.soNumber + ".pdf");
			merger.mergeDocuments(null);

			newDoc.close();
		} catch (FileNotFoundException fnfe) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(fnfe.getMessage());
			alert.showAndWait();
			System.err.println(fnfe.getMessage());

		} catch (Exception ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	private List<String> makeLocationAndMissingList(DataReader reader, HashMap<String, String> manualsMaster)
			throws FileNotFoundException {
		List<String> manuals = reader.readTopLevelFile(new File(CSV_LOCATION + this.soNumber + ".csv"));
		List<String> locations = new ArrayList<String>();
		for (String current : manuals) {
			if (manualsMaster.containsKey(current)) {
				String location = manualsMaster.get(current);
				if (locations.isEmpty() || !locations.contains(location) && !location.contains(GENERAL_MAINTENANCE)
						&& !location.contains(SRP_INSTALL)) {
					locations.add(location);
				}
			} else {
				this.missingManuals.add(current);
			}
		}
		return locations;
	}

	public void createInstallPacketWithCSV(String brand, String filePath) {

		try {
			PDFMergerUtility merger = new PDFMergerUtility();
			DataReader reader = new DataReader(new File(Manager.MASTER_FILE));
			HashMap<String, String> manualsMaster = reader.readMasterFile();
			PDDocument newDoc = createSaveToFile();
			this.addIntroAndMaintenance(merger, brand);
			List<String> manuals = reader.readTopLevelFile(new File(filePath));
			for (String current : manuals) {
				String location = manualsMaster.get(current);
				if (location != null) {
					File newfile = new File(location);
					merger.addSource(newfile);
					System.out.println("Found");
				} else {
					this.missingManuals.add(current);
				}
			}
			merger.setDestinationFileName(Manager.SAVE_TO + this.soNumber + ".pdf");
			merger.mergeDocuments(null);

			newDoc.close();
		} catch (FileNotFoundException fnfe) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("There is not a csv file named " + this.soNumber
					+ " in the InstallCSV Folder on NetsuiteData Drive");
			alert.showAndWait();

		} catch (Exception ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	private PDDocument createSaveToFile() throws IOException {
		File file = new File(Manager.SAVE_TO + this.soNumber + ".pdf");

		if (file.exists()) {
			PDDocument test = new PDDocument();
			test.save(file);
			return test;
		}
		return null;
	}

	private void addIntroAndMaintenance(PDFMergerUtility merger, String brand) {
		try {
			if (brand.equals("SRP")) {
				File srpIntro = new File(Manager.SRP_INSTALL);
				File srpMaintenance = new File(Manager.GENERAL_MAINTENANCE);
				merger.addSource(srpIntro);
				merger.addSource(srpMaintenance);
			}
			// if(brand.equals("GFP")) {
			// File srpIntro = new file(Manager.GFP_INSTALL);
			// File srpMaintenance = new File(Manager.GFP_GENERAL_MAINTENANCE);
			// merger.addSource(srpIntro);
			// merger.addSource(srpMaintenance);

		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		}

	}

	public List<String> getMissingManuals() {
		return this.missingManuals;
	}

}
