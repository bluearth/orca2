package com.akiradata.orca;

import java.io.File;
import java.net.URL;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourceUtil {

	public enum IconName {
		  COLLECTION
		, RASTER_NODE
		, PROJECT
		// moar!
	}
	public enum IconSize {
		  SZ_16
		, SZ_32
	}
	private static final String RESOURCE_ROOT = "/icons";
	private static Image ICON_COLLECTION_16 = null;
	private static Image ICON_COLLECTION_32 = null;
	private static Image ICON_RASTER_NODE_16 = null;
	private static Image ICON_RASTER_NODE_32 = null;
	private static Image ICON_PROJECT_16 = null;
	private static Image ICON_PROJECT_32 = null;
	
	private static Image createIcon(String path){
		return new Image(ResourceUtil.class.getResourceAsStream(path));
	}
	
	public static Image getIcon(IconName name, IconSize size){
		switch (name){
		case COLLECTION : 
			switch (size) {
			case SZ_16:
				if (ICON_COLLECTION_16 == null) 
					ICON_COLLECTION_16 = createIcon(RESOURCE_ROOT + "/16px/folder-open.png");
				return ICON_COLLECTION_16;
			case SZ_32:
				if (ICON_COLLECTION_32 == null)
					ICON_COLLECTION_32 = createIcon(RESOURCE_ROOT + "/32px/folder-open.png");
				return ICON_COLLECTION_32;
			default:
				return null;
			}
		case RASTER_NODE : 
			switch (size) {
			case SZ_16:
				if (ICON_RASTER_NODE_16 == null) 
					ICON_RASTER_NODE_16 = createIcon(RESOURCE_ROOT + "/16px/image.png");
				return ICON_RASTER_NODE_16;
			case SZ_32:
				if (ICON_RASTER_NODE_32 == null)
					ICON_RASTER_NODE_32 = createIcon(RESOURCE_ROOT + "/32px/image.png");
				return ICON_RASTER_NODE_32;
			default:
				return null;
			}
		case PROJECT : 
			switch (size) {
			case SZ_16:
				if (ICON_PROJECT_16 == null) 
					ICON_PROJECT_16 = createIcon(RESOURCE_ROOT + "/16px/tree.png");
				return ICON_PROJECT_16;
			case SZ_32:
				if (ICON_PROJECT_32 == null)
					ICON_PROJECT_32 = createIcon(RESOURCE_ROOT + "/32px/tree.png");
				return ICON_PROJECT_32;
			default:
				return null;
			}
		default :
				return null;
		}
		
	}
}
