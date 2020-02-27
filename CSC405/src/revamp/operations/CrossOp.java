package revamp.operations;

public class CrossOp
{
    // for surface
    public static double[] cross(double[] A, double[] B)
    {
        double[] C = new double[3];
        C[0] = A[1] * B[2] - A[2] * B[1];
        C[1] = A[2] * B[0] - A[0] * B[2];
        C[2] = A[0] * B[1] - A[1] * B[0];
        return C;
    }

    public static double magnitude(double[] A)
    {
        double sum = 0.0;
        for(double p : A) {
            sum+= Math.pow(p,2);
        }
        return Math.sqrt(sum);
    }

    public static double[] unitVector(double[] A)
    {
        double mag = magnitude(A);
        return new double[] {A[0]/mag,A[1]/mag,A[2]/mag};
    }

    public static double[][] getUVvectors(double[][] sq)
    {
        double [][] UV = new double[2][3];
        UV[0][0] = sq[0][1] - sq[0][0];
        UV[0][1] = sq[1][1] - sq[1][0];
        UV[0][2] = sq[2][1] - sq[2][0];
        UV[1][0] = sq[0][3] - sq[0][0];
        UV[1][1] = sq[1][3] - sq[1][0];
        UV[1][2] = sq[2][3] - sq[2][0];

        for(int i = 0; i < UV.length; i++)
        {
            for(int j = 0; j < UV[0].length; j++)
            {
                UV[i][j] = sq[j][i] - sq[j][0];
            }
        }
        return UV;
    }
}
