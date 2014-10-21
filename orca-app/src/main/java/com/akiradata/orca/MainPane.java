package com.akiradata.orca;

import java.io.File;
import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
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

import com.akiradata.orca.capture.CaptureDevice;
import com.akiradata.orca.capture.CaptureDeviceEvent;
import com.akiradata.orca.capture.CaptureDeviceEventListener;
import com.akiradata.orca.capture.CaptureDeviceSelectionDialog;
import com.akiradata.orca.model.OrcaProjectType;

public class MainPane extends VBox implements CaptureDeviceEventListener {

	final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@FXML private MenuBar mainMenuBar;
	@FXML private MenuItem newProjectMni;
	@FXML private ToolBar mainToolBar;
	@FXML private Button newProjectTlb;
	@FXML private Button startCaptureTlb;
	@FXML private MenuItem closeMni;
	@FXML private MenuItem exitMni;
	
	private Dialog newProjectDialog;
	private Dialog captureDeviceSelectionDialog;
	Stage currentStage;
	ObjectProperty<CaptureDevice> selectedDevice = new SimpleObjectProperty<CaptureDevice>();
	ObjectProperty<OrcaProjectType> project = new SimpleObjectProperty<OrcaProjectType>();

	public MainPane(Stage s) {
		super();
		this.currentStage = s;
		init();
	}

	private void init() {
		try{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
		}catch(IOException e){
			throw new ApplicationRuntimeException(e);
		}
		this.newProjectMni.setOnAction(this::createNewProjectAction);
		this.newProjectTlb.setOnAction(this::createNewProjectAction);
		this.startCaptureTlb.setOnAction(this::startCaptureAction);
		this.startCaptureTlb.disableProperty().bind(this.project.isNull());	
		
		this.project.addListener((obs, oldVal, newVal) -> {
			if (obs.getValue() == null) {
				this.currentStage.setTitle("Orca");
			} else {
				this.currentStage.setTitle(obs.getValue().getTitle() + " - Orca");
			}
		});		
	}

	@FXML
	void closeProjectAction() {
		// do the dirty stuff, then...
		this.project.set(null);
	}
	
	@FXML void exitAppAction(){
		if (this.project != null){
			closeProjectAction();
		}
	
	}	
	
	@FXML 
	private void startCaptureAction(ActionEvent e) {
		if (this.selectedDevice.get() == null){
			showCaptureDeviceSelectionDialog();
		}else{
			
		}
	}

	private void showCaptureDeviceSelectionDialog() {
		if (this.captureDeviceSelectionDialog == null){
			this.captureDeviceSelectionDialog = new CaptureDeviceSelectionDialog(this, "Select capture device");
		}
		
		if (this.captureDeviceSelectionDialog.show() == Dialog.Actions.OK){
			CaptureDevice d = ((CaptureDeviceSelectionDialog) this.captureDeviceSelectionDialog).getSelectedCaptureDevice();
			this.selectedDevice.set(d);
			d.configure();
			d.capture();
		}
	}

	@FXML
	private void createNewProjectAction(ActionEvent e) {
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
							.message("Could not create project in "
										+ projLocPath.getCanonicalPath())
							.showError();
					return;
				}

				File projFile = new File(projLocPath, projName + ".adi");
				if (projFile.exists()) {
					Action response = Dialogs
							.create()
							.title("New Project")
							.masthead("Confirm overwrite")
							.message("Destination file "
										+ projFile.getCanonicalPath()
										+ " already exists. Confirm overwrite?")
							.showConfirm();
					if (response == Actions.YES) {
						log.debug("User confirms overwrite.");
					} else {
						return;
					}
				}

				this.project.set(ProjectUtil.createProject(projName, projFile));
			}
		} catch (IOException | JAXBException ex) {
			throw new ApplicationRuntimeException(ex);
		}
	}

	public void applicationCloseRequested() {
		log.info("Closing...");
	}

	@Override
	public void captureStarted(CaptureDeviceEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pageStarted(CaptureDeviceEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pageCompleted(CaptureDeviceEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void captureCompleted(CaptureDeviceEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void captureException(CaptureDeviceEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configurationRequested(CaptureDeviceEvent e) {
		CaptureDevice activeDevice = this.selectedDevice.get();
		Dialog d =	activeDevice.createConfigurationDialog(this);
		d.show();
	}
	
}
