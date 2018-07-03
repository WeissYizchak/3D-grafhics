/**
 * 
 */
package primitives;

/**
 * class to represents a vector (direction) contain Point3D
 */
public class Vector {

	private Point3D _direction;

	// ***************** Constructors ********************** //

	/**
	 * Constructor - gets 3 doubles and initializes the point by using the set
	 * function to check it's not a zero point
	 * 
	 * @param x
	 *            (double)
	 * @param y
	 *            (double)
	 * @param z
	 *            (double)
	 */
	public Vector(double x, double y, double z) {
		setDirection(new Point3D(new Coordinate(x), new Coordinate(y), new Coordinate(z)));
	}

	/**
	 * Constructor - gets 3 Coordinate and initializes the point by using the set
	 * function to check it's not a zero point
	 * 
	 * @param Coordinate
	 *            x
	 * @param Coordinate
	 *            y
	 * @param Coordinate
	 *            z
	 */
	public Vector(Coordinate x, Coordinate y, Coordinate z) {
		setDirection(new Point3D(x, y, z));
	}

	/**
	 * Constructor - gets other point and initializes the point by using the set
	 * function to check it's not a zero point
	 * 
	 * @param Point3D
	 *            other
	 */
	public Vector(Point3D direction) {
		setDirection(new Point3D(direction));
	}

	/**
	 * Copy Constructor
	 * 
	 * @param Vector
	 *            other
	 */
	public Vector(Vector other) {
		setDirection(new Point3D(other._direction));
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Private seter to checks if the vector isn't zero vector (to the use of the
	 * constructors)
	 * 
	 * @param Point3D
	 *            point
	 * @exception Exception
	 *                will be thrown if the direction point it's a zero point
	 */
	private void setDirection(Point3D point) {
		if (point.equals(Point3D.ZERO)) {
			throw new IllegalArgumentException("ERROR: cannot be a zero vector");
		}
		// there no need to create a new point because the constructors already sent a
		// new point
		_direction = point;
	}

	/**
	 * geter
	 * 
	 * @return Returns the direction point
	 */
	public Point3D getDirection() {
		return _direction;
	}

	// ***************** Administration ******************** //

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		Vector other = (Vector) obj;
		return _direction.equals(other._direction);
	}

	/**
	 * @return Returns the direction point in format "Vector:(x,y,z)" by using the
	 *         point3D toString
	 */
	@Override
	public String toString() {
		return "Vector:" + _direction.toString();
	}

	// ***************** Operations ******************** //
	/**
	 * Adds two vectors
	 * 
	 * @param Vector
	 *            - the second operand of the adding operation
	 * @return Returns the addition vector between this vector and argument vector
	 * 
	 */
	public Vector add(Vector vec) {
		return new Vector(new Point3D(_direction.addVector(vec)));
	}

	/**
	 * Subtracts two vectors
	 * 
	 * @param Vector
	 *            - the second operand of the subtraction operation
	 * @return Returns the subtraction vector between this vector and argument
	 *         vector
	 * 
	 */
	public Vector subtract(Vector vec) { // Multiplier the second vector by -1 scalar and then uses the addVector
											// function
		return new Vector(new Point3D(_direction.addVector(vec.scaleMult(-1))));
	}

	/**
	 * Scalar multiplication
	 * 
	 * @param scale
	 *            (double) - the operand in the multiplication operation
	 * @return Returns a new vector after doubling by scalar
	 * @exception if
	 *                the scalar is 0 - Exception will be thrown by vector
	 *                constructor
	 */
	public Vector scaleMult(double scale) {
		return new Vector(
				new Point3D(_direction._x.scale(scale), _direction._y.scale(scale), _direction._z.scale(scale)));
	}

	/**
	 * Dot-product calculation (x1*x2 + y1*y2 + z1*z2)
	 * 
	 * @param Vector
	 *            - the second operand (x2,y2,z2) of the dot-product operation
	 * @return Returns the dot-product calculation (double) between this vector
	 *         (x1,y1,z1) and argument vector
	 */
	public double dotProduct(Vector vec) {
		Coordinate newX = this._direction._x.multiply(vec._direction._x);
		Coordinate newY = this._direction._y.multiply(vec._direction._y);
		Coordinate newZ = this._direction._z.multiply(vec._direction._z);
		return (newX.add(newY).add(newZ)).get_coord();
	}

	/**
	 * Cross-product calculation (u2*v3 - u3*v2, u3*v1 - u1*v3, u1*v2 - u2*v1)
	 * 
	 * @param Vector
	 *            - the second operand (v2,v2,v2) of the cross-product operation
	 * @return Returns the cross-product calculation (new Vector) between this
	 *         vector (u1,u1,u1) and argument vector
	 */
	public Vector crossProduct(Vector vec) {
		// x = u2*v3 - u3*v2
		Coordinate x = (_direction._y.multiply(vec._direction.getZ()))
				.subtract(_direction.getZ().multiply(vec._direction._y));
		// y = u3*v1 - u1*v3
		Coordinate y = (_direction.getZ().multiply(vec._direction._x))
				.subtract(_direction._x.multiply(vec._direction.getZ()));
		// z = u1*v2 - u2*v1
		Coordinate z = (_direction._x.multiply(vec._direction._y)).subtract(_direction._y.multiply(vec._direction._x));
		// returns the new vector
		return new Vector(new Point3D(x, y, z));
	}

	/**
	 * the vector's length
	 * 
	 * @return Returns the length (double)
	 */
	public double length() {
		// uses the formula: |A| = sqrt(A*A);
		return Math.sqrt(this.dotProduct(this));
	}

	/**
	 * normalization operation - normalizes this vector itself
	 * 
	 * @return Returns this vector after normalization
	 */
	public Vector normalization() {
		// If length == 1 - The vector is already normalized
		if (length() != 1) {
			// the normalization: (vector)*(1/length)
			_direction = (this.scaleMult(1 / length()))._direction;
		}
		return this;
	}
}
