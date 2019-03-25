package com.srp.installpacket.view;

import java.io.File;

import com.srp.installpacket.viewmodel.InstallPacketViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

	@FXML
	private CheckBox srpcheckBox;

	@FXML
	private CheckBox gfpCheckBox;

	private InstallPacketViewModel viewmodel;

	public InstallPacketCodeBehind() {
		this.viewmodel = new InstallPacketViewModel();
	}

	public void initialize() {
		this.csvFileTextField.textProperty().bindBidirectional(this.viewmodel.csvLocationProperty());
		this.salesOrderTextField.textProperty().bindBidirectional(this.viewmodel.soNumberProperty());
		this.setupBindings();
	}

	private void setupBindings() {
		this.createInstallButton.disableProperty().bind(
				this.salesOrderTextField.textProperty().isEmpty().and(this.csvFileTextField.textProperty().isEmpty()));
		this.csvFileTextField.disableProperty().bind(this.salesOrderTextField.textProperty().isNotEmpty());
		this.salesOrderTextField.disableProperty().bind(this.csvFileTextField.textProperty().isNotEmpty());

	}

	@FXML
	void createInstallPacket() {
		if (this.salesOrderTextField.textProperty().get().matches("SO\\d{7}")) {
			if (this.srpcheckBox.isSelected()) {
				this.viewmodel.createInstallPacket("SRP");
			} else if (this.gfpCheckBox.isSelected()) {
				this.viewmodel.createInstallPacket("GFP");
			} else {
				this.viewmodel.createInstallPacket("None");
			}

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Invalid entry");
			alert.setContentText("You must fill out the full sales order number. Example:SO0123456");
			alert.showAndWait();
		}

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

	@FXML
	void addGFPGuideLine(ActionEvent event) {

	}

	@FXML
	void addSRPGuideLine(ActionEvent event) {

	}

}
