package com.akiradata.orca.capture;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akiradata.orca.ApplicationRuntimeException;

public class CaptureDeviceSelectionDialog extends Dialog {

	final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ObjectProperty<CaptureDevice> selectedDevice = new SimpleObjectProperty<>();

	@FXML private ListView<CaptureDevice> captureDeviceListView;
	
	public CaptureDeviceSelectionDialog(Object owner, String title) {
		super(owner, title, false, DialogStyle.NATIVE);
		init();
	}
	
	public CaptureDeviceSelectionDialog(Object owner, String title,
			boolean lightweight) {
		super(owner, title, lightweight, DialogStyle.NATIVE);
		init();
	}

	public CaptureDeviceSelectionDialog(Object owner, String title,
			boolean lightweight, DialogStyle style) {
		super(owner, title, lightweight, style);
	}



	private void init(){
		try{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CaptureDeviceSelectionDialog.fxml"));
			fxmlLoader.setRoot(this.getContent());
			fxmlLoader.setController(this);
			this.setContent(fxmlLoader.load());
		}catch(IOException e){
			throw new ApplicationRuntimeException(e);
		}	
		
		setResizable(false);
		setClosable(true);
		setIconifiable(false);
		setMasthead("Select capture device to use.");
		getActions().addAll(Dialog.Actions.OK, Dialog.Actions.CANCEL);	
		getActions().get(0).disabledProperty().set(true);
	
		this.selectedDevice.addListener((obs, oldVal, newVal) -> 
			getActions().get(0).disabledProperty().set(newVal == null));
		
		this.captureDeviceListView.setCellFactory(list -> new CaptureDeviceListCell());
		this.captureDeviceListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		this.captureDeviceListView.getSelectionModel().selectedItemProperty()
			.addListener((obs, oldVal, newVal) -> this.selectedDevice.set(newVal));
		populateList();
		
	}
	
	public CaptureDevice getSelectedCaptureDevice(){
		return this.selectedDevice.get();
	}
	
	private void populateList(){
		this.captureDeviceListView.getItems().clear();
		this.captureDeviceListView.getItems().addAll(CaptureDeviceManager.enumerate());
	}
	
	class CaptureDeviceListCell extends ListCell<CaptureDevice>{

		@Override
		protected void updateItem(CaptureDevice item, boolean empty) {
			super.updateItem(item, empty);
			if (empty || item == null){
				//setText("<no name>");
				;
			}else{
				setText(item.getName());
			}
		}
	}
	

}
