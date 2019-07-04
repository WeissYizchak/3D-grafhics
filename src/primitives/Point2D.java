package primitives;

/**
 * class to represents a point in a plane contains 2 coordinates (x,y)
 */
public class Point2D {

	protected Coordinate _x;
	protected Coordinate _y;

	// ***************** Constructors ********************** //

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 */
	public Point2D(double x, double y) {
		_x = new Coordinate(x);
		_y = new Coordinate(y);
	}

	/**
	 * Constructor
	 * 
	 * @param Coordinate
	 *            x
	 * @param Coordinate
	 *            y
	 */
	public Point2D(Coordinate x, Coordinate y) {
		_x = new Coordinate(x);
		_y = new Coordinate(y);
	}

	/**
	 * Copy constructor
	 * 
	 * @param Point2D
	 *            other
	 */
	public Point2D(Point2D other) {
		_x = new Coordinate(other._x);
		_y = new Coordinate(other._y);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * geter
	 * 
	 * @return Coordinate x
	 */
	public Coordinate getX() {
		return _x;
	}

	/**
	 * geter
	 * 
	 * @return Coordinate y
	 */
	public Coordinate getY() {
		return _y;
	}

	// ***************** Administration ******************** //

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point2D))
			return false;
		Point2D other = (Point2D) obj;
		return _x.equals(other._x) && _y.equals(other._y);
	}

	/**
	 * @return the point in format (x,y)
	 */
	@Override
	public String toString() {
		return "(" + _x.get_coord() + "," + _y.get_coord() + ")";
	}

	// ***************** Operations ******************** //

}
