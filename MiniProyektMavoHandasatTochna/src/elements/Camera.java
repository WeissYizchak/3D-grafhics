package elements;

import primitives.*;

/**
 * class to represent a camera
 * 
 * @param P0
 *            - Point3D: Position of center of the camera
 * @param vUp
 *            - Vector: Direction vector up/down
 * @param vTo
 *            - Vector: Direction vector to the middle of the screen
 * @param vRight
 *            - Vector: Direction vector for right/left (vRight = vTo X vUp)
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 *
 */
public class Camera {

	private Point3D _P0;
	private Vector _vUp;
	private Vector _vTo;
	private Vector _vRight;

	// ***************** Constructors ********************** //

	/**
	 * constructor of camera
	 * 
	 * @param P0
	 *            - Point3D: Position of center of the camera
	 * @param vUp
	 *            - Vector: Direction vector up
	 * @param vTo
	 *            - Vector: Direction vector to the middle of the screen
	 * @exception if
	 *                the vectors are not vertical each other
	 */
	public Camera(Point3D P0, Vector vUp, Vector vTo) {

		if (vUp.dotProduct(vTo) != 0) { // vertical check
			throw new IllegalArgumentException("ERROR: the vectors are not vertical");
		}

		_P0 = new Point3D(P0);
		_vTo = new Vector(vTo.normalization());
		_vUp = new Vector(vUp.normalization());
		_vRight = vTo.crossProduct(vUp).normalization(); // _vRight is vertical to "Up" and "To" vectors
	}

	/**
	 * copy constructor of camera
	 * 
	 * @param other
	 */
	public Camera(Camera other) {

		_P0 = new Point3D(other._P0);
		_vTo = new Vector(other._vTo);
		_vUp = new Vector(other._vUp);
		_vRight = new Vector(other._vRight);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * 
	 * @return the vector Vright
	 */
	public Vector getVRight() {
		return _vRight;
	}

	/**
	 * 
	 * @return the vector Vup
	 */
	public Vector getVUp() {
		return _vUp;
	}

	/**
	 * 
	 * @return the vector Vto
	 */
	public Vector getVTo() {
		return _vTo;
	}

	/**
	 * 
	 * @return point3D (Position the center of the camera)
	 */
	public Point3D getPO() {
		return _P0;
	}

	// ***************** Operations ******************** //

	/**
	 * construct ray through pixel
	 * 
	 * @param Nx
	 *            - number of pixels in the screen width
	 * @param Ny
	 *            - number of pixels in the screen height
	 * @param i
	 *            - "x" rate of the pixel
	 * @param j
	 *            - "y" rate of the pixel
	 * @param screenDistance
	 *            - distance from the camera to the screen
	 * @param screenWidth
	 * @param screenHeight
	 * @return ray from camera to the middle of the given pixel
	 */
	public Ray constructRayThroughPixel(int Nx, int Ny, int i, int j, double screenDistance, double screenWidth,
			double screenHeight) {

		// Pc is the point in the middle of the screen (Pc = P0 + distance*Vto)
		// Pij is the point on the middle of the given pixel
		Point3D Pc = new Point3D(_P0.addVector(_vTo.scaleMult(screenDistance)));
		double pixelWidth = screenWidth / Nx; // width of one pixel
		double pixelHeight = screenHeight / Ny; // height of one pixel
		double PcX = ((double) Nx + 1) / 2; // "x" rate of Pc point
		double PcY = ((double) Ny + 1) / 2; // "y" rate of Pc point
		double dx = i - PcX; // the distance between Pij and Pc on the X line
		double dy = j - PcY; // the distance between Pij and Pc on the Y line
		Point3D Pij = Pc; // to find Pij point - start moving from Pc point

		if (dx != 0) // move right/left
			Pij = Pij.addVector(_vRight.scaleMult(dx * pixelWidth));

		if (dy != 0) // move up/down
			Pij = Pij.addVector(_vUp.scaleMult(-dy * pixelHeight));

		return new Ray(_P0, Pij.subtract(_P0).normalization());
	}
}
