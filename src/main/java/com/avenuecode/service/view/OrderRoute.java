package com.avenuecode.service.view;

import java.util.List;

import com.avenuecode.model.Route;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class to sort the routes.
 */
public class OrderRoute {

	@JsonIgnore
	private Route path;
	@JsonIgnore
	private boolean ending;
	@JsonIgnore
	private List<OrderRoute> lstOrder;

	public Route getPath() {

		return path;
	}

	public void setPath(final Route path) {

		this.path = path;
	}

	public boolean isEnding() {

		return ending;
	}

	public void setEnding(final boolean ending) {

		this.ending = ending;
	}

	public List<OrderRoute> getLstOrder() {

		return lstOrder;
	}

	public void setLstOrder(List<OrderRoute> lstOrder) {

		this.lstOrder = lstOrder;
	}

}
