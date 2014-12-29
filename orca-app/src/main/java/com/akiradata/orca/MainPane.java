package com.akiradata.orca;

import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialog.Actions;
import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akiradata.orca.ResourceUtil.IconName;
import com.akiradata.orca.ResourceUtil.IconSize;
import com.akiradata.orca.capture.CaptureDevice;
import com.akiradata.orca.capture.CaptureDeviceEvent;
import com.akiradata.orca.capture.CaptureDeviceEventListener;
import com.akiradata.orca.capture.CaptureDeviceException;
import com.akiradata.orca.capture.CaptureDeviceSelectionDialog;
import com.akiradata.orca.capture.DataReadyEvent;
import com.akiradata.orca.jaxb.model.CollectionType;
import com.akiradata.orca.jaxb.model.NodeType;
import com.akiradata.orca.jaxb.model.OrcaProjectType;
import com.akiradata.orca.jaxb.model.RasterNodeType;
import com.akiradata.orca.projectmodel.Collection;
import com.akiradata.orca.projectmodel.Node;
import com.akiradata.orca.projectmodel.NodeFactory;
import com.akiradata.orca.projectmodel.Project;
import com.akiradata.orca.projectmodel.RasterNode;

public class MainPane extends VBox implements CaptureDeviceEventListener {

	final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@FXML private MenuBar mainMenuBar;
	@FXML private MenuItem fileNewMni;
	@FXML private MenuItem fileOpenMni;
	@FXML private MenuItem fileCloseMni;
	@FXML private MenuItem connectToWorkspaceMni;
	@FXML private MenuItem connectToCloudMni;
	@FXML private MenuItem exportProjectMni;
	@FXML private MenuItem exportSelectionMni;
	@FXML private MenuItem appExitMni;
	@FXML private MenuItem cutMni;
	@FXML private MenuItem copyMni;
	@FXML private MenuItem pasteMni;
	@FXML private MenuItem saveSelectionMni;
	@FXML private MenuItem loadSelectionMni;
	@FXML private MenuItem viewZoomInMni;
	@FXML private MenuItem viewZoomOutMni;
	@FXML private MenuItem viewZoomResetMni;
	@FXML private MenuItem openHelpMni;
	@FXML private MenuItem openAboutBoxMni;
	@FXML private MenuItem modTessaractRunOCRMni;
	
	@FXML private ToolBar mainToolBar;
	@FXML private Button createCollectionBtn;
	@FXML private Button deleteNodeBtn;
	@FXML private Button createFromCaptureDeviceBtn;
	@FXML private Button refreshBtn;
	
//	@FXML private TreeView<? extends Node> projectTreeView;
	@FXML private TreeView<Node> projectTreeView;
	
	private Dialog newProjectDialog;
	private Dialog captureDeviceSelectionDialog;
	Stage currentStage;
	ObjectProperty<CaptureDevice> selectedDevice = new SimpleObjectProperty<CaptureDevice>();
	ObjectProperty<Project> project = new SimpleObjectProperty<Project>();

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
		this.fileNewMni.setOnAction(this::createNewProjectAction);
		this.createFromCaptureDeviceBtn.setOnAction(this::createNewProjectAction);
		this.createFromCaptureDeviceBtn.setOnAction(this::startCaptureAction);
		this.createFromCaptureDeviceBtn.disableProperty().bind(this.project.isNull());	
		this.fileOpenMni.setOnAction(this::openProjectAction);
		
