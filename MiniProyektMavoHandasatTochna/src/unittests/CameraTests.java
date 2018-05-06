package unittests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import elements.*;

/**
 *
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @autor Israel Yakovson 300190055 sariruli@gmail.com
 *
 *
 */
class CameraTests {

	@Test
	void testConstructor() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, -1));
		Vector Right = new Vector(-1, 0, 0);
		assertEquals("", Right, camera.getVRight());

		try {
			new Camera(Point3D.ZERO, new Vector(1, 1, 1), new Vector(1, 2, 3));
			fail("didn't throw ERROR: the vectors are not vertical");
		} catch (Exception e) {
		}
	}

	@Test
	void testConstructRayThroughPixel() {

		Camera camera1 = new Camera(Point3D.ZERO, new Vector(0, 1, 0), new Vector(0, 0, 1));

		// 3X3
		Ray ray11 = camera1.constructRayThroughPixel(3, 3, 1, 1, 100, 150, 150);
		Ray expectedRay11 = new Ray(Point3D.ZERO, new Vector(50, 50, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (1,1)", ray11, expectedRay11);

		Ray ray33 = camera1.constructRayThroughPixel(3, 3, 3, 3, 100, 150, 150);
		Ray expectedRay33 = new Ray(Point3D.ZERO, new Vector(-50, -50, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (3,3)", ray33, expectedRay33);

		Ray ray21 = camera1.constructRayThroughPixel(3, 3, 2, 1, 100, 150, 150);
		Ray expectedRay21 = new Ray(Point3D.ZERO, new Vector(0, 50, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (2,1)", ray21, expectedRay21);

		Ray ray23 = camera1.constructRayThroughPixel(3, 3, 2, 3, 100, 150, 150);
		Ray expectedRay23 = new Ray(Point3D.ZERO, new Vector(0, -50, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (2,3)", ray23, expectedRay23);

		Ray ray12 = camera1.constructRayThroughPixel(3, 3, 1, 2, 100, 150, 150);
		Ray expectedRay12 = new Ray(Point3D.ZERO, new Vector(50, 0, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (1,2)", ray12, expectedRay12);

		Ray ray32 = camera1.constructRayThroughPixel(3, 3, 3, 2, 100, 150, 150);
		Ray expectedRay32 = new Ray(Point3D.ZERO, new Vector(-50, 0, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (3,2)", ray32, expectedRay32);

		Ray ray22 = camera1.constructRayThroughPixel(3, 3, 2, 2, 100, 150, 150);
		Ray expectedRay22 = new Ray(Point3D.ZERO, new Vector(0, 0, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (2,2) - A case test that Pij is equal to Pc", ray22,
				expectedRay22);

		// 3X4 Py={1,2,3} Px={1,2,3,4}

		Ray rayS22 = camera1.constructRayThroughPixel(4, 3, 2, 2, 100, 200, 150);
		Ray expectedRayS22 = new Ray(Point3D.ZERO, new Vector(25, 0, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X4 in pixel (2,2)", rayS22, expectedRayS22);

		Ray rayS32 = camera1.constructRayThroughPixel(4, 3, 3, 2, 100, 200, 150);
		Ray expectedRayS32 = new Ray(Point3D.ZERO, new Vector(-25, 0, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X4 in pixel (3,2)", rayS32, expectedRayS32);

		Ray rayS11 = camera1.constructRayThroughPixel(4, 3, 1, 1, 100, 200, 150);
		Ray expectedRayS11 = new Ray(Point3D.ZERO, new Vector(75, 50, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X4 in pixel (1,1)", rayS11, expectedRayS11);

		Ray rayS43 = camera1.constructRayThroughPixel(4, 3, 4, 3, 100, 200, 150);
		Ray expectedRayS43 = new Ray(Point3D.ZERO, new Vector(-75, -50, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X4 in pixel (4,3)", rayS43, expectedRayS43);

		// 4X3 Py={1,2,3,4} Px={1,2,3}

		Ray ray_c22 = camera1.constructRayThroughPixel(3, 4, 2, 2, 100, 150, 200);
		Ray expectedRay_c22 = new Ray(Point3D.ZERO, new Vector(0, 25, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 4X3 in pixel (2,2)", ray_c22, expectedRay_c22);

		Ray ray_c32 = camera1.constructRayThroughPixel(3, 4, 3, 2, 100, 150, 200);
		Ray expectedRay_c32 = new Ray(Point3D.ZERO, new Vector(-50, 25, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 4X3 in pixel (3,2)", ray_c32, expectedRay_c32);

		Ray ray_c11 = camera1.constructRayThroughPixel(3, 4, 1, 1, 100, 150, 200);
		Ray expectedRay_c11 = new Ray(Point3D.ZERO, new Vector(50, 75, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 4X3 in pixel (1,1)", ray_c11, expectedRay_c11);

		Ray ray_c43 = camera1.constructRayThroughPixel(3, 4, 3, 4, 100, 150, 200);
		Ray expectedRay_c43 = new Ray(Point3D.ZERO, new Vector(-50, -75, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 4X3 in pixel (4,3)", ray_c43, expectedRay_c43);

		// ======
		// Negative vectors.
		Camera camera2 = new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, -1));

		// 3X3
		Ray ray1 = camera2.constructRayThroughPixel(3, 3, 3, 3, 100, 150, 150);
		Ray resultRay = new Ray(Point3D.ZERO,
				new Vector(-1 / Math.sqrt(6), 1 / Math.sqrt(6), -(Math.sqrt((double) 2 / 3))));
		assertEquals("Negative vectors testing at 3X3 in pixel (3,3)", ray1, resultRay);

		// 3X3
		Ray ray_d11 = camera2.constructRayThroughPixel(3, 3, 1, 1, 100, 150, 150);
		Ray expectedRay_d11 = new Ray(Point3D.ZERO, new Vector(50, -50, -100).normalization());
		assertEquals("negative up to vectors testing at 3X3 in pixel (1,1)", ray_d11, expectedRay_d11);

		Ray ray_d33 = camera2.constructRayThroughPixel(3, 3, 3, 3, 100, 150, 150);
		Ray expectedRay_d33 = new Ray(Point3D.ZERO, new Vector(-50, 50, -100).normalization());
		assertEquals("negative up to vectors testing at 3X3 in pixel (3,3)", ray_d33, expectedRay_d33);

		Ray ray_d21 = camera2.constructRayThroughPixel(3, 3, 2, 1, 100, 150, 150);
		Ray expectedRay_d21 = new Ray(Point3D.ZERO, new Vector(0, -50, -100).normalization());
		assertEquals("negative up to vectors testing at 3X3 in pixel (2,1)", ray_d21, expectedRay_d21);

		Ray ray_d23 = camera2.constructRayThroughPixel(3, 3, 2, 3, 100, 150, 150);
		Ray expectedRay_d23 = new Ray(Point3D.ZERO, new Vector(0, 50, -100).normalization());
		assertEquals("negative up to vectors testing at 3X3 in pixel (2,3)", ray_d23, expectedRay_d23);

		Ray ray_d12 = camera2.constructRayThroughPixel(3, 3, 1, 2, 100, 150, 150);
		Ray expectedRay_d12 = new Ray(Point3D.ZERO, new Vector(50, 0, -100).normalization());
		assertEquals("negative up to vectors testing at 3X3 in pixel (1,2)", ray_d12, expectedRay_d12);

		Ray ray_d32 = camera2.constructRayThroughPixel(3, 3, 3, 2, 100, 150, 150);
		Ray expectedRay_d32 = new Ray(Point3D.ZERO, new Vector(-50, 0, -100).normalization());
		assertEquals("negative up to vectors testing at 3X3 in pixel (3,2)", ray_d32, expectedRay_d32);

		// vTo negative vUp positive
		Camera camera3 = new Camera(Point3D.ZERO, new Vector(0, 1, 0), new Vector(0, 0, -1));

		// 3X4

		Ray ray_e22 = camera3.constructRayThroughPixel(4, 3, 2, 2, 100, 200, 150);
		Ray expectedRay_e22 = new Ray(Point3D.ZERO, new Vector(-25, 0, -100).normalization());
		assertEquals("vTo negative vUp positive vectors testing at 3X4 in pixel (2,2)", ray_e22, expectedRay_e22);

		Ray ray_e32 = camera3.constructRayThroughPixel(4, 3, 3, 2, 100, 200, 150);
		Ray expectedRay_e32 = new Ray(Point3D.ZERO, new Vector(25, 0, -100).normalization());
		assertEquals("vTo negative vUp positive vectors testing at 3X4 in pixel (3,2)", ray_e32, expectedRay_e32);

		Ray ray_e11 = camera3.constructRayThroughPixel(4, 3, 1, 1, 100, 200, 150);
		Ray expectedRay_e11 = new Ray(Point3D.ZERO, new Vector(-75, 50, -100).normalization());
		assertEquals("vTo negative vUp positive vectors testing at 3X4 in pixel (1,1)", ray_e11, expectedRay_e11);

		Ray ray_e43 = camera3.constructRayThroughPixel(4, 3, 4, 3, 100, 200, 150);
		Ray expectedRay_e43 = new Ray(Point3D.ZERO, new Vector(75, -50, -100).normalization());
		assertEquals("vTo negative vUp positive vectors testing at 3X4 in pixel (4,3)", ray_e43, expectedRay_e43);

		// ======
		Camera littlCam = new Camera(Point3D.ZERO, new Vector(0, 1, 0), new Vector(0, 0, 1));
		Ray littl33 = littlCam.constructRayThroughPixel(3, 3, 3, 3, 10, 6, 6);
		Ray checklittl33 = new Ray(Point3D.ZERO, new Vector(-2, -2, 10).normalization());
		Ray littl11 = littlCam.constructRayThroughPixel(3, 3, 1, 1, 10, 6, 6);
		Ray checklittl11 = new Ray(Point3D.ZERO, new Vector(2, 2, 10).normalization());

		assertEquals("3,3", littl33, checklittl33);
		assertEquals("1,1", littl11, checklittl11);
	}

}
