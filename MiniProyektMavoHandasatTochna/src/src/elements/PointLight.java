/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Class to manage a point lighting extends from Light and implement LightSource
 * 
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 */
public class PointLight extends Light implements LightSource {

	protected Point3D _point; // The position of the light
	protected double _kC, _kL, _kQ;

	// ***************** Constructors ********************** //
	/**
	 * Contractor to build a Point light
	 * 
	 * @param _point
	 *            - Position of the light
	 * @param _kL
	 *            - Coefficient of linear weakening of the light in the distance
	 * @param _kQ
	 *            - Coefficient of exponential weakening of the light at a distance
	 */
	public PointLight(Color _color, Point3D _point, double _kL, double _kQ) {
		super(_color);
		this._point = _point;
		this._kC = 1;
		this._kL = _kL;
		this._kQ = _kQ;
	}

	// ***************** Getters/Setters ********************** //

	@Override
	public Color getIntensity(Point3D point) {
		double distance = _point.distance(point);
		double denominator = _kC + _kL * distance + _kQ * distance * distance;
		return getIntensity().reduce(denominator);
	}

	@Override
	public Vector getL(Point3D point) {
		return point.subtract(_point).normalization();
	}

	@Override
	public Vector getD(Point3D point) {
		return getL(point);
	}

	@Override
	public double getDistance(Point3D point) {
		return point.distance(_point);
	}

}
