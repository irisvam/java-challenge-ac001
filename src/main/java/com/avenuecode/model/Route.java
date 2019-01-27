package com.avenuecode.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity class to mapping the routes.
 */
@Entity
public class Route implements Serializable {

	private static final long serialVersionUID = -7544384762279096559L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String source;

	private String target;

	private int distance;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "graph_id")
	private Graph graph;

	@JsonIgnore
	public Integer getId() {

		return id;
	}

	@JsonProperty
	public void setId(final Integer id) {

		this.id = id;
	}

	public String getSource() {

		return source;
	}

	public void setSource(final String source) {

		this.source = source;
	}

	public String getTarget() {

		return target;
	}

	public void setTarget(final String target) {

		this.target = target;
	}

	public int getDistance() {

		return distance;
	}

	public void setDistance(final int distance) {

		this.distance = distance;
	}

	public Graph getGraph() {

		return graph;
	}

	public void setGraph(final Graph graph) {

		this.graph = graph;
	}

}
