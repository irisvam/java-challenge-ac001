package com.avenuecode.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avenuecode.model.Graph;
import com.avenuecode.service.impl.RouteServiceImpl;
import com.avenuecode.service.view.Distance;
import com.avenuecode.service.view.Path;
import com.avenuecode.service.view.ResponseRoutes;

@RestController
public class RouteController {

	@Autowired
	RouteServiceImpl service;

	@RequestMapping(value = "/graph", method = RequestMethod.POST)
	public ResponseEntity<Graph> saveGraph(@RequestBody final Graph graph) {

		Graph response = null;

		if (null != graph && null != graph.getData()) {

			response = service.save(graph);
		}

		if (null == response) {

			return new ResponseEntity<Graph>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Graph>(response, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/graph/{graphId}", method = RequestMethod.GET)
	public ResponseEntity<Graph> getGraph(@PathVariable final Integer graphId) {

		final Graph response = service.find(graphId);

		if (null == response) {

			return new ResponseEntity<Graph>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Graph>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/routes/{graphId}/from/{townSource}/to/{townTarget}", method = RequestMethod.POST)
	public ResponseEntity<ResponseRoutes> findRoutes(@PathVariable final Integer graphId, @PathVariable final String townSource,
			@PathVariable final String townTarget, @RequestParam(required = false, name = "maxStops") final Integer maxStops) {

		final Graph graph = service.find(graphId);

		if (null == graph) {

			return new ResponseEntity<ResponseRoutes>(HttpStatus.NOT_FOUND);
		}

		final ResponseRoutes response = service.findRoutes(graph.getData(), townSource, townTarget, maxStops);

		return new ResponseEntity<ResponseRoutes>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/distance/{graphId}", method = RequestMethod.POST)
	public ResponseEntity<Distance> findDistance(@PathVariable final Integer graphId, @RequestBody final Path path) {

		final Graph graph = service.find(graphId);

		if (null == graph) {

			return new ResponseEntity<Distance>(HttpStatus.NOT_FOUND);
		}

		final Distance response = service.findDistance(graph.getData(), path.getPath());

		return new ResponseEntity<Distance>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/distance/{graphId}/from/{townSource}/to/{townTarget}", method = RequestMethod.POST)
	public ResponseEntity<Path> findDistance(@PathVariable final Integer graphId, @PathVariable final String townSource,
			@PathVariable final String townTarget) {

		final Graph graph = service.find(graphId);

		if (null == graph) {

			return new ResponseEntity<Path>(HttpStatus.NOT_FOUND);
		}

		final Path response = service.findShortestDistance(graph.getData(), townSource, townTarget);

		return new ResponseEntity<Path>(response, HttpStatus.OK);
	}

}
