package com.avenuecode.service;

import java.io.Serializable;
import java.util.List;

import com.avenuecode.model.Graph;
import com.avenuecode.model.Route;
import com.avenuecode.service.view.Distance;
import com.avenuecode.service.view.Path;
import com.avenuecode.service.view.ResponseRoutes;

/**
 * Interface to give the access of the public methods.
 */
public interface IRouteService extends Serializable {

	/**
	 * Method to save the the routes.
	 *
	 * @param graph a {@link Graph} prepared to save
	 * @return {@link Graph} saved with the {@code ID}
	 */
	Graph save(Graph graph);

	/**
	 * Method to find a graph.
	 *
	 * @param graphId a {@code ID} from the graph saved
	 * @return {@link Graph} saved
	 */
	Graph find(Integer graphId);

	/**
	 * Method to find available routes from a graph saved.
	 *
	 * @param data a {@code List<}{@link Route}{@code >} with all the routes from the graph
	 * @param townSource a name of the started town point
	 * @param townTarget a name of the end town point
	 * @param maxStops a quantities of maximum of stops
	 * @return {@link ResponseRoutes} with the routes found
	 */
	ResponseRoutes findRoutes(List<Route> data, String townSource, String townTarget, Integer maxStops);

	/**
	 * Method to find the distance throw the path given.
	 *
	 * @param data {@code List<}{@link Route}{@code >} with all the routes from the graph
	 * @param path {@code array} of {@code String} with given path
	 * @return {@link Distance} with the distance
	 */
	Distance findDistance(List<Route> data, String[] path);

	/**
	 * Method to find the shortest distance between two given towns.
	 *
	 * @param data {@code List<}{@link Route}{@code >} with all the routes from the graph
	 * @param townSource a name of the started town point
	 * @param townTarget a name of the end town point
	 * @return {@link Path} with the necessary information
	 */
	Path findShortestDistance(List<Route> data, String townSource, String townTarget);

}
