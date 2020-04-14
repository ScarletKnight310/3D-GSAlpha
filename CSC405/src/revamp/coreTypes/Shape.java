package revamp.coreTypes;

import revamp.operations.BaseOp;
import revamp.operations.DrawOp;
import java.util.ArrayList;

//import static revamp.operations.BaseOp.dot;

public class Shape
{

    private Shape2D[] shapeFull;
    public double[] fixedpoint;
    public double[] viewer = new double[] {0,0,-1};

    // makes def shape "cube"
    public Shape()
    {
        shapeFull = new Shape2D[] {
                // Front
                new Shape2D(new double[][] {
                        {-100.0, 100.0, 100.0, -100.0},// X
                        {-100.0, -100.0, 100.0, 100.0},// Y
                        {100.0, 100.0, 100.0, 100.0},// Z
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
        // fixedpoint = calculateFixedPoint();
    }

    // takes an arraylist of 2d shape cords and makes a 3d (or 2d) shape
    public Shape(ArrayList<double[][]> faces)
    {
        shapeFull = new Shape2D[faces.size()];
        for(int i = 0; i < faces.size(); i++)
        {
            shapeFull[i] = new Shape2D(faces.get(i));
        }
        fixedpoint = calculateFixedPoint();
    }

    // renders the full shape seen
    public void render(int[][] framebuffer)
    {
        for(Shape2D shape: shapeFull)
        {
            if(shape.visible)
                shape.render(framebuffer);
        }
    }

    // gets the shape info
    public ArrayList<double[][]> getShape()
    {
        ArrayList<double[][]> faces = new ArrayList<>();
        for(Shape2D f : shapeFull)
        {
            faces.add(f.shape);
        }
        return faces;
    }

    // gets a specific face of the shape
    public double[][] getFace(int i)
    {
        return shapeFull[i].shape;
    }

    // changes the shape by simply passing its points
    // Gonna fix this later :)
    public void setFace(int i, double[][] points)
    {
        shapeFull[i].setShape2D(points);
    }

    // calculates the fixed points of the shape, as a whole
    public double[] calculateFixedPoint()
    {
        double[] fp = new double[4];
        int len = shapeFull[0].shape[0].length-2;
        for(int i= 0; i< fp.length; i++)
        {
            for(int c = 0; c < shapeFull.length; c++)
            {
                fp[i] += shapeFull[c].shape[i][len];
            }
            fp[i] = fp[i]/shapeFull.length;
        }
        return fp;
    }

    public void reset()
    {
        for(int i = 0; i < shapeFull.length; i++)
        {
            shapeFull[i].reset();
        }
        fixedpoint = calculateFixedPoint();
    }

    public int numOfFaces()
    {
        return shapeFull.length;
    }

    public class Shape2D
    {
        // shape has the shape
        double[][] shape;
        private double[][] original;
        int color = 255;
        int outline = 100;
        boolean visible = true;

        public Shape2D(double[][] shape_part)
        {
            shape = new double[4][shape_part.length + 2];
            original = new double[4][shape_part.length + 2];
            double[] fixedpoint = calculateFixedPoint(shape_part);
            double[] surfaceNorm = calculateSurfaceNorm(shape_part);
            for(int i = 0; i < shape.length; i++)
            {
                for(int j = 0; j < shape_part[0].length; j++)
                {
                    shape[i][j] = shape_part[i][j];
                    original[i][j] = shape_part[i][j];
                }
                shape[i][shape[0].length-1] = surfaceNorm[i];
                shape[i][shape[0].length-2] = fixedpoint[i];

                original[i][shape[0].length-1] = surfaceNorm[i];
                original[i][shape[0].length-2] = fixedpoint[i];
            }
            //setVisible();
        }

        protected void render(int[][] framebuffer)
        {
            renderOutline(framebuffer);
            // fill color
            DrawOp.fill(color, framebuffer);
            // redraws outline
            renderOutline(framebuffer);
        }

        public void setShape2D(double[][] input)
        {
            shape = input;
            //setVisible();
        }

        protected void setVisible()
        {
            visible = (0 <= BaseOp.dot(viewer, new double[]{shape[0][shape[0].length-1],
                    shape[1][shape[0].length-1],
                    shape[2][shape[0].length-1]}));
        }

        protected void reset()
        {
            shape = new double[original.length][original[0].length];
            for(int i = 0; i < shape.length; i++)
            {
                for(int j = 0; j < shape[0].length; j++)
                {
                    shape[i][j] = original[i][j];
                }
            }
            setVisible();
        }

        private void renderOutline(int[][] framebuffer)
        {
            for(int i = 0; i < shape[0].length-3; i++)
            {
                DrawOp.drawLine(shape[0][i],shape[1][i],shape[0][i+1],shape[1][i+1], outline, framebuffer);
            }
            DrawOp.drawLine(shape[0][shape[0].length-3],shape[1][shape[0].length-3],shape[0][0],shape[1][0], outline, framebuffer);
        }

        protected double[] calculateSurfaceNorm(double[][] shapePrt)
        {
            double[] A = new double[] {shapePrt[0][1] - shapePrt[0][0],
                                       shapePrt[1][1] - shapePrt[1][0],
                                       shapePrt[2][1] - shapePrt[2][0]};
            double[] B = new double[] {shapePrt[0][3] - shapePrt[0][0],
                                       shapePrt[1][3] - shapePrt[1][0],
                                       shapePrt[2][3] - shapePrt[2][0]};
            double[] unit = BaseOp.unitVector(BaseOp.cross(A,B));
            return new double[]{unit[0], unit[1], unit[2], 1.0};
        }

        protected double[] calculateFixedPoint(double[][] shapePrt)
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