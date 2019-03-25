package com.srp.installpacket.viewmodel;

import com.srp.installpacket.model.Manager;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InstallPacketViewModel {

	private Manager manager;
	private StringProperty csvLocationProperty;
	private StringProperty soNumberProperty;

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

	public InstallPacketViewModel() {
		this.csvLocationProperty = new SimpleStringProperty();
		this.soNumberProperty = new SimpleStringProperty();

	}

	public void createInstallPacket() {
		Manager manager = new Manager(this.soNumberProperty.get(), this.csvLocationProperty.get());
		manager.createInstallPacket();
	}
}
