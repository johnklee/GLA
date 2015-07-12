package la.test

import la.LA
import la.Matrix

class Test {
	static main(args) {		
		Matrix A = LA.newMtx3(2, 3, [1,2,3,4,5,6])
		printf("Matrix A:\n%s\n", A)
		Matrix B = LA.newMtx3(3, 2, [1,0,0,2,1,0])
		Matrix C = A*B
		printf("%s\n", C)
		printf("%s\n", A.col2Mtx(0)*B.val(0, 0))
		printf("%s\n", A.col2Mtx(1)*B.val(1, 0))
		printf("%s\n", A.col2Mtx(2)*B.val(2, 0))
		Matrix O = A.col2Mtx(0)*B.val(0, 0)+A.col2Mtx(1)*B.val(1, 0)+A.col2Mtx(2)*B.val(2, 0)
		printf("%s\n", O)
	}
}
