package com.akiradata.orca;

import java.io.File;
import java.util.List;

public class DirectoryCaptureDevice extends CaptureDevice {

	File rootDirectory;
	List<String> imageFormat;
	
	@Override
	public void capture() {

	}

	@Override
	public void registerEventListener(CaptureDeviceEventListener listener) {

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

}
