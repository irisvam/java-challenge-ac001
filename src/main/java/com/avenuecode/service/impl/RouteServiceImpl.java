package com.avenuecode.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenuecode.model.Graph;
import com.avenuecode.model.Route;
import com.avenuecode.repository.GraphRepository;
import com.avenuecode.service.IRouteService;
import com.avenuecode.service.view.Distance;
import com.avenuecode.service.view.OrderRoute;
import com.avenuecode.service.view.Path;
import com.avenuecode.service.view.ResponseRoutes;
import com.avenuecode.service.view.RoutePoint;

/**
 * Class responsible for the business rules. 
 */
@Service
public class RouteServiceImpl implements IRouteService {

	private static final long serialVersionUID = -47704935571129319L;

	@Autowired
	GraphRepository repGraph;

	@Override
	public Graph save(final Graph graph) {

		return repGraph.save(graph);
	}

	@Override
	public Graph find(final Integer graphId) {

		return repGraph.findOne(graphId);
	}

	@Override
	public ResponseRoutes findRoutes(final List<Route> data, final String townSource, final String townTarget, final Integer maxStops) {

		final List<OrderRoute> lstOrder = prepareOrderRoute(data, townSource, townTarget, maxStops);

		final List<RoutePoint> routes = new ArrayList<RoutePoint>();

		prepareRoutes(lstOrder, routes, 1, "", 0);

		for (final RoutePoint routePoint : routes) {

			routePoint.setRoute(routePoint.getRoute().replaceAll(",", ""));
		}

		final ResponseRoutes response = new ResponseRoutes();

		response.setRoutes(routes);

		return response;
	}

	@Override
	public Distance findDistance(final List<Route> data, final String[] path) {

		final Distance distancePath = prepareOrderPath(path, 0);

		if (null != distancePath.getPathSorce()) {

			calculateDistance(data, distancePath);
		}

		return distancePath;
	}

	@Override
	public Path findShortestDistance(final List<Route> data, final String townSource, final String townTarget) {

		final Path path = new Path();

		if (townSource.equals(townTarget)) {

			final String[] pathString = new String[1];
			pathString[0] = townTarget;
			path.setPath(pathString);
			path.setDistance(0);

		} else {

			final List<OrderRoute> lstOrder = prepareOrderRoute(data, townSource, townTarget, 0);

			final List<RoutePoint> routes = new ArrayList<RoutePoint>();

			prepareRoutes(lstOrder, routes, 1, "", 0);

			int indexBetter = 1;
			boolean found = false;

			for (int index = 0; index < routes.size() - 1; index++) {

				if (routes.get(index).getDistance() < routes.get(indexBetter).getDistance()) {

					indexBetter = index;
					found = true;
				}
			}

			if (found) {

				path.setPath(routes.get(indexBetter).getRoute().split(","));
				path.setDistance(routes.get(indexBetter).getDistance());

			} else {

				final String[] pathString = new String[0];
				path.setPath(pathString);
				path.setDistance(-1);
			}
		}

		return path;
	}

	/**
	 * Method to find and sort the routes in a internal list.
	 *
	 * @param data a {@code List<}{@link Route}{@code >} with all the routes from the graph
	 * @param townSource a name of the started town point
	 * @param townTarget a name of the end town point
	 * @param maxStops a quantities of maximum of stops
	 * @return {@code List<}{@code OrderRoute}{@code >} with the sorted routes.
	 */
	private List<OrderRoute> prepareOrderRoute(final List<Route> data, final String townSource, final String townTarget, final int maxStops) {

		final List<OrderRoute> lstOrder = new ArrayList<OrderRoute>();

		for (final Route route : data) {

			OrderRoute orderRoute = null;

			if (townSource.equals(route.getSource())) {

				orderRoute = new OrderRoute();
				orderRoute.setPath(route);
				nextRoute(orderRoute, data, townTarget, 1, maxStops);
			}

			if (null != orderRoute) {

				lstOrder.add(orderRoute);
			}
		}

		return lstOrder;
	}

