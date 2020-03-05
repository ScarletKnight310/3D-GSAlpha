package revamp.basetypes;

import revamp.operations.CrossOp;
import revamp.operations.LineOp;

import java.util.ArrayList;

public class Shape
{
    // WIP
    private Shape2D[] shapeFull;
    private Shape2D[] original;
    public double[] fixedpoint;

    public Shape()
    {
        shapeFull = new Shape2D[] {
                // Front
                new Shape2D(new double[][] {
                        {-100.0, 100.0, 100.0, -100.0},
                        {-100.0, -100.0, 100.0, 100.0},
                        {100.0, 100.0, 100.0, 100.0},
                        {1.0, 1.0, 1.0, 1.0}
                }),
                // Back
                new Shape2D(new double[][] {
                        {100.0, -100.0, -100.0, 100.0},
                        {-100.0, -100.0, 100.0, 100.0},
                        {-100.0, -100.0, -100.0, -100.0},
                        {1.0, 1.0, 1.0, 1.0}
                }),
                // Top
                new Shape2D(new double[][] {
                        {-100.0, 100.0, 100.0, -100.0},
                        {-100.0, -100.0, -100.0, -100.0},
                        {-100.0, -100.0, 100.0, 100.0},
                        {1.0, 1.0, 1.0, 1.0}
                }),
                // Bottom
                new Shape2D(new double[][] {
                        {100.0, -100.0, -100.0, 100.0},
                        {100.0, 100.0, 100.0, 100.0},
                        {-100.0, -100.0, 100.0, 100.0},
                        {1.0, 1.0, 1.0, 1.0}
                }),
                // Left
                new Shape2D(new double[][] {
                        {-100.0, -100.0, -100.0, -100.0},
                        {-100.0, -100.0, 100.0, 100.0},
                        {-100.0, 100.0, 100.0, -100.0},
                        {1.0, 1.0, 1.0, 1.0}
                }),
                // Right
                new Shape2D(new double[][] {
                        {100.0, 100.0, 100.0, 100.0},
                        {-100.0, -100.0, 100.0, 100.0},
                        {100.0, -100.0, -100.0, 100.0},
                        {1.0, 1.0, 1.0, 1.0}
                })
        };
        fixedpoint = new double[]{0.0,0.0,0.0,1.0};
        //fixedpoint = calculateFixedPoint();
        original = new Shape2D[shapeFull.length];
        for(int i = 0; i < shapeFull.length; i++)
        {
            original[i] = shapeFull[i];
        }
    }

    public Shape(ArrayList<double[][]> faces)
    {
        shapeFull = new Shape2D[faces.size()];
        for(int i = 0; i < faces.size(); i++)
        {
            shapeFull[i] = new Shape2D(faces.get(i));
            original[i] = shapeFull[i];
        }
        fixedpoint = calculateFixedPoint();
    }

    public void render(int[][] framebuffer)
    {
        for(Shape2D shape: shapeFull)
        {
            shape.render(framebuffer);
        }
    }

    public ArrayList<double[][]> getShape()
    {
        ArrayList<double[][]> faces = new ArrayList<>();
        for(Shape2D f : shapeFull)
        {
            faces.add(f.shapePrt);
        }
        return faces;
    }

    public double[][] getFace(int i)
    {
        return shapeFull[i].shapePrt;
    }

    public double[] getSurfaceNorm(int i)
    {
        return shapeFull[i].surfaceNorm;
    }

    public void setFace(int i, double[][] points)
    {
        shapeFull[i] = new Shape2D(points);
    }

    public double[] calculateFixedPoint()
    {
        double[] fp = new double[4];
        for(int i= 0; i< fp.length; i++)
        {
            for(int c = 0; c < shapeFull.length; c++)
            {
                fp[i] += shapeFull[c].fixedpoint[i];
            }
            fp[i] = fp[i]/shapeFull.length;
        }
        return fp;
    }

    public void reset()
    {
        shapeFull = original;
        fixedpoint = calculateFixedPoint();
    }

    public int numOfFaces()
    {
        return shapeFull.length;
    }

    public class Shape2D
    {
        double[][] shapePrt;
        double[] surfaceNorm;
        double[] fixedpoint;

        public Shape2D(double[][] shape_part)
        {
            this.shapePrt = shape_part;
            this.fixedpoint = calculateFixedPoint();
            this.surfaceNorm = calculateSurfaceNorm();
        }

        protected void render(int[][] framebuffer)
        {
            for(int i = 0; i < shapePrt[0].length-1; i++)
            {
                LineOp.drawLine(shapePrt[0][i],shapePrt[1][i],shapePrt[0][i+1],shapePrt[1][i+1],framebuffer);
            }
            LineOp.drawLine(shapePrt[0][shapePrt[0].length-1],shapePrt[1][shapePrt[0].length-1],shapePrt[0][0],shapePrt[1][0],framebuffer);
        }

        protected double[] calculateSurfaceNorm()
        {
            double[] A = new double[] {shapePrt[0][1] - shapePrt[0][0],
                                       shapePrt[1][1] - shapePrt[1][0],
                                       shapePrt[2][1] - shapePrt[2][0]};
            double[] B = new double[] {shapePrt[0][3] - shapePrt[0][0],
                                       shapePrt[1][3] - shapePrt[1][0],
                                       shapePrt[2][3] - shapePrt[2][0]};

            return CrossOp.unitVector(CrossOp.cross(A,B));
        }

        protected double[] calculateFixedPoint()
        {
            double[] fp = new double[4];
            for(int i= 0; i< fp.length; i++)
            {
                for(int c = 0; c < shapePrt[0].length; c++)
                {
                    fp[i] += shapePrt[i][c];
                }
                fp[i] = fp[i]/shapePrt[0].length;
            }
            return fp;
        }

    }

}