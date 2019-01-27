package com.avenuecode.service.view;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class to respond the route and the stops.
 */
public class RoutePoint {

	@JsonIgnore
	private int distance;

	private String route;
	private int stops;

	public int getDistance() {

		return distance;
	}

	public void setDistance(int distance) {

		this.distance = distance;
	}

	public String getRoute() {

		return route;
	}

	public void setRoute(final String route) {

		this.route = route;
	}

	public int getStops() {

		return stops;
	}

	public void setStops(final int stops) {

		this.stops = stops;
	}

}
