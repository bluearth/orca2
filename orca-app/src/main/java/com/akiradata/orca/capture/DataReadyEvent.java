package com.akiradata.orca.capture;

import java.nio.Buffer;

public class DataReadyEvent<T extends Buffer> extends CaptureDeviceEvent {

	transient T buffer;
	int start;
	int size;
	
	public DataReadyEvent(Object source, T buff, int size) {
		super(source, Type.DATA_READY);
		this.buffer = buff;
		this.start = 0;
		this.size = size;
	}

	public T getBuffer() {
		return buffer;
	}

	public int getStart() {
		return start;
	}

	public int getSize() {
		return size;
	}
	
	

}
