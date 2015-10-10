package la.alg

import la.Matrix

class Alg {
	static double MinmumDoubleAcy=0.0000000001
	
	public static int Rank(Matrix m)
	{
		for(int i=0; i<m.r(); i++)
		{
			if(m.v(i, i)==0.0) return 
		}
	}
	
	/**
	 * Inverse the input matrix
	 * 
	 * @see
	 * 	https://en.wikipedia.org/wiki/Invertible_matrix
	 * @param m: Matrix object
	 * 
	 * @return The inverted Matrix object 
	 */
	public static Matrix Invert(Matrix m)
	{
		return new Matrix(m.toSM().invert())
	}
	
	/**
	 * Transfer input matrix into RREF
	 * 
	 * @see
	 * 	https://en.wikipedia.org/wiki/Row_echelon_form
	 * @param m: Matrix object
	 * 
	 * @return Matrix in RREF
	 */
	public static Matrix RREF(Matrix m)
	{
		Matrix nm = m.copy()
		int c=nm.c(), r=nm.r(), ti=0
		
		for(int i=0; i<c; i++)
		{
			/** 1) Select row with non-zero value in pivot position**/
			ti=i
			while(ti<r && nm.v(ti,i)==0)
			{
				ti++
			}
			if(ti>=c) continue
			else if(ti!=i) nm.sr(i, ti)
			
			/** 2) Process the pivot row**/
			nm.divRow(i, nm.v(i,i))
			
			/** 3) Clean up/down: Row elimination **/
			r.times {
				if(it==i) return
				else
				{
					if(nm.v(it, i)!=0)
					{
						nm.rowElim(i, -nm.v(it, i), it, false)
						c.times {ci->
							if(Math.abs(nm.v(it, ci))<MinmumDoubleAcy) 
							{
								//printf("Clean M[%d,%d]=%.010f...\n", it, ci, nm.v(it, ci))
								nm.v(it, ci, 0.0)
							}
						}						
					}
				}
			}
		}
		return nm
	}
}
