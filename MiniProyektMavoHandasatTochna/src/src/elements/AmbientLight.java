/**
 * 
 */
package elements;

import primitives.Color;

/**
 * Class to manage the ambient lighting - The calculation of the light intensity
 * (Ka * color), implemented in the constructor
 * 
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 */
public class AmbientLight extends Light {

	private double _Ka; // Sets the intensity of the light

	//***************** Constructors **********************//

	/**
	 * Constructor
	 * 
	 * @param r
	 *            - Red color
	 * @param g
	 *            - Green color
	 * @param b
	 *            - Blue color
	 * @param Ka
	 *            - Intensity of the light
	 */
	public AmbientLight(double r, double g, double b, double Ka) {
		_color = new Color(r, g, b).scale(Ka);
		_Ka = Ka;
	}

	/**
	 * Constructor
	 * 
	 * @param other
	 *            - The color (with r,g,b)
	 * @param Ka
	 *            - intensity of the light
	 */
	public AmbientLight(Color other, double Ka) {
		_color = new Color(other).scale(Ka);
		_Ka = Ka;
	}

	/**
	 * Constructor
	 * 
	 * @param color
	 *            - the color (from java.awt.Color)
	 * @param Ka
	 *            - intensity of the light
	 */
	public AmbientLight(java.awt.Color color, double Ka) {
		_color = new Color(color).scale(Ka);
		_Ka = Ka;
	}

	/**
	 * Copy constructor
	 * 
	 * @param other
	 *            -
	 */
	public AmbientLight(AmbientLight other) {
		_color = new Color(other._color);
		_Ka = other._Ka;
	}

	//***************** Getters/setters ****************************//

	// No getter for color - to get the color use only the getIntensity function

	/**
	 * Getter
	 * 
	 * @return the _Ka
	 */
	public double getKa() {
		return _Ka;
	}
}
