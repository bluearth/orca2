package com.akiradata.orca.capture.directory;

import java.io.File;
import java.util.List;

import javafx.scene.Node;

import org.controlsfx.dialog.Dialog;

import com.akiradata.orca.capture.CaptureDevice;
import com.akiradata.orca.capture.CaptureDeviceEventListener;

public class DirectoryCaptureDevice extends CaptureDevice {

	File rootDirectory;
	List<String> imageFormat;

	@Override
	public void capture() {

	}

	@Override
	public void addEventListener(CaptureDeviceEventListener listener) {

	}

	@Override
	public void configure() {
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "Directory";
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
		// TODO Auto-generated method stub
		return null;
	}

}
