package com.akiradata.orca;

public abstract class CaptureDevice {

	public abstract void configure();
	
	public abstract void capture();
	
	public abstract void registerEventListener(CaptureDeviceEventListener listener);
}
