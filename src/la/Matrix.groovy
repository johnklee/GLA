package la

class Matrix {
	def data = []
	
	public Matrix(int r, int c, def d)
	{
		r.times{ ri->
			def row=[]
			c.times { ci->
				row << d[ri*c+ci]
			}
			data << row
		}
	}
	
	private def _vmv(v1, v2)
	{
		def val=0
		v1.eachWithIndex{ v, i->
			val+=(v*v2[i])
		}	
		return val
	}
	
	Matrix plus(def m)
	{
		if(m instanceof Matrix)
		{
			Matrix matr = (Matrix)m
			if(this.r()==matr.r() && this.c()==matr.c())
			{
				int nc = this.c(), nr=this.r()
				def matVals = []
				r().times{ ri->
					c().times {ci->
						matVals << (val(ri, ci)+matr.val(ri, ci))
					}
				}	
				return new Matrix(nr, nc, matVals)
			}
			else
			{
				throw new Exception(String.format("Unbalance Matrix Size: %s, %s", this, m));
			}
		}
		else throw new Exception(String.format("Illegal Input Class=%s", m.class.name));
	}
	
	Matrix multiply(def m)
	{		
		if(m instanceof Matrix)
		{			
			Matrix matr = (Matrix)m
			if(this.c()==matr.r())
			{				
				int nr = this.r(), nc=matr.c()
				def matVals = []
				for(int i=0; i<r(); i++)
				{
					for(int j=0; j<matr.c(); j++)
					{											
						matVals.add(_vmv(this.r(i), matr.c(j)))						
					}
				}
				return new Matrix(nr, nc, matVals)
			}
			else
			{
				throw new Exception(String.format("Unbalance Matrix Size: %s, %s", this, m));
			}
		}
		else if(m instanceof Number)
		{
			r().times{ ri->
				c().times{ ci->					
					val(ri, ci, val(ri, ci)*m)
				}
			}
			return this
		}
		else throw new Exception(String.format("Illegal Input Class=%s", m.class.name));
	}

	/**
	 * Transpose of Matrix
	 * 
	 * @return Matrix object
	 */
	def t()
	{
		int nr=c(), nc=r()
		def d = []
		c().times { ci->
			data.each{ r->
				d.add(r[ci])
			}
		}
		return new Matrix(nr, nc, d)
	}
	
	/**
	 * In linear algebra, the trace of an n-by-n square matrix A is defined to be the 
	 * sum of the elements on the main diagonal of A.
	 * 
	 * @see
	 * 	https://en.wikipedia.org/wiki/Trace_(linear_algebra)
	 * 
	 * @return
	 */
	def tr()
	{
		if(c()==r())
		{
			def b=0
			data.inject(0){i,r->b=b+data[i][i];i+1}
			return b
		}	
		throw new Exception(String.format("Not square matrix!\n%s", this))
	}
	def sr(int r1, int r2)
	{
		if(r1<r() && r2<r())
		{
			
		}	
		else throw new Exception("Exceed row size!")
	}	
	int r(){return data.size()}
	int c(){return data.size()>0?data[0].size():0}
	def r(int i){return data[i]}
	def c(int i){return data.collect { r->	r[i]}}
	def val(int ri, int ci)
	{
		if(ci>=c() || ri>=r()) throw new Exception(String.format("Out of size: %dx%d!", ri, ci))
		else 
		{
			return data[ri][ci]
		}	
	}
	def val(int ri, int ci, def v){data[ri][ci]=v}
	def row2Mtx(int i){return new Matrix(1, c(), r(i))}
	def col2Mtx(int i){return new Matrix(r(), 1, c(i))}
	def size(){return [r(), c()]}
	
	@Override
	public String toString(){
		StringBuffer strBuf = new StringBuffer(String.format("Matrix(%dx%d):\n", this.r(), this.c()))
		this.r().times{ i->
			strBuf.append(String.format("%s\n", this.r(i)))
		}
		return strBuf.toString()
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Matrix)
		{
			Matrix m = (Matrix)o
			if(c()==m.c() && r()==m.r())
			{
				r().times { ri->
					c().times { ci->
						if(val(ri,ci)!=m.val(ri, ci)) return false
					}
				}
				return true
			}
		}
		return false
	}
}
