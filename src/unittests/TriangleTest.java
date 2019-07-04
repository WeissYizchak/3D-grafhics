/**
 * 
 */
package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import elements.Camera;
import geometries.*;
import primitives.*;

class TriangleTest {

	/**
	 * Help function to throw rays through the all pixels written by avi margali
	 * 
	 * @param Camera
	 * @param Triangle
	 * @return
	 */
	private List<Point3D> findIntersctionTriangle(Triangle t, Camera cam) {
		List<Point3D> list = new ArrayList<>();
		for (int i = 1; i < 4; i++)
			for (int j = 1; j < 4; j++) {
				Ray r = cam.constructRayThroughPixel(3, 3, i, j, 2, 9, 9);
				List<Point3D> list1 = t.findIntersections(r).get(t);
				if(list1 != null) {
					list.addAll(list1);
				}
			}
		return list;
	}

	@Test
	void test() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, -1));

		// middle ray passes through the edge of the triangle
		Triangle triangle1 = new Triangle(new Point3D(3, -3, -10), new Point3D(-3, 3, -10), new Point3D(3, 3, -10), null);
		List<Point3D> intersctionList1 = findIntersctionTriangle(triangle1, camera);
		if (intersctionList1 != null)
			assertEquals("point of the end", 0, intersctionList1.size());
		else
			fail("null list");

		// triangle is located at the top of the camera and at its center, the ray (2.2)
		// and (1,2) passes through it.
		Triangle triangle2 = new Triangle(new Point3D(0, -8, -4), new Point3D(2, 0.5, -4), new Point3D(-2, 0.5, -4), null);
		List<Point3D> intersctionList2 = findIntersctionTriangle(triangle2, camera);
		if (intersctionList2 != null)
			assertEquals("tow point", 2, intersctionList2.size());
		else
			fail("null list");
	}

	/*** Triangle test ***/
	@Test
	public void TriangleIntersectionPointsTest() {
		final int WIDTH = 3;
		final int HEIGHT = 3;
		Ray[][] rays = new Ray[HEIGHT][WIDTH];
		Camera camera = new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, 1.0, 0.0), new Vector(0.0, 0.0, -1.0));
		Triangle triangle = new Triangle(new Point3D(0, 1, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2), null);
		Triangle triangle2 = new Triangle(new Point3D(0, 10, -2), new Point3D(1, -1, -2), new Point3D(-1, -1, -2), null);
		List<Point3D> intersectionPointsTriangle = new ArrayList<Point3D>();
		List<Point3D> intersectionPointsTriangle2 = new ArrayList<Point3D>();
		System.out.println("Camera:\n" + camera);
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				rays[i][j] = camera.constructRayThroughPixel(WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);
				List<Point3D> rayIntersectionPoints = triangle.findIntersections(rays[i][j]).get(triangle);
				List<Point3D> rayIntersectionPoints2 = triangle2.findIntersections(rays[i][j]).get(triangle2);
				if(rayIntersectionPoints != null) {
				for (Point3D iPoint : rayIntersectionPoints)
					intersectionPointsTriangle.add(iPoint);
				}
				if(rayIntersectionPoints2 != null) {
				for (Point3D iPoint : rayIntersectionPoints2)
					intersectionPointsTriangle2.add(iPoint);
				}
			}
		}
		assertTrue(intersectionPointsTriangle.size() == 1);
		assertTrue(intersectionPointsTriangle2.size() == 2);
		System.out.println("Intersection Points:");
		for (Point3D iPoint : intersectionPointsTriangle)
			System.out.println(iPoint);
		System.out.println("--");
		for (Point3D iPoint : intersectionPointsTriangle2)
			System.out.println(iPoint);
	}
	
//	@Test
//	void testMax() {
//		Triangle t1 = new Triangle(new Point3D(100,100,100),new Point3D, vertexC, color)
//	}
}
