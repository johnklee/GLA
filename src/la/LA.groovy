package la

class LA {
	/**
	 * Create a L{Matrix} object.
	 * 
	 * @param c: Column size
	 * @param r: Row size
	 * @param val: Matrix content
	 * @return L{Matrix} object
	 */
	static Matrix newMtx3(int r, int c, def val)
	{
		return new Matrix(r,c,val)
	}
	
	/**
	 * Create a L{Matrix} object.
	 * 
	 * @param val: Matrix content
	 * @return L{Matrix} object
	 */
	static Matrix newMtx3(def val)
	{
		return new Matrix(val)
	}
	
	/**
	 * Create a Identity Matrix
	 * 
	 * @param n: Identity Matrix size
	 * @return L{Matrix} object
	 */
	static Matrix newI(int n)
	{
		def val = []
		n.times { c->
			n.times { r->
				if(c==r) val << 1
				else val << 0
			}
		}
		return new Matrix(n, n, val)
	}
}
