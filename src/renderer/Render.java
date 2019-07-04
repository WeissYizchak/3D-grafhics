package renderer;

import java.util.List;
import java.util.Map;

import elements.Camera;
import elements.DirectionalLight;
import elements.Light;
import elements.LightSource;
import geometries.Geometries;
import geometries.Geometry;
import primitives.*;
import scene.Grid3D;
import scene.Scene;

/**
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 */
public class Render {
	final int MAX_CALC_COLOR_LEVEL = 10;
	private Scene _scene;
	private ImageWriter _imageWriter;
	private Grid3D _grid;

	/**
	 * 
	 * Inner class to link each intersection point to the specific shape at that
	 * point
	 *
	 */
	private static class GeometryPoint {
		public Geometry geometry;
		public Point3D point;
	}

	// ***************** Constructors ********************** //

	/**
	 * Constructor
	 * 
	 * @param _scene
	 * @param _imageWriter
	 */
	public Render(Scene scene, ImageWriter imageWriter) {
		this._scene = scene;
		this._imageWriter = imageWriter;
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * Getter
	 * 
	 * @return the _scene
	 */
	public Scene getScene() {
		return _scene;
	}

	/**
	 * Getter
	 * 
	 * @return the _imageWriter
	 */
	public ImageWriter getImageWriter() {
		return _imageWriter;
	}

	/**
	 * Setter
	 * 
	 * @param _scene
	 *            the _scene to set
	 */
	public void setScene(Scene _scene) {
		this._scene = _scene;
	}

	/**
	 * 
	 * @param geometris
	 * @param voxellSizeX
	 * @param voxellSizeY
	 * @param voxellSizeZ
	 */
	public void setGrid(int voxellSizeX, int voxellSizeY, int voxellSizeZ) {
		this._grid = new Grid3D(_scene.getGeometries(), voxellSizeX, voxellSizeY, voxellSizeZ);
	}

	
	// ***************** Administration ******************** //

	// ***************** Operations ******************** //

	/**
	 * Prints the scene to an image
	 */
	public void printImage() {
		_renderImage();
		_imageWriter.writeToimage();
	}

	/**
	 * Prints grid for the background of the image
	 * 
	 * @param interval
	 *            - The interval between line to line
	 */
	public void printGrid(int interval) {

		for (int i = 1; i < _imageWriter.getWidth(); i++) {
			for (int j = 1; j < _imageWriter.getHeight(); j++) {
				if (i % interval == 0 || j % interval == 0) {
					_imageWriter.writePixel(i, j, 255, 255, 255);
				}
			}
		}
		_imageWriter.writeToimage();
	}

	/**
	 * Throws rays through the all pixels and for each ray - if it's got
	 * intersection points with the shapes of the scene - paints the closest point
	 */
	private void _renderImage() {

		int Ny = _imageWriter.getNy();
		int Nx = _imageWriter.getNx();
		int width = _imageWriter.getWidth();
		int height = _imageWriter.getHeight();
		double screenDistance = _scene.getScreenDistance();
		Camera camera = _scene.getCamera();
		Geometries geometries = _scene.getGeometries();
		java.awt.Color background = _scene.getBackground().getColor();
		for (int i = 1; i < Nx; i++) { // for each pixel
			for (int j = 1; j < Ny; j++) {
				// Throw ray through the pixel
				Ray ray = camera.constructRayThroughPixel(Nx, Ny, i, j, screenDistance, width, height);
				// Find the intersection points of the ray with all geometries in the scene
				GeometryPoint closestPoint = _findClosestIntersection(ray);
				_imageWriter.writePixel(i, j,
						/* if the ray have no intersection points - paint the background color *
						 * else - the ray have intersection points - paint it                  */
						closestPoint == null ? background : _calcColor(closestPoint, ray).getColor());
			}
		}
	}

	/**
	 * Wrapper function of sending to the _calcColor function also the
	 * MAX_CALC_COLOR_LEVEL and the k (begins from 1) to double the kr and kt of
	 * each material
	 * 
	 * @param geoPoint
	 * @param inRay
	 * @return - The result from the extended calcColor function
	 */
	private Color _calcColor(GeometryPoint geoPoint, Ray inRay) {
		return _calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);
	}


