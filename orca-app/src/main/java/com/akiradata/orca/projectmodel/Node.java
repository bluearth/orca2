package com.akiradata.orca.projectmodel;


public abstract class Node {

	String id;
	
	String text;

	public Node(String id) {
		this.id = id;
		this.text = this.id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
}
