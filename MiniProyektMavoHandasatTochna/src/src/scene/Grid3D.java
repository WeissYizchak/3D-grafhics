package scene;

import primitives.Point3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import geometries.*;
import primitives.*;

/**
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 * 
 *         Class to represent a grid to insert the scene into it the grid
 *         contains "voxells" to insert each geometry of the scene to the
 *         appropriate voxell. that way the ray find intersection only with the
 *         geometries that in the voxell the ray inside it
 */
public class Grid3D {

	/**
	 * Inner class to represent a 3D cell in the grid uses only in the grid class
	 * Contain 3 indexes (Integers)
	 */
	private class VoxellIndex {
		private int _x, _y, _z;

		// ***************** Constructors ********************** //

		/**
		 * Constructor - get 3 indexes
		 * 
		 * @param x
		 * @param y
		 * @param z
		 */
		private VoxellIndex(int x, int y, int z) {
			this._x = x;
			this._y = y;
			this._z = z;
		}

		// ***************** Administration ******************** //

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + _x;
			result = prime * result + _y;
			result = prime * result + _z;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			VoxellIndex other = (VoxellIndex) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (_x != other._x)
				return false;
			if (_y != other._y)
				return false;
			if (_z != other._z)
				return false;
			return true;
		}

		private Grid3D getOuterType() {
			return Grid3D.this;
		}

	}

	// ***************** Fields of the grid ******************** //

	// The two points in the extreme corners of the grid
	private Point3D _gridMinPoint;
	private Point3D _gridMaxPoint;

	// The two Voxells in the extreme corners of the grid
	private VoxellIndex _voxellIndexMax;
	private VoxellIndex _voxellIndexMin;

	// Size of the grid
	private double _gridSizeX;
	private double _gridSizeY;
	private double _gridSizeZ;

	// Size of the voxell
	private double _voxellSizeX;
	private double _voxellSizeY;
	private double _voxellSizeZ;

	// Map to connect each voxel with the geometries that inside it
	private Map<VoxellIndex, Geometries> mapGeometries;

	// ***************** Constructor ******************** //

	/**
	 * Constructor
	 * 
	 * @param gridVoxellSizeX
	 *            - the number of Voxell in the grid on the X axis
	 * @param gridVoxellSizeY
	 *            - the number of Voxell in the grid on the Y axis
	 * @param gridVoxellSizeZ
	 *            - the number of Voxell in the grid on the Z axis
	 */
	public Grid3D(Geometries geometries, int gridVoxellSizeX, int gridVoxellSizeY, int gridVoxellSizeZ) {

		// Builds the grid as small as possible to contain all the geometries in the
		// scene
		_gridMaxPoint = geometries.getMax();
		_gridMinPoint = geometries.getMin();

		// Calculates the size of the grid
		this._gridSizeX = _gridMaxPoint.getX().get_coord() - _gridMinPoint.getX().get_coord();
		this._gridSizeY = _gridMaxPoint.getY().get_coord() - _gridMinPoint.getY().get_coord();
		this._gridSizeZ = _gridMaxPoint.getZ().get_coord() - _gridMinPoint.getZ().get_coord();

		// Calculates the size of the voxell
		_voxellSizeX = _gridSizeX / gridVoxellSizeX;
		_voxellSizeY = _gridSizeY / gridVoxellSizeY;
		_voxellSizeZ = _gridSizeZ / gridVoxellSizeZ;

		// Initialized the min/max voxells of the grid
		_voxellIndexMax = _convertPointToVoxell(_gridMaxPoint);
		_voxellIndexMin = _convertPointToVoxell(_gridMinPoint);

		// Initialized the map with HashMap for quick retrieval
		mapGeometries = new HashMap<VoxellIndex, Geometries>();
		// Insert all the geometry in scene into the voxells
		insertToVoxell(geometries.getGeometries());
	}

	/**
	 * Runs on all the geometry in scene and insert each geometry to the appropriate
	 * voxell
	 * 
	 * @param geometries
	 *            - The geometries in scene
	 */
	public void insertToVoxell(List<Geometry> geometries) {
		for (Geometry geometry : geometries) {
			// Produces two Voxells in both corners of the cube containing the shape
			VoxellIndex maxVox = _convertPointToVoxell(geometry.getMax());
			VoxellIndex minVox = _convertPointToVoxell(geometry.getMin());
			// Runs on all the Voxells between maxVox and MinVox and inserts the geometry
			// inside them
			for (int x = minVox._x; x <= maxVox._x; x++) {
				for (int y = minVox._y; y <= maxVox._y; y++) {
					for (int z = minVox._z; z <= maxVox._z; z++) {
						VoxellIndex currentVox = new VoxellIndex(x, y, z);
						if (mapGeometries.containsKey(currentVox) == false) {
							Geometries voxellGeometries = new Geometries();
							voxellGeometries.add(geometry);
							mapGeometries.put(currentVox, voxellGeometries);
						} else {
							mapGeometries.get(currentVox).add(geometry);
						}
					}
				}
			}
		}
	}

	/**
	 * The 3DDDA algorithm to run on the voxells by the way of the ray and find
	 * intersection points only with the geometries in the ray's way
	 * 
	 * @param ray
	 *            - The ray to run on the voxells, the sent ray always in the grid
	 * @return - Returns the closest intersection point for each ray or null if
	 *         there no geometries on the ray's way
	 */
	public Map<Geometry, List<Point3D>> gridFindIntersection(Ray ray) {

		Vector rayDirection = ray.getDirectionVector();
		double rayDirectionX = rayDirection.getDirection().getX().get_coord();
		double rayDirectionY = rayDirection.getDirection().getY().get_coord();
		double rayDirectionZ = rayDirection.getDirection().getZ().get_coord();

		// P0 of the ray it always in the grid
		Point3D rayOrigin = ray.getStartingPoint();

		// vector from the min corner of the grid to the P0 of the ray to get the
		// distance between them for each axis
		Vector rayOrigGrid = rayOrigin.subtract(_gridMinPoint);
		double rayOrigGridX = rayOrigGrid.getDirection().getX().get_coord();
		double rayOrigGridY = rayOrigGrid.getDirection().getY().get_coord();
		double rayOrigGridZ = rayOrigGrid.getDirection().getZ().get_coord();
		double deltaX, deltaY, deltaZ, tX, tY, tZ;
		if (rayDirectionX < 0) { // Negative direction on the x axis
			deltaX = -_voxellSizeX / rayDirectionX;
			tX = (Math.floor(rayOrigGridX / _voxellSizeX) * _voxellSizeX - rayOrigGridX) / rayDirectionX;
		} else { // Positive direction on the x axis
			deltaX = _voxellSizeX / rayDirectionX;
			tX = (Math.floor(rayOrigGridX / _voxellSizeX + 1) * _voxellSizeX - rayOrigGridX) / rayDirectionX;

		}
		if (rayDirectionY < 0) { // Negative direction on the y axis
			deltaY = -_voxellSizeY / rayDirectionY;
			tY = (Math.floor(rayOrigGridY / _voxellSizeY) * _voxellSizeY - rayOrigGridY) / rayDirectionY;
		} else { // Positive direction on the y axis
			deltaY = _voxellSizeY / rayDirectionY;
			tY = (Math.floor(rayOrigGridY / _voxellSizeY + 1) * _voxellSizeY - rayOrigGridY) / rayDirectionY;

		}
		if (rayDirectionZ < 0) { // Negative direction on the z axis
			deltaZ = -_voxellSizeZ / rayDirectionZ;
			tZ = (Math.floor(rayOrigGridZ / _voxellSizeZ) * _voxellSizeZ - rayOrigGridZ) / rayDirectionZ;
		} else { // Positive direction on the z axis
			deltaZ = _voxellSizeZ / rayDirectionZ;
			tZ = (Math.floor(rayOrigGridZ / _voxellSizeZ + 1) * _voxellSizeZ - rayOrigGridZ) / rayDirectionZ;

		}

		// Map to keep the geometries in ray's way to prevent transition on an object
		// more than once
		Map<Geometry, Boolean> wayOfRayGeomtries = new HashMap<Geometry, Boolean>();
		// Map to return the closest point
		Map<Geometry, List<Point3D>> closestPoint = new HashMap<Geometry, List<Point3D>>();
		double currentDistance, minDistance = Double.MAX_VALUE; // To find the closest point
		VoxellIndex currentVoxell = _convertPointToVoxell(rayOrigin); // The voxell to start with

		// While the ray still inside the grid and didn't found any intersection point
		// go
		// forwards to the next voxell by the ray direction
		while (true) {
			if (tX < tY) {
				if (tX < tZ) {
					tX += deltaX;
					currentVoxell._x += (rayDirectionX < 0) ? -1 : 1;
					if (currentVoxell._x < _voxellIndexMin._x || currentVoxell._x > _voxellIndexMax._x)
						return closestPoint;
				} else {
					tZ += deltaZ;
					currentVoxell._z += (rayDirectionZ < 0) ? -1 : 1;
					if (currentVoxell._z < _voxellIndexMin._z || currentVoxell._z > _voxellIndexMax._z)
						return closestPoint;
				}
			} else {
				if (tY < tZ) {
					tY += deltaY;
					currentVoxell._y += (rayDirectionY < 0) ? -1 : 1;
					if (currentVoxell._y < _voxellIndexMin._y || currentVoxell._y > _voxellIndexMax._y)
						return closestPoint;

				} else {
					tZ += deltaZ;
					currentVoxell._z += (rayDirectionZ < 0) ? -1 : 1;
					if (currentVoxell._z < _voxellIndexMin._z || currentVoxell._z > _voxellIndexMax._z)
						return closestPoint;
				}
			}

			Geometries voxellGeometris = mapGeometries.get(currentVoxell);
			Geometries currentGeometris = new Geometries();
			if (voxellGeometris != null) {
				for (Geometry geometry : voxellGeometris.getGeometries()) {
					if (wayOfRayGeomtries.containsKey(geometry) == false) {
						currentGeometris.add(geometry);
						wayOfRayGeomtries.put(geometry, null);
					}
				}
			}

			// Gets the intersection points only for the geometry in the current voxell by
			// using the geometries findIntersection function
			Map<Geometry, List<Point3D>> intersectionPoints = currentGeometris.findIntersections(ray);
			// Finds the closest point
			if (intersectionPoints != null) {
				for (Map.Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet()) {
					for (Point3D p : entry.getValue()) {
						currentDistance = rayOrigin.distance(p);
						if (currentDistance < minDistance) {
							List<Point3D> point = new ArrayList<Point3D>();
							point.add(p);
							closestPoint.clear();
							closestPoint.put((Geometry) entry.getKey(), point);
							minDistance = currentDistance;
						}
					}
				}
			}
			if (!(closestPoint.isEmpty())) {
				Point3D point = null;
				for (Map.Entry<Geometry, List<Point3D>> entry : closestPoint.entrySet()) {
					for (Point3D p : entry.getValue()) {
						point = new Point3D(p);
					}
				}
				if (point != null) {
					// Finds the voxell of the intersection point
					VoxellIndex vox = _convertPointToVoxell(point);
					// Returns the point just if it in the current voxell
					if (vox.equals(currentVoxell)) {
						return closestPoint;
					}
				}
			}
		}
	}

	/**
	 * Finds the enter point of the ray into the grid
	 * 
	 * @param ray
	 * @return - Returns the enter point or null if the ray miss the grid
	 */
	public Point3D findIntersectionWithGrid(Ray ray) {

		// 8 points at the corners of the grid
		Point3D a = new Point3D(_gridMaxPoint); // (0,0,0)
		Point3D b = new Point3D(_gridMinPoint.getX(), _gridMaxPoint.getY(), _gridMaxPoint.getZ()); // (1,0,0)
		Point3D c = new Point3D(_gridMinPoint.getX(), _gridMinPoint.getY(), _gridMaxPoint.getZ()); // (1,1,0)
		Point3D d = new Point3D(_gridMaxPoint.getX(), _gridMinPoint.getY(), _gridMaxPoint.getZ()); // (0,1,0)
		Point3D e = new Point3D(_gridMaxPoint.getX(), _gridMaxPoint.getY(), _gridMinPoint.getZ()); // (0,0,1)
		Point3D f = new Point3D(_gridMinPoint.getX(), _gridMaxPoint.getY(), _gridMinPoint.getZ()); // (1,0,1)
		Point3D g = new Point3D(_gridMinPoint); // (1,1,1)
		Point3D h = new Point3D(_gridMaxPoint.getX(), _gridMinPoint.getY(), _gridMinPoint.getZ()); // (0,1,1)

		// 6 Plans to create a cube
		Square Sa = new Square(a, b, c, d, null);
		Square Sb = new Square(c, b, f, g, null);
		Square Sc = new Square(e, f, g, h, null);
		Square Sd = new Square(h, e, a, d, null);
		Square Se = new Square(d, c, g, h, null);
		Square Sf = new Square(a, b, f, e, null);

		Geometries geometries = new Geometries();
		geometries.add(Sa, Sb, Sc, Sd, Se, Sf);
		// Finds intersections with all plans
		Map<Geometry, List<Point3D>> intersectionPoints = geometries.findIntersections(ray);
		// The closest intersection point = the enter point into the grid
		double currentDistance, minDistance = Double.MAX_VALUE;
		Point3D P0 = ray.getStartingPoint();
		Point3D closestPoint = null;
		for (Map.Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet()) {
			for (Point3D p : entry.getValue()) {
				currentDistance = P0.distance(p);
				if (currentDistance < minDistance) {
					closestPoint = new Point3D(p);
					minDistance = currentDistance;
				}
			}
		}
		return closestPoint;
	}

	/**
	 * Gets a ray and: if the ray inside the grid sends it to the 3DDDA algorithm.
	 * if the P0 of the ray is outside of the grid - trying to find intersection
	 * with the grid. if there is, creates a new ray with P0 = enter point and sends
	 * it to the 3DDDA algorithm. if there no intersection with the grid returns an
	 * empty Map
	 * 
	 * @param ray
	 * @return - The closest intersection point or (if there no intersection) an
	 *         empty Map
	 */
	public Map<Geometry, List<Point3D>> findIntersection(Ray ray) {
		Point3D point = ray.getStartingPoint();
		// If the point outside of the grid
		if (point.getX().get_coord() > _gridMaxPoint.getX().get_coord()
				|| point.getX().get_coord() < _gridMinPoint.getX().get_coord()
				|| point.getY().get_coord() > _gridMaxPoint.getY().get_coord()
				|| point.getY().get_coord() < _gridMinPoint.getY().get_coord()
				|| point.getZ().get_coord() > _gridMaxPoint.getZ().get_coord()
				|| point.getZ().get_coord() < _gridMinPoint.getZ().get_coord()) {
			point = findIntersectionWithGrid(ray); // Trying to find intersection with the grid
		}
		if (point != null) { // There is intersection with the grid
			return gridFindIntersection(new Ray(point, ray.getDirectionVector()));
		} else { // The ray miss the grid
			return new HashMap<Geometry, List<Point3D>>();
		}
	}

	/**
	 * Function to find for the sent point the appropriate Voxell
	 * 
	 * @param point
	 *            - The point on the geometry
	 * @return - Returns the voxell that the point inside it
	 */
	private VoxellIndex _convertPointToVoxell(Point3D point) {

		return new VoxellIndex((int) ((point.getX().get_coord() - _gridMinPoint.getX().get_coord()) / _voxellSizeX),
				(int) ((point.getY().get_coord() - _gridMinPoint.getY().get_coord()) / _voxellSizeY),
				(int) ((point.getZ().get_coord() - _gridMinPoint.getZ().get_coord()) / _voxellSizeZ));
	}

}
