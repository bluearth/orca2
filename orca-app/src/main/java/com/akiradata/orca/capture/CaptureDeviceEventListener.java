package com.akiradata.orca.capture;

public interface CaptureDeviceEventListener {

	public void captureStarted(CaptureDeviceEvent e);
	
	public void pageStarted(CaptureDeviceEvent e);
	
	public void pageCompleted(CaptureDeviceEvent e);
	
	public void captureCompleted(CaptureDeviceEvent e);
	
	public void captureException(CaptureDeviceEvent e);
	
}
