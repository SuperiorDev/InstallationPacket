package com.srp.installpacket.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

public class Manager {
	private static final String MASTER_FILE = "C:\\Users\\jeremy.trimble\\Documents\\Projects\\PDFMerger\\TopLevelFileLocations.csv";
	private static final String SAVE_TO = "C:\\Users\\jeremy.trimble\\Documents\\Projects\\PDFMerger\\";
	private String csvLocation;
	private String soNumber;

	public Manager(String soNumber, String csvLocation) {
		this.csvLocation = csvLocation;
		this.soNumber = soNumber;
	}

	public void createInstallPacket() {

		try {
			PDFMergerUtility merger = new PDFMergerUtility();
			DataReader reader = new DataReader(new File(Manager.MASTER_FILE));
			HashMap<String, String> manualsMaster = reader.readMasterFile();
			PDDocument test = new PDDocument();
			test.save(new File(Manager.SAVE_TO + this.soNumber + ".pdf"));
			List<String> manuals = reader.readTopLevelFile(new File(this.csvLocation));
			for (String current : manuals) {
				String location = manualsMaster.get(current);
				File newfile = new File(location);
				merger.addSource(newfile);
				System.out.println("Found");
			}
			merger.setDestinationFileName(Manager.SAVE_TO + this.soNumber + ".pdf");
			merger.mergeDocuments(null);

			test.close();
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
}
