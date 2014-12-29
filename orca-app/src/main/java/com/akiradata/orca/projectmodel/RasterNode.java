package com.akiradata.orca.projectmodel;

import java.nio.ByteBuffer;

public class RasterNode extends Node {

	ByteBuffer cachedContent;
	
	String content;
	
	public RasterNode(String id) {
		super(id);
	}

}
