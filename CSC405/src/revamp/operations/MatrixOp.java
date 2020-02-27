package revamp.operations;

import revamp.base.SceneGraph;
import java.util.function.BiConsumer;


public class MatrixOp
{
    private static double[][] workspace;

    // Speci Functions
    public static void translate(SceneGraph graph, double[] point)
    {
        Operation(graph,
                new double[][] {
                        {1.0,0.0,0.0,point[0]},
                        {0.0,1.0,0.0,point[1]},
                        {0.0,0.0,1.0,point[2]},
                        {0.0,0.0,0.0,1.0}});
    }

    public static void scale(SceneGraph graph, double[] point)
    {
        Operation(graph,
                new double[][] {
                        {point[0],0.0,0.0,0.0},
                        {0.0,point[1],0.0,0.0},
                        {0.0,0.0,point[2],0.0},
                        {0.0,0.0,0.0,1.0}});
    }

    public static void rotateX(SceneGraph graph, double degreeN)
    {
        Operation(graph,
                new double[][]{
                        {1, 0, 0, 0},
                        {0, Math.cos(Math.toRadians(degreeN)), -Math.sin(Math.toRadians(degreeN)), 0},
                        {0, Math.sin(Math.toRadians(degreeN)), Math.cos(Math.toRadians(degreeN)), 0},
                        {0, 0, 0, 1}});
    }

    public static void rotateY(SceneGraph graph, double degree)
    {
        Operation(graph,
                new double[][]{
                        {Math.cos(Math.toRadians(degree)), 0, Math.sin(Math.toRadians(degree)), 0},
                        {0, 1, 0, 0},
                        {-Math.sin(Math.toRadians(degree)), 0, Math.cos(Math.toRadians(degree)), 0},
                        {0, 0, 0, 1}});
    }

    public static void rotateZ(SceneGraph graph, double degree)
    {
        Operation(graph,
                new double[][]{
                        {Math.cos(Math.toRadians(degree)), -Math.sin(Math.toRadians(degree)), 0, 0},
                        {Math.sin(Math.toRadians(degree)), Math.cos(Math.toRadians(degree)), 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1}});
    }

    public static void rotateXInPlace(SceneGraph graph, double degree)
    {
        rotateInPlace(graph,degree,MatrixOp::rotateX);
    }

    public static void rotateYInPlace(SceneGraph graph, double degree)
    {
        rotateInPlace(graph,degree,MatrixOp::rotateY);
    }

    public static void rotateZInPlace(SceneGraph graph, double degree)
    {
        rotateInPlace(graph,degree,MatrixOp::rotateZ);
    }

    // GENERAL functions
    private static void rotateInPlace(SceneGraph graph, double degree, BiConsumer<SceneGraph,Double> rotate)
    {
        //  to origin
        translate(graph,new double[] {-graph.fixedPoint[0],-graph.fixedPoint[1],-graph.fixedPoint[2]});
        // rotate
        rotate.accept(graph,degree);
        // translate back
        translate(graph,new double[] {graph.fixedPoint[0],graph.fixedPoint[1],graph.fixedPoint[2]});
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