package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Cylinder;
import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Square;
import geometries.Triangle;
import geometries.Tube;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import elements.*;
import scene.*;

class renderTest {
	// Light tests
	@Test
	void test_PointLight() {

		Sphere sphere = new Sphere(100, new Point3D(0, 0, 200), new Color(50, 0, 0));
		sphere.setMaterial(1, 0.5, 100, 0, 0);

		Camera camera = new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, 1));
		Scene scene = new Scene("testSphere_PointLight_occluded");

		PointLight light = new PointLight(new Color(255, 255, 255), new Point3D(-50, 0, 60), 0, 0.000001);

		scene.addGeometry(sphere);
		scene.setAmbientLight(new Color(130, 130, 130), 0.1);
		scene.addLight(light);
		scene.setBackground(new Color(0, 0, 0));
		scene.setScreenDistance(99);
		scene.setCamera(camera);

		ImageWriter imageWriter = new ImageWriter("testSphere_PointLight", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.setGrid(50, 50, 50);
		render.printImage();
	}

	@Test
	public void spotLight_sadow_sphere() {
		Scene scene = new Scene("Test light");
		scene.setCamera(new Camera(new Point3D(0,0,-120), new Vector(0, -1, 0), new Vector(0, 0, 1)));
		scene.setScreenDistance(130);
		scene.setBackground(Color.BLACK);

		Sphere sphere = new Sphere(60, new Point3D(0, 0, 80), new Color(23, 23, 46));// new Color(241, 6, 151));
		sphere.setMaterial(0.9, 0.8, 100, 0, 0);

		Triangle triangle1 = new Triangle(new Point3D(-250, -250, 120), new Point3D(-250, 250, 120),
				new Point3D(250, -250, 120), new Color(0, 0, 0));
		triangle1.setMaterial(0.9, 0.8, 100, 0, 0);
		Triangle triangle2 = new Triangle(new Point3D(250, 250, 120), new Point3D(-250, 250, 120),
				new Point3D(250, -250, 120), new Color(0, 0, 0));
		triangle2.setMaterial(0.9, 0.8, 100, 0, 0);

		scene.addGeometry(sphere);
		scene.addGeometry(triangle1);
		scene.addGeometry(triangle2);
		scene.setAmbientLight(new Color(130, 130, 130), 0.1);

		scene.addLight(new SpotLight(new Color(255, 255, 255), new Point3D(30, 0, 0), 0, 0, new Vector(-30, 0, 80)));
		scene.addLight(new PointLight(new Color(1, 1, 0), new Point3D(25, 0, 0), 1, 1));

		ImageWriter imageWriter = new ImageWriter("spotLight_sadow_sphere", 500, 500, 500, 500);
		Render testRender = new Render(scene, imageWriter);
		testRender.setGrid(50, 50, 50);
		testRender.printImage();
	}

	@Test
	void test_all_lights() {

		Camera camera = new Camera(new Point3D(0,0,150), new Vector(0, -1, 0), new Vector(0, 0, -1));
		Scene scene = new Scene("test2_1");

		SpotLight spot = new SpotLight(new Color(100, 100, 100), new Point3D(100, 0, 0), 2, 0.01,
				new Vector(-100, 0, -250).normalization());
		PointLight pointLight = new PointLight(new Color(77, 0, 0), new Point3D(200, -200, -100), 0.01, 0.01);
		DirectionalLight directionalLight = new DirectionalLight(new Color(1, 1, 0), new Vector(1, 0, 0));

		Sphere sphere = new Sphere(170, new Point3D(0, 0, -250), new Color(23, 23, 46));
		sphere.setMaterial(500, 20, 100, 0, 0);
		scene.addGeometry(sphere);

		scene.setAmbientLight(new Color(130, 130, 130), 0.1);
		scene.addLight(spot);
		scene.addLight(pointLight);
		scene.addLight(directionalLight);
		scene.setBackground(new Color(0, 0, 0));
		scene.setScreenDistance(149);
		scene.setCamera(camera);

		ImageWriter imageWriter = new ImageWriter("test_all_Lights_of_sphere", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.setGrid(50, 50, 50);
		render.printImage();
	}
	// Shadow tests

	@Test
	public void sphereOnSphere() {
		Scene scene = new Scene("sphereInSphere");
		scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, 1)));
		scene.setScreenDistance(100);
		scene.setBackground(new Color());
		
		Sphere sphere = new Sphere(60, new Point3D(0, 0, 185), new Color(241, 6, 151));
		sphere.setMaterial(0.5, 0.5, 300, 0, 0);
		
		Sphere sphere1 = new Sphere(5, new Point3D(0, 10, 120), new Color(5, 55, 255));
		sphere1.setMaterial(0.35, 10, 500, 0, 0);
		
		Triangle triangle1 = new Triangle(new Point3D(-250, -250, 220), new Point3D(-250, 250, 220),
				new Point3D(250, -250, 220), new Color());
		triangle1.setMaterial(0.9, 0.8, 100, 0, 0);
		
		Triangle triangle2 = new Triangle(new Point3D(250, 250, 220), new Point3D(-250, 250, 220),
				new Point3D(250, -250, 220), new Color());
		triangle2.setMaterial(0.9, 0.8, 100, 1, 0);
		scene.addGeometry(sphere);
		scene.addGeometry(sphere1);
		scene.addGeometry(triangle1);
		scene.addGeometry(triangle2);
		scene.setAmbientLight(new Color(130, 130, 130), 0.1);
		scene.addLight(new SpotLight(new Color(100,100,100), new Point3D(25, 25, 100), 0, 0, new Vector(-25, -25, 80)));
		scene.addLight(new SpotLight(new Color(100,100,100), new Point3D(0, 5, 90), 0, 0, new Vector(0, 0, 1)));
		//scene.addLight(new PointLight(new Color(1, 1, 0), new Point3D(25, 25, 0), 0.01, 0.01));
		ImageWriter imageWriter = new ImageWriter("sphere on sphere_shadow", 300, 300, 300, 300);
		Render testRender = new Render(scene, imageWriter);
		testRender.setGrid(50, 50, 50);
		testRender.printImage();
	}

	// reflect tests
	@Test
	void testReflect() {
		Scene scene = new Scene("Test");
		scene.setAmbientLight(new Color(0, 0, 0), 2);
		scene.setBackground(new Color(0, 0, 0));
		scene.setCamera(new Point3D(0,-250, 300), new Vector(0, -1, 0), new Vector(0, 0, -1));
		scene.setScreenDistance(200);
		scene.getCamera().rotateAroundX(0);
		

		Square p = new Square(new Point3D(-2000, 0, -2000), new Point3D(-2000, 0, 2000), 
				new Point3D(2000, 0,2000),new Point3D(2000, 0, -2000), new Color(40, 40, 62));
		p.setMaterial(100, 10, 100, 0.5, 0);
		scene.addGeometry(p);
		
//		Square s = new Square(new Point3D(500,-500,0), new Point3D(0,-500,0), new Point3D(0,0,0), new Point3D(500,0,0),
//				new Color(10,10,10));
//		s.setMaterial(0.5,0.9,100,0.1,1);
//		scene.addGeometry(s);
		
		Square s1 = new Square(new Point3D(-500,-500,0), new Point3D(0,-500,0), new Point3D(0,0,0), new Point3D(-500,0,0),
				new Color(20,20,20));
		s1.setMaterial(0.5,0.9,100,0.1,1);
		scene.addGeometry(s1);
		
//		Square s2 = new Square(new Point3D(25,-500,1), new Point3D(-25,-500,1), new Point3D(-25,0,1), new Point3D(25,0,1),
//				new Color(102, 51, 0));
//		s2.setMaterial(0.5,0,100,0.1,0);
//		scene.addGeometry(s2);
//		
//		Square s3 = new Square(new Point3D(500,-500,0), new Point3D(-500,-500,0), new Point3D(-500,-900,0), new Point3D(500,-900,0),
//				new Color(102, 51, 0));
//		s3.setMaterial(0.5,0,100,0.1,0);
//		scene.addGeometry(s3);
		
		SpotLight spot = new SpotLight(new Color(100, 100, 100), new Point3D(0, -100, -400), 0.2,0, new Vector(0, -1, 0));
		scene.addLight(spot);

		Sphere sphere = new Sphere(199, new Point3D(0, -400, -400), new Color(23, 23, 46));
		sphere.setMaterial(30, 0.2, 100, 0, 0);
		scene.addGeometry(sphere);

		
		PointLight pointL = new PointLight(new Color(150, 0, 0), new Point3D(-200, -380, -201), 0, 0.01);
		scene.addLight(pointL);

		ImageWriter imageWriter = new ImageWriter("Test_ reflactde", 500, 500, 500, 500);
		Render render = new Render(scene, imageWriter);
		render.setGrid(50, 50, 50);
		render.printImage();
	}
	

	@Test
	void Test_refr_refl() {
		Camera camera = new Camera(new Point3D(0,0,150), new Vector(0, -1, 0), new Vector(0, 0, -1));
		Scene scene = new Scene("test_refracted_reflacted");
		scene.setCamera(camera);

		SpotLight spot = new SpotLight(new Color(100, 100, 0), new Point3D(100, 0, 0), 0.0002, 0,
				new Vector(-100, 0, -250));
		
		SpotLight spot2 = new SpotLight(new Color(0,100, 0), new Point3D(-100, 0, -300), 0.002, 0,
				new Vector(100, 0, 250));
		scene.addLight(spot2);
		
		PointLight pointLight = new PointLight(new Color(77, 0, 0), new Point3D(200, -200, -100), 0, 0.0000001);
		
		Sphere sphere = new Sphere(170, new Point3D(0, 0, -250), new Color(23, 23, 46));
		sphere.setMaterial(1, 0.2, 100, 0, 0);
		scene.addGeometry(sphere);
		
		Square t = new Square(new Point3D(400,400,0), new Point3D(300,400,-1000), new Point3D(300,-400,-1000),new Point3D(400,-400,0), new Color(0,0,0));
		t.setMaterial(0.9,2,100,1,0);
		scene.addGeometry(t);
		
		Square t1 = new Square(new Point3D(-400,400,0), new Point3D(-300,400,-1000), new Point3D(-300,-400,-1000),new Point3D(-400,-400,0), new Color(0,0,0));
		t1.setMaterial(0.9,0.2,100,1,0);
		scene.addGeometry(t1);
		
		scene.setAmbientLight(new Color(130, 130, 130), 0.1);
		scene.addLight(spot);
		scene.addLight(pointLight);
		
		scene.setBackground(new Color(0, 0, 0));
		scene.setScreenDistance(149);

		ImageWriter imageWriter = new ImageWriter("test_refracted_reflacted", 750, 500, 750, 500);
		Render render = new Render(scene, imageWriter);
		render.setGrid(50, 50, 50);
		render.printImage();
	}


	// things
	@Test // From yehuda pinchas
	public void shadowTest() {
		Scene scene = new Scene("TestStar");

		Triangle triangle11 = new Triangle(new Point3D(0, 100, 120),
				new Point3D(22.451398828979272, 30.901699437494740, 120), new Point3D(0, -3.647450844, 100),
				new Color(0, 0, 0));
		triangle11.setMaterial(0.9, 0.8, 2, 0, 0);

		Triangle triangle12 = new Triangle(new Point3D(0, 100, 120),
				new Point3D(-22.451398828979272, 30.901699437494740, 120), new Point3D(0, -3.647450844, 100),
				new Color(0, 0, 0));
		triangle12.setMaterial(0.9, 0.8, 2, 0, 0);

		Triangle triangle21 = new Triangle(new Point3D(22.451398828979272, 30.901699437494740, 120),
				new Point3D(95.105651629515353, 30.901699437494740, 120), new Point3D(0, -3.647450844, 100),
				new Color(0, 0, 0));
		triangle21.setMaterial(0.9, 0.8, 2, 0, 0);

		Triangle triangle22 = new Triangle(new Point3D(95.105651629515353, 30.901699437494740, 120),
				new Point3D(36.327126400268039, -11.803398874989492, 120), new Point3D(0, -3.647450844, 100),
				new Color(0, 0, 0));
		triangle22.setMaterial(0.9, 0.8, 2, 0, 0);

		Triangle triangle31 = new Triangle(new Point3D(36.327126400268039, -11.803398874989492, 120),
				new Point3D(58.778525229247292, -80.901699437494734, 120), new Point3D(0, -3.647450844, 100),
				new Color(0, 0, 0));
		triangle31.setMaterial(0.9, 0.8, 2, 0, 0);

		Triangle triangle32 = new Triangle(new Point3D(58.778525229247292, -80.901699437494734, 120),
				new Point3D(0, -38.196601125010515, 120), new Point3D(0, -3.647450844, 100), new Color(0, 0, 0));
		triangle32.setMaterial(0.9, 0.8, 2, 0, 0);

		Triangle triangle41 = new Triangle(new Point3D(0, -38.196601125010515, 120),
				new Point3D(-58.778525229247292, -80.901699437494734, 120), new Point3D(0, -3.647450844, 100),
				new Color(0, 0, 0));
		triangle41.setMaterial(0.9, 0.8, 2, 0, 0);

		Triangle triangle42 = new Triangle(new Point3D(-58.778525229247292, -80.901699437494734, 120),
				new Point3D(-36.327126400268039, -11.803398874989492, 120), new Point3D(0, -3.647450844, 100),
				new Color(0, 0, 0));
		triangle42.setMaterial(0.9, 0.8, 2, 0, 0);

		Triangle triangle51 = new Triangle(new Point3D(-36.327126400268039, -11.803398874989492, 120),
				new Point3D(-95.105651629515353, 30.901699437494740, 120), new Point3D(0, -3.647450844, 100),
				new Color(0, 0, 0));
		triangle51.setMaterial(0.9, 0.8, 2, 0, 0);

		Triangle triangle52 = new Triangle(new Point3D(-95.105651629515353, 30.901699437494740, 120),
				new Point3D(-22.451398828979272, 30.901699437494740, 120), new Point3D(0, -3.647450844, 100),
				new Color(0, 0, 0));
		triangle52.setMaterial(0.9, 0.8, 2, 0, 0);

//		Plane plane = new Plane(new Point3D(0, 0, 140), new Vector(0, 0, -1), new Color(0, 75, 210));
//		plane.setMaterial(1, 1, 100, 0, 0);

		Square square = new Square(new Point3D(3000,3000,140), new Point3D(3000,-3000,140), new Point3D(-3000,-3000,140), new Point3D(-3000,3000,140), new Color(0, 75, 210));
		square.setMaterial(1, 1, 100, 0, 0);
		
		scene.addGeometry(triangle11);
		scene.addGeometry(triangle12);
		scene.addGeometry(triangle21);
		scene.addGeometry(triangle22);
		scene.addGeometry(triangle31);
		scene.addGeometry(triangle32);
		scene.addGeometry(triangle41);
		scene.addGeometry(triangle42);
		scene.addGeometry(triangle51);
		scene.addGeometry(triangle52);
		scene.addGeometry(square);

		Camera camera = new Camera(new Point3D(0,0,-100), new Vector(0, 1, 0), new Vector(0, 0, 1));
		SpotLight spot = new SpotLight(new Color(255, 215, 0), new Point3D(15, 0, 0), 0, 0, new Vector(-80, 20, 180));
		PointLight pointLight = new PointLight(new Color(255, 255, 255), new Point3D(0, 0, 0), 0.1, 0);
		scene.addLight(spot);
		scene.addLight(pointLight);
		scene.setAmbientLight(new AmbientLight(new Color(100, 100, 100), 0.5));
		scene.setBackground(new Color(255, 255, 255));
		scene.setScreenDistance(200);
		scene.setCamera(camera);

		ImageWriter imageWriter = new ImageWriter("starTest", 500, 500, 500, 500);
		Render testRender = new Render(scene, imageWriter);
		testRender.setGrid(20, 20, 20);
		testRender.printImage();
	}
}
