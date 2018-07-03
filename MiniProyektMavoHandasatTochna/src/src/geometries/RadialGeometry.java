/**
 * 
 */
package geometries;

import primitives.Color;
import primitives.Coordinate;

/**
 * abstract class to represent a radial geometric shapes inherits from Geometry
 *
 */
public abstract class RadialGeometry extends Geometry {

	protected double _radius;

	// ***************** Constructors ********************** //

	/**
	 * Constructor
	 * 
	 * @param radius -
	 *            (double)
	 */
	public RadialGeometry(double radius, Color color) { // DOESN'T WORK -- NEED TO ASK DAN
		super(color);
		if (Coordinate.ZERO.equals(radius)) // if radius < 0.000001 it's counts 0
			throw new IllegalArgumentException("ERROR: radius cannot be 0");
		_radius = radius;
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * getter
	 * 
	 * @return the radius of the shape
	 */
	public double getRadius() {
		return _radius;
	}

	// ***************** Administration ******************** //

	@Override
	public String toString() {
		return "radius: " + _radius;
	}
}
