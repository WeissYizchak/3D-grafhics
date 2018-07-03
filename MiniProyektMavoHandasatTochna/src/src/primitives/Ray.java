/**
 * 
 */
package primitives;

/**
 * class to represent a ray (vector from specific point)
 * contain point (for the beginning) and Vector (for the direction)
 */
public class Ray {
	private Point3D _startingPoint;
	private Vector _directionVector;
	
	// ***************** Constructors ********************** // 

	/**
	 * Constructor
	 * @param Point3D startingPoint
	 * @param Vector directionVector
	 */
	public Ray(Point3D startingPoint, Vector directionVector) {
		_startingPoint = new Point3D(startingPoint);
		_directionVector = new Vector(directionVector.normalization());
	}
	
	/**
	 * Copy constructor
	 * @param Ray other
	 */
	public Ray(Ray other) {
		_startingPoint = new Point3D(other._startingPoint);
		_directionVector = new Vector(other._directionVector);
	}
	
	// ***************** Getters/Setters ********************** //

	/**
	 * geter
	 * @return Point3D - the starting point
	 */
	public Point3D getStartingPoint() {
		return _startingPoint;
	}
	
	/**
	 * geter
	 * @return Vector - the direction vector
	 */
	public Vector getDirectionVector() {
		return _directionVector;
	}
	
	// ***************** Administration  ******************** //
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray other = (Ray) obj;
		return _startingPoint.equals(other._startingPoint) && _directionVector.equals(other._directionVector);
	}

	/**
	 * toString of Ray, uses the Point3D toString and Vector toString
	 * @return Returns the details of ray in format: "Ray= Point:(x,y,z) Vector:(x,y,z)
	 */
	@Override
	public String toString() {
		return "Ray= Point:" + _startingPoint.toString() + " " + _directionVector.toString();
	}

}
