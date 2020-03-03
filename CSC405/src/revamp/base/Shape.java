package revamp.base;

import revamp.operations.LineOp;

import java.util.ArrayList;
import java.util.List;

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
                        {-100.0, 100.0, 100.0, -100.0},
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
                        {-100.0, 100.0, 100.0, -100.0},
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
        //fixedpoint = new double[]{0.0,0.0,0.0,1.0};
        fixedpoint = calculateFixedPoint();
        original = shapeFull;
    }

    public Shape(ArrayList<double[][]> faces)
    {
        shapeFull = new Shape2D[faces.size()];
        for(int i = 0; i < faces.size(); i++)
        {
            shapeFull[i] = new Shape2D(faces.get(i));
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
            faces.add(f.shape_part);
        }
        return faces;
    }

    public double[][] getFace(int i)
    {
        return shapeFull[i].shape_part;
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

    public int numOfFaces()
    {
        return shapeFull.length;
    }

    public void reset()
    {
        shapeFull = original;
        fixedpoint = calculateFixedPoint();
    }

    public class Shape2D
    {
        double[][] shape_part;
        double[] fixedpoint;

        public Shape2D(double[][] shape_part)
        {
            this.shape_part = shape_part;
            this.fixedpoint = calculateFixedPoint();
        }

        protected void render(int[][] framebuffer)
        {
            for(int i = 0; i < shape_part[0].length-1; i++)
            {
                LineOp.drawLine(shape_part[0][i],shape_part[1][i],shape_part[0][i+1],shape_part[1][i+1],framebuffer);
            }
            LineOp.drawLine(shape_part[0][shape_part[0].length-1],shape_part[1][shape_part[0].length-1],shape_part[0][0],shape_part[1][0],framebuffer);
        }

        protected double[] calculateFixedPoint()
        {
            double[] fp = new double[4];
            for(int i= 0; i< fp.length; i++)
            {
                for(int c = 0; c < shape_part[0].length; c++)
                {
                    fp[i] += shape_part[i][c];
                }
                fp[i] = fp[i]/shape_part[0].length;
            }
            return fp;
        }

    }

}