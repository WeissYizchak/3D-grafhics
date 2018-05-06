/**
 * 
 */
package scene;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import elements.*;
import geometries.*;

/**
 * 
 * Represents a three-dimensional scene
 * 
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 */
public class Scene {

	private String _scnenName;
	private Color _background;
	private List<Geometry> _shapesOfScene;
	private Camera _camera;
	private double _screenDistance;

	// ***************** Constructors ********************** //

	/**
	 * Constructor
	 * 
	 * @param _scnenName
	 *            - the name of the scene (String)
	 * @param _background
	 *            - the background color (Color)
	 * @param _camera
	 *            - the viewer's point of view (Camera)
	 * @param _screenDistance
	 *            - the distance from the camera to screen view (double)
	 */
	public Scene(String scnenName, Color background, Camera camera, double screenDistance) {

		_scnenName = scnenName;
		_background = background;
		_camera = new Camera(camera);
		_screenDistance = screenDistance;
		_shapesOfScene = new ArrayList<Geometry>(); // Initialize empty list
	}

	/**
	 * Copy constructor !!!!! I'M NOT SURE IT'S NEEDED !!!!!
	 * 
	 * @param other
	 */
	public Scene(Scene other) {

		_scnenName = other._scnenName;
		_background = other._background;
		_camera =new Camera(other._camera);
		_screenDistance = other._screenDistance;
		for (Geometry g : other._shapesOfScene) { // Deep copy
			_shapesOfScene.add(g);
		}
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Geter
	 * 
	 * @return the _background
	 */
	public Color getBackground() {
		return _background;
	}

	/**
	 * Geter
	 * 
	 * @return the _camera
	 */
	public Camera getCamera() {
		return _camera;
	}

	/**
	 * Geter
	 * 
	 * @return the _screenDistance
	 */
	public double getScreenDistance() {
		return _screenDistance;
	}

	/**
	 * Seter
	 * 
	 * @param _background
	 *            the _background to set
	 */
	public void setBackground(Color _background) {
		this._background = _background;
	}

	/**
	 * Seter
	 * 
	 * @param _camera
	 *            the _camera to set
	 */
	public void setCamera(Camera _camera) {
		this._camera = _camera;
	}

	/**
	 * Seter
	 * 
	 * @param _screenDistance
	 *            the _screenDistance to set
	 */
	public void setScreenDistance(double _screenDistance) {
		this._screenDistance = _screenDistance;
	}
	
	// ***************** Administration ******************** //

		// ***************** Operations ******************** //

	/**
	 * Adds geometric shape to the scene
	 * @param g - The geometric shape to add
	 */
	public void addGeometry(Geometry g) {
		_shapesOfScene.add(g);
	}

}
