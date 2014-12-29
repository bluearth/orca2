package com.akiradata.orca.capture;

import java.nio.Buffer;

public interface CaptureDeviceEventListener {

	void captureStarted(CaptureDeviceEvent e);
	
	void pageStarted(CaptureDeviceEvent e);
	
	void pageCompleted(CaptureDeviceEvent e);
	
	void captureCompleted(CaptureDeviceEvent e);
	
	void captureException(CaptureDeviceEvent e);

	void configurationRequested(CaptureDeviceEvent e);
	
	<T extends Buffer> void dataReady(DataReadyEvent<T> e);
	
}
