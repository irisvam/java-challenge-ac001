package com.avenuecode.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.avenuecode.model.Route;
import com.avenuecode.service.impl.RouteServiceImpl;
import com.avenuecode.service.view.Distance;
import com.avenuecode.service.view.Path;
import com.avenuecode.service.view.ResponseRoutes;

public class RootServiceTest {

	static IRouteService service;

	static List<Route> data;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		service = new RouteServiceImpl();
		data = new ArrayList<>();

		final Route route1 = new Route();
		route1.setId(1);
		route1.setSource("A");
		route1.setTarget("B");
		route1.setDistance(5);

		final Route route2 = new Route();
		route2.setId(2);
		route2.setSource("B");
		route2.setTarget("C");
		route2.setDistance(4);

		final Route route3 = new Route();
		route3.setId(3);
		route3.setSource("C");
		route3.setTarget("D");
		route3.setDistance(8);

		final Route route4 = new Route();
		route4.setId(4);
		route4.setSource("D");
		route4.setTarget("C");
		route4.setDistance(8);

		final Route route5 = new Route();
		route5.setId(5);
		route5.setSource("D");
		route5.setTarget("E");
		route5.setDistance(6);

		final Route route6 = new Route();
		route6.setId(6);
		route6.setSource("A");
		route6.setTarget("D");
		route6.setDistance(5);

		final Route route7 = new Route();
		route7.setId(7);
		route7.setSource("C");
		route7.setTarget("E");
		route7.setDistance(2);

		final Route route8 = new Route();
		route8.setId(8);
		route8.setSource("E");
		route8.setTarget("B");
		route8.setDistance(3);

		final Route route9 = new Route();
		route9.setId(9);
		route9.setSource("A");
		route9.setTarget("E");
		route9.setDistance(7);

		data.add(route1);
		data.add(route2);
		data.add(route3);
		data.add(route4);
		data.add(route5);
		data.add(route6);
		data.add(route7);
		data.add(route8);
		data.add(route9);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		service = null;
		data = null;
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testFindDistanceRouteABC() {

		final String[] path = new String[3];
		path[0] = "A";
		path[1] = "B";
		path[2] = "C";

		final Distance orderPath = service.findDistance(data, path);

		Assert.assertEquals(orderPath.getDistance(), 9);
	}

	@Test
	public void testFindDistanceRouteAD() {

		final String[] path = new String[2];
		path[0] = "A";
		path[1] = "D";

		final Distance orderPath = service.findDistance(data, path);

		Assert.assertEquals(orderPath.getDistance(), 5);
	}

	@Test
	public void testFindDistanceRouteADC() {

		final String[] path = new String[3];
		path[0] = "A";
		path[1] = "D";
		path[2] = "C";

		final Distance orderPath = service.findDistance(data, path);

		Assert.assertEquals(orderPath.getDistance(), 13);
	}

	@Test
	public void testFindDistanceRouteAEBCD() {

		final String[] path = new String[5];
		path[0] = "A";
		path[1] = "E";
		path[2] = "B";
		path[3] = "C";
		path[4] = "D";

		final Distance orderPath = service.findDistance(data, path);

		Assert.assertEquals(orderPath.getDistance(), 22);
	}

	@Test
	public void testFindDistanceRouteAED() {

		final String[] path = new String[3];
		path[0] = "A";
		path[1] = "E";
		path[2] = "D";

		final Distance orderPath = service.findDistance(data, path);

		Assert.assertEquals(orderPath.getDistance(), -1);
	}

	@Test
	public void testFindShortestDistanceAtoC() {

		final String townSource = "A";
		final String townTarget = "C";

		final Path path = service.findShortestDistance(data, townSource, townTarget);

		final String[] pathString = path.getPath();

		Assert.assertEquals(pathString[0], "A");
		Assert.assertEquals(pathString[1], "B");
		Assert.assertEquals(pathString[2], "C");
		Assert.assertEquals(path.getDistance(), 9);
	}

	@Test
	public void testFindShortestDistanceBtoB() {

		final String townSource = "B";
		final String townTarget = "B";

		final Path path = service.findShortestDistance(data, townSource, townTarget);

		final String[] pathString = path.getPath();

		Assert.assertEquals(pathString[0], "B");
		Assert.assertEquals(path.getDistance(), 0);
	}

	@Test
	public void testFindRoutesAtoC() {

		final String townSource = "A";
		final String townTarget = "C";
		final int maxStops = 4;

		final ResponseRoutes response = service.findRoutes(data, townSource, townTarget, maxStops);

		Assert.assertEquals(response.getRoutes().get(0).getRoute(), "ABC");
		Assert.assertEquals(response.getRoutes().get(0).getStops(), 2);

		Assert.assertEquals(response.getRoutes().get(1).getRoute(), "ADC");
		Assert.assertEquals(response.getRoutes().get(1).getStops(), 2);

		Assert.assertEquals(response.getRoutes().get(2).getRoute(), "ADEBC");
		Assert.assertEquals(response.getRoutes().get(2).getStops(), 4);

		Assert.assertEquals(response.getRoutes().get(3).getRoute(), "AEBC");
		Assert.assertEquals(response.getRoutes().get(3).getStops(), 3);
	}

	@Test
	public void testFindRoutesCtoC() {

		final String townSource = "C";
		final String townTarget = "C";
		final int maxStops = 3;

		final ResponseRoutes response = service.findRoutes(data, townSource, townTarget, maxStops);

		Assert.assertEquals(response.getRoutes().get(0).getRoute(), "CDC");
		Assert.assertEquals(response.getRoutes().get(0).getStops(), 2);

		Assert.assertEquals(response.getRoutes().get(1).getRoute(), "CEBC");
		Assert.assertEquals(response.getRoutes().get(1).getStops(), 3);
	}

}
