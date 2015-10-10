package la.test

import la.LA
import la.Matrix

Matrix A = LA.newMtx3(3,4, [1,2,2,2,2,4,6,8,3,6,8,10]) 
printf("Matrix A:\n%s\n", A)
Matrix B = A.rowElim(0,-2,1)
B = B.rowElim(0,-3,2)
B = B.rowElim(1,-1,2)
printf("Matrix B:\n%s\n", B)
