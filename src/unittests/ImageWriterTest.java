package unittests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

class ImageWriterTest {

	@Test
	void writeWhiteGrid() {
		ImageWriter test1 = new ImageWriter("WhiteGrid", 500, 500, 500, 500);
		for (int i = 1; i < test1.getNy(); i++) {
			for (int j = 1; j < test1.getNx(); j++) {
				if (i % 50 == 0 || j % 50 == 0) {
					test1.writePixel(i, j, 255, 255, 255);
				}
			}
		}
		test1.writeToimage();
	}

	@Test
	void write4TrianglesAndSphere() {
		try {
			Triangle t1 = new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100),
					new Color(150, 0, 0));
			t1.setMaterial(1, 1, 1,0,0);
			Triangle t2 = new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100),
					new Point3D(-100, 100, -100), new Color(0, 150, 0));
			t2.setMaterial(1, 1, 1,0,0);
			Triangle t3 = new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100),
					new Point3D(100, -100, -100), new Color(0,0,150));
			t3.setMaterial(1, 1, 1,0,0);
			Triangle t4 = new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100),
					new Point3D(-100, -100, -100), new Color(255,255,255));
			t4.setMaterial(1, 1, 1,0,0);
			Sphere s = new Sphere(35, new Point3D(0, 0, -50), new Color(0,250,250));
			s.setMaterial(1, 1, 1,0,0);
			Scene testScene = new Scene("testScene");
			testScene.setBackground(75, 127, 190);
			testScene.setAmbientLight(new Color(150, 150, 150), 0.5);
			testScene.setCamera(Point3D.ZERO, new Vector(0, -1,0), new Vector(0, 0, -1));
			testScene.setScreenDistance(50);
			testScene.addGeometry(t1);
			testScene.addGeometry(t2);
			testScene.addGeometry(t3);
			testScene.addGeometry(t4);
			testScene.addGeometry(s);
			ImageWriter testImageWriter = new ImageWriter("4TrianglesAndSphere", 500, 500, 500, 500);
			Render testRender = new Render(testScene, testImageWriter);
			testRender.setGrid(50, 50, 50);
			testRender.printImage();
			testRender.printGrid(50);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

//	@Test
//	public void basicRendering() {
//		Scene scene = new Scene("Test scene");
//		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)));
//		scene.setScreenDistance(100);
//		scene.setBackground(new Color(0, 0, 0));
//		scene.setAmbientLight(new Color(255, 255, 255), 1);
//		scene.addGeometry(new Sphere(50, new Point3D(0, 0, 150)));
//
//		scene.addGeometry(new Triangle(new Point3D(100, 0, 149), new Point3D(0, 100, 149), new Point3D(100, 100, 149)));
//
//		scene.addGeometry(
//				new Triangle(new Point3D(100, 0, 149), new Point3D(0, -100, 149), new Point3D(100, -100, 149)));
//
//		scene.addGeometry(
//				new Triangle(new Point3D(-100, 0, 149), new Point3D(0, 100, 149), new Point3D(-100, 100, 149)));
//
//		scene.addGeometry(
//				new Triangle(new Point3D(-100, 0, 149), new Point3D(0, -100, 149), new Point3D(-100, -100, 149)));
//
//		ImageWriter imageWriter = new ImageWriter("test0", 500, 500, 500, 500);
//		Render render = new Render(scene, imageWriter);
//
//		render.printImage();
//		;
//		render.printGrid(50);
//	}
//
//	@Test
//	void writeHouse() {
//		try {
//			Triangle t1 = new Triangle(new Point3D(-150, -50, -49), new Point3D(-150, 100, -49),
//					new Point3D(0, 100, -49));
//			Triangle t2 = new Triangle(new Point3D(-150, -50, -49), new Point3D(0, -50, -49), new Point3D(0, 100, -49));
//			Triangle t3 = new Triangle(new Point3D(0, -100, -49), new Point3D(0, 150, -49), new Point3D(100, 25, -49));
//			Triangle t4 = new Triangle(new Point3D(25, 50, -49), new Point3D(100, 50, -49), new Point3D(25, 100, -49));
//			Triangle t5 = new Triangle(new Point3D(100, 100, -49), new Point3D(100, 50, -49),
//					new Point3D(25, 100, -49));
//			Sphere s1 = new Sphere(20, new Point3D(100, 75, -49));
//			Scene testScene = new Scene("testScene");
//			testScene.setBackground(75, 127, 190);
//			testScene.setAmbientLight(new Color(255, 255, 255), 1);
//			testScene.setCamera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, -1));
//			testScene.setScreenDistance(50);
//			testScene.addGeometry(t1);
//			testScene.addGeometry(t2);
//			testScene.addGeometry(t3);
//			testScene.addGeometry(t4);
//			testScene.addGeometry(t5);
//			testScene.addGeometry(s1);
//			ImageWriter testImageWriter = new ImageWriter("house", 500, 500, 500, 500);
//			Render testRender = new Render(testScene, testImageWriter);
//			testRender.printImage();
//			testRender.printGrid(50);
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}
//	}

}