	/**
	 * Calculate the color for specific point Calculates all sources of light, With
	 * regard to shading Transparency and sensitivity of the material
	 * 
	 * @param geoPoint
	 *            - point at which the color is calculated
	 * @param inRay
	 *            -
	 * @param level
	 *            - how much time to recursive the function
	 * @param k
	 *            - the amount of the transparency of the material (1><0)
	 * @return the specific color of the point
	 */
	private Color _calcColor(GeometryPoint geoPoint, Ray inRay, int level, double k) {
		if (level == 0 || Coordinate.isZero(k)) // Stopping conditions for the recursion
			return new Color(0, 0, 0);

		Color color = _scene.getAmbientLight().getIntensity();
		color.add(geoPoint.geometry.getEmission());

		Vector n = geoPoint.geometry.getNormal(geoPoint.point);
		int nShininess = geoPoint.geometry.getMaterial().getShininess();
		double kd = geoPoint.geometry.getMaterial().getKd();
		double ks = geoPoint.geometry.getMaterial().getKs();
		Vector v = inRay.getDirectionVector();
		
		for (LightSource lightSource : _scene.getLights()) {

			Vector l = lightSource.getL(geoPoint.point);
			if (n.dotProduct(l) * n.dotProduct(v) > 0) {
				double o = _occluded(l, geoPoint.point, geoPoint.geometry, lightSource);
				if (!Coordinate.isZero(o)) {
					Color lightIntensity = new Color(lightSource.getIntensity(geoPoint.point));//.scale(o);
					color.add(calcDiffusive(kd, l, n, lightIntensity),
							calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				}
			}
		}

		// Recursive call for a reflected ray
		Ray reflectedRay = _constructReflectedRay(n, geoPoint.point, inRay);
		GeometryPoint reflectedPoint = _findClosestIntersection(reflectedRay);
		if (reflectedPoint != null) {
			double Kr = geoPoint.geometry.getMaterial().getKr();
			Color reflectedLight = _calcColor(reflectedPoint, reflectedRay, level - 1, k * Kr).scale(Kr);
			color.add(reflectedLight);
		}
		// Recursive call for a refracted ray
		Ray refractedRay = _constructRefractedRay(n, geoPoint.point, inRay);
		GeometryPoint refractedPoint = _findClosestIntersection(refractedRay);
		if (refractedPoint != null) {
			double Kt = geoPoint.geometry.getMaterial().getKt();
			Color refractedLight = _calcColor(refractedPoint, refractedRay, level - 1, k * Kt).scale(Kt);
			color.add(refractedLight);
		}
		return new Color(color);
	}

	/**
	 * Calculating the diffuse light
	 * 
	 * @param kd
	 *            - degree of light return of the material (double)
	 * @param l
	 *            - the ray of the light
	 * @param n
	 *            - normal ray from the point of object
	 * @param lightIntensity
	 *            - the intensity of the light as it reaches the object (color)
	 * @return intensity of diffusive color (color)
	 */
	private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
		return new Color(lightIntensity).scale(Math.abs(l.dotProduct(n)) * kd);
	}

	/**
	 * Calculating the specular light
	 * 
	 * @param ks
	 *            - degree of light return shining of the material (double)
	 * @param l
	 *            - the ray of the light
	 * @param n
	 *            - normal ray from the point of object
	 * @param v
	 *            - A ray on the observer's side (camera, etc.)
	 * @param nShininess
	 *            - degree of light shining of the material (int)
	 * @param lightIntensity
	 *            - the intensity of the light as it reaches the object (color)
	 * @return intensity of specular color (color)
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r = l.subtract(n.scaleMult(2 * (l.dotProduct(n))));
		if (v.dotProduct(r) > 0) { // To this angle there is no specular
			return new Color(0, 0, 0);
		}
		return new Color(lightIntensity).scale(Math.pow(r.dotProduct(v.scaleMult(-1)), nShininess) * ks);
	}

	/**
	 * Checks whether the point is shaded by an object
	 * 
	 * Note: The function also deals with light sources within spaces
	 * 
	 * @param l
	 *            - Vector from the light source to the point on the geometry
	 * @param point
	 *            - Specific point on geometry
	 * @param geometry
	 *            - the type geometry of the point
	 * @param lightSource
	 *            - the source which shines of the point
	 * @return number (double) between 1 and 0 how much the point is shaded
	 */
	private double _occluded(Vector l, Point3D point, Geometry geometry, LightSource lightSource) {
		Vector lightDirection = l.scaleMult(-1);

		Vector normal = geometry.getNormal(point);
//		Vector epsVector = normal.scaleMult((normal.dotProduct(lightDirection) > 0) ? 2 : -2);
//		Point3D GeomtryPoint = point.addVector(epsVector);
		Point3D GeomtryPoint = _addEps(normal, point, lightDirection);
		
		Ray lightRay = new Ray(GeomtryPoint, lightDirection);
		
		//Map<Geometry, List<Point3D>> intersctionPoints = _scene.getGeometries().findIntersections(lightRay);
		Map<Geometry, List<Point3D>> intersctionPoints = _grid.findIntersection(lightRay);

		double shadowK = 1;
		double distanceFromlightSource = lightSource.getDistance(GeomtryPoint);
		double distanceToGeometry;
		for (Map.Entry<Geometry, List<Point3D>> entry : intersctionPoints.entrySet()) {
			for (Point3D p : entry.getValue()) {
				distanceToGeometry = point.distance(p);
				if (distanceToGeometry < distanceFromlightSource) { // check if it shaded or not
					shadowK *= entry.getKey().getMaterial().getKt();
				}
			}
		}
		return shadowK;
	}

