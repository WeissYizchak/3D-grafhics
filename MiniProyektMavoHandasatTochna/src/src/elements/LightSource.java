package elements;

import primitives.Color;
import primitives.Vector;
import primitives.Point3D;

/**
 * Interface to Light source
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 * 
 */
    public interface LightSource {

//	protected Point3D _point; // The position of the light
//	public LightSource(Color color) {
//		super(color);
//	}
	/**
	 * Gets the intensity of the light in the given Point
	 * 
	 * @param point
	 *            - Point3D
	 * @return - intensity (Color) of the light considering the distance from the
	 *         light source
	 */
	public Color getIntensity(Point3D point);

	/**
	 * 
	 * @param point
	 * @return - Returns vector from the light source to the point on the geometry
	 */
	public Vector getL(Point3D point);

	/**
	 * 
	 * @param point
	 * @return - Returns the directional vector of the light source 
	 */
	public Vector getD(Point3D point);
	
	
	public double getDistance(Point3D point);
		
    }
