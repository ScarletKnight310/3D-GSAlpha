package revamp.base;

import revamp.operations.LineOp;

public class Shape
{
    // WIP
    private Shape2D[] shape_full;
    private Shape2D[] orig;
    private double[] fixed_point;

    public Shape()
    {
        shape_full = new Shape2D[] {
                // Front
                new Shape2D(new double[][] {
                        {-100, 100, 100, -100},
                        {-100, -100, 100, 100},
                        {100, 100, 100, 100},
                        {1, 1, 1, 1}
                }),
                // Back
                new Shape2D(new double[][] {
                        {-100, 100, 100, -100},
                        {-100, -100, 100, 100},
                        {-100, -100, -100, -100},
                        {1, 1, 1, 1}
                }),
                // Top
                new Shape2D(new double[][] {
                        {-100, 100, 100, -100},
                        {-100, -100, -100, -100},
                        {-100, -100, 100, 100},
                        {1, 1, 1, 1}
                }),
                // Bottom
                new Shape2D(new double[][] {
                        {-100, 100, 100, -100},
                        {100, 100, 100, 100},
                        {-100, -100, 100, 100},
                        {1, 1, 1, 1}
                }),
                // Left
                new Shape2D(new double[][] {
                        {-100, -100, -100, -100},
                        {-100, -100, 100, 100},
                        {-100, 100, 100, -100},
                        {1, 1, 1, 1}
                }),
                // Right
                new Shape2D(new double[][] {
                        {100, 100, 100, 100},
                        {-100, -100, 100, 100},
                        {100, -100, -100, 100},
                        {1, 1, 1, 1}
                })
        };
        fixed_point = new double[]{0,0,0,1};
        orig = shape_full;
    }

    public void render(int[][] framebuffer)
    {
        for(Shape2D shape:shape_full)
        {
            shape.render(framebuffer);
        }
    }

    public Shape2D[] getShape()
    {
        return shape_full;
    }

    public Shape2D getFace(int i)
    {
        return shape_full[i];
    }

    public Shape2D getFace()
    {
        return shape_full[0];
    }
    public class Shape2D
    {
        double[][] shape_part;
        public Shape2D(double[][] shape_part)
        {
            this.shape_part = shape_part;
        }

        protected void render(int[][] framebuffer)
        {
            for(int i = 0; i < shape_part[0].length-1; i++)
            {
                LineOp.drawLine(shape_part[0][i],shape_part[1][i],shape_part[0][i+1],shape_part[1][i+1],framebuffer);
            }
            LineOp.drawLine(shape_part[0][shape_part[0].length-1],shape_part[1][shape_part[0].length-1],shape_part[0][0],shape_part[1][0],framebuffer);
        }
    }
}
