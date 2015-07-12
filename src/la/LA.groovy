package la

class LA {
	static Matrix newMtx3(int c, int r, def val)
	{
		return new Matrix(c,r,val)
	}
	
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
