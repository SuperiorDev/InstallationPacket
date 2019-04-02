package com.srp.installpacket.viewmodel;

import java.util.List;

import com.srp.installpacket.model.Manager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InstallPacketViewModel {

	private Manager manager;
	private StringProperty csvLocationProperty;
	private StringProperty soNumberProperty;

	public InstallPacketViewModel() {
		this.csvLocationProperty = new SimpleStringProperty();
		this.soNumberProperty = new SimpleStringProperty();

	}

	public void createInstallPacket(String brand) {
		this.manager = new Manager(this.soNumberProperty.get());

		if (this.csvLocationProperty.get() != null) {
			manager.createInstallPacket(brand, this.csvLocationProperty().get());
		}
	}

	public void alertMissingManuals() {
		List<String> missingManuals = this.manager.getMissingManuals();
		if (!missingManuals.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Manuals missing file");
			alert.setHeaderText("The following Sales Order items didn't have manuals");
			String contentString = "";
			for (String current : missingManuals) {
				contentString += current + System.lineSeparator();
			}
			alert.setContentText(contentString);
			alert.showAndWait();
		}
	}

	public StringProperty csvLocationProperty() {
		return csvLocationProperty;
	}

	public void setCsvLocationProperty(StringProperty csvLocationProperty) {
		this.csvLocationProperty = csvLocationProperty;
	}

	public StringProperty soNumberProperty() {
		return soNumberProperty;
	}

	public void setSoNumberProperty(StringProperty soNumberProperty) {
		this.soNumberProperty = soNumberProperty;
	}
}
