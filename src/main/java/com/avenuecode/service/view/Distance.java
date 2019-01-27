package com.avenuecode.service.view;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class to calculate the distance.
 */
public class Distance {

	@JsonIgnore
	private String pathSorce;
	@JsonIgnore
	private String pathTarget;
	@JsonIgnore
	private boolean passed;
	@JsonIgnore
	private Distance path;

	private int distance;

	public String getPathSorce() {

		return pathSorce;
	}

	public void setPathSorce(final String pathSorce) {

		this.pathSorce = pathSorce;
	}

	public String getPathTarget() {

		return pathTarget;
	}

	public void setPathTarget(final String pathTarget) {

		this.pathTarget = pathTarget;
	}

	public boolean isPassed() {

		return passed;
	}

	public void setPassed(final boolean passed) {

		this.passed = passed;
	}

	public Distance getPath() {

		return path;
	}

	public void setPath(final Distance path) {

		this.path = path;
	}

	public int getDistance() {

		return distance;
	}

	public void setDistance(final int distance) {

		this.distance = distance;
	}

}
