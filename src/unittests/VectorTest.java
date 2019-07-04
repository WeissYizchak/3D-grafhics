/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import primitives.*;

/**
 * @author 1
 *
 */
class VectorTest {

	@Test
	void testConstructor() {
		try {
			new Vector(0.0000001,-0.0000001,0.0000001);
			fail("didn't throw ERROR: cannot be a zero vector ");
		} catch (Exception e) {}		
		try {
			new Vector(0,0,0);
			fail("didn't throw ERROR: cannot be a zero vector ");
		} catch (Exception e) {}
		try {
			new Vector(Coordinate.ZERO, Coordinate.ZERO, Coordinate.ZERO);
			fail("didn't throw ERROR: cannot be a zero vector ");
		} catch (Exception e) {}
		try {
			new Vector(Point3D.ZERO);
			fail("didn't throw ERROR: cannot be a zero vector ");
		} catch (Exception e) {}
	}

	@Test
	void testAdd() {		
		Vector vec = new Vector(1,0,1);
		Vector vecClose = new Vector(0.000001,-0.000001,0.999999);
		Vector vecVeryClose = new Vector(0.0000001,-0.0000001,0.9999999);
		Vector vecBig = new Vector(99999,-99999,99999);

		Vector vecResult = new Vector(1,0,2);
		Vector vecResult2 = new Vector(100000,-99999,100000);
		assertNotEquals("vec+vecClose != vecResult",vec.add(vecClose), vecResult);
		assertEquals("vec+vecVeryClose = vecResult",vec.add(vecVeryClose), vecResult);
		assertEquals("vec+vecBig = vecResult2",vec.add(vecBig), vecResult2);
		try {
			vec.add(new Vector(-1, 0, -1));
			fail("didn't throw ERROR: cannot be a zero vector ");
		} catch (Exception e) {}

	}

	@Test
	void testSubtract() {		
		Vector vec = new Vector(1,0,1);
		Vector vecClose = new Vector(0.000001,-0.000001,0.999999);
		Vector vecVeryClose = new Vector(0.0000001,-0.0000001,0.9999999);
		Vector vecBig = new Vector(99999,-99999,99999);

		Vector vecResult = new Vector(1,0,0);
		Vector vecResult2 = new Vector(-99998,99999,-99998);
		assertNotEquals("vec-vecClose != vecResult",vec.subtract(vecClose), vecResult);
		assertEquals("vec-vecVeryClose = vecResult",vec.subtract(vecVeryClose), vecResult);
		assertEquals("vec-vecBig = vecResult2",vec.subtract(vecBig), vecResult2);
		try {
			vec.subtract(vec);
			fail("didn't throw ERROR: cannot be a zero vector ");
		} catch (Exception e) {}

	}

	@Test
	void testScaling() {		
		Vector vec = new Vector(1,1,1);
		Vector vec2 = new Vector(1,2,3);
		Vector vecBig = new Vector(99999,99999,99999);
		Vector vecResult = new Vector(2,4,6);

		assertNotEquals("vec*1.000001 != vec",vec.scaleMult(1.000001), vec);
		assertEquals("vec*1.0000001 = vec",vec.scaleMult(1.0000001), vec);
		assertEquals("vec*99999 = vecBig",vec.scaleMult(99999), vecBig);
		assertEquals("vec2*2 = vecResult",vec2.scaleMult(2), vecResult);
		try {
			vec.scaleMult(0);
			fail("didn't throw ERROR: cannot be a zero vector ");
		} catch (Exception e) {}

	}

	@Test
	void testLength() {		
		Vector vec1 = new Vector(1,0,0);
		Vector vec5 = new Vector(3,4,0);

		assertEquals("length = 1", 1, vec1.length(), 0);
		assertEquals("length = 5", 5, vec5.length(), 0);
		assertEquals("length = 25", 25, vec5.normalization().scaleMult(25).length(), 0);
	}

