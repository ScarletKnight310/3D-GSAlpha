package revamp.operations;

public class VectorOp
{
    // for surface
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
        if(Math.abs(mag) < MatrixOp.tresHold)
            throw new IllegalArgumentException("Invalid Axis Rotation");
        return mag;
    }

    // it vectors :)
    public static double[] unitVector(double[] A)
    {
        double mag = magnitude(A);
        return new double[] {A[0]/mag,A[1]/mag,A[2]/mag};
    }

}
