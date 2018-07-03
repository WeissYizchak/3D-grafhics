package primitives;

/**
 * class to represents a point in a space, inherit from Point2D
 *
 */
public class Point3D extends Point2D {

	protected Coordinate _z;

	public static final Point3D ZERO = new Point3D(Coordinate.ZERO, Coordinate.ZERO, Coordinate.ZERO); // to represent a
																										// zero point

	// ***************** Constructors ********************** //
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point3D(double x, double y, double z) {
		super(x, y);
		_z = new Coordinate(z);
	}
	/**
	 * Constructor
	 * 
	 * @param Coordinate
	 *            x
	 * @param Coordinate
	 *            y
	 * @param Coordinate
	 *            z
	 */
	public Point3D(Coordinate x, Coordinate y, Coordinate z) {
		super(x, y);
		_z = z;
	}

	/**
	 * Copy constructor
	 * 
	 * @param Point3D
	 *            other
	 */
	public Point3D(Point3D other) {
		super(other);
		_z = other._z;

	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Getter
	 * 
	 * @return Coordinate z
	 */
	public Coordinate getZ() {
		return _z;
	}

	// ***************** Administration ******************** //

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Point3D))
			return false;
		Point3D other = (Point3D) obj;
		return super.equals(other) && _z.equals(other._z);
	}

	/**
	 * @return the point in format (x,y,z) by using some of "super" toString
	 */
	@Override
	public String toString() {
		return super.toString().substring(0, super.toString().length() - 1) + "," + _z.get_coord() + ")";
	}

	// ***************** Operations ******************** //

	/**
	 * Subtracts one point (argument) form another (this)
	 * 
	 * @param point
	 *            - Second operand of subtract operation (argument point)
	 * @return Returns vector from argument point to this point
	 */
	public Vector subtract(Point3D point) {
		// subtracts the coordinates to a new point
		Point3D _vectorHead = new Point3D(this._x.subtract(point._x), this._y.subtract(point._y),
				this.getZ().subtract(point.getZ()));
		// the new point it's the vector's head
		return new Vector(_vectorHead);
	}

	/**
	 * Adds vector to the point
	 * 
	 * @param Vector
	 *            - to add to this point
	 * @return Returns a new point
	 */
	public Point3D addVector(Vector vec) {
		return new Point3D(this._x.add(vec.getDirection()._x), this._y.add(vec.getDirection()._y),
				this.getZ().add(vec.getDirection().getZ()));
	}

	/**
	 * Calculates the formula: sqrt( (x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2 ) to find the
	 * distance between this point (x1,y1,z1) and another point
	 * 
	 * @param Point3D
	 *            - the second point (x2,y2,z2)
	 * @return Returns the distance (double) between this point and argument point
	 */
	public double distance(Point3D point) {
		// subtracts between each 2 coordinates to get (x1-x2)
		Coordinate x = this._x.subtract(point._x);
		Coordinate y = this._y.subtract(point._y);
		Coordinate z = this.getZ().subtract(point.getZ());
		// multiply each coordinate to get (x1-x2)^2
		x = x.multiply(x);
		y = y.multiply(y);
		z = z.multiply(z);
		// returns the final result: distance = sqrt( (x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2
		// )
		return Math.sqrt((x.add(y).add(z)).get_coord());
	}
}
