package unittests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import elements.*;

/**
 *
 * @author Yizchak Meir Weiss 20401776 ymcw770@gmail.com
 * @author Israel Yakovson 300190055 sariruli@gmail.com
 *
 *
 */
class CameraTest {

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
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (1,1)", ray11.getDirectionVector(), expectedRay11.getDirectionVector());

		Ray ray33 = camera1.constructRayThroughPixel(3, 3, 3, 3, 100, 150, 150);
		Ray expectedRay33 = new Ray(Point3D.ZERO, new Vector(-50, -50, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (3,3)", ray33.getDirectionVector(), expectedRay33.getDirectionVector());

		Ray ray21 = camera1.constructRayThroughPixel(3, 3, 2, 1, 100, 150, 150);
		Ray expectedRay21 = new Ray(Point3D.ZERO, new Vector(0, 50, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (2,1)", ray21.getDirectionVector(), expectedRay21.getDirectionVector());

		Ray ray23 = camera1.constructRayThroughPixel(3, 3, 2, 3, 100, 150, 150);
		Ray expectedRay23 = new Ray(Point3D.ZERO, new Vector(0, -50, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (2,3)", ray23.getDirectionVector(), expectedRay23.getDirectionVector());

		Ray ray12 = camera1.constructRayThroughPixel(3, 3, 1, 2, 100, 150, 150);
		Ray expectedRay12 = new Ray(Point3D.ZERO, new Vector(50, 0, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (1,2)", ray12.getDirectionVector(), expectedRay12.getDirectionVector());

		Ray ray32 = camera1.constructRayThroughPixel(3, 3, 3, 2, 100, 150, 150);
		Ray expectedRay32 = new Ray(Point3D.ZERO, new Vector(-50, 0, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (3,2)", ray32.getDirectionVector(), expectedRay32.getDirectionVector());

		Ray ray22 = camera1.constructRayThroughPixel(3, 3, 2, 2, 100, 150, 150);
		Ray expectedRay22 = new Ray(Point3D.ZERO, new Vector(0, 0, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X3 in pixel (2,2) - A case test that Pij is equal to Pc", ray22.getDirectionVector(),
				expectedRay22.getDirectionVector());

		// 3X4 Py={1,2,3} Px={1,2,3,4}

		Ray rayS22 = camera1.constructRayThroughPixel(4, 3, 2, 2, 100, 200, 150);
		Ray expectedRayS22 = new Ray(Point3D.ZERO, new Vector(25, 0, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X4 in pixel (2,2)", rayS22.getDirectionVector(), expectedRayS22.getDirectionVector());

		Ray rayS32 = camera1.constructRayThroughPixel(4, 3, 3, 2, 100, 200, 150);
		Ray expectedRayS32 = new Ray(Point3D.ZERO, new Vector(-25, 0, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X4 in pixel (3,2)", rayS32.getDirectionVector(), expectedRayS32.getDirectionVector());

		Ray rayS11 = camera1.constructRayThroughPixel(4, 3, 1, 1, 100, 200, 150);
		Ray expectedRayS11 = new Ray(Point3D.ZERO, new Vector(75, 50, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X4 in pixel (1,1)", rayS11.getDirectionVector(), expectedRayS11.getDirectionVector());

		Ray rayS43 = camera1.constructRayThroughPixel(4, 3, 4, 3, 100, 200, 150);
		Ray expectedRayS43 = new Ray(Point3D.ZERO, new Vector(-75, -50, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 3X4 in pixel (4,3)", rayS43.getDirectionVector(), expectedRayS43.getDirectionVector());

		// 4X3 Py={1,2,3,4} Px={1,2,3}

		Ray ray_c22 = camera1.constructRayThroughPixel(3, 4, 2, 2, 100, 150, 200);
		Ray expectedRay_c22 = new Ray(Point3D.ZERO, new Vector(0, 25, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 4X3 in pixel (2,2)", ray_c22.getDirectionVector(), expectedRay_c22.getDirectionVector());

		Ray ray_c32 = camera1.constructRayThroughPixel(3, 4, 3, 2, 100, 150, 200);
		Ray expectedRay_c32 = new Ray(Point3D.ZERO, new Vector(-50, 25, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 4X3 in pixel (3,2)", ray_c32.getDirectionVector(), expectedRay_c32.getDirectionVector());

		Ray ray_c11 = camera1.constructRayThroughPixel(3, 4, 1, 1, 100, 150, 200);
		Ray expectedRay_c11 = new Ray(Point3D.ZERO, new Vector(50, 75, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 4X3 in pixel (1,1)", ray_c11.getDirectionVector(), expectedRay_c11.getDirectionVector());

		Ray ray_c43 = camera1.constructRayThroughPixel(3, 4, 3, 4, 100, 150, 200);
		Ray expectedRay_c43 = new Ray(Point3D.ZERO, new Vector(-50, -75, 100).normalization());
		assertEquals("pozitiv up to vectors testing at 4X3 in pixel (4,3)", ray_c43.getDirectionVector(), expectedRay_c43.getDirectionVector());

		// ======
		// Negative vectors.
		Camera camera2 = new Camera(Point3D.ZERO, new Vector(0, -1, 0), new Vector(0, 0, -1));

		// 3X3
		Ray ray1 = camera2.constructRayThroughPixel(3, 3, 3, 3, 100, 150, 150);
		Ray resultRay = new Ray(Point3D.ZERO,
				new Vector(-1 / Math.sqrt(6), 1 / Math.sqrt(6), -(Math.sqrt((double) 2 / 3))));
		assertEquals("Negative vectors testing at 3X3 in pixel (3,3)", ray1.getDirectionVector(), resultRay.getDirectionVector());

		// 3X3
		Ray ray_d11 = camera2.constructRayThroughPixel(3, 3, 1, 1, 100, 150, 150);
		Ray expectedRay_d11 = new Ray(Point3D.ZERO, new Vector(50, -50, -100).normalization());
		assertEquals("negative up to vectors testing at 3X3 in pixel (1,1)", ray_d11.getDirectionVector(), expectedRay_d11.getDirectionVector());

		Ray ray_d33 = camera2.constructRayThroughPixel(3, 3, 3, 3, 100, 150, 150);
		Ray expectedRay_d33 = new Ray(Point3D.ZERO, new Vector(-50, 50, -100).normalization());
		assertEquals("negative up to vectors testing at 3X3 in pixel (3,3)", ray_d33.getDirectionVector(), expectedRay_d33.getDirectionVector());

		Ray ray_d21 = camera2.constructRayThroughPixel(3, 3, 2, 1, 100, 150, 150);
		Ray expectedRay_d21 = new Ray(Point3D.ZERO, new Vector(0, -50, -100).normalization());
		assertEquals("negative up to vectors testing at 3X3 in pixel (2,1)", ray_d21.getDirectionVector(), expectedRay_d21.getDirectionVector());

		Ray ray_d23 = camera2.constructRayThroughPixel(3, 3, 2, 3, 100, 150, 150);
		Ray expectedRay_d23 = new Ray(Point3D.ZERO, new Vector(0, 50, -100).normalization());
		assertEquals("negative up to vectors testing at 3X3 in pixel (2,3)", ray_d23.getDirectionVector(), expectedRay_d23.getDirectionVector());

		Ray ray_d12 = camera2.constructRayThroughPixel(3, 3, 1, 2, 100, 150, 150);
		Ray expectedRay_d12 = new Ray(Point3D.ZERO, new Vector(50, 0, -100).normalization());
		assertEquals("negative up to vectors testing at 3X3 in pixel (1,2)", ray_d12.getDirectionVector(), expectedRay_d12.getDirectionVector());

		Ray ray_d32 = camera2.constructRayThroughPixel(3, 3, 3, 2, 100, 150, 150);
		Ray expectedRay_d32 = new Ray(Point3D.ZERO, new Vector(-50, 0, -100).normalization());
		assertEquals("negative up to vectors testing at 3X3 in pixel (3,2)", ray_d32.getDirectionVector(), expectedRay_d32.getDirectionVector());

		// vTo negative vUp positive
		Camera camera3 = new Camera(Point3D.ZERO, new Vector(0, 1, 0), new Vector(0, 0, -1));

		// 3X4

		Ray ray_e22 = camera3.constructRayThroughPixel(4, 3, 2, 2, 100, 200, 150);
		Ray expectedRay_e22 = new Ray(Point3D.ZERO, new Vector(-25, 0, -100).normalization());
		assertEquals("vTo negative vUp positive vectors testing at 3X4 in pixel (2,2)", ray_e22.getDirectionVector(), expectedRay_e22.getDirectionVector());

		Ray ray_e32 = camera3.constructRayThroughPixel(4, 3, 3, 2, 100, 200, 150);
		Ray expectedRay_e32 = new Ray(Point3D.ZERO, new Vector(25, 0, -100).normalization());
		assertEquals("vTo negative vUp positive vectors testing at 3X4 in pixel (3,2)", ray_e32.getDirectionVector(), expectedRay_e32.getDirectionVector());

		Ray ray_e11 = camera3.constructRayThroughPixel(4, 3, 1, 1, 100, 200, 150);
		Ray expectedRay_e11 = new Ray(Point3D.ZERO, new Vector(-75, 50, -100).normalization());
		assertEquals("vTo negative vUp positive vectors testing at 3X4 in pixel (1,1)", ray_e11.getDirectionVector(), expectedRay_e11.getDirectionVector());

		Ray ray_e43 = camera3.constructRayThroughPixel(4, 3, 4, 3, 100, 200, 150);
		Ray expectedRay_e43 = new Ray(Point3D.ZERO, new Vector(75, -50, -100).normalization());
		assertEquals("vTo negative vUp positive vectors testing at 3X4 in pixel (4,3)", ray_e43.getDirectionVector(), expectedRay_e43.getDirectionVector());

		// ======
		Camera littlCam = new Camera(Point3D.ZERO, new Vector(0, 1, 0), new Vector(0, 0, 1));
		Ray littl33 = littlCam.constructRayThroughPixel(3, 3, 3, 3, 10, 6, 6);
		Ray checklittl33 = new Ray(Point3D.ZERO, new Vector(-2, -2, 10).normalization());
		Ray littl11 = littlCam.constructRayThroughPixel(3, 3, 1, 1, 10, 6, 6);
		Ray checklittl11 = new Ray(Point3D.ZERO, new Vector(2, 2, 10).normalization());

		assertEquals("3,3", littl33.getDirectionVector(), checklittl33.getDirectionVector());
		assertEquals("1,1", littl11.getDirectionVector(), checklittl11.getDirectionVector());
	}
}
