package com.akiradata.orca.capture.directory;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.Node;

import javax.imageio.IIOImage;

import org.controlsfx.dialog.Dialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akiradata.orca.capture.CaptureDevice;
import com.akiradata.orca.capture.CaptureDeviceEvent;
import com.akiradata.orca.capture.CaptureDeviceEvent.Type;
import com.akiradata.orca.capture.CaptureDeviceException;
import com.akiradata.orca.capture.DataReadyEvent;

public class DirectoryCaptureDevice extends CaptureDevice {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	File rootDirectory;
	List<String> extensions;
	List<IIOImage> imageBuffer = new ArrayList<IIOImage>(10);
	
	@Override
	public void capture() throws CaptureDeviceException {
		
		fireCaptureDeviceEvent(new CaptureDeviceEvent(this, Type.CAPTURE_STARTED));
		
		if (!rootDirectory.exists()) throw new CaptureDeviceException("Path selected does not exists");
		if (!rootDirectory.isDirectory()) throw new CaptureDeviceException("Path selected is not a directory");
		if (!rootDirectory.canRead()) throw new CaptureDeviceException("Could not read the path selected");
		
		List<File> files = Arrays.asList(
				rootDirectory.listFiles((file) -> 
					extensions.stream().anyMatch(file.getName()::endsWith)));
		
		log.debug("Enumeration found " + files.size() + " file" + (files.size() > 1 ? "s." : "."));
		
		ByteBuffer buff = ByteBuffer.allocate(20000); // 20 KBytes
		for (File imgFile : files) {
			fireCaptureDeviceEvent(new CaptureDeviceEvent(this, Type.PAGE_STARTED));
			log.debug("Reading file [" + imgFile.getAbsolutePath() + "]");
			try{
				FileChannel fc = FileChannel.open(imgFile.toPath(), StandardOpenOption.READ);
				while (fc.read(buff) != -1){
					buff.flip();
					fireCaptureDeviceEvent(new DataReadyEvent<ByteBuffer>(this, buff, buff.limit()));
					buff.clear();
				}
				
				fc.close();
			}catch(IOException e){
				log.error(e.getMessage(), e);
			}
			fireCaptureDeviceEvent(new CaptureDeviceEvent(this, Type.PAGE_COMPLETED));
		}
		
		fireCaptureDeviceEvent(new CaptureDeviceEvent(this, Type.CAPTURE_COMPLETED));
		
	}

	@Override
	public void configure() {
		fireCaptureDeviceEvent(new CaptureDeviceEvent(this, Type.CONFIG_REQUESTED));
	}

	@Override
	public void release() {
	}

	@Override
	public String getName() {
		return "Directory capture device";
	}

	@Override
	public String getVendor() {
		return "Akiradata";
	}

	@Override
	public String getDriverVersion() {
		return "1.0";
	}

	@Override
	public Dialog createConfigurationDialog(Node owner) {
		DirectoryCaptureDeviceConfigurationDialog d = new DirectoryCaptureDeviceConfigurationDialog(owner, "Capture device configuration");
		d.setDevice(this);
		return d;
	}
	
	public void setRootDirectory(String path){
		this.rootDirectory = new File(path);
	}

	public void setExtensions(List<String> extensions){
		this.extensions = extensions;
	}
}
