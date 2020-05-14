package revamp.operations;

public class BaseOp
{

    public static double[] cross(double[] A, double[] B)
    {
        double C[] = new double[3];
        C[0] = A[1] * B[2] - A[2] * B[1];
        C[1] = A[2] * B[0] - A[0] * B[2];
        C[2] = A[0] * B[1] - A[1] * B[0];
        return C;
    }

    public static double dot(double[] A, double[] B)
    {
        if(A.length != B.length)
            throw new IllegalArgumentException("incompatible arrays -> dotProduct");
        double res = 0.0;
        for(int i = 0; i < A.length; i++) {
            res += A[i] * B[i];
        }
        return res;
    }

    public static double magnitude(double[] A)
    {
        double mag = Math.sqrt(Math.pow(A[0],  2) + Math.pow(A[1],  2) + Math.pow(A[2],  2));
        if(Math.abs(mag) < TransformOp.tresHold)
           throw new IllegalArgumentException("Invalid Axis Rotation");
        return mag;
    }

    // it vectors :)
    public static double[] unitVector(double[] A)
    {
        double mag = magnitude(A);
        return new double[] {A[0]/mag,A[1]/mag,A[2]/mag};
    }

    public static double[][] matrixMult(double A[][], double B[][]) throws IllegalArgumentException
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

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}
