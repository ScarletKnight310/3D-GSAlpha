package revamp.operations;

import revamp.basetypes.*;

public class MatrixOp 
{
    public static double tresHold = 0.000000001;

    public static void translate(Shape shape, double[] point)
    {
        Operation(shape,
                translationM(point[0],point[1],point[2]));
       /* shape.fixedpoint = new double[]{point[0]+shape.fixedpoint[0],
                                        point[1]+shape.fixedpoint[1],
                                        point[2]+shape.fixedpoint[2],
                                        shape.fixedpoint[3]};*/
    }

    public static void translate_WithoutFixed(Shape shape, double[] point)
    {
        Operation(shape,
                translationM(point[0],point[1],point[2]));
    }

    public static void scale(Shape shape, double[] point)
    {
        Operation(shape,
                scaleM(point[0],point[1],point[2]));
    }

    public static void rotateX(Shape shape, double degree)
    {
        Operation(shape,
                rotateXM(degree));
    }

    public static void rotateY(Shape shape, double degree)
    {
        Operation(shape,
                rotateYM(degree));
    }

    public static void rotateZ(Shape shape, double degree)
    {
        Operation(shape,
                rotateZM(degree));
    }

    public static void rotateXInPlace(Shape shape, double degree)
    {
        rotateInPlace(shape,rotateXM(degree));
    }

    public static void rotateYInPlace(Shape shape, double degree)
    {
        rotateInPlace(shape,rotateYM(degree));
    }

    public static void rotateZInPlace(Shape shape, double degree)
    {
        rotateInPlace(shape,rotateZM(degree));
    }

    // GENERAL functions for seperate rotations
    private static void rotateInPlace(Shape shape, double[][] rotate)
    {
        double[][] preMult_1 = mult(rotate,translationM(-shape.fixedpoint[0],-shape.fixedpoint[1],-shape.fixedpoint[2]));
        double[][] preMult_2 = mult(translationM(shape.fixedpoint[0],shape.fixedpoint[1],shape.fixedpoint[2]),preMult_1);
        Operation(shape, preMult_2);
    }

    // axis[0] == x, axis[1] == y, axis[2] == z
    // degree gets converted from degrees to rads in function call rotateZM()
    public static void rotateArb(Shape shape, double[] axisPre, double degree)
    {
        double[] axis = VectorOp.unitVector(axisPre);
        double d = Math.sqrt(Math.pow(axis[1],2) + Math.pow(axis[2],2));
        if(Math.abs(d) < tresHold)
        {
            // x axis
            if(true)
            {
                
            }
            // y-axis
            else if(true)
            {

            }

            return;
        }
        double[][] m = mult(Rxp(axis[2],axis[1],d),translationM(-shape.fixedpoint[0],-shape.fixedpoint[1],-shape.fixedpoint[2]));
        m = mult(Ryp(axis[0],d),m);
        m = mult(rotateZM(degree),m);
        m = mult(Ryn(axis[0],d),m);
        m = mult(Rxn(axis[2],axis[1],d),m);
        m = mult(translationM(shape.fixedpoint[0],shape.fixedpoint[1],shape.fixedpoint[2]),m);
        Operation(shape,m);
    }

    // Does an operation of any to the given shape
    public static void Operation(Shape shape, double[][] op)
    {
        for(int i = 0; i < shape.numOfFaces(); i++)
        {
            shape.setFace(i,mult(op,shape.getFace(i)));
        }
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
////////////////////// all raw operations
    public static double[][] translationM(double x, double y, double z) {
        return new double[][]{
                {1.0, 0.0, 0.0, x},
                {0.0, 1.0, 0.0, y},
                {0.0, 0.0, 1.0, z},
                {0.0, 0.0, 0.0, 1.0}};
    }

    public static double[][] scaleM(double x, double y, double z) {
        return new double[][]{
                {x, 0.0, 0.0, 0.0},
                {0.0, y, 0.0, 0.0},
                {0.0, 0.0, z, 0.0},
                {0.0, 0.0, 0.0, 1.0}};
    }

    public static double[][] rotateXM(double degree) {
        return new double[][]{
                {1, 0, 0, 0},
                {0, Math.cos(Math.toRadians(degree)), -Math.sin(Math.toRadians(degree)), 0},
                {0, Math.sin(Math.toRadians(degree)), Math.cos(Math.toRadians(degree)), 0},
                {0, 0, 0, 1}};
    }

    public static double[][] rotateYM(double degree) {
        return new double[][]{
                {Math.cos(Math.toRadians(degree)), 0, Math.sin(Math.toRadians(degree)), 0},
                {0, 1, 0, 0},
                {-Math.sin(Math.toRadians(degree)), 0, Math.cos(Math.toRadians(degree)), 0},
                {0, 0, 0, 1}};
    }

    public static double[][] rotateZM(double degree) {
        return new double[][]{
                {Math.cos(Math.toRadians(degree)), -Math.sin(Math.toRadians(degree)), 0, 0},
                {Math.sin(Math.toRadians(degree)), Math.cos(Math.toRadians(degree)), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}};
    }

    /// for Later-----------------------------------------------------------------
    public static double[][] Rxp(double z, double y, double d) {
        return new double[][]{
                {1, 0, 0, 0},
                {0,z/d , -y/d, 0},
                {0, y/d, z/d, 0},
                {0, 0, 0, 1}};
    }

    public static double[][] Rxn(double z, double y, double d) {
        return new double[][]{
                {1, 0, 0, 0},
                {0,z/d , y/d, 0},
                {0, -y/d, z/d, 0},
                {0, 0, 0, 1}};
    }

    public static double[][] Ryp(double x, double d) {
        return new double[][]{
                {d, 0, -x, 0},
                {0, 1, 0, 0},
                {x, 0, d, 0},
                {0, 0, 0, 1}};
    }
    public static double[][] Ryn(double x, double d) {
        return new double[][]{
                {d, 0, x, 0},
                {0, 1, 0, 0},
                {-x, 0, d, 0},
                {0, 0, 0, 1}};
    }
}
