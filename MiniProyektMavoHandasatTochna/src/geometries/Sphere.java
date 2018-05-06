/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.*;

/**
 * class to represent a sphere
 *
 */
public class Sphere extends RadialGeometry {

	private Point3D _centerPoint;

	// ***************** Constructors ********************** //

	/**
	 * constructor
	 * 
	 * @param _radius
	 *            (double)
	 * @param Point3D
	 *            centerPoint
	 */
	public Sphere(double _radius, Point3D centerPoint) {
		super(_radius);
		_centerPoint = new Point3D(centerPoint);
	}

	/**
	 * copy constructor
	 * 
	 * @param other
	 */
	public Sphere(Sphere other) {
		super(other);
		_centerPoint = new Point3D(other._centerPoint);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * geter
	 * 
	 * @return the center point of the sphere
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
	public List<Point3D> findIntersections(Ray ray) {

		List<Point3D> intersections = new ArrayList<Point3D>(); // to return the intersection points

		Vector u;
		double Tm;
		double d;
		if (!_centerPoint.equals(ray.getStartingPoint())) {
			u = _centerPoint.subtract(ray.getStartingPoint());
			// Tm = the projection of u on the ray (the length from P0 to the point on the
			// ray that parallel to the sphere's center point
			Tm = ray.getDirectionVector().dotProduct(u);
			d = Math.sqrt(Math.pow(u.length(), 2) - Math.pow(Tm, 2));
		} else {
			Tm = 0;
			d = 0;
		}
		if (d <= _radius) { // there at least one intersection point
			double Th = Math.sqrt(Math.pow(_radius, 2) - Math.pow(d, 2));
			double t1 = Tm - Th;
			double t2 = Tm + Th;
			if (t1 > 0) // COULD IT BE LESS THAN 0 ???
				intersections.add(ray.getStartingPoint().addVector(ray.getDirectionVector().scaleMult(t1)));
			if (t2 > 0 && t2 != t1) // if T2 = T1 there is only one intersection point
				intersections.add(ray.getStartingPoint().addVector(ray.getDirectionVector().scaleMult(t2)));
		}
		return intersections;
	}
}
