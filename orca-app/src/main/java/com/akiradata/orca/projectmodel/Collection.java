package com.akiradata.orca.projectmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Collection extends Node {

	ObservableList<Node> childrenProperty;
	
	public Collection(String id) {
		super(id);
		this.childrenProperty = FXCollections.observableArrayList();
	}

	public ObservableList<Node> getChildrenProperty() {
		return childrenProperty;
	}

	public void setChildrenProperty(ObservableList<Node> childrenProperty) {
		this.childrenProperty = childrenProperty;
	}
	
}
