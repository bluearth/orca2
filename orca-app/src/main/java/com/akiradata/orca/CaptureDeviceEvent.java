package com.akiradata.orca;

import java.util.EventObject;

public class CaptureDeviceEvent extends EventObject {

	private static final long serialVersionUID = -1349711880207308278L;

	public enum Type {
		  CONFIG_REQUESTED
		, CAPTURE_STARTED
		, PAGE_STARTED
		, PAGE_COMPLETED
		, CAPTURE_COMPLETED
		, CAPTURE_EXCEPTION
	};
	
	private Type eventType;
	
	public CaptureDeviceEvent(Object source, Type type) {
		super(source);
		this.eventType = type;
	}

	public final Type getType(){
		return this.eventType;
	}
}
