package primitives;

/**
 * Class to manage the properties of matter
 * 
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 */
public class Material {

	private double _Kd;
	private double _Ks;
	private int _nShininess;
	private double _Kr;
	private double _Kt;

	// ***************** Constructors ********************** //

	/**
	 * 
	 * @param _Kd
	 *            -
	 * @param _Ks
	 *            -
	 * @param nShininess
	 *            - Determines the level of shining of the material
	 * @param _Kr
	 *            - The reflection level (equal 1 for a perfect mirror and 0 for
	 *            matt surface)
	 * @param _Kt
	 *            - Transparency level (equal 1 for translucent object and 0 for
	 *            opaque object)
	 */
	public Material(double _Kd, double _Ks, int nShininess, double _Kr, double _Kt) {
		this._Kd = _Kd;
		this._Ks = _Ks;
		this._nShininess = nShininess;
		this._Kr = _Kr;
		this._Kt = _Kt;
	}

	public Material(Material material) {
		this(material._Kd, material._Ks, material._nShininess, material._Kr, material._Kt);
	}
	/**
	 * Getter
	 * 
	 * @return
	 */
	public double getKd() {
		return _Kd;
	}

	/**
	 * Getter
	 * 
	 * @return
	 */
	public double getKs() {
		return _Ks;
	}

	/**
	 * Gets the degree of shining of the material (int)
	 * 
	 * @return Exponent of shininess (int)
	 */
	public int getShininess() {
		return _nShininess;
	}

	/**
	 * Setter
	 * 
	 * @param kd
	 */
	public void setKd(double kd) {
		this._Kd = kd;
	}

	/**
	 * Setter
	 * 
	 * @param ks
	 */
	public void setKs(double ks) {
		this._Ks = ks;
	}

	/**
	 * Setter
	 * 
	 * @param shininess
	 */
	public void setShininess(int shininess) {
		this._nShininess = shininess;
	}

	public double getKr() {
		return _Kr;
	}
	
	public double getKt() {
		return _Kt;
	}
}
