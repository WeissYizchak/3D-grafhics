/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.*;

/**
 * The "Composite" class - represent complex shape builds from a several shapes,
 * all shapes implements Geometry (the "father" class) and collected into
 * _theShapes - an ArrayList of Geometry
 * 
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 *
 */
public class Geometries extends Geometry {

	private List<Geometry> _theShapes;

	// ***************** Constructors ********************** //

	/**
	 * Constructor - initialize list of geometries shapes
	 */
	public Geometries() {
		_theShapes = new ArrayList<Geometry>();
	}

	/**
	 * Copy constructor !!!!! I'M NOT SURE IT'S NEEDED !!!!!
	 * 
	 * @param other
	 */
	public Geometries(Geometries other) {

		for (Geometry g : other._theShapes) { // Deep copy
			_theShapes.add(g);
		}
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Geter
	 * 
	 * @return the _theShapes - ArrayList of the shapes
	 */
	public List<Geometry> getTheShapes() {
		return _theShapes;
	}

	// ***************** Administration ******************** //

	// ***************** Operations ******************** //

	/**
	 * Adds a new shape to the list
	 * 
	 * @param shape
	 *            - The shape to add (One of the realists of Geometry)
	 * 
	 */
	public void add(Geometry shape) {
		_theShapes.add(shape);
	}

	@Override
	public Vector getNormal(Point3D point) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Finds the intersections points of ray on composite shape by using the
	 * findIntersections function of each shape in _theShape collection
	 * 
	 * @return - Returns the intersection points on the composite shape
	 */
	@Override
	public List<Point3D> findIntersections(Ray ray) {

		List<Point3D> intersections = new ArrayList<Point3D>(); // to return the intersection points
		for (Geometry g : _theShapes) {
			intersections.addAll(g.findIntersections(ray));

		}
		return intersections;
	}

}
