package com.akiradata.orca;

import java.io.File;
import java.util.List;

import org.controlsfx.dialog.Dialog;

import com.akiradata.orca.CaptureDeviceEvent.Type;

public class DirectoryCaptureDevice extends CaptureDevice {

	File rootDirectory;
	List<String> imageFormat;

	@Override
	public void capture() {

	}

	@Override
	public void configure() {
		// check for some precondition. If not met, then yell out to listeners.
		if (rootDirectory == null || !rootDirectory.canRead()
				|| !rootDirectory.isDirectory()) {
			fireEvent(new CaptureDeviceEvent(this, Type.CONFIG_REQUESTED));
			return;
		}
	}

	@Override
	public void release() {

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
	public Dialog createConfigurationDialog(Object owner) {
		DirectoryCaptureDeviceConfigurationDialog d = new DirectoryCaptureDeviceConfigurationDialog(
				owner, "Configure capture device");
		return d;
	}
	

}
