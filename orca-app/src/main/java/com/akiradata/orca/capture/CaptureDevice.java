package com.akiradata.orca;

public abstract class CaptureDevice {

	public enum State {
		  DEFAULT
		, READY
		, CAPTURING
		, DISPOSED
	}
	
	private State currentState = State.DEFAULT;
	
	public abstract void configure();
	
	public abstract void capture();
	
	public abstract void addEventListener(CaptureDeviceEventListener listener);
	
	public abstract void release();
	
	public final State getState(){
		return this.currentState;
	}
	
	public final void setState(State newState) throws CaptureDeviceException{
		// TODO validate state transition
		// some states can only be reached by transitioning from some other states
		// invalid state changes should throw IllegalStateException
		this.currentState = newState;
		
	}
	
	public abstract String getName();
	
	public abstract String getVendor();
	
	public abstract String getDriverVersion();
	
}
