package com.avenuecode.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Entity class to mapping the graph.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Graph implements Serializable {
	
	private static final long serialVersionUID = 5644871679551131649L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "graph", orphanRemoval = true)
	private List<Route> data;
	
	public Integer getId() {
		
		return id;
	}
	
	public void setId(final Integer id) {
		
		this.id = id;
	}
	
	public List<Route> getData() {
		
		return data;
	}
	
	public void setData(final List<Route> data) {
		
		this.data = data;
	}
	
}
