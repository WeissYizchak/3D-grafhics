package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import primitives.*;
import elements.*;
import geometries.*;

class PlaneTests {

	private List<Point3D> findIntersctionPlane(Plane p, Camera cam) {
		List<Point3D> list = new ArrayList<>();
		for (int i = 1; i < 4; i++) 
			for (int j = 1; j < 4; j++) {
				Ray r = cam.constructRayThroughPixel(3, 3, i, j, 2, 9, 9);
				list.addAll(p.findIntersections(r));
		}
		return list;
	}

	@Test
	void testFindIntersction() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0,-1, 0), new Vector(0, 0, -1));
		
		//Check when the plane is perpendicular
		Plane plane1 = new Plane(new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(-50)),
				new Vector(0, 0, -1));
		List<Point3D> intersctionList1 = findIntersctionPlane(plane1, camera);
		if(intersctionList1!= null)
			assertEquals("plane is perpendicular", 9, intersctionList1.size());
		else
			fail("null list");
		
		//0 
		Plane plane2 = new Plane(new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(50)),
				new Vector(0, 0, -1));
		List<Point3D> intersctionList2 = findIntersctionPlane(plane2, camera);
		if(intersctionList2!= null)
			assertEquals("plane before P0", 0, intersctionList2.size());
		else
			fail("null list");
		
		//plane is horizontal and vectors or contained in or not touching it
		Plane plane3 = new Plane(new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(-50)),
				new Vector(0, 1, 0));
		List<Point3D> intersctionList3 = findIntersctionPlane(plane3, camera);
		if(intersctionList3!= null)
			assertEquals("plane horizontal", 0, intersctionList3.size());
		else
			fail("null list");
		
		//plane is horizontal but not in the middle
		Plane plane4 = new Plane(new Point3D(Coordinate.ZERO,new Coordinate(100),new Coordinate(-50)),new Vector(0,1,0));
		List<Point3D> intersctionList4 = findIntersctionPlane(plane4, camera);
		if(intersctionList4!= null)
			assertEquals("plane horizontal up", 3, intersctionList4.size());
		else
			fail("null list");
		
		//The plane is diagonal 45 degrees
		//The ray in the pixel (2,3) is also at 45 degrees and does not harm it.
		Plane plane5 = new Plane(new Point3D(Coordinate.ZERO, Coordinate.ZERO, new Coordinate(-10)), new Vector(0,-10,-10));
		List<Point3D> intersctionList5 = findIntersctionPlane(plane5, camera);
		if(intersctionList5!= null)
			assertEquals("plane parallel to one ray", 6, intersctionList5.size());
		else
			fail("null list");

		//P0=P
		Plane plane6 = new Plane(new Point3D(Coordinate.ZERO, Coordinate.ZERO, Coordinate.ZERO),
				new Vector(0, 0, -1));
		List<Point3D> intersctionList6 = findIntersctionPlane(plane6, camera);
		if(intersctionList6!= null)
			assertEquals("plane before P0", 0, intersctionList6.size());
		else
			fail("null list");
	}

	
}
