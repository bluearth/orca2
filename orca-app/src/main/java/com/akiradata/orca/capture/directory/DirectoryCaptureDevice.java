package com.akiradata.orca.capture.directory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.IIOImage;

import javafx.scene.Node;

import org.controlsfx.dialog.Dialog;

import com.akiradata.orca.capture.CaptureDevice;
import com.akiradata.orca.capture.CaptureDeviceEvent;
import com.akiradata.orca.capture.CaptureDeviceEvent.Type;
import com.akiradata.orca.capture.CaptureDeviceException;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class DirectoryCaptureDevice extends CaptureDevice {

	File rootDirectory;
	List<String> imageFormat;
	List<IIOImage> imageBuffer = new ArrayList<IIOImage>(10);
	
	@Override
	public void capture() throws CaptureDeviceException {
		
		fireCaptureDeviceEvent(new CaptureDeviceEvent(this, Type.CAPTURE_STARTED));
		
		if (!rootDirectory.exists()) throw new CaptureDeviceException("Path selected does not exists");
		if (!rootDirectory.isDirectory()) throw new CaptureDeviceException("Path selected is not a directory");
		if (!rootDirectory.canRead()) throw new CaptureDeviceException("Could not read the path selected");
		
		List<File> files = Arrays.asList(
				rootDirectory.listFiles((file) -> 
					imageFormat.stream().anyMatch(file.getName()::endsWith)));
		
		
		for (File imgFile : files) {
			fireCaptureDeviceEvent(new CaptureDeviceEvent(this, Type.PAGE_STARTED));
			
			
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

}
