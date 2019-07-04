/**
 * 
 */
package elements;

import primitives.Color;

/**
 * Abstract class to represent type of light
 * 
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 * 
 */
public abstract class Light {

	protected Color _color;

	/**
	 * Constructor
	 * 
	 * @param _color
	 */
	public Light(Color _color) {
		this._color = _color;
	}
	
	/**
	 * Default constructor
	 * 
	 * @param _color
	 */
	public Light() {
		this._color = Color.BLACK;
	}

	/**
	 * The light intensity - for each type of light
	 * 
	 * @return The color after calculating the light intensity
	 */
	public Color getIntensity() {
		return new Color(_color);
	}
}
