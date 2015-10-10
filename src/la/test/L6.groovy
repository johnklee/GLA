package la.test

import la.LA
import la.Matrix

Matrix A = LA.newMtx3(4,3, [1,1,2,
	                        2,1,3,
							3,1,4,
							4,1,5])
printf("Matrix A:\n%s\n", A)
