package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import elements.SpotLight;
import geometries.Sphere;
import geometries.Square;
import geometries.Triangle;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

class GridTests {

	@Test
	void test() {
		Camera camera = new Camera(new Point3D(0,0,150), new Vector(0, -1, 0), new Vector(0, 0, -1));
		Scene scene = new Scene("test2_1");

		SpotLight spot = new SpotLight(new Color(100, 100, 100), new Point3D(100, 0, 0), 2, 0.01,
				new Vector(-100, 0, -250).normalization());
		PointLight pointLight = new PointLight(new Color(77, 0, 0), new Point3D(200, -200, -100), 0.01, 0.01);
		DirectionalLight directionalLight = new DirectionalLight(new Color(1, 1, 0), new Vector(1, 0, 0));

		for (int i = 0; i < 200; i++) {
			for (int j = 0; j < 200; j++) {
				Sphere sphere = new Sphere(10, new Point3D(800-25*i, 800-25*j, -300), new Color(100*(i%2), 100*(i%2+1), 0));
				sphere.setMaterial(500, 20, 100, 0, 0);
				scene.addGeometry(sphere);
			}
		}
		scene.setAmbientLight(new Color(130, 130, 130), 0.1);
		scene.addLight(spot);
		scene.addLight(pointLight);
		scene.addLight(directionalLight);
		scene.setBackground(new Color(0, 0, 0));
		scene.setScreenDistance(149);
		scene.setCamera(camera);

		ImageWriter imageWriter = new ImageWriter("test grid malan sphere", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.setGrid(50, 50, 10);
		render.printImage();
	}

	@Test
	void testTriangle() {
		Camera camera = new Camera(new Point3D(0,0,150), new Vector(0, -1, 0), new Vector(0, 0, -1));
		Scene scene = new Scene("test2_1");

		SpotLight spot = new SpotLight(new Color(100, 100, 100), new Point3D(100, 0, 0), 2, 0.01,
				new Vector(-100, 0, -250).normalization());
		PointLight pointLight = new PointLight(new Color(77, 0, 0), new Point3D(200, -200, -100), 0.01, 0.01);
		DirectionalLight directionalLight = new DirectionalLight(new Color(1, 1, 0), new Vector(1, 0, 0));

		for (int i = 0; i < 200; i++) {
			for (int j = 0; j < 200; j++) {
				//for (int j2 = 0; j2 < 200; j2++) {
					Triangle t = new Triangle(
							new Point3D(800 - (i * 20), 800 - (j * 20), -300),
							new Point3D(785 - (i * 20), 800 - (j * 20), -300),
							new Point3D(792 - (i * 20), 810 - (j * 20), -300),
							new Color(255 * (j % 2), 255 * (j % 2 + 1), 0));
					t.setMaterial(500, 20, 100, 0, 0);
					scene.addGeometry(t);
				//}
			}
		}
		scene.setAmbientLight(new Color(130, 130, 130), 0.1);
		scene.addLight(spot);
		scene.addLight(pointLight);
		scene.addLight(directionalLight);
		scene.setBackground(new Color(0, 0, 0));
		scene.setScreenDistance(149);
		scene.setCamera(camera);

		ImageWriter imageWriter = new ImageWriter("test grid malan triangle", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.setGrid(50, 50, 10);
		render.printImage();
	}
	
	void makeCubes(Scene scene, int x, int y, int z) {
		Square a1 = new Square(new Point3D(x,y,z), new Point3D(x-10,y,z),
				new Point3D(x-10,y-10,z), new Point3D(x,y-10,z), new Color(255,0,0)); 
		a1.setMaterial(1,0.2,10,0,0);
		Square a2 = new Square(new Point3D(x-10,y,z), new Point3D(x-10,y,z-10),
				new Point3D(x-10,y-10,z-10), new Point3D(x-10,y-10,z), new Color(0,0,255));
		a2.setMaterial(1,0.2,10,0,0);
		Square a3 = new Square(new Point3D(x,y-10,z), new Point3D(x-10,y-10,z),
				new Point3D(x-10,y-10,z-10), new Point3D(x,y-10,z-10), new Color(0,255,0));
		a3.setMaterial(1,0.2,10,0,0);
		scene.addGeometry(a1,a2,a3);
		
		Square b1 = new Square(new Point3D(x-10,y-10,z-10), new Point3D(x-20,y-10,z-10),
				new Point3D(x-20,y-20,z-10), new Point3D(x-10,y-20,z-10), new Color(255,0,0));
		b1.setMaterial(1,0.2,10,0,0);
		Square b2 = new Square(new Point3D(x-20,y-10,z-10), new Point3D(x-20,y-10,z-20),
				new Point3D(x-20,y-20,z-20), new Point3D(x-20,y-20,z-10), new Color(0,0,255));
		b2.setMaterial(1,0.2,10,0,0);

		Square b3 = new Square(new Point3D(x-10,y-20,z-10), new Point3D(x-20,y-20,z-10),
				new Point3D(x-20,y-20,z-20), new Point3D(x-10,y-20,z-20), new Color(0,255,0));
		b3.setMaterial(1,0.2,10,0,0);

		scene.addGeometry(b1,b2,b3);
		
		Square c1 = new Square(new Point3D(x,y-10,z-10), new Point3D(x-10,y-10,z-10),
				new Point3D(x-10,y-20,z-10), new Point3D(x,y-20,z-10), new Color(255,0,0)); 
		c1.setMaterial(1,0.2,10,0,0);
		Square c2 = new Square(new Point3D(x-10,y-10,z-10), new Point3D(x-10,y-10,z-20),
				new Point3D(x-10,y-20,z-20), new Point3D(x-10,y-20,z-10), new Color(0,0,255));
		c2.setMaterial(1,0.2,10,0,0);
		Square c3 = new Square(new Point3D(x,y-20,z-10), new Point3D(x-10,y-20,z-10),
				new Point3D(x-10,y-20,z-20), new Point3D(x,y-20,z-20), new Color(0,255,0));
		c3.setMaterial(1,0.2,10,0,0);
		scene.addGeometry(c1,c2,c3);
		
		Square d1 = new Square(new Point3D(x-10,y-20,z-20), new Point3D(x-20,y-20,z-20),
				new Point3D(x-20,y-30,z-20), new Point3D(x-10,y-30,z-20), new Color(255,0,0)); 
		d1.setMaterial(1,0.2,10,0,0);

		Square d2 = new Square(new Point3D(x-20,y-20,z-20), new Point3D(x-20,y-20,z-30),
				new Point3D(x-20,y-30,z-30), new Point3D(x-20,y-30,z-20), new Color(0,0,255));
		d2.setMaterial(1,0.2,10,0,0);

		Square d3 = new Square(new Point3D(x-10,y-30,z-20), new Point3D(x-20,y-30,z-20),
				new Point3D(x-20,y-30,z-30), new Point3D(x-10,y-30,z-30), new Color(0,255,0));
		d3.setMaterial(1,0.2,10,0,0);

		scene.addGeometry(d1,d2,d3);
	}
	
	@Test
	void testCube() {
		Camera camera = new Camera(new Point3D(0,0,150), new Vector(0, -1, 0), new Vector(0, 0, -1));
		Scene scene = new Scene("test2_1");

//		SpotLight spot = new SpotLight(new Color(100, 100, 100), new Point3D(100, 0, 0), 2, 0.01,
//				new Vector(-100, 0, -250).normalization());
//		PointLight pointLight = new PointLight(new Color(77, 0, 0), new Point3D(200, -200, -100), 0.01, 0.01);
//		DirectionalLight directionalLight = new DirectionalLight(new Color(1, 1, 0), new Vector(1, 0, 0));

		for (int i = 0; i < 200; i++) {
			
				for (int j = 0; j < 200; j++) {
					makeCubes(scene, 800 - i * 20, 800 - i * 20 - j*20, 300 - i * 20);
				}
		}
		scene.setAmbientLight(new Color(130, 130, 130), 0.1);
//		scene.addLight(spot);
//		scene.addLight(pointLight);
//		scene.addLight(directionalLight);
		scene.setBackground(new Color(0, 0, 0));
		scene.setScreenDistance(149);
		scene.setCamera(camera);

		ImageWriter imageWriter = new ImageWriter("test grid with cubes", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.setGrid(100, 100, 100);
		render.printImage();
	}
}
