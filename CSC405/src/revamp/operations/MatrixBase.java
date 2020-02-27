package revamp.operations;

public class MatrixBase
{
    private static double x = 0.0;
    private static double y = 0.0;
    private static double z = 0.0;
    private static double degree = 0.0;

    private static double[][] transM = new double[][]{
            {1.0,0.0,0.0,x},
            {0.0,1.0,0.0,y},
            {0.0,0.0,1.0,z},
            {0.0,0.0,0.0,1.0}};

    private static double[][] scaleM =  new double[][] {
            {x,0.0,0.0,0.0},
            {0.0,y,0.0,0.0},
            {0.0,0.0,z,0.0},
            {0.0,0.0,0.0,1.0}};

    private static double[][] rotateXM = new double[][]{
            {1, 0, 0, 0},
            {0, Math.cos(Math.toRadians(degree)), -Math.sin(Math.toRadians(degree)), 0},
            {0, Math.sin(Math.toRadians(degree)), Math.cos(Math.toRadians(degree)), 0},
            {0, 0, 0, 1}};

    private static double[][] rotateYM = new double[][]{
            {Math.cos(Math.toRadians(degree)), 0, Math.sin(Math.toRadians(degree)), 0},
            {0, 1, 0, 0},
            {-Math.sin(Math.toRadians(degree)), 0, Math.cos(Math.toRadians(degree)), 0},
            {0, 0, 0, 1}};

    private static double[][] rotateZM =  new double[][]{
            {Math.cos(Math.toRadians(degree)), -Math.sin(Math.toRadians(degree)), 0, 0},
            {Math.sin(Math.toRadians(degree)), Math.cos(Math.toRadians(degree)), 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}};


}
