package com.akiradata.orca.capture;

import java.nio.Buffer;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.Node;

import org.controlsfx.dialog.Dialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CaptureDevice {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public enum State {
		  DEFAULT
		, READY
		, CAPTURING
		, DISPOSED
	}
	
	private State currentState = State.DEFAULT;
	
	private List<CaptureDeviceEventListener> listeners = new LinkedList<CaptureDeviceEventListener>();
	
	public abstract void configure();
	
	public abstract void capture() throws CaptureDeviceException;
	
	public final void addEventListener(CaptureDeviceEventListener listener) {
		listeners.add(listener);
	}	
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
	
	public abstract Dialog createConfigurationDialog(Node owner);

	public final void fireCaptureDeviceEvent(CaptureDeviceEvent e){
		log.debug("Notifiying listeners : " + e);
		listeners.stream().forEach(listener -> {
			switch(e.getEventType()){
				case CAPTURE_STARTED : listener.captureStarted(e); break;
				case PAGE_STARTED : listener.pageStarted(e); break;
				case PAGE_COMPLETED : listener.pageCompleted(e); break;
				case CAPTURE_COMPLETED : listener.captureCompleted(e); break;
				case CAPTURE_EXCEPTION : listener.captureException(e); break;
				case CONFIG_REQUESTED : listener.configurationRequested(e); break;
				case DATA_READY :  
					listener.dataReady((DataReadyEvent<? extends Buffer>) e); 
					break;
				default:
			}
		});
	}
}
