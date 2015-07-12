package unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import la.LA;
import la.Matrix;

import org.junit.Test;

public class TestMatrix {

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
		assertEquals(String.format("%s", m.c(0)), "[0, 1, 2]");
		assertEquals(String.format("%s", m.r(0)), "[0, 3, 6]");
		Matrix m2 = m.multiply(i);
		System.out.printf("%s\n", m2);
	}
	
	@Test
	public void testOp_Multiplicity()
	{
		
	}
}
