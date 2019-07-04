/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.hamcrest.core.IsInstanceOf;

import primitives.*;

/**
 * The "Composite" class - represent complex shape builds from a several shapes,
 * all shapes implements Geometry (the "father" class) and collected into
 * _theShapes - an ArrayList of Geometry
 * 
 * @author Yizchak Meir Weiss 204017776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 *
 */
public class Geometries extends Geometry {

	private List<Geometry> _geometries;

	// ***************** Constructors ********************** //

	/**
	 * Constructor - initialize list of geometries shapes
	 */
	public Geometries() {
		_geometries = new ArrayList<Geometry>();
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Getter
	 * 
	 * @return the _theShapes - ArrayList of the shapes
	 */
	public List<Geometry> getGeometries() {
		return _geometries;
	}

	// ***************** Administration ******************** //

	// ***************** Operations ******************** //

	/**
	 * Adds a new shape to the list
	 * 
	 * @param g
	 *            - The shape to add (One of the realists of Geometry)
	 * 
	 */
	public void add(Geometry... g) {
		for (Geometry geometry : g) {
			_geometries.add(geometry);
		}
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
	public Map<Geometry, List<Point3D>> findIntersections(Ray ray) {

		Map<Geometry, List<Point3D>> intersections = new HashMap<Geometry, List<Point3D>>(); // to return the
																								// intersection points
		for (Geometry g : _geometries) {
			intersections.putAll(g.findIntersections(ray));
		}
		return intersections;
	}

	@Override
	public Point3D getMax() {
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		double maxZ = Double.MIN_VALUE;
		for (Geometry geometry : _geometries) {
			Point3D maxP = geometry.getMax();
			maxX = maxP.getX().get_coord() > maxX ? maxP.getX().get_coord() : maxX;
			maxY = maxP.getY().get_coord() > maxY ? maxP.getY().get_coord() : maxY;
			maxZ = maxP.getZ().get_coord() > maxZ ? maxP.getZ().get_coord() : maxZ;
		}
		return new Point3D(maxX, maxY, maxZ);
	}

	@Override
	public Point3D getMin() {
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double minZ = Double.MAX_VALUE;
		for (Geometry geometry : _geometries) {
			Point3D minP = geometry.getMin();
			minX = minP.getX().get_coord() < minX ? minP.getX().get_coord() : minX;
			minY = minP.getY().get_coord() < minY ? minP.getY().get_coord() : minY;
			minZ = minP.getZ().get_coord() < minZ ? minP.getZ().get_coord() : minZ;
		}
		return new Point3D(minX, minY, minZ);
	}

}
