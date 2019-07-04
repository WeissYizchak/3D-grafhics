/**
 * 
 */
package primitives;

/**
 * Class to manage color and convert it to awt.color
 * 
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 */
public class Color {

	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color WHITE = new Color(255, 255, 255);

	final int BRIGHTEST = 255; // More than 250 it's to bright
	final int DARKEST = 0; // Less than 0 it's to dark

	private double _r; // for the red color
	private double _g; // for the green color
	private double _b; // for the blue color

	// ***************** Constructors ********************** //

	/**
	 * default constructor
	 * Initialized the color whit zero = black 
	 */
	public Color() {}
	
	/**
	 * Constructor - gets 3 doubles
	 * 
	 * @param _r
	 *            - the red color
	 * @param _g
	 *            - the green color
	 * @param _b
	 *            - the blue color
	 */
	public Color(double r, double g, double b) {

		this._r = r;
		this._g = g;
		this._b = b;
	}

	/**
	 * Copy constructor
	 * 
	 * @param other
	 */
	public Color(Color other) {

		this(other._r, other._g, other._b);
	}

	/**
	 * Constructor for awt.Color
	 * 
	 * @param color
	 *            - instance of java.awt.Color
	 */
	public Color(java.awt.Color color) {

		this(color.getRed(), color.getGreen(), color.getBlue());
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Converts r,g,b to integers and making sure they are within the valid range
	 * (0-255) by using _convertAndCheck helps function
	 * 
	 * @return - New color from awt.Color
	 */
	public java.awt.Color getColor() {
		return new java.awt.Color(_convertAndCheck(_r), _convertAndCheck(_g), _convertAndCheck(_b));
	}

	/**
	 * Getter
	 * 
	 * @return the _r
	 */
	public double getR() {
		return _r;
	}

	/**
	 * Getter
	 * 
	 * @return the _g
	 */
	public double getG() {
		return _g;
	}

	/**
	 * Getter
	 * 
	 * @return the _b
	 */
	public double getB() {
		return _b;
	}

	/**
	 * Setter
	 * 
	 * @param _r
	 *            the _r to set
	 */
	public void setR(double r) {
		this._r = r;
	}

	/**
	 * Setter
	 * 
	 * @param _g
	 *            the _g to set
	 */
	public void setG(double g) {
		this._g = g;
	}

	/**
	 * Setter
	 * 
	 * @param _b
	 *            the _b to set
	 */
	public void setB(double b) {
		this._b = b;
	}

	// ***************** Administration ******************** //

	/**
	 * Help function to convert double to int and make sure it's within the valid
	 * range (0-255)
	 * 
	 * @param num
	 *            - The number to convert and check
	 * @return - integer within the valid range
	 */
	private int _convertAndCheck(double num) {

		if (num > BRIGHTEST)
			return BRIGHTEST;
		if (num < DARKEST)
			return DARKEST;
		return (int) num;
	}

	// ***************** Operations ******************** //

	/**
	 * Adds color to this color
	 * 
	 * @param color
	 *            - The second operand of adding operation
	 * @return - Returns this color after adding another color
	 */
	public Color add(Color... colors) {
		for (Color color : colors) {
			_r += color._r;
			_g += color._g;
			_b += color._b;
		}
		return this;
	}

	/**
	 * Multiplies the color by scalar
	 * 
	 * @param scale
	 *            - The operand for the multiplication operation
	 * @return - Returns this color after duplicated by the scalar
	 */
	public Color scale(double scale) {
		_r *= scale;
		_g *= scale;
		_b *= scale;
		return this;
	}

	/**
	 * Divides the color with number
	 * 
	 * @param num
	 *            - The operand for the division operation
	 * @return - Returns this color after divided by the number
	 */
	public Color reduce(double num) {
		_r /= num;
		_g /= num;
		_b /= num;
		return this;
	}

}
