package com.akiradata.orca;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;

public class NewProjectDialog extends Dialog {

	@FXML private TextField projectNameTxf;
	
	@FXML private TextField projectLocationTxf;
	
	public NewProjectDialog(Object owner, String title) {
		super(owner, title, false, DialogStyle.NATIVE);
		init();
	}

	public NewProjectDialog(Object owner, String title, boolean lightweight) {
		super(owner, title, lightweight, DialogStyle.NATIVE);
		init();
	}

	public NewProjectDialog(Object owner, String title, boolean lightweight,
			DialogStyle style) {
		super(owner, title, lightweight, style);
		init();
	}
	
	public void init() throws ApplicationRuntimeException {
		try{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NewProjectDialog.fxml"));
			fxmlLoader.setRoot(this.getContent());
			fxmlLoader.setController(this);
			this.setContent(fxmlLoader.load());
		}catch(IOException e){
			throw new ApplicationRuntimeException(e);
		}
		
		setResizable(true);
		setClosable(true);
		setIconifiable(false);
		
		this.getActions().addAll(
				Dialog.Actions.OK,
				Dialog.Actions.CANCEL);
		
	}
	
	public String getProjectName(){
		return this.projectNameTxf.getText();
	}
	
	public String getProjectLocation(){
		return this.projectLocationTxf.getText();
	}
	
	

}
