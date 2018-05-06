/**
 * 
 */
package geometries;

import java.util.List;

import primitives.*;

/**
 * class to represent a cylinder, inherits from RadialGeometry
 *
 */
public class Cylinder extends RadialGeometry {

	private Ray _ray;
	private double _height;

	// ***************** Constructors ********************** //

	/**
	 * constructor for ray
	 * 
	 * @param Ray
	 *            - for the beginning and direction
	 * @param height
	 * @param radius
	 *            - double for the scope
	 */
	public Cylinder(Ray ray, double height, double radius) {
		super(radius);
		_ray = new Ray(ray);
		_height = height;
	}

	/**
	 * copy constructor for ray
	 * 
	 * @param other
	 */
	public Cylinder(Cylinder other) {
		super(other);
		_ray = new Ray(other._ray);
		_height = other._height;
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * 
	 * @return _ray
	 */
	public Ray getRay() {
		return _ray;
	}

	/**
	 * 
	 * @return _height
	 */
	public double getHeight() {
		return _height;
	}

	// ***************** Operations ******************** //

	/**
	 * @param point - Point3D
	 * @return normal vector to the body at the given point
	 */
	@Override
	public Vector getNormal(Point3D point) {
		// find cross vector according to gram-shmidt.
		Vector u = point.subtract(_ray.getStartingPoint());
		Vector v = _ray.getDirectionVector()
				.scaleMult(_ray.getDirectionVector().dotProduct(u) / _ray.getDirectionVector().length());
		return u.subtract(v);

	}

	@Override
	public List<Point3D> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

}
