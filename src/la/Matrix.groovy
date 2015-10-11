package la

import org.ejml.data.DenseMatrix64F
import org.ejml.simple.SimpleMatrix

class Matrix {
	def data = []
	
	public Matrix(SimpleMatrix mtx)
	{
		DenseMatrix64F dm = mtx.getMatrix()
		int r = dm.numRows
		int c = dm.numCols		
		r.times{ ri->
			def row=[]
			c.times { ci->
				row << dm.get(ri, ci)
			}
			data << row
		}		
	}	
	
	public Matrix(DenseMatrix64F dm)
	{
		int r = dm.numRows
		int c = dm.numCols
		r.times{ ri->
			def row=[]
			c.times { ci->
				row << dm.get(ri, ci)
			}
			data << row
		}
	}
	
	public Matrix(def d)
	{
		this.data=d	
	}
	
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
	def t(boolean makeNew=false)
	{
		def d = []
		c().times { ci->			
			def row = []
			r().times {
				row.add(v(it, ci))
			}			
			d.add(row)
		}		
		if(makeNew) return new Matrix(d)
		else 
		{
			this.data = d
			return this
		}
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
	
	/**
	 * Translate to DenseMatrix64F
	 * 
	 * @return DenseMatrix64F object
	 */
	DenseMatrix64F toDM()
	{
		int r=r(),c=c()
		def data = new double[r][c] as double[][]
		r.times{ ri->
			c.times{ ci->
				data[ri][ci]=v(ri, ci)
			}
		}
		return new DenseMatrix64F(data)
	}
	
	/**
	 * Translate to SimpleMatrix
	 * 
	 * @return SimpleMatrix object
	 */
	SimpleMatrix toSM()
	{
		return SimpleMatrix.wrap(toDM())
	}
	
	def deepCopydata()
	{
		def ndata = []
		data.each{ r->
			def nr=[]
			r.each{v->nr<<v}
			ndata << nr
		}
		return ndata
	}
	
	def divRow(int ri, double v, boolean makeNew=false)
	{
		if(ri<this.r())
		{
			Matrix m=null
			if(makeNew)
			{
				m = LA.newMtx3(deepCopydata())
			}
			else
			{
				m = this
			}
			m.c().times {
				if(m.v(ri, it)==0.0) return
				m.v(ri, it, (double)m.v(ri, it)/v)
			}
			return m
		}
		else throw new Exception("Exceed row size boundary!")
	}
	
	/**
	 * Swap row of Matrix.
	 * 
	 * @param i1
	 * @param i2
	 * @param makeNew
	 * @return
	 */
	def sr(int i1, int i2, boolean makeNew=false)
	{
		if(i1<r() && i2<r())
		{
			Matrix m=null
			if(makeNew)
			{				
				m = LA.newMtx3(deepCopydata())
			}
			else
			{
				m = this
			}			
			def t = m.data[i1]
			m.data[i1]=m.data[i2]
			m.data[i2]=t
			return m
		}	
		else throw new Exception("Exceed row size boundary!")
	}	
	
	/**
	 * Swap column of Matrix
	 * 
	 * @param i1
	 * @param i2
	 * @param makeNew
	 * @return
	 */
	def sc(int i1, int i2, boolean makeNew=false)
	{
		if(i1<c() && i2<c())
		{
			Matrix m=null
			if(makeNew)
			{				
				m = LA.newMtx3(deepCopydata())
			}
			else
			{
				m = this
			}
			m.data.each{ r->
				def t=r[i1]
				r[i1]=r[i2]
				r[i2]=t
			}
		}
		else throw new Exception("Exceed column size boundary!")
	}
	
	/**
	 * Row Dimension
	 * @return
	 */
	int r(){return data.size()}
	
	/**
	 * Column Dimension
	 * @return
	 */
	int c(){return data.size()>0?data[0].size():0}
	
	/**
	 * Row(i)
	 * @param i: Row index
	 * @return Array of Row(i)
	 */
	def r(int i){return data[i]}
	
	/**
	 * Column(i)
	 * @param i:Column index
	 * @return Array of Column(i)
	 */
	def c(int i){return data.collect { r->	r[i]}}
	
	/**
	 * Value of Matrix at M[ri,ci]
	 * @param ri: Row index
	 * @param ci: Column index
	 * @return Value of matrix at specific position.
	 */
	def v(int ri, int ci){return val(ri,ci)}
	
	/**
	 * Value of Matrix at M[ri,ci]
	 * 
	 * @param ri: Row index
	 * @param ci: Column index
	 * 
	 * @return Value at M[ri,ci]
	 */
	def val(int ri, int ci)
	{
		if(ci>=c() || ri>=r()) throw new Exception(String.format("Out of size: %dx%d!", ri, ci))
		else 
		{
			return data[ri][ci]
		}	
	}
	
	/**
	 * Set value M[ri,ci]=v
	 * @param ri: Row index
	 * @param ci: Column index
	 * @param v: Value to be set into M[ri,ci]
	 * @return Original matrix
	 */
	def v(int ri, int ci, def v){return val(ri,ci,v)}
	def val(int ri, int ci, def v){data[ri][ci]=v; return this}
	def row2Mtx(int i){return new Matrix(1, c(), r(i))}
	def col2Mtx(int i){return new Matrix(r(), 1, c(i))}
	def size(){return [r(), c()]}
	def copy()
	{
		def ndata = []
		data.each{ row->
			def nrow = []
			row.each{v->nrow.add(v)}
			ndata.add(nrow)
		}
		return new Matrix(ndata)
	}
	
	def rowElim(si, m, di, boolean makeNew=true)
	{
		Matrix nm = null
		if(makeNew) nm=this.copy()
		else nm=this		
		r(si).eachWithIndex{ v, i->
			nm.v(di, i, nm.v(di, i)+v*m)
		}
		return nm
	}

	/**
	 * Reduced-Row-Echelon-Form
	 * 
	 * @see https://en.wikibooks.org/wiki/Linear_Algebra/Row_Reduction_and_Echelon_Forms
	 * @return
	 */
	def rref(boolean inPlace=false)
	{
		Matrix nm = (inPlace)?this.copy():this
		// To-do
		return nm
	}
		
	@Override
	public String toString(){
		StringBuffer strBuf = new StringBuffer(String.format("Matrix(%dx%d):\n", this.r(), this.c()))
		this.r().times{ i->
			strBuf.append("|")
			StringBuffer rowBuf = new StringBuffer()
			for(e in r(i))
			{
				if(e instanceof Double) rowBuf.append(String.format("%.02f\t", e))
				else if(e instanceof Integer) rowBuf.append(String.format("%02d\t", e))
				else rowBuf.append(String.format("%s\t", e))
			}
			strBuf.append(String.format("%s|\n", rowBuf.toString().trim()))
			//strBuf.append(String.format("%s\n", this.r(i)))
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