	/**
	 * Recursive method to create internal list and routes.
	 * 
	 * @param orderRoute a actual route
	 * @param data a {@code List<}{@link Route}{@code >} with all the routes from the graph
	 * @param townTarget a name of the end town point
	 * @param stops a quantities of stops was made until now
	 * @param maxStops a quantities of maximum of stops
	 */
	private void nextRoute(final OrderRoute orderRoute, final List<Route> data, final String townTarget, final int stops, final Integer maxStops) {

		if (townTarget.equals(orderRoute.getPath().getTarget())) {

			orderRoute.setEnding(true);

		} else if (null == maxStops || 0 >= maxStops || stops < maxStops) {

			final List<OrderRoute> lstOrder = new ArrayList<OrderRoute>();
			int count = 0;

			for (final Route route : data) {

				if (orderRoute.getPath().getTarget().equals(route.getSource())) {

					lstOrder.add(new OrderRoute());
					lstOrder.get(count).setPath(route);
					nextRoute(lstOrder.get(count), data, townTarget, stops + 1, maxStops);
					count++;
				}
			}

			if (!lstOrder.isEmpty()) {

				orderRoute.setLstOrder(lstOrder);
			}
		}
	}

	/**
	 * Recursive method to create a list of routes with its distance, stops and named route.
	 *
	 * @param lstOrder a {@code List<}{@link OrderRoute}{@code >} with the routes sorted
	 * @param lstRoute a {@code List<}{@link RoutePoint}{@code >} with the information of the routes
	 * @param stops a quantities of stops was made until now
	 * @param startRoute a {@code String} with the route until now
	 * @param startDistance the amount of the distance until now
	 */
	private void prepareRoutes(final List<OrderRoute> lstOrder, final List<RoutePoint> lstRoute, final int stops, final String startRoute,
			final int startDistance) {

		for (final OrderRoute orderRoute : lstOrder) {

			if (orderRoute.isEnding()) {

				final RoutePoint novo = new RoutePoint();
				novo.setStops(stops);

				if (startRoute.isEmpty()) {

					novo.setRoute(orderRoute.getPath().getTarget());

				} else {

					novo.setRoute(startRoute + "," + orderRoute.getPath().getTarget());
				}
				novo.setDistance(startDistance + orderRoute.getPath().getDistance());
				lstRoute.add(novo);

			} else {

				String route = "";

				if (!startRoute.isEmpty()) {

					route = startRoute + ",";
				}

				if (route.contains(orderRoute.getPath().getSource())) {

					route = route + orderRoute.getPath().getTarget();

				} else {

					route = route + orderRoute.getPath().getSource() + "," + orderRoute.getPath().getTarget();
				}

				final int distance = startDistance + orderRoute.getPath().getDistance();

				if (null != orderRoute.getLstOrder()) {

					prepareRoutes(orderRoute.getLstOrder(), lstRoute, stops + 1, route, distance);
				}
			}
		}
	}

	/**
	 * Recursive method to prepare the classes to receive the distances.
	 *
	 * @param path {@code array} of {@code String} with given path
	 * @param indexArray a {@code int} with the index of the {@code array} to go through it
	 * @return {@link Distance} with the distance
	 */
	private Distance prepareOrderPath(final String[] path, final int indexArray) {

		final Distance distancePath = new Distance();

		if (0 == path.length || 1 == path.length) {

			distancePath.setDistance(0);

		} else {

			distancePath.setPathSorce(path[indexArray]);
			distancePath.setPathTarget(path[indexArray + 1]);

			if (indexArray + 1 < path.length - 1) {

				distancePath.setPath(prepareOrderPath(path, indexArray + 1));
			}
		}

		return distancePath;
	}

	/**
	 * Recursive method to calculate the distance
	 *
	 * @param data {@code List<}{@link Route}{@code >} with all the routes from the graph
	 * @param distancePath a {@link Distance} with the distance data
	 */
	private void calculateDistance(final List<Route> data, final Distance distancePath) {

		for (final Route route : data) {

			if (distancePath.getPathSorce().equals(route.getSource()) && distancePath.getPathTarget().equals(route.getTarget())) {

				distancePath.setPassed(true);
				distancePath.setDistance(route.getDistance());

				if (null != distancePath.getPath()) {

					calculateDistance(data, distancePath.getPath());

					distancePath.setDistance(distancePath.getDistance() + distancePath.getPath().getDistance());

					if (!distancePath.getPath().isPassed()) {

						distancePath.setPassed(false);
						distancePath.setDistance(-1);
					}
				}
			}

			if (distancePath.isPassed()) {

				break;
			}
		}
	}

}
