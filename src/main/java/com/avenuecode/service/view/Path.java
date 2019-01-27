package com.avenuecode.service.view;

/**
 * Class with the data of the path and distance.
 */
public class Path {
	
	private int distance;
	private String[] path;
	
	public int getDistance() {
		
		return distance;
	}
	
	public void setDistance(final int distance) {
		
		this.distance = distance;
	}
	
	public String[] getPath() {
		
		return path;
	}
	
	public void setPath(final String[] path) {
		
		this.path = path;
	}
	
}
