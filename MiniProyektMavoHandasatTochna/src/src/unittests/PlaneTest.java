///**
// * 
// */
//package unittests;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import primitives.*;
//import elements.*;
//import geometries.*;
//
///**
// * @author 1
// *
// */
//class PlaneTest {
//
//	/**
//	 * Help function to throw rays through the all pixels written by avi margali
//	 * 
//	 * @param Camera
//	 * @param Plane
//	 * @return
//	 */
//	private List<Point3D> findIntersctionPlane(Plane p, Camera cam) {
//		List<Point3D> list = new ArrayList<>();
//		for (int i = 1; i < 4; i++)
//			for (int j = 1; j < 4; j++) {
//				Ray r = cam.constructRayThroughPixel(3, 3, i, j, 2, 9, 9);
//				List<Point3D> list1 = p.findIntersections(r).get(p);
//				if(list1 != null) {
//					list.addAll(list1);
//				}
//			}
//		return list;
//	}
//
//	@Test
//	void testFindIntersction() {
//		Camera camera = new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, -1));
//
//		// Check when the plane is perpendicular
//		Plane plane1 = new Plane(new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(-50)),
//				new Vector(0, 0, -1), null);
//		List<Point3D> intersctionList1 = findIntersctionPlane(plane1, camera);
//		if (intersctionList1 != null)
//			assertEquals("plane is perpendicular", 9, intersctionList1.size());
//		else
//			fail("null list");
//
//		// 0
//		Plane plane2 = new Plane(new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(50)),
//				new Vector(0, 0, -1), null);
//		plane2.setMaterial(0, 0, 0, 0, 0);
//		List<Point3D> intersctionList2 = findIntersctionPlane(plane2, camera);
//		if (intersctionList2 != null)
//			assertEquals("plane before P0", 0, intersctionList2.size());
//		else
//			fail("null list");
//
//		// plane is horizontal and vectors or contained in or not touching it
//		Plane plane3 = new Plane(new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(-50)),
//				new Vector(0, 1, 0), null);
//		List<Point3D> intersctionList3 = findIntersctionPlane(plane3, camera);
//		if (intersctionList3 != null)
//			assertEquals("plane horizontal", 0, intersctionList3.size());
//		else
//			fail("null list");
//
//		// plane is horizontal but not in the middle
//		Plane plane4 = new Plane(new Point3D(Coordinate.ZERO, new Coordinate(100), new Coordinate(-50)),
//				new Vector(0, 1, 0), null);
//		List<Point3D> intersctionList4 = findIntersctionPlane(plane4, camera);
//		if (intersctionList4 != null)
//			assertEquals("plane horizontal up", 3, intersctionList4.size());
//		else
//			fail("null list");
//
//		// The plane is diagonal 45 degrees
//		// The ray in the pixel (2,3) is also at 45 degrees and does not harm it.
//		Plane plane5 = new Plane(new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(-10)),
//				new Vector(0, -10, -10), null);
//		List<Point3D> intersctionList5 = findIntersctionPlane(plane5, camera);
//		if (intersctionList5 != null)
//			assertEquals("plane parallel to one ray", 6, intersctionList5.size());
//		else
//			fail("null list");
//
//		// P0=P
//		Plane plane6 = new Plane(new Point3D(Coordinate.ZERO, Coordinate.ZERO, Coordinate.ZERO), new Vector(0, 0, -1), null);
//		List<Point3D> intersctionList6 = findIntersctionPlane(plane6, camera);
//		if (intersctionList6 != null)
//			assertEquals("plane before P0", 0, intersctionList6.size());
//		else
//			fail("null list");
//	}
//
//	/*** Plane test ***/
//	@Test
//	public void testIntersectionPoints() {
//		final int WIDTH = 3;
//		final int HEIGHT = 3;
//		Ray[][] rays = new Ray[HEIGHT][WIDTH];
//		Camera camera = new Camera(new Point3D(0.0, 0.0, 0.0), new Vector(0.0, 1.0, 0.0), new Vector(0.0, 0.0, -1.0));
//		// plane orthogonal to the view plane
//		Plane plane = new Plane(new Point3D(0.0, 0.0, -3.0), new Vector(0.0, 0.0, -1.0), null);
//		// 45 degrees to the view plane
//		Plane plane2 = new Plane(new Point3D(0.0, 0.0, -3.0), new Vector(0.0, 0.25, -1.0), null);
//		List<Point3D> intersectionPointsPlane = new ArrayList<Point3D>();
//		List<Point3D> intersectionPointsPlane2 = new ArrayList<Point3D>();
//		System.out.println("Camera:\n" + camera);
//		for (int i = 0; i < HEIGHT; i++) {
//			for (int j = 0; j < WIDTH; j++) {
//				rays[i][j] = camera.constructRayThroughPixel(WIDTH, HEIGHT, j, i, 1, 3 * WIDTH, 3 * HEIGHT);
//				List<Point3D> points = plane.findIntersections(rays[i][j]).get(plane);
//				if(points!= null) {		
//					intersectionPointsPlane.addAll(points);
//				}
//				points = plane2.findIntersections(rays[i][j]).get(plane2);
//				if(points!= null) {
//					intersectionPointsPlane2.addAll(points);
//				}
//			}
//		}
//		assertTrue(intersectionPointsPlane.size() == 9);
//		assertEquals(9,intersectionPointsPlane2.size());
//		for (Point3D iPoint : intersectionPointsPlane)
//			System.out.println(iPoint);
//		System.out.println("---");
//		for (Point3D iPoint : intersectionPointsPlane2)
//			System.out.println(iPoint);
//	}
//
//	private void addAll(List<Point3D> list) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