	/**
	 * Finds the closest point to the camera from all intersection points
	 * 
	 * @param intersectionPoints
	 *            (Map<Geometry, List<Point3D>>)
	 * @return - The closest point to the camera
	 * 
	 */
	private GeometryPoint _getClosestPoint(Map<Geometry, List<Point3D>> intersectionPoints) {

		if (intersectionPoints == null) {
			throw new IllegalArgumentException("The list of points cannot be null");
		}
		// initialization
		double currentDistance, minDistance = Double.MAX_VALUE;
		Point3D P0 = new Point3D(_scene.getCamera().getP0());
		GeometryPoint closestPoint = new GeometryPoint();
		// for each intersection point if it's closes from the previous one - keep it
		// and compare to the next point
		for (Map.Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet()) {
			for (Point3D p : entry.getValue()) {
				currentDistance = P0.distance(p);
				if (currentDistance < minDistance) {
					closestPoint.geometry = (Geometry) entry.getKey();
					closestPoint.point = new Point3D(p);
					minDistance = currentDistance;
				}
			}
		}
		return closestPoint;

	}

	/**
	 * 
	 * @param ray
	 * @return - The closest intersection point
	 */
	private GeometryPoint _findClosestIntersection(Ray ray) {
		//Map<Geometry, List<Point3D>> intersectionPoints = _scene.getGeometries().findIntersections(reflectedRay);
		Map<Geometry, List<Point3D>> intersectionPoints = _grid.findIntersection(ray);
		return intersectionPoints.isEmpty() ? null : _getClosestPoint(intersectionPoints);
	}

	/**
	 * Adds epsilon to the point to shift it from the object
	 * 
	 * @param n
	 *            - The normal vector to object from the specific point
	 * @param p
	 *            - The point on the object
	 * @param r
	 *            - The ray from the camera
	 * @return - Point shifted epsilon from the object
	 */
	private Point3D _addEps(Vector n, Point3D p, Ray r) {
		Vector toGeometry = r.getDirectionVector();
		Vector epsVector = n.scaleMult(n.dotProduct(toGeometry) > 0 ? -2 : 2);
		Point3D point = p.addVector(epsVector);
		return point;
	}

	private Point3D _addEps(Vector n, Point3D p, Vector direction) {
		Vector epsVector = n.scaleMult(n.dotProduct(direction) > 0 ? 0.5 : -0.5);
		return p.addVector(epsVector);
	}

	/**
	 * 
	 * @param n
	 *            - The normal vector to object from the specific point
	 * @param p
	 *            - The point on the object
	 * @param r
	 *            - The ray from the camera
	 * @return - The reflected ray from the point
	 */
	private Ray _constructReflectedRay(Vector n, Point3D p, Ray r) {
		Vector toGeometry = r.getDirectionVector();
		Vector reflected = toGeometry.subtract(n.scaleMult(2 * (toGeometry.dotProduct(n))));
		Point3D point = _addEps(n, p, reflected);
		return new Ray(point, reflected);

	}

	/**
	 * 
	 * @param n
	 *            - The normal vector to object from the specific point
	 * @param p
	 *            - The point on the object
	 * @param r
	 *            - The ray from the camera
	 * @return - The refracted ray from the point
	 */
	private Ray _constructRefractedRay(Vector n, Point3D p, Ray r) {
		Point3D point = _addEps(n, p, r.getDirectionVector());
		return new Ray(point, r.getDirectionVector());
	}
}
