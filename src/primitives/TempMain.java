
/**
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 */

package primitives;

import elements.Camera;
import geometries.*;

import java.util.ArrayList;
import java.util.List;

//import org.junit.jupiter.api.Test;

public class TempMain {

	public static void main(String[] args) {

		try {
			//coordinates 
			Coordinate a_x = new Coordinate(999.9999999999999);
			Coordinate a_y= new Coordinate(1);
			Coordinate a_z = new Coordinate(0);
			Coordinate b_x = new Coordinate(1);
			Coordinate b_y= new Coordinate(0);
			Coordinate b_z = new Coordinate(0);
			Coordinate c_x = new Coordinate(1.2003);
			Coordinate c_y= new Coordinate(6.30002);
			Coordinate c_z = new Coordinate(7.250001);
			Coordinate d_x = new Coordinate(0);
			Coordinate d_y= new Coordinate(2);
			Coordinate d_z = new Coordinate(0);

			System.out.println("====Coordinats====");
			System.out.println(a_x.equals(b_x));
			System.out.println(a_x);
			System.out.println(a_y.scale(2.0));

			//points
			System.out.println("====Points====");
			Point3D a = new Point3D(a_x, a_y, a_z);
			Point3D b = new Point3D(b_x, b_y, b_z);
			Point3D c = new Point3D(c_x, c_y, c_z);		

			Point2D p1 = new Point2D(a_x, a_y);
			Point2D p2 = new Point2D(a_x, a_y);
			//test//		
			System.out.println(p1.equals(p2));
			System.out.println(p1.equals(new Point2D(b_x,a_y)));
			System.out.println(p1);

			Point3D p3 = new Point3D(a_x, a_y, a_z);
			Point3D p4 = new Point3D(b_x, b_y, b_z);
			Point3D p5 = new Point3D(d_x, d_y, d_z);

			System.out.println("test for point3D");
			System.out.println("p3 is:" + p3);
			System.out.println("p5 is:" + p5);
			System.out.println("distance p3 to p5 is: " + p3.distance(p5));
			//test//		
			System.out.println(p3.equals(p4));
			System.out.println(p3);
			System.out.println("====Vectors====");
			System.out.println(Vector.class);

			Vector vv = new Vector(1,2,3);
			Vector vv2 = new Vector(1,1,1);
			System.out.println("====Vectors====");

			System.out.println(vv.equals(vv2));			
			Vector v1 = new Vector(p3);
			Vector v2 = new Vector(p4);
			Vector v3 = v1.subtract(v2);
			Vector v4 = v1.add(v2);
			Vector v5 = v1.scaleMult(2.0);
			Vector v6 = v5.normalization();
			Vector v7 = v1.crossProduct(v2);
			double dot = v1.dotProduct(v2);
			System.out.println("v1 is: " +v1);
			System.out.println("v2 is: " +v2);
			System.out.println("v3 is: " +v3);
			System.out.println("v4 is: " +v4);

			System.out.println("v5 is normal: "+ v5);
			System.out.println(v1.getDirection()._y.scale(2.0));
			System.out.println("v1 mult 2 is: " +v1.scaleMult(2.0));

			System.out.println("v6 is: " +v6);
			System.out.println("v7 is: " + v7);
			System.out.println("check dotProduct");
			Vector v8 = new Vector(new Point3D(new Coordinate(3),new Coordinate(2),new Coordinate(4)));
			System.out.println("v8 is: " + v8);
			Vector v9 = new Vector(new Point3D(new Coordinate(3),new Coordinate(4),new Coordinate(2)));
			System.out.println("v9 is: " + v9);
			System.out.println("dotProduct v8 with v9 is: " + v8.dotProduct(v9));

			System.out.println("check equals v1 with v8:" + v1.equals(v7));
			System.out.println(v6.length());
			Vector v10 = v9.normalization();
			System.out.println("normalization of v9: " + v10);
			System.out.println("v10.langth: " + v10.length());


			System.out.println("====Rays====");
			Ray r1 = new Ray(p4,v1);
			System.out.println(r1);

			System.out.println("====Triangle====");
			Triangle triangle1 = new Triangle(a,b,c, null);
			System.out.println(triangle1);
			System.out.println("====Cylinder====");
			//Cylinder cylinder1 = new Cylinder(r1,5.0,2.3, null);
			//System.out.println(cylinder1);
			System.out.println("====Plane====");
			//Plane plane1 = new Plane(p3,v1, null);
			//System.out.println(plane1);
			System.out.println("====Sphere====");
			Sphere sphere1 = new Sphere(0.000000000001,p3, null);
			System.out.println(sphere1);

			Camera camera = new Camera(Point3D.ZERO, new Vector(0, 1, 0), new Vector(0, 0, 1));
			//Camera camera1 = new Camera(Point3D.ZERO, new Vector(1, 1, 1), new Vector(2, 2, 1));
			Ray ray1 = camera.constructRayThroughPixel(3, 3, 3, 3, 100, 150, 150);
			Ray ray2 = camera.constructRayThroughPixel(3, 3, 3, 3, 100, 150, 150);
			System.out.println(ray1);

			List grades = new ArrayList<Point3D>();
			List grades1 = new ArrayList<Point3D>();
			//grades.add(0.0);
			System.out.println("-----List-----");
			grades.add(v1);
			grades.add(p1);
			for (Object object : grades1) {
				System.out.println(object);

			}
			grades1.add(2.5);
			grades1.add(323);
			grades1.add("aba");
			grades.addAll(grades1);

			for (Object object : grades) {
				System.out.println(object);

			}	




//
//			//18 points
//			Sphere sphere2 = new Sphere(0.00000000000000000000000000000000000000000000000001,new Point3D(0,0,-3), null);
//			Camera camera2 = new Camera(new Point3D(0,0,0.5),new Vector(0,-1,0),new Vector(0,0,-1));
//			List<Point3D> list2 = getIntersections(camera2,sphere2);
//			for (Point3D p : list2) {
//				System.out.println(p);
//
//			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}

		/*try {
			new Vector(0,0,0);
			//fail("Expected to: ERROR: cannot be a zero vector ");
			new Vector(Coordinate.zeroCoordinate, Coordinate.zeroCoordinate, Coordinate.zeroCoordinate);
			//fail("Expected to: ERROR: cannot be a zero vector ");
			new Vector(Point3D.zeroPoint);
			//fail("Expected to: ERROR: cannot be a zero vector ");
		} catch (Exception e) {			
			System.out.println(e.getMessage());
		}
		 */
		System.out.println(mult(5));
	}
	public static double mult(double d) {

		if (d==1) {
			return 1;
		}
		return d+mult(d-1);
	}
}


