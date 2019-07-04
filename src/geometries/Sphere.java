/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA._PolicyStub;

import primitives.*;

/**
 * Class to represent a sphere - inherit from radialGeometry
 *
 */
public class Sphere extends RadialGeometry {

	private Point3D _centerPoint;

	// ***************** Constructors ********************** //

	/**
	 * Constructor
	 * 
	 * @param _radius
	 *            (double)
	 * @param Point3D
	 *            centerPoint
	 */
	public Sphere(double _radius, Point3D centerPoint, Color color) {
		super(_radius, color);
		_centerPoint = new Point3D(centerPoint);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Getter
	 * 
	 * @return - Returns the center point of the sphere
	 */
	public Point3D get_centerPoint() {
		return _centerPoint;
	}

	// ***************** Administration ******************** //

	// ***************** Operations ******************** //

	@Override
	public Vector getNormal(Point3D point) {
		return point.subtract(_centerPoint).normalization();
	}

	@Override
	public Map<Geometry,List<Point3D>> findIntersections(Ray ray) {

		Map<Geometry, List<Point3D>> intersectionPoints = new HashMap<Geometry, List<Point3D>>();
		List<Point3D> intersections = new ArrayList<Point3D>(); // to return the intersection points

		if (_centerPoint.equals(ray.getStartingPoint())) {
			// Happens when P0 = O, in this case there only one intersection point (P0 +
			// V*radius)
			intersections.add(ray.getStartingPoint().addVector(ray.getDirectionVector().scaleMult(_radius)));
			intersectionPoints.put(this, intersections);			
			return intersectionPoints;
		}
		// u = vector from P0 to the sphere's center point (O)
		Vector u = _centerPoint.subtract(ray.getStartingPoint());
		// Tm = the projection of u on the ray (the length from P0 to the point on the
		// ray that parallel to the sphere's center point
		double Tm = ray.getDirectionVector().dotProduct(u);
		double d = Math.sqrt((u.length()*u.length()) - (Tm*Tm)); 
		if (d <= _radius) { // there at least one intersection point
			double Th = Math.sqrt((_radius*_radius) - (d*d)); 
			double t1 = Tm - Th;
			double t2 = Tm + Th;
			if (!Coordinate.isZero(t1) && t1 > 0)
				intersections.add(ray.getStartingPoint().addVector(ray.getDirectionVector().scaleMult(t1)));
			if (!Coordinate.isZero(t2) && t2 > 0) // if T2 = T1 there is only one intersection point
				intersections.add(ray.getStartingPoint().addVector(ray.getDirectionVector().scaleMult(t2)));
		}
		if (!intersections.isEmpty()) {
			intersectionPoints.put(this, intersections);
		} 
		return intersectionPoints;
	}

	@Override
	public Point3D getMax() {
		return new Point3D(
				_centerPoint.getX().get_coord()+_radius,
				_centerPoint.getY().get_coord()+_radius,
				_centerPoint.getZ().get_coord()+_radius);
	}

	@Override
	public Point3D getMin() {
		return new Point3D(
				_centerPoint.getX().get_coord()-_radius,
				_centerPoint.getY().get_coord()-_radius,
				_centerPoint.getZ().get_coord()-_radius);
	}

}
