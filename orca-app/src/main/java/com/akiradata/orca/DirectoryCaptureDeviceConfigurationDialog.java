package com.akiradata.orca;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;

public class DirectoryCaptureDeviceConfigurationDialog extends Dialog {

	@FXML TextField dirSourceLocationTxt;
	@FXML ComboBox<String> dirSourceFileTypeFilterCbo;
	ObjectProperty<DirectoryCaptureDevice> device = new SimpleObjectProperty<DirectoryCaptureDevice>();
	
	public DirectoryCaptureDeviceConfigurationDialog(Object owner, String title) {
		super(owner, title, false, DialogStyle.NATIVE);
		init();
	}

	public DirectoryCaptureDeviceConfigurationDialog(Object owner,
			String title, boolean lightweight) {
		super(owner, title, lightweight, DialogStyle.NATIVE);
		init();
	}

	public DirectoryCaptureDeviceConfigurationDialog(Object owner,
			String title, boolean lightweight, DialogStyle style) {
		super(owner, title, lightweight, style);
		init();
	}

	private void init() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					"/fxml/DirectoryCaptureDeviceConfigurationDialog.fxml"));
			fxmlLoader.setRoot(this.getContent());
			fxmlLoader.setController(this);
			this.setContent(fxmlLoader.load());
		} catch (IOException e) {
			throw new ApplicationRuntimeException(e);
		}
		
		setResizable(false);
		setClosable(true);
		setIconifiable(false);
		setTitle("Configure capture device");
		setMasthead("Select image source directory");
		getActions().addAll(Dialog.Actions.OK, Dialog.Actions.CANCEL);	
		getActions().get(0).disabledProperty().set(true);
		
		// TODO dynamically populate filter combobox by integorating 
		// a filter registry for supported image types 
		this.dirSourceFileTypeFilterCbo.getItems().add("All supported image types (TIFF, JPG, PNG)");
		
	}

	public DirectoryCaptureDevice getDevice() {
		return this.device.get();
	}

	public void setDevice(DirectoryCaptureDevice device) {
		this.device.set(device);
	}

	
	
}
