/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Class to represent a triangle, inherit from plane. have 3 points to 3
 * vertexes
 */
public class Triangle extends Plane {

	private Point3D _vertexA;
	private Point3D _vertexB;
	private Point3D _vertexC;

	// ***************** Constructors ********************** //

	/**
	 * Constructor
	 * 
	 * @param Point3D
	 *            vertexA
	 * @param Point3D
	 *            vertexB
	 * @param Point3D
	 *            vertexC
	 */
	public Triangle(Point3D vertexA, Point3D vertexB, Point3D vertexC, Color color) {
		super(vertexA, vertexB, vertexC, color); // Operates the father constructor to build a normal
		_vertexA = new Point3D(vertexA);
		_vertexB = new Point3D(vertexB);
		_vertexC = new Point3D(vertexC);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Getter
	 * 
	 * @return the first vertex (A)
	 */
	public Point3D getVertexA() {
		return _vertexA;
	}

	/**
	 * Getter
	 * 
	 * @return the second vertex (B)
	 */
	public Point3D getVertexB() {
		return _vertexB;
	}

	/**
	 * Getter
	 * 
	 * @return the third vertex (C)
	 */
	public Point3D getVertexC() {
		return _vertexC;
	}

	// ***************** Administration ******************** //

	// ***************** Operations ******************** //

	@Override
	public Map<Geometry, List<Point3D>> findIntersections(Ray ray) {

		Map<Geometry, List<Point3D>> intersectionPoints = super.findIntersections(ray);
		List<Point3D> intersection = new ArrayList<Point3D>();
		if (!intersectionPoints.isEmpty()) {// If there is intersection points checks if the intersection point is
											// inside the triangle
//			intersection.addAll((ArrayList<Point3D>)intersectionPoints.entrySet().iterator().next());
			for (Map.Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet()) {
				intersection.addAll(entry.getValue());
			}
			Vector v1 = _vertexA.subtract(ray.getStartingPoint());
			Vector v2 = _vertexB.subtract(ray.getStartingPoint());
			Vector v3 = _vertexC.subtract(ray.getStartingPoint());
			Vector n1 = v1.crossProduct(v2);
			Vector n2 = v2.crossProduct(v3);
			Vector n3 = v3.crossProduct(v1);
			Vector P0toP = intersection.get(0).subtract(ray.getStartingPoint());
			double s1 = P0toP.dotProduct(n1);
			double s2 = P0toP.dotProduct(n2);
			double s3 = P0toP.dotProduct(n3);
			if (!(s1 > 0 && s2 > 0 && s3 > 0 || s1 < 0 && s2 < 0 && s3 < 0))
				intersection.remove(0); // The intersection point is not inside the triangle
		}
		intersectionPoints.clear();
		if (!intersection.isEmpty()) {
			intersectionPoints.put(this, intersection);
		} 
		return intersectionPoints;  // Returns one point or an empty list
		
	}

	@Override
	public Point3D getMax() {
		double x =Double.max(_vertexC.getX().get_coord(), Double.max(_vertexA.getX().get_coord(), _vertexB.getX().get_coord()));
		double y =Double.max(_vertexC.getY().get_coord(), Double.max(_vertexA.getY().get_coord(), _vertexB.getY().get_coord()));
		double z =Double.max(_vertexC.getZ().get_coord(), Double.max(_vertexA.getZ().get_coord(), _vertexB.getZ().get_coord()));

		return new Point3D(x,y,z);
	}

	@Override
	public Point3D getMin() {
		double x =Double.min(_vertexC.getX().get_coord(), Double.min(_vertexA.getX().get_coord(), _vertexB.getX().get_coord()));
		double y =Double.min(_vertexC.getY().get_coord(), Double.min(_vertexA.getY().get_coord(), _vertexB.getY().get_coord()));
		double z =Double.min(_vertexC.getZ().get_coord(), Double.min(_vertexA.getZ().get_coord(), _vertexB.getZ().get_coord()));

		return new Point3D(x,y,z);
	}

}
