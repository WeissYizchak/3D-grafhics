/**
 * 
 */
package geometries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
public class Square extends Plane {

	// private Triangle _A;
	// private Triangle _B;

	private Point3D _vertexA;
	private Point3D _vertexB;
	private Point3D _vertexC;
	private Point3D _vertexD;

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
	public Square(Point3D vertexA, Point3D vertexB, Point3D vertexC, Point3D vertexD, Color color) {
		super(vertexA, vertexB, vertexC, color);
		_vertexA = vertexA;
		_vertexB = vertexB;
		_vertexC = vertexC;
		_vertexD = vertexD;
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Getter
	 * 
	 * @return the first vertex (A)
	 */
	// public Point3D getVertexA() {
	// return _A.getVertexA();
	// }

	/**
	 * Getter
	 * 
	 * @return the second vertex (B)
	 */
	// public Point3D getVertexB() {
	// return _A.getVertexB();
	// }

	/**
	 * Getter
	 * 
	 * @return the third vertex (C)
	 */
	// public Point3D getVertexC() {
	// return _A.getVertexC();
	// }

	/**
	 * Getter
	 * 
	 * @return the third vertex (D)
	 */
	// public Point3D getVertexD() {
	// return _B.getVertexC();
	// }

	// ***************** Administration ******************** //

	// ***************** Operations ******************** //

	@Override
	public Map<Geometry, List<Point3D>> findIntersections(Ray ray) {

		Map<Geometry, List<Point3D>> intersectionPoints = super.findIntersections(ray);
		List<Point3D> intersectionPoint = new ArrayList<>();

		for (Map.Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet()) {
			intersectionPoint.addAll(entry.getValue());
		}

		if (!(intersectionPoint.isEmpty())) {
			Vector a = _vertexA.subtract(_vertexB).normalization();
			Vector b = _vertexC.subtract(_vertexB).normalization();
			
			Point3D interPoint = intersectionPoint.get(0);
			Vector toPointNormliset = null;
			if(interPoint.equals(_vertexB)) {
				return intersectionPoints;
			}
			toPointNormliset = interPoint.subtract(_vertexB).normalization();
			Vector toPoint = intersectionPoint.get(0).subtract(_vertexB);

			double disetanceA = _vertexA.distance(_vertexB);
			double disetanceB = _vertexC.distance(_vertexB);

			if (a.dotProduct(toPointNormliset) >= 0 && b.dotProduct(toPointNormliset) >= 0
					&& toPoint.dotProduct(a) <= disetanceA && toPoint.dotProduct(b) <= disetanceB) {
				return intersectionPoints;
			}
		}
		intersectionPoints.clear();
		return intersectionPoints;

		// Map<Geometry, List<Point3D>> intersectionPoints = _A.findIntersections(ray);
		// //intersectionPoints.put(this,_B.findIntersections(ray).values());
		//
		// List<Point3D> intersection = new ArrayList<Point3D>();
		// for (Map.Entry<Geometry, List<Point3D>> entry :
		// intersectionPoints.entrySet()) {
		// intersection.addAll(entry.getValue());
		// }
		// intersectionPoints = _B.findIntersections(ray);
		// for (Map.Entry<Geometry, List<Point3D>> entry :
		// intersectionPoints.entrySet()) {
		// intersection.addAll(entry.getValue());
		// }
		// intersectionPoints.clear();
		// if (!intersection.isEmpty()) {
		// intersectionPoints.put(this, intersection);
		// }
		// return intersectionPoints; // Returns one point or an empty list

	}

	@Override
	public Vector getNormal(Point3D point) {
		return super.getNormal(point);
	}

	@Override
	public Point3D getMax() {
		double x1 = Math.max(_vertexA.getX().get_coord(), _vertexB.getX().get_coord());
		double x2 = Math.max(_vertexC.getX().get_coord(), _vertexD.getX().get_coord());
		double x = Math.max(x1,x2);
		double y1 = Math.max(_vertexA.getY().get_coord(), _vertexB.getY().get_coord());
		double y2 = Math.max(_vertexC.getY().get_coord(), _vertexD.getY().get_coord());
		double y = Math.max(y1,y2);
		double z1 = Math.max(_vertexA.getZ().get_coord(), _vertexB.getZ().get_coord());
		double z2 = Math.max(_vertexC.getZ().get_coord(), _vertexD.getZ().get_coord());
		double z = Math.max(z1,z2);
		return new Point3D(x,y,z);
	}

	@Override
	public Point3D getMin() {
		double x1 = Math.min(_vertexA.getX().get_coord(), _vertexB.getX().get_coord());
		double x2 = Math.min(_vertexC.getX().get_coord(), _vertexD.getX().get_coord());
		double x = Math.min(x1,x2);
		double y1 = Math.min(_vertexA.getY().get_coord(), _vertexB.getY().get_coord());
		double y2 = Math.min(_vertexC.getY().get_coord(), _vertexD.getY().get_coord());
		double y = Math.min(y1,y2);
		double z1 = Math.min(_vertexA.getZ().get_coord(), _vertexB.getZ().get_coord());
		double z2 = Math.min(_vertexC.getZ().get_coord(), _vertexD.getZ().get_coord());
		double z = Math.min(z1,z2);
		return new Point3D(x,y,z);
	}

}
