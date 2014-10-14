package com.akiradata.orca;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPane extends VBox {

	final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@FXML private MenuBar mainMenuBar;
	@FXML private MenuItem newProjectMni;
	@FXML private ToolBar mainToolBar;
	@FXML private Button newProjectTlb;
	@FXML private Button startCaptureTlb;
	private Dialog newProjectDialog;
	Stage currentStage;
	
	public MainPane(){
		super();
		init();		
	}
	
	public MainPane(Stage s) {
		super();
		this.currentStage = s;
		init();
	}

	private void init() throws ApplicationRuntimeException {
		try{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
		}catch(IOException e){
			throw new ApplicationRuntimeException(e);
		}
		this.newProjectMni.setOnAction(event -> createNewProjectAction());
		this.newProjectTlb.setOnAction(event -> createNewProjectAction());
	
		this.startCaptureTlb.setOnAction(event -> startCaptureAction());
	}
	
	
	@FXML 
	private void startCaptureAction() {
		log.debug("Start capture");
		
		log.debug("Enumerating capture device");
		
		log.debug("Make preset selection");
		
		log.debug("Check for device availability");
		
		log.debug("Capturing...");
		
		log.debug("Capture complete");
	}

	@FXML
	private void createNewProjectAction() {
		try {
			if (this.newProjectDialog == null) {
				this.newProjectDialog = new NewProjectDialog(this,
						"New Project");
			}

			if (this.newProjectDialog.show() == Dialog.Actions.OK) {
				String projLoc = ((NewProjectDialog) newProjectDialog)
						.getProjectLocation();
				String projName = ((NewProjectDialog) newProjectDialog)
						.getProjectName();

				File projLocPath = new File(projLoc);
				if (!projLocPath.exists() || !projLocPath.canWrite()
						|| !projLocPath.isDirectory()) {
					Dialogs.create()
							.title("New Project")
							.masthead("Problem creating new Project")
							.message(
									"Could not create project in "
											+ projLocPath.getCanonicalPath())
							.showError();
					return;
				}

				File projFile = new File(projLocPath, projName + ".adi");
				if (projFile.exists()) {
					Action response = Dialogs
							.create()
							.title("New Project")
							.masthead("Confim overwrite")
							.message(
									"Destination file "
											+ projFile.getCanonicalPath()
											+ " already exists. Confirm overwrite?")
							.showConfirm();
					if (response == Actions.OK) {
						log.debug("User confirms overwrite.");
					} else {
						return;
					}
				}

				ProjectUtil.createProject(projName, projFile);
				this.currentStage.setTitle(projName + " - "
						+ this.currentStage.getTitle());

			}
		} catch (IOException | JAXBException e) {
			throw new ApplicationRuntimeException(e);
		}
	}

	public void applicationCloseRequested() {
		log.info("Closing...");
	}
	
}
