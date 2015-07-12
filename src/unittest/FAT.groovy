package unittest

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import la.LA
import la.Matrix

import org.junit.Test

class FAT {
	@Test
	public void testCreation() {
		int c=3, r=3;
		List<Integer> val = new ArrayList<Integer>();
		for(int i=0; i<9; i++) val.add(i);
		Matrix m = LA.newMtx3(c, r, val);
		Matrix i = LA.newI(c);
		assertEquals(m.c(), 3);
		assertEquals(m.r(), 3);
		assertEquals(m.val(0, 0), 0);
		assertEquals(String.format("%s", m.r(0)), "[0, 1, 2]");
		assertEquals(String.format("%s", m.c(0)), "[0, 3, 6]");
		Matrix m2 = m.multiply(i);
		//System.out.printf("%s\n", m2);
	}
	
	@Test
	public void testL03()
	{
		// Multiplication W1
		// A(mxn) * B(nxp) = C(mxp)
		// C(c1) = A*(Column 1 of B) <--- Column combinatioin of A
		// C(c2) = A*(Column 2 of B)
		// ...
		Matrix A = LA.newMtx3(2, 3, [1,2,3,4,5,6])
		Matrix B = LA.newMtx3(3, 2, [1,0,0,2,1,0])
		Matrix C = A*B
		printf("%s\n", C)
		// First column of C ---> c1
		Matrix O = A.col2Mtx(0)*B.val(0, 0)+A.col2Mtx(1)*B.val(1, 0)+A.col2Mtx(2)*B.val(2, 0)
		assertEquals(O.c(0), C.c(0))
		// Second column of C ---> c2
		O = A.col2Mtx(0)*B.val(0, 1)+A.col2Mtx(1)*B.val(1, 1)+A.col2Mtx(2)*B.val(2, 1)
		assertEquals(O.c(0), C.c(1))
		
		// Multiplication W2
		// A(mxn) * B(nxp) = C(mxp)
		// C(r1) = (Row 1 of A)*B
		// C(r2) = (Row 2 of A)*B
		// ...		
		Matrix m = LA.newMtx3(2, 3, [1,2,3,4,5,6])
		Matrix t = m.t()
		assertEquals(t.r(), 3)
		assertEquals(t.c(), 2)
		assertEquals(t.r(0), [1,4])
		assertEquals(t.r(1), [2,5])
		assertEquals(t.r(2), [3,6])
	}
	
	@Test
	public void testOp_Multiplicity()
	{		
		Matrix m = LA.newMtx3(3, 3, [1,2,3,4,5,6,7,8,9])
		//printf("%s\n", m)		
		Matrix p = LA.newMtx3(3, 1, [1,2,3])
		//printf("%s\n", p)
		Matrix r = m*p
		//printf("%s\n", r)
		assertEquals(r.c(), 1)
		assertEquals(r.r(), 3)
		def c1 = r.c(0)
		assertEquals(c1[0], 14)
		assertEquals(c1[1], 32)
		assertEquals(c1[2], 50)		
		Matrix t = m.col2Mtx(0)+m.col2Mtx(1)*2+m.col2Mtx(2)*3
		//printf("%s\n", t)
		assertTrue(r==t)
		
		p = LA.newMtx3(1, 3, [1,2,3])
		r = p*m
		//printf("%s\n", r)
		assertEquals(r.r(), 1)
		assertEquals(r.c(), 3)
		def r1 = r.r(0)
		assertEquals(r1[0], 30)
		assertEquals(r1[1], 36)
		assertEquals(r1[2], 42)
		t = m.row2Mtx(0)+m.row2Mtx(1)*2+m.row2Mtx(2)*3
		//printf("%s\n", t)
		assertTrue(r==t)
		
		Matrix A = LA.newMtx3(4,4,[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16])
		Matrix B = LA.newMtx3(4,4,[1,0,2,0,0,1,0,2,3,0,4,0,0,3,0,4])
		Matrix C = A*B 
		Matrix A11 = LA.newMtx3(2,2,[1,2,5,6])
		Matrix A12 = LA.newMtx3(2,2,[3,4,7,8]) 
		Matrix B11 = LA.newMtx3(2,2,[1,0,0,1]) 
		Matrix B21 = LA.newMtx3(2,2,[3,0,0,3]) 
		Matrix C11 = A11*B11 + A12*B21 
		assertEquals(C.val(0, 0), C11.val(0, 0))
		assertEquals(C.val(0, 1), C11.val(0, 1))
		assertEquals(C.val(1, 0), C11.val(1, 0))
		assertEquals(C.val(1, 1), C11.val(1, 1))
	}
}
