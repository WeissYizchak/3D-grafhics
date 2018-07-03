/**
 * 
 */
package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Class to manage a directional lighting extends from Light and implement LightSource
 * 
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 */
public class DirectionalLight extends Light implements LightSource{

	private Vector _direction;

	//***************** Constructors **********************//

	/**
	 * @param _direction
	 */
	public DirectionalLight(Color _color, Vector _direction) {
		super(_color);
		this._direction = _direction;
	}

	//***************** Getters/setters ****************************//

	@Override
	public Color getIntensity(Point3D point) {
		return getIntensity(); // It's the same intensity for any point
	}

	@Override
	public Vector getL(Point3D point) {
		return _direction;
	}

	@Override
	public Vector getD(Point3D point) {
		return _direction;
	}

	@Override
	public double getDistance(Point3D point) {
		return Double.MAX_VALUE;
	}
}
