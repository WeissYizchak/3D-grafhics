/**
 * 
 */
package geometries;

import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * class to represent a triangle inherit from plane have 3 points to 3 vertexes
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
	public Triangle(Point3D vertexA, Point3D vertexB, Point3D vertexC) {
		super(vertexA, vertexB, vertexC); // operates the father constructor to build a normal
		_vertexA = new Point3D(vertexA);
		_vertexB = new Point3D(vertexB);
		_vertexC = new Point3D(vertexC);
	}

	/**
	 * Copy constructor
	 * 
	 * @param Triangle
	 *            other
	 */
	public Triangle(Triangle other) {
		super(other);
		_vertexA = new Point3D(other._vertexA);
		_vertexB = new Point3D(other._vertexB);
		_vertexC = new Point3D(other._vertexC);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * geter
	 * 
	 * @return the first vertex (A)
	 */
	public Point3D getVertexA() {
		return _vertexA;
	}

	/**
	 * geter
	 * 
	 * @return the second vertex (B)
	 */
	public Point3D getVertexB() {
		return _vertexB;
	}

	/**
	 * geter
	 * 
	 * @return the third vertex (C)
	 */
	public Point3D getVertexC() {
		return _vertexC;
	}

	// ***************** Administration ******************** //

	// ***************** Operations ******************** //

	@Override
	public List<Point3D> findIntersections(Ray ray) {
		List<Point3D> list = super.findIntersections(ray);
		if (!list.isEmpty()) {
			Point3D P = list.get(0);
			Vector V = P.subtract(ray.getStartingPoint());

			Vector V1 = _vertexA.subtract(ray.getStartingPoint());
			Vector V2 = _vertexB.subtract(ray.getStartingPoint());
			Vector V3 = _vertexC.subtract(ray.getStartingPoint());

			Vector N1 = V1.crossProduct(V2).normalization();
			Vector N2 = V2.crossProduct(V3).normalization();
			Vector N3 = V3.crossProduct(V1).normalization();

			if (!(V.dotProduct(N1) > 0 && V.dotProduct(N2) > 0 && V.dotProduct(N3) > 0
					|| V.dotProduct(N1) < 0 && V.dotProduct(N2) < 0 && V.dotProduct(N3) < 0))
				list.remove(0);
		}
		return list;
	}
}
