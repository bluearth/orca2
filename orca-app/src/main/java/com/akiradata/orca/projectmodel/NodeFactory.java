package com.akiradata.orca.projectmodel;

import java.util.Random;

public class NodeFactory {

	private static final int MAX_NODE_NAME_LEN = 32;
	private static final Random RNG = new Random();
	
	public static Project createProjectNode(){
		return new Project(genRandomName());
	}

	public static RasterNode createRasterNode(){
		return new RasterNode(genRandomName());
	}
	
	public static Collection createCollectionNode(){
		return new Collection(genRandomName());
	}
	
	private static String genRandomName(){
		byte [] b = new byte[MAX_NODE_NAME_LEN];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) (97 + RNG.nextInt(25));
		}
		
		return new String(b);
	}	
}
