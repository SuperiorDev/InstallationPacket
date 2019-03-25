package com.srp.installpacket.view;

import java.io.File;

import com.srp.installpacket.viewmodel.InstallPacketViewModel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class InstallPacketCodeBehind {

	@FXML
	private TextField csvFileTextField;

	@FXML
	private AnchorPane mainPane;

	@FXML
	private TextField salesOrderTextField;

	@FXML
	private Button selectCSVFileButton;

	@FXML
	private Button createInstallButton;

	private InstallPacketViewModel viewmodel;

	public InstallPacketCodeBehind() {
		this.viewmodel = new InstallPacketViewModel();
	}

	public void initialize() {
		this.csvFileTextField.textProperty().bindBidirectional(this.viewmodel.csvLocationProperty());
		this.salesOrderTextField.textProperty().bindBidirectional(this.viewmodel.soNumberProperty());
	}

	@FXML
	void createInstallPacket() {
		this.viewmodel.createInstallPacket();

	}

	@FXML
	void openCSVFile() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Select csv File");
		chooser.getExtensionFilters().add(new ExtensionFilter("CSV Only", "*.csv"));

		Stage stage = (Stage) this.mainPane.getScene().getWindow();
		File selectedFile = chooser.showOpenDialog(stage);
		this.viewmodel.csvLocationProperty().set(selectedFile.getAbsolutePath());
		System.out.println(selectedFile.getAbsolutePath());

	}

}
