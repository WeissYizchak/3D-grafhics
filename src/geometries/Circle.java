package geometries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Protectable;
import primitives.Color;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Circle extends Plane {
	protected double _radius;
	protected double _annulusRadius = 0;

	public Circle(Point3D point, Vector normal, double _radius, Color color) {
		super(point, normal ,color);
		this._radius = _radius;
	}

	public void setAnnulus(double radius) {
		_annulusRadius = radius;
	}
	
	
	@Override
	public Map<Geometry, List<Point3D>> findIntersections(Ray ray) {
		Map<Geometry, List<Point3D>> intersectionMap =  super.findIntersections(ray);
		List<Point3D> intersectionPoints = new ArrayList<Point3D>();
		for(Map.Entry<Geometry,List<Point3D>> entry:intersectionMap.entrySet()) {
			intersectionPoints.addAll(entry.getValue());
		}
		intersectionMap.clear();
		if(!intersectionPoints.isEmpty()) {
			double distence = intersectionPoints.get(0).distance(_point);
			if(distence<_radius &&(Coordinate.isZero(_annulusRadius)||distence >_annulusRadius)) {
				intersectionMap.put(this, intersectionPoints);
			}
		}
		return intersectionMap;
	}

	@Override
	public Point3D getMax() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point3D getMin() {
		// TODO Auto-generated method stub
		return null;
	}


}
