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

/**
 * @author 1
 *
 */
class SphereTest { /// FROM AVI MARGALI !!

	private List<Point3D> getIntersections(Camera camera, Sphere sphere) {
		List<Point3D> list = new ArrayList<>();
		for (int i = 1; i < 4; ++i) {
			for (int j = 1; j < 4; ++j) {
				Ray r = camera.constructRayThroughPixel(3, 3, i, j, 4, 9, 9);
				list.addAll(sphere.findIntersections(r));
			}
		}
		return list;
	}

	@Test
	void testIntersectionPoints() {

		// 18 points
		Sphere sphere2 = new Sphere(9, new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(-9.5)));
		Camera camera2 = new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, -1));
		List<Point3D> list2 = getIntersections(camera2, sphere2);
		if (list2 != null)
			assertEquals(18, list2.size(), "18 points");
		else
			fail("null list");
		
		// 9 points ,P0 before the center point
		
		Sphere sphere3 = new Sphere(9, new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(-4.5)));
		List<Point3D> list3 = getIntersections(camera2, sphere3);
		if (list3 != null)
			assertEquals(9, list3.size(), "9 points befor center point");
		else
			fail("null list");
		
		//9 points, P0 after center point
		Sphere sphere4 = new Sphere(9, new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(4.5)));
		List<Point3D> list4 = getIntersections(camera2, sphere4);
		if (list4 != null)
			assertEquals(9, list4.size(), "9 points, P0 after center point");
		else
			fail("null list");
		
		//0 points
		Sphere sphere5 = new Sphere(9, new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(10)));
		List<Point3D> list5 = getIntersections(camera2, sphere5);
		if (list5 != null)
			assertEquals(0, list5.size(), "0 points");
		
		//9 points ,P0 by the center point
		Sphere sphere7 = new Sphere(9, Point3D.ZERO);
		List<Point3D> list7 = getIntersections(camera2, sphere7);
		if (list7 != null)
			assertEquals(9, list7.size(), "9 points, P0 by center point");
		else
			fail("null list");
		
		//10 points 
		Sphere sphere6 = new Sphere(5, new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(-6)));
		Camera camera6 = new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, -1));
		List<Point3D> list6 =new ArrayList<>();
		for (int i = 1; i < 4; ++i) {
			for (int j = 1; j < 4; ++j) {
				Ray r = camera6.constructRayThroughPixel(3, 3, i, j, 3, 12, 12);
				list6.addAll(sphere6.findIntersections(r));
			}
		}
		if (list6 != null)
			assertEquals(10, list6.size(), "10 points");

		
		
		
		
	}
}