	@Test
	void testNormalize() {		
		Vector vec1 = new Vector(1.89,-2.58,23.852);
		Vector vec2 = new Vector(1,0,0);
		vec1.normalization();
		vec2.normalization();
		assertEquals("length1 = 1", 1, vec1.length(), 1e-10);
		assertEquals("length2 = 1", 1, vec2.length(), 0);
	}

	@Test
	void testDotProduct() {		
		Vector vec1 = new Vector(5,0,3);
		Vector vec2 = new Vector(-3,4,5);
		Vector vec111 = new Vector(1,1,1);
		Vector vec123 = new Vector(1,2,3);
		Vector vecClose = new Vector(1.000001,1.000001,1.000001);
		Vector vecVeryClose = new Vector(1.0000001,1.0000001,1.0000001);
		Vector vecCloseM = new Vector(-1.000001,-1.000001,-1.000001);
		Vector vecVeryCloseM = new Vector(-1.0000001,-1.0000001,-1.0000001);	

		assertEquals( "vertical vectors",0, vec1.dotProduct(vec2), 0);
		assertEquals("vec111*vec123 = 6", 6, vec111.dotProduct(vec123), 0);
		assertEquals("sqrt(vec123*vec123) = length", vec123.length(), Math.sqrt(vec123.dotProduct(vec123)), 0);
		assertEquals("vec111*vecVeryClose = 3", 3, vec111.dotProduct(vecVeryClose), 1e-7);
		assertNotEquals("vec111*vecClose != 3", 3, vec111.dotProduct(vecClose), 1e-7);
		assertEquals("vec111*vecVeryCloseM = -3", -3, vec111.dotProduct(vecVeryCloseM), 1e-6);
		assertNotEquals("vec111*vecCloseM != -3", -3, vec111.dotProduct(vecCloseM), 1e-7);
	}

	@Test
	void testCrossProduct() {		
		Vector vec1 = new Vector(1,2,3);
		Vector vec2 = new Vector(2,4,6);
		Vector vec3 = new Vector(1,-2,1);
		Vector vec111 = new Vector(1,1,1);
		Vector vec123 = new Vector(1,2,3);
		Vector vecClose = new Vector(1.000001,1.000001,1.000001);
		Vector vecVeryClose = new Vector(1.0000001,1.0000001,1.0000001);
		Vector vecCloseM = new Vector(-1.000001,-1.000001,-1.000001);
		Vector vecVeryCloseM = new Vector(-1.0000001,-1.0000001,-1.0000001);
	
		assertEquals("(1,1,1)X(1,2,3)*(1,2,3)=0", 0, vec111.crossProduct(vec1).dotProduct(vec1), 0);
		assertEquals("(1,1,1)X(1,2,3)=(-1,2,-1)", vec3, vec111.crossProduct(vec1));
		assertEquals("(1,1,1)X(1,2,3)=(-1,2,-1)", vec3, vec111.crossProduct(vec1));
		assertEquals("vecVeryClose X (1,2,3) = (-1,2,-1)", vec3, vecVeryClose.crossProduct(vec1));
		assertNotEquals("vecClose X (1,2,3) != (-1,2,-1)", vec3, vecClose.crossProduct(vec1));
		assertEquals("vecVeryCloseM X (1,2,3) = (1,-2,1)", vec3.scaleMult(-1), vecVeryCloseM.crossProduct(vec1));
		assertNotEquals("vecCloseM X (1,2,3) != (1,-2,1)", vec3.scaleMult(-1), vecCloseM.crossProduct(vec1));
		
		try {
			vec1.crossProduct(vec2); // parallel vectors
			fail("didn't throw ERROR: cannot be a zero vector");
		} catch (Exception e) {}
		try {
			vec1.crossProduct(vec1); // self cross product
			fail("didn't throw ERROR: cannot be a zero vector");
		} catch (Exception e) {}
		try {
			vec1.crossProduct(vec1.scaleMult(-1)); // reverse vectors
			fail("didn't throw ERROR: cannot be a zero vector");
		} catch (Exception e) {}
	
	}
}
