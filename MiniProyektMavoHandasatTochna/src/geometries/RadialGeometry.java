/**
 * 
 */
package geometries;

import primitives.Coordinate;

/**
 * abstract class to represent a radial geometric shapes inherits from Geometry
 *
 */
public abstract class RadialGeometry extends Geometry {

	protected double _radius;

	// ***************** Constructors ********************** //

	/**
	 * constructor
	 * 
	 * @param radius
	 *            (double)
	 */
	public RadialGeometry(double radius) { // DOESN'T WORK -- NEED TO ASK DAN
		super();
		if (Coordinate.ZERO.equals(radius)) // if radius < 0.000001 it's counts 0
			throw new IllegalArgumentException("ERROR: radius cannot be 0");
		_radius = radius;
	}

	/**
	 * copy constructor
	 * 
	 * @param RadialGeometry
	 *            other
	 */
	public RadialGeometry(RadialGeometry other) {
		super(other);
		_radius = other._radius;
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * geter
	 * 
	 * @return the radius of the shape
	 */
	public double getRadius() {
		return _radius;
	}

	// ***************** Administration ******************** //

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RadialGeometry))
			return false;
		RadialGeometry other = (RadialGeometry) obj;
		return super.equals(other) && _radius == other._radius;
	}

	@Override
	public String toString() {
		return super.toString() + "radius: " + _radius;
	}
}
