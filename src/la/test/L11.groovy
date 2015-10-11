package la.test;

import static org.junit.Assert.*
import la.LA
import la.Matrix

import org.junit.Test

class L11 {

	@Test
	public void test() {
		Matrix u = LA.newMtx3(2,1,[1,2])
		Matrix v = LA.newMtx3(3,1,[1,4,5])
		Matrix m = u*(v.t())
		assertTrue(m.v(0,0)==1.0)
		assertTrue(m.v(0,1)==4.0)
		assertTrue(m.v(0,2)==5.0)
		assertTrue(m.v(1,0)==2.0)
		assertTrue(m.v(1,1)==8.0)
		assertTrue(m.v(1,2)==10.0)
	}
}
