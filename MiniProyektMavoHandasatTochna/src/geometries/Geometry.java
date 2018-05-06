/**
 * 
 */
package geometries;

import java.util.List;
import primitives.*;

/**
 * abstract class to represent geometric shapes There is only the getNormal
 * function that the inheritors realize
 */
public abstract class Geometry {

	// ***************** Constructors ********************** //

	public Geometry() {
		// no implementation yet
	}

	public Geometry(Geometry other) {
		// no implementation yet
	}

	// ***************** Operations ******************** //

	/**
	 * Function to return the normal vector to the body at the given point
	 * 
	 * @param point
	 *            - Point3D
	 * @return normal vector to the body at the given point
	 */
	public abstract Vector getNormal(Point3D point);

	/**
	 * Function to return the intersections points of ray on a shape
	 * 
	 * @param Ray
	 *            - The ray sent towards the shape
	 * @return Return list of intersections points
	 */
	public abstract List<Point3D> findIntersections(Ray ray);
}
