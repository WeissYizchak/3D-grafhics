/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * 
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 */
public class SpotLight extends PointLight {

	private Vector _direction;

	// ***************** Constructors ********************** //

	/**
	 * Constructor of a spot light
	 * 
	 * @param _point
	 *            - Position of the light (Point3D)
	 * @param _kL
	 *            - Coefficient of linear weakening of the light in the distance
	 *            (double)
	 * @param _kQ
	 *            - Coefficient of exponential weakening of the light at a distance
	 *            (double)
	 * @param _direction
	 *            - direction of lighting (Normalized Vector)
	 */
	public SpotLight(Color _color, Point3D _point, double _kL, double _kQ, Vector _direction) {
		super(_color, _point, _kL, _kQ);
		this._direction = _direction.normalization();
	}

	// ***************** Getters/Setters ********************** //

	@Override
	public Color getIntensity(Point3D point) {
		// (I0*der*L)/(kc + cl*dis + kq*dis^2)
		double angle = getD(point).dotProduct(getL(point));
		return angle <= 0 ? new Color() : super.getIntensity(point).scale(angle);
	}

	@Override
	public Vector getD(Point3D point) {
		return _direction;
	}
}
