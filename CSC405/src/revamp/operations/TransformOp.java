package revamp.operations;

import revamp.coreTypes.*;

//import static revamp.operations.VectorOp.VectorOp.matrixMult;

public class TransformOp
{
    public static final double tresHold = 0.000000001;

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

    public static void scaleInPlace(Shape shape, double[] sca)
    {
        OperateInPlace(shape,scaleM(sca[0], sca[1], sca[2]));
    }

    public static void rotateXInPlace(Shape shape, double degree)
    {
        OperateInPlace(shape,rotateXM(degree));
    }

    public static void rotateYInPlace(Shape shape, double degree)
    {
        OperateInPlace(shape,rotateYM(degree));
    }

    public static void rotateZInPlace(Shape shape, double degree)
    {
        OperateInPlace(shape,rotateZM(degree));
    }

    // axis[0] == x, axis[1] == y, axis[2] == z
    // degree gets converted from degrees to rads in function call rotateZM()
    public static void rotateArb(Shape shape, double[] axisPre, double degree)
    {
        double[] axis = BaseOp.unitVector(axisPre);
        double d = Math.sqrt(Math.pow(axis[1],2) + Math.pow(axis[2],2));
        // System.out.println("x, y, z -> d =" + d);
        // System.out.println(axisPre[0] +", " + axisPre[1] +", "+axisPre[2]);
        // System.out.println(axis[0] +", " + axis[1] +", "+axis[2]);
        if(Math.abs(d) < tresHold)
        {
            rotateXInPlace(shape,degree);
            return;
        }

        double[][] m = BaseOp.matrixMult(Rxp(axis[2],axis[1],d),translationM(-shape.fixedpoint[0],-shape.fixedpoint[1],-shape.fixedpoint[2]));
        m = BaseOp.matrixMult(Ryp(axis[0],d),m);
        m = BaseOp.matrixMult(rotateZM(degree),m);
        m = BaseOp.matrixMult(Ryn(axis[0],d),m);
        m = BaseOp.matrixMult(Rxn(axis[2],axis[1],d),m);
        m = BaseOp.matrixMult(translationM(shape.fixedpoint[0],shape.fixedpoint[1],shape.fixedpoint[2]),m);
        Operation(shape,m);
    }

    // GENERAL functions for seperate rotations
    private static void OperateInPlace(Shape shape, double[][] op)
    {
        double[][] matrixMult_1 = BaseOp.matrixMult(op,translationM(-shape.fixedpoint[0],-shape.fixedpoint[1],-shape.fixedpoint[2]));
        double[][] matrixMult_2 = BaseOp.matrixMult(translationM(shape.fixedpoint[0],shape.fixedpoint[1],shape.fixedpoint[2]),matrixMult_1);
        Operation(shape, matrixMult_2);
    }

    // Does an operation of any to the given shape
    public static void Operation(Shape shape, double[][] op)
    {
        for(int i = 0; i < shape.numOfFaces(); i++)
        {
            shape.setFace(i, BaseOp.matrixMult(op,shape.getFace(i)));
        }
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