		this.project.addListener((obs, oldVal, newVal) -> {
			if (obs.getValue() == null) {
				this.currentStage.setTitle("Orca");
			} else {
				this.currentStage.setTitle(obs.getValue().getText() + " - Orca");
			}
		});		
		
	}

	@FXML private void openProjectAction(ActionEvent e) {

		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("ADI files (*.adi)", "*.adi");
		fileChooser.getExtensionFilters().add(extentionFilter);
		
//		File fileUserDirectory = new File(System.getProperty("user.dir") + File.separator);
		File fileUserDirectory = new File("c:\\Users\\barkah\\Documents\\");
		if(!fileUserDirectory.canRead()) {
		    fileUserDirectory = new File("C:/");
		}
		fileChooser.setInitialDirectory(fileUserDirectory);
		fileChooser.setInitialFileName("sample01.adi");
		try {
			OrcaProjectType orcaProjectType = ProjectUtil.openProject(fileChooser.showOpenDialog(null));
			buildSubTree(orcaProjectType, null);
		} catch (IOException | JAXBException ex) {
			// TODO show message / log exception
			ex.printStackTrace();
		} 
	}	

	private void buildSubTree(NodeType elementType, TreeItem<Node> parentTreeItem) {

		if (elementType instanceof OrcaProjectType){
			assert (parentTreeItem == null);
			Project project = NodeFactory.createProjectNode();
			project.setText(elementType.getText() == null ? elementType.getId() : elementType.getText());
			TreeItem<Node> rootItem = new TreeItem<Node>(project); 
			this.projectTreeView.setRoot(rootItem);
			for (NodeType childElementType : ((OrcaProjectType) elementType).getNodeTypes()) {
				buildSubTree(childElementType, rootItem);
			}
		} else if (elementType instanceof CollectionType){
			assert (parentTreeItem != null);
			Collection collection = NodeFactory.createCollectionNode();
			collection.setText(elementType.getText() == null ? elementType.getId() : elementType.getText());
			TreeItem<Node> collectionTreeItem = new TreeItem<Node>(collection); 
			parentTreeItem.getChildren().add(collectionTreeItem);
			for (NodeType childElementType : ((CollectionType) elementType).getNodeTypes()) {
				buildSubTree(childElementType, collectionTreeItem);
			}			
		} else if (elementType instanceof RasterNodeType){
			assert (parentTreeItem != null);
			RasterNode rasterNode = NodeFactory.createRasterNode();
			rasterNode.setText(elementType.getText() == null ? elementType.getId() : elementType.getText());
			TreeItem<Node> rasterNodeTreeItem = new TreeItem<Node>(rasterNode);
			parentTreeItem.getChildren().add(rasterNodeTreeItem);
		} else{
			log.warn("Unknown node type");
		}
		

		projectTreeView.setCellFactory((tv) -> {
			return new TreeCell<Node>(){
				@Override
				protected void updateItem(Node item, boolean empty) {
					// TODO Auto-generated method stub
					super.updateItem(item, empty);
					
					if (empty || item == null){
						setText(null);
					} else {
						if (item instanceof Project){
							setText(item.getText());
							setGraphic(new ImageView(ResourceUtil.getIcon(IconName.PROJECT, IconSize.SZ_16)));
						}
						else if (item instanceof Collection){
							setText(item.getText());
							setGraphic(new ImageView(ResourceUtil.getIcon(IconName.COLLECTION, IconSize.SZ_16)));							
						}
						else if (item instanceof RasterNode){
							setText(item.getText());
							setGraphic(new ImageView(ResourceUtil.getIcon(IconName.RASTER_NODE, IconSize.SZ_16)));							
						}
						else{
							setText(item.getText());
						}
					}
				}				
			};
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
			d.addEventListener(this);
			d.configure();
			try {
				d.capture();
			} catch (CaptureDeviceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

//				TreeItem<Item> rootItem = new TreeItem<Item>();
//				rootItem.valueProperty().bindBidirectional(project.get().rootItemProperty());
//				rootItem.
//				treeView.rootProperty().setValue(rootItem);
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

	List<ByteBuffer> pageBuffers;
	int ptrPageBuffer = 0;
	long szTotal = 0;
	int pageNum = 0;
	
	@Override
	public void pageStarted(CaptureDeviceEvent e) {
		pageBuffers = new LinkedList<ByteBuffer>();
		ptrPageBuffer = 0;
	}

	@Override
	public void pageCompleted(CaptureDeviceEvent e) {
		// temp coba coba
		log.debug("Page completed. Current size is " + szTotal);
		final Path pthTarget = FileSystems.getDefault().getPath("d:\\orca_test\\out");
		log.debug("Attempting to write to " + pthTarget.toString());
		try {
			FileChannel fc = FileChannel.open(Files.createTempFile(pthTarget, "hasil-", ".coba"), StandardOpenOption.WRITE);
			ByteBuffer [] buffs = new ByteBuffer[pageBuffers.size()];
			pageBuffers.toArray(buffs);
			fc.write(buffs);
			fc.close();
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		}		
		pageNum++;
	}

	@Override
	public void captureCompleted(CaptureDeviceEvent e) {
		
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

	@Override
	public <T extends Buffer> void dataReady(DataReadyEvent<T> e) {		
		ByteBuffer srcBuff = (ByteBuffer) e.getBuffer();
		ByteBuffer dstBuff = ByteBuffer.allocate(e.getSize());
		szTotal += e.getSize();
		dstBuff.put(srcBuff);
		dstBuff.flip();
		this.pageBuffers.add(dstBuff);
		ptrPageBuffer++;
	}
	
}
