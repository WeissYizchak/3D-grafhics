/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import primitives.*;

/**
 * Class to represent a cylinder, inherits from RadialGeometry
 *
 */
public class Cylinder extends RadialGeometry {

	protected Ray _ray;

	// ***************** Constructors ********************** //

	/**
	 * Constructor for ray
	 * 
	 * @param Ray
	 *            - for the beginning and direction
	 * @param height
	 * @param radius
	 *            - double for the scope
	 */
	public Cylinder(Ray ray, double radius, Color color) {
		super(radius, color);
		_ray = new Ray(ray);
		//_height = height;
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Getter
	 * 
	 * @return _ray
	 */
	public Ray getRay() {
		return _ray;
	}

	/**
	 * Getter
	 * 
	 * @return _height
	 */
//	public double getHeight() {
//		return _height;
//	}

	// ***************** Operations ******************** //

	/**
	 * @param point
	 *            - Point3D
	 * @return normal vector to the body at the given point
	 */
	@Override
	public Vector getNormal(Point3D point) {
		// find cross vector according to gram-shmidt.
		Vector u = point.subtract(_ray.getStartingPoint());
		double v_u = _ray.getDirectionVector().dotProduct(u);
		if (v_u == 0)
			return u.normalization(); // u normal to the direction vector of the cylinder
		Vector v = _ray.getDirectionVector().scaleMult(v_u / _ray.getDirectionVector().length());
		return u.subtract(v).normalization();

	}

	@Override
	public Point3D getMax() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point3D getMin() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public Map<Geometry, List<Point3D>> findIntersections(Ray ray) {
		List<Point3D> intersections = new ArrayList<Point3D>(); // to return the intersection points
		
		double A = 0;
		double B = 0;
		double C = 0;

		// Cylinder details
		Vector Va = _ray.getDirectionVector();
		Point3D Pa = _ray.getStartingPoint();
		
		//Ray intersection details
		Vector V = ray.getDirectionVector();
		Point3D P0 = ray.getStartingPoint();
		
		//find the coefficient A, B, C.
		//find the Vector (V - (V*Va)Va)
		Vector coefficient1 = null, helper1 = null;
		//Checks whether Va is reseting
		double V_Va = V.dotProduct(Va);
		if(!Coordinate.isZero(V_Va)) {
			helper1 = Va.scaleMult(V_Va);
			//Checks if vector subtraction resets them
			if(!V.equals(helper1)) {
				coefficient1 = V.subtract(helper1); 
			}
		}else {
			coefficient1 = V;
		}
		
		//find the Vector(DP-(DP*Va)Va)
		Vector coefficient2 = null, helper2 = null, DP = null;
		//in two cases is coefficient2 not initialized:
		//DP == 0
		//DP == helper
		if(!P0.equals(Pa)) {
			DP = P0.subtract(Pa);
			double DP_Va = DP.dotProduct(Va);
			if(!Coordinate.ZERO.equals(DP_Va)) {
				helper2 = Va.scaleMult(DP_Va);
				
				if(!DP.equals(helper2)) {
					coefficient2 = DP.subtract(helper2);
				}
			}
			else {
				coefficient2 = DP;
			}
		}
		
		//enter in A,B,C
		if(coefficient1 != null) {
			A = coefficient1.dotProduct(coefficient1);
			if(coefficient2 != null) {
				B = 2*(coefficient1.dotProduct(coefficient2));
			}
		}
		
		if(coefficient2 != null) {
			C = coefficient2.dotProduct(coefficient2) - (_radius*_radius);
		}
		else {
			C = - (_radius*_radius);
		}
		
		double Discriminant = (B * B) - (4.0 * A * C);
		
		//calculates the intersection
		double t1, t2;
		if (Coordinate.isZero(Discriminant)) {
			t1 = (-B) / 2.0 * A;
			intersections.add(P0.addVector(V.scaleMult(t1)));
		} else if (Discriminant > 0) {
			double disSqurt = Math.sqrt(Discriminant);
			
			t1 = ((-B) + disSqurt) / 2.0 * A;
			Vector direction = V; 
			if(!Coordinate.isZero(t1)) {
				direction = V.scaleMult(t1);
			}
			intersections.add(P0.addVector(direction));
			
			t2 = ((-B) - disSqurt) / 2.0* A;
			direction = V;
			if(!Coordinate.isZero(t2)) {
				direction = V.scaleMult(t2);
			}
			intersections.add(P0.addVector(direction));
		}
		
		Map<Geometry, List<Point3D>> intersectionPoints = new HashMap<Geometry, List<Point3D>>();
		if (!intersections.isEmpty()) {
			intersectionPoints.put(this, intersections);
		}
		return intersectionPoints;
		}
}
