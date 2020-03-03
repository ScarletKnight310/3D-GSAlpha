package revamp.operations;

import revamp.base.*;

public class MatrixOp 
{
    public static void translate(Shape shape, double[] point)
    {
        Operation(shape,
                MatrixBase.translationM(point[0],point[1],point[2]));
    }

    public static void scale(Shape shape, double[] point)
    {
        Operation(shape,
                MatrixBase.scaleM(point[0],point[1],point[2]));
    }

    public static void rotateX(Shape shape, double degree)
    {
        Operation(shape,
                MatrixBase.rotateXM(degree));
    }

    public static void rotateY(Shape shape, double degree)
    {
        Operation(shape,
                MatrixBase.rotateYM(degree));
    }

    public static void rotateZ(Shape shape, double degree)
    {
        Operation(shape,
                MatrixBase.rotateZM(degree));
    }

    public static void rotateXInPlace(Shape shape, double degree)
    {
        rotateInPlace(shape,MatrixBase.rotateXM(degree));
    }

    public static void rotateYInPlace(Shape shape, double degree)
    {
        rotateInPlace(shape,MatrixBase.rotateYM(degree));
    }

    public static void rotateZInPlace(Shape shape, double degree)
    {
        rotateInPlace(shape,MatrixBase.rotateZM(degree));
    }

    // GENERAL functions
    private static void rotateInPlace(Shape shape, double[][] rotate)
    {
        double[][] preMult_1 = mult(rotate,MatrixBase.translationM(-shape.fixedpoint[0],-shape.fixedpoint[1],-shape.fixedpoint[2]));
        double[][] preMult_2 = mult(MatrixBase.translationM(shape.fixedpoint[0],shape.fixedpoint[1],shape.fixedpoint[2]),preMult_1);
        Operation(shape, preMult_2);
    }
    
    public static void Operation(Shape shape, double[][] op)
    {
        for(int i = 0; i < shape.numOfFaces(); i++)
        {
            shape.setFace(i,mult(op,shape.getFace(i)));
        }
        shape.fixedpoint = shape.calculateFixedPoint();
    }
    
    private static double[][] mult(double A[][], double B[][]) throws IllegalArgumentException
    {
        if (A[0].length != B.length) {
            throw new IllegalArgumentException("incompatible arrays");
        }
        double C[][] = new double[A.length][B[0].length];

        for (int i = 0; i < A.length; ++i) { // -- for every row in A
            for (int j = 0; j < B[0].length; ++j) { // -- for every column in B
                double dotprod = 0;
                for (int k = 0; k < A[0].length; ++k) { // -- dot product
                    dotprod += A[i][k] * B[k][j];
                }
                C[i][j] = dotprod;
            }
        }
        return C;
    }
}
