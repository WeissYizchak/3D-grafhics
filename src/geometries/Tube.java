package geometries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.omg.CORBA._PolicyStub;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;

public class Tube extends Cylinder {
	protected double _height;
	public Tube(Ray ray, double height, double radius, Color color) {
		super(ray, radius, color);
		_height = height;
	}
	
	@Override
	public Map<Geometry, List<Point3D>> findIntersections(Ray ray) {
		Map<Geometry, List<Point3D>> intersectionPoints = super.findIntersections(ray);
		List<Point3D> intersection = new ArrayList<>();
		List<Point3D> intersectionPointInTheHeight = new ArrayList<>();
		if(!intersectionPoints.isEmpty()) {
			for (Map.Entry<Geometry, List<Point3D>> entry : intersectionPoints.entrySet()) {
				intersection.addAll(entry.getValue());
			}
			for(Point3D point:intersection) {
				Point3D heighPoint = point.addVector(getNormal(point).scaleMult(_radius));
				if(_ray.getStartingPoint().distance(heighPoint)<=_height) {
					intersectionPointInTheHeight.add(point);
				}
			}
		}
		intersectionPoints.clear();
		if(!intersectionPointInTheHeight.isEmpty())
			intersectionPoints.put(this, intersectionPointInTheHeight);
		return intersectionPoints;
	}

}
