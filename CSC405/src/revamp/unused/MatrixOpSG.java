package revamp.unused;

import revamp.operations.MatrixBase;


public class MatrixOpSG
{
    // Scene Graph Operation------------------------------------------------------------------------------------------
    public static void translate(SceneGraph graph, double[] point)
    {
        Operation(graph,
                MatrixBase.translationM(point[0],point[1],point[2]));
    }

    public static void scale(SceneGraph graph, double[] point)
    {
        Operation(graph,
                MatrixBase.scaleM(point[0],point[1],point[2]));
    }

    public static void rotateX(SceneGraph graph, double degree)
    {
        Operation(graph,
                MatrixBase.rotateXM(degree));
    }

    public static void rotateY(SceneGraph graph, double degree)
    {
        Operation(graph,
                MatrixBase.rotateYM(degree));
    }

    public static void rotateZ(SceneGraph graph, double degree)
    {
        Operation(graph,
                MatrixBase.rotateZM(degree));
    }

    public static void rotateXInPlace(SceneGraph graph, double degree)
    {
        rotateInPlace(graph,MatrixBase.rotateXM(degree));
    }

    public static void rotateYInPlace(SceneGraph graph, double degree)
    {
        rotateInPlace(graph,MatrixBase.rotateYM(degree));
    }

    public static void rotateZInPlace(SceneGraph graph, double degree)
    {
        rotateInPlace(graph,MatrixBase.rotateZM(degree));
    }

    // GENERAL functions
    private static void rotateInPlace(SceneGraph graph, double[][] rotate)
    {
        double[][] preMult_1 = mult(rotate,MatrixBase.translationM(-graph.fixedPoint[0],-graph.fixedPoint[1],-graph.fixedPoint[2]));
        double[][] preMult_2 = mult(MatrixBase.translationM(graph.fixedPoint[0],graph.fixedPoint[1],graph.fixedPoint[2]),preMult_1);
        Operation(graph, preMult_2);
    }

    public static void Operation(SceneGraph graph,double[][] op)
    {
        graph.change(mult(op,graph.getShape()));
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
