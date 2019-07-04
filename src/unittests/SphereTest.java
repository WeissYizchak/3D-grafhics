/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import elements.*;
import geometries.*;
import primitives.*;

class SphereTest {

	/**
	 * Help function to throw rays through the all pixels and find the intersection
	 * points with the shape written by avi margali
	 * 
	 * @param Camera
	 * @param Sphere
	 * @return - Returns list of all intersection points of the rays thrown through
	 *         the pixels
	 */
	public List<Point3D> getIntersections(Camera camera, Sphere sphere) {
		List<Point3D> list = new ArrayList<>();
		for (int i = 1; i < 4; ++i) {
			for (int j = 1; j < 4; ++j) {
				Ray r = camera.constructRayThroughPixel(3, 3, i, j, 4, 9, 9);
				List<Point3D> list1 = sphere.findIntersections(r).get(sphere);
				if (list1 != null) {
					list.addAll(list1);
				}
			}
		}
		return list;
	}

	@Test
	void testIntersectionPoints() {

		// 18 points
		Sphere sphere2 = new Sphere(30, new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(-40)), null);
		Camera camera2 = new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, -1));
		List<Point3D> list2 = getIntersections(camera2, sphere2);
		if (list2 != null)
			assertEquals(18, list2.size(), "18 points");
		else
			fail("null list");

		// 9 points ,P0 before the center point

		Sphere sphere3 = new Sphere(9, new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(-4.5)), null);
		List<Point3D> list3 = getIntersections(camera2, sphere3);
		if (list3 != null)
			assertEquals(9, list3.size(), "9 points befor center point");
		else
			fail("null list");

		// 9 points, P0 after center point
		Sphere sphere4 = new Sphere(30, new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(1)), null);
		List<Point3D> list4 = getIntersections(camera2, sphere4);
		if (list4 != null)
			assertEquals(9, list4.size(), "9 points, P0 after center point");
		else
			fail("null list");

		// 0 points
		Sphere sphere5 = new Sphere(9, new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(10)), null);
		List<Point3D> list5 = getIntersections(camera2, sphere5);
		if (list5 != null)
			assertEquals(0, list5.size(), "0 points");

		// 9 points ,P0 by the center point
		Sphere sphere7 = new Sphere(9, Point3D.ZERO, null);
		List<Point3D> list7 = getIntersections(camera2, sphere7);
		if (list7 != null)
			assertEquals(9, list7.size(), "9 points, P0 by center point");
		else
			fail("null list");
	}

	/**** Sphere test ****/
	@Test
	public void testIntersectionPoints1() {
		final int WIDTH = 3;
		final int HEIGHT = 3;
		Ray[][] rays = new Ray[HEIGHT][WIDTH];
		Camera camera = new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, 1.0, 0.0), new Vector(0.0, 0.0, -1.0));
		Sphere sphere = new Sphere(1, new Point3D(0.0, 0.0, -3.0), null);
		Sphere sphere2 = new Sphere(10, new Point3D(0.0, 0.0, -3.0), null);
		// Only the center ray intersect the sphere in two locations
		List<Point3D> intersectionPointsSphere = new ArrayList<Point3D>();
		// The sphere encapsulates the view plane - all rays intersect with the sphere
		// once
		List<Point3D> intersectionPointsSphere2 = new ArrayList<Point3D>();
		System.out.println("Camera:\n" + camera);
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				rays[i][j] = camera.constructRayThroughPixel(WIDTH, HEIGHT, j + 1, i + 1, 1, 3 * WIDTH, 3 * HEIGHT);
				List<Point3D> rayIntersectionPoints = sphere.findIntersections(rays[i][j]).get(sphere);
				List<Point3D> rayIntersectionPoints2 = sphere2.findIntersections(rays[i][j]).get(sphere2);
				if (rayIntersectionPoints != null) {
					for (Point3D iPoint : rayIntersectionPoints)
						intersectionPointsSphere.add(iPoint);
				}
				if (rayIntersectionPoints2 != null) {
					for (Point3D iPoint : rayIntersectionPoints2)
						intersectionPointsSphere2.add(iPoint);
				}
			}
		}
		assertEquals(2, intersectionPointsSphere.size());
		assertEquals(9, intersectionPointsSphere2.size());
		System.out.println("Intersection Points:");
		for (Point3D iPoint : intersectionPointsSphere) {
			assertTrue(iPoint.equals(new Point3D(0.0, 0.0, -2.0)) || iPoint.equals(new Point3D(0.0, 0.0, -4.0)));
			System.out.println(iPoint);
		}
	}
}
