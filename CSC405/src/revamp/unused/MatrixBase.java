package revamp.unused;

public class MatrixBase {
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
    /// for Later
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
