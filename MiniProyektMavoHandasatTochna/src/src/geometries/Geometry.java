/**
 * 
 */
package geometries;

import java.util.List;
import java.util.Map;

import primitives.*;

/**
 * An abstract class to represent geometric shapes, the class have the getNormal
 * function and findIntersections function that the inheritors realize
 * 
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 */
public abstract class Geometry {

	protected Color _emission;
	protected Material _material;

	// ***************** Constructors ********************** //

	/**
	 * Constructor
	 * 
	 * @param _emission
	 */
	public Geometry(Color emission) {

		this._emission = emission;
	}

	/**
	 * Default constructor (needed to the geometries inheritor for default value)
	 */
	public Geometry() {

		this._emission = new Color(255, 255, 255);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Getter
	 * 
	 * @return - The _emission
	 */
	public Color getEmission() {

		return _emission;
	}

	/**
	 * Getter
	 * 
	 * @return - The _material
	 */
	public Material getMaterial() {
		return _material;
	}

	/**
	 * 
	 * @param kd
	 * @param ks
	 * @param shininess
	 * @param kr
	 * @param kt
	 */
	public void setMaterial(double kd, double ks, int shininess, double kr, double kt) {
		_material = new Material(kd, ks, shininess, kr, kt);
	}
	
	public void setMaterial(Material material) {
		_material = new Material(material);
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
	public abstract Map<Geometry, List<Point3D>> findIntersections(Ray ray);
	
	/**
	 * 
	 * @return - Returns the max point of the cube contains the geometry
	 */
	public abstract Point3D getMax();
	
	/**
	 * 
	 * @return - Returns the min point of the cube contains the geometry
	 */
	public abstract Point3D getMin();
}
