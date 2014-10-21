package com.akiradata.orca.capture;

public interface CaptureDeviceEventListener {

	void captureStarted(CaptureDeviceEvent e);
	
	void pageStarted(CaptureDeviceEvent e);
	
	void pageCompleted(CaptureDeviceEvent e);
	
	void captureCompleted(CaptureDeviceEvent e);
	
	void captureException(CaptureDeviceEvent e);

	void configurationRequested(CaptureDeviceEvent e);
	
}
