/**
 * 
 */
package scene;

import java.util.ArrayList;
import java.util.List;

import elements.*;
import geometries.*;
import primitives.*;

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
	private AmbientLight _ambientLight;
	private Geometries _geometries;
	private Camera _camera;
	private double _screenDistance;
	private List<LightSource> _lights;

	// ***************** Constructors ********************** //
	/**
	 * Constructor - initializes the fields with default values
	 * 
	 * @param scnenName
	 *            - the name of the scene (String)
	 */
	public Scene(String scnenName) {

		_scnenName = scnenName;
		_background = null;
		_ambientLight = null;
		_camera = null;
		_screenDistance = 0.0;
		_geometries = new Geometries(); // Initialize empty list
		_lights = new ArrayList();
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Getter
	 * 
	 * @return the _background
	 */
	public Color getBackground() {
		return _background;
	}

	/**
	 * Getter
	 * 
	 * @return the _shapesOfScene
	 */
	public Geometries getGeometries() {
		return _geometries;
	}

	/**
	 * Getter
	 * 
	 * @return the _ambientLight
	 */
	public AmbientLight getAmbientLight() {
		return _ambientLight;
	}

	/**
	 * Getter
	 * 
	 * @return the _camera
	 */
	public Camera getCamera() {
		return _camera;
	}

	/**
	 * Getter
	 * 
	 * @return the _screenDistance
	 */
	public double getScreenDistance() {
		return _screenDistance;
	}
	
	/**
	 * gets the List of the Lights in the scene
	 * @return array list of Object inherited from lightSource
	 */
	public List<LightSource> getLights(){
		return _lights;
	}

	/**
	 * Setter for background color
	 * 
	 * @param _background
	 *            - Color instance for the _background color
	 */
	public void setBackground(Color background) {
		this._background = new Color(background);
	}

	/**
	 * Setter for background color
	 * 
	 * @param r
	 *            - Red color
	 * @param g
	 *            - Green color
	 * @param b
	 *            - Blue color
	 */
	public void setBackground(double r, double g, double b) {
		this._background = new Color(r, g, b);
	}

	/**
	 * Setter
	 * 
	 * @param _ambientLight
	 *            the _ambientLight to set
	 */
	public void setAmbientLight(AmbientLight ambientLight) {
		this._ambientLight = new AmbientLight(ambientLight);
	}

	/**
	 * Setter
	 * 
	 * @param color
	 * @param Ka
	 */
	public void setAmbientLight(Color color, double Ka) {
		this._ambientLight = new AmbientLight(color, Ka);
	}

	/**
	 * Setter
	 * 
	 * @param _camera
	 *            the _camera to set
	 */
	public void setCamera(Camera _camera) {
		this._camera = _camera;
	}

	/**
	 * Setter
	 * 
	 * @param P0
	 * @param vUp
	 * @param vTo
	 */
	public void setCamera(Point3D P0, Vector vUp, Vector vTo) {
		_camera = new Camera(P0, vUp, vTo);
	}

	/**
	 * Setter
	 * 
	 * @param _screenDistance
	 *            the _screenDistance to set
	 */
	public void setScreenDistance(double screenDistance) {
		this._screenDistance = screenDistance;
	}

	// ***************** Administration ******************** //

	// ***************** Operations ******************** //

	/**
	 * Adds geometric shape to the scene
	 * 
	 * @param g
	 *            - The geometric shape to add
	 */
	public void addGeometry(Geometry ...g) {
		for(Geometry geometry: g) {
			_geometries.add(geometry);
		}
	}

	/**
	 * adds a light source to the scene
	 * 
	 * @param lightSource
	 *            -Object inherited from lightSource to add
	 */
	public void addLight(LightSource lightSource) {
		_lights.add(lightSource);
	}

}
