package com.akiradata.orca.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akiradata.orca.ApplicationRuntimeException;

public class AppController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@FXML public void onNewProjectButtonClicked() throws ApplicationRuntimeException{
		try{
			Dialog d = new Dialog(new Stage(), "New Project");
			d.setContent(FXMLLoader.load(getClass().getResource("/fxml/NewProjectDialog.fxml")));
			
			Action selected = d.show();
		}catch(IOException e){
			throw new ApplicationRuntimeException(e);
		}
	}
}
