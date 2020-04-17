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
    public boolean fillshape = true;
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

    // FOR A 2D Shape with 3d capabilities
    public Shape(double[][] face2D)
    {
        shapeFull = new Shape2D[]{new Shape2D(face2D, true)};
        int fpLoc = shapeFull[0].shape.length - 2;
        fixedpoint = new double[] {shapeFull[0].shape[0][fpLoc],
                shapeFull[0].shape[1][fpLoc],
                shapeFull[0].shape[2][fpLoc],
                shapeFull[0].shape[3][fpLoc]};
    }

    // renders the full shape seen
    public int[][] render(int[][] framebuffer)
    {
        for(Shape2D shape: shapeFull)
        {
            if(shape.visible)
                shape.render(framebuffer);
        }
        if(fillshape) {
            for (Shape2D shape : shapeFull) {
                if (shape.visible)
                    shape.renderOutline(framebuffer);
            }
        }
        return framebuffer;
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
        int color = 100;
        int outline = 255;
        boolean visible = false;
        boolean isSingle = false;

        public Shape2D(double[][] shape_part)
        {
            this(shape_part, false);
        }

        public Shape2D(double[][] shape_part, boolean isSing)
        {
            shape = new double[4][shape_part[0].length + 2];
            original = new double[4][shape_part[0].length + 2];
            double[] fixedpoint = calculateFixedPoint(shape_part);

            if(!isSing) {
                double[] surfaceNorm = calculateSurfaceNorm(shape_part);
                for (int i = 0; i < shape.length; i++) {
                    for (int j = 0; j < shape_part[0].length; j++) {
                        shape[i][j] = shape_part[i][j];
                        original[i][j] = shape_part[i][j];
                    }
                    shape[i][shape[0].length - 1] = surfaceNorm[i];
                    shape[i][shape[0].length - 2] = fixedpoint[i];

                    original[i][shape[0].length - 1] = surfaceNorm[i];
                    original[i][shape[0].length - 2] = fixedpoint[i];
                }
                setVisible();
            }
            else
            {
                double[] surfaceNorm = new double[]{0.0, 0.0, 0.0, 1.0};
                for (int i = 0; i < shape.length; i++) {
                    for (int j = 0; j < shape_part[0].length; j++) {
                        shape[i][j] = shape_part[i][j];
                        original[i][j] = shape_part[i][j];
                    }
                    shape[i][shape[0].length - 1] = surfaceNorm[i];
                    shape[i][shape[0].length - 2] = fixedpoint[i];

                    original[i][shape[0].length - 1] = surfaceNorm[i];
                    original[i][shape[0].length - 2] = fixedpoint[i];
                }
                visible = true;
            }
        }

        protected int[][] render(int[][] framebuffer)
        {
            renderOutline(framebuffer);
            if(fillshape) {
                DrawOp.fill(color, framebuffer);
            }
            return framebuffer;
        }

        public void setShape2D(double[][] input)
        {
            shape = input;
            if(!isSingle)
                setVisible();
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

        protected void renderOutline(int[][] framebuffer)
        {
            int shp_pointLen = shape[0].length-3;

            for (int i = 0; i < shp_pointLen; i++) {
                DrawOp.drawLine(shape[0][i], shape[1][i], shape[0][i + 1], shape[1][i + 1], outline, framebuffer);
                System.out.println(i +", "+(i+1));
            }
            DrawOp.drawLine(shape[0][shp_pointLen], shape[1][shp_pointLen], shape[0][0], shape[1][0], outline, framebuffer);
            System.out.println(shp_pointLen +", "+shape[0].length+"----------");
        }

        protected double[] calculateSurfaceNorm(double[][] shapePrt)
        {
            double[] U;
            double[] V;
            U = new double[]{shapePrt[0][1] - shapePrt[0][0],
                    shapePrt[1][1] - shapePrt[1][0],
                    shapePrt[2][1] - shapePrt[2][0]};
            V = new double[]{shapePrt[0][3] - shapePrt[0][0],
                    shapePrt[1][3] - shapePrt[1][0],
                    shapePrt[2][3] - shapePrt[2][0]};

            double[] unit = BaseOp.unitVector(BaseOp.cross(U,V));
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