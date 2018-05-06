/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class to represent a plane contains point and normal vector
 *
 */
public class Plane extends Geometry {

	private Point3D _point;
	private Vector _normal;

	// ***************** Constructors ********************** //

	/**
	 * constructor of Plane. gets 3 points, chooses the first to be the "_point" of
	 * the plan and builds from the others a normal vector to the plane
	 * 
	 * @param Point3D
	 *            point1
	 * @param Point3D
	 *            point2
	 * @param Point3D
	 *            point3
	 * @exception if
	 *                it's not 3 different points
	 */
	public Plane(Point3D point1, Point3D point2, Point3D point3) {

		super();
		Vector vector1 = point1.subtract(point2); // if p1=p2 there will be an exception (zero vector)
		Vector vector2 = point1.subtract(point3); // if p1=p3 there will be an exception (zero vector)
		_point = new Point3D(point1);
		_normal = vector1.crossProduct(vector2).normalization(); // if the vectors are parallel (e.g. if p2=p3) there
																	// will be an
																	// exception (zero vector)
	}

	/**
	 * constructor of Plane
	 * 
	 * @param Point3D
	 *            point
	 * @param Vector
	 *            normal
	 */
	public Plane(Point3D point, Vector normal) {
		super();
		_point = new Point3D(point);
		_normal = new Vector(normal);
	}

	/**
	 * copy constructor of plane
	 * 
	 * @param Plane
	 *            other
	 */
	public Plane(Plane other) {
		super(other);
		_point = new Point3D(other._point);
		_normal = new Vector(other._normal);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * 
	 * @return the point
	 */
	public Point3D getPoint() {
		return _point;
	}

	/**
	 * 
	 * @return the normal vector
	 */
	public Vector getNormal() {
		return _normal;
	}

	// ***************** Administration ******************** //

	// ***************** Operations ******************** //

	/**
	 * @param point
	 *            - Point3D
	 * @return normal vector to the body at the given point (because it's a plane
	 *         it's the same vector in any point on the plane)
	 */
	@Override
	public Vector getNormal(Point3D point) {
		return new Vector(_normal);
	}

	@Override
	public List<Point3D> findIntersections(Ray ray) {

		List<Point3D> intersections = new ArrayList<Point3D>(); // to return the intersection point
		if (!_point.equals(ray.getStartingPoint())) { // to avoid zero vector 
			                                          // (if P0 and Q0 the same can't see anything !!!)
			Vector p0toQ0 = _point.subtract(ray.getStartingPoint()); // vector from point of imposition of the ray (P0)
																		// to
																		// some point on the plane (Q0)
			double nv = _normal.dotProduct(ray.getDirectionVector()); // N*V
			if (nv!=0) { // if nv = 0, the ray is parallel to the plane
				// t = the length of the ray from P0 to the plane
				double t = _normal.dotProduct(p0toQ0) / nv;
				if (t > 0) // if t = 0, P0 is on the plane (can't see anything !!!)
					intersections.add(ray.getStartingPoint().addVector(ray.getDirectionVector().scaleMult(t)));
			}
		}
		return intersections;
	}
}




