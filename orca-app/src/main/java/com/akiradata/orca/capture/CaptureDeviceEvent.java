package com.akiradata.orca.capture;

import java.util.EventObject;

import com.google.gson.Gson;

public class CaptureDeviceEvent extends EventObject {

	private static final long serialVersionUID = -1349711880207308278L;

	public enum Type {
		  CAPTURE_STARTED
		, PAGE_STARTED
		, PAGE_COMPLETED
		, CAPTURE_COMPLETED
		, CAPTURE_EXCEPTION
		, CONFIG_REQUESTED
		, DATA_READY
	}
	
	Type eventType;
	
	public CaptureDeviceEvent(Object source, Type type) {
		super(source);
		this.eventType = type;
	}

	public Type getEventType() {
		return eventType;
	}
	
	
	private transient Gson gson = new Gson();
	
	@Override
	public String toString(){
		return gson.toJson(this);
	}
}
