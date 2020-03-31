package revamp.operations;

public class DrawOp
{
    /// Ref: Wikipedia - https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm#Algorithm
    private static void drawLine(int x0, int y0, int x1, int y1, int[][] framebuffer)
    {
        //System.out.println("( " + x0 + ", " + y0 + " )" +"( " + x1 + ", " + y1 + " )" );
        if (Math.abs(y1 - y0) < Math.abs(x1 - x0))
        {
            if (x0 > x1)
                plotLineLow(x1, y1, x0, y0, framebuffer);
            else
                plotLineLow(x0, y0, x1, y1, framebuffer);
        }
        else {
            if (y0 > y1)
                plotLineHigh(x1, y1, x0, y0, framebuffer);
            else
                plotLineHigh(x0, y0, x1, y1, framebuffer);
        }
    }

    // also corrects x-axis and y-axis
    public static void drawLine(double x0, double y0, double x1, double y1, int[][] framebuffer)
    {
        drawLine((int) y0,(int) x0,(int) y1, (int) x1,framebuffer);
    }

    private static void plotLineLow(int x0, int y0, int x1, int y1,  int[][] framebuffer)
    {
        // delta x and y
        int dx = x1 - x0;
        int dy = y1 - y0;
        int yi = 1;

        if (dy < 0)
        {
            yi = -1;
            dy = -dy;
        }

        int D = 2*dy - dx;
        int y = y0;

        int x = x0;
        while(x <= x1)
        {
            try {
                framebuffer[x][y] = 255;
            }
            catch (IndexOutOfBoundsException e) {

            }
            if(D > 0)
            {
                y = y + yi;
                D = D - 2*dx;
            }
            D = D + 2*dy;
            x++;
        }
    }

    private static void plotLineHigh(int x0, int y0, int x1, int y1,  int[][] framebuffer)
    {
        // delta x and y
        int dx = x1 - x0;
        int dy = y1 - y0;
        int xi = 1;

        if (dx < 0) {
            xi = -1;
            dx = -dx;
        }

        int D = 2*dx - dy;
        int x = x0;

        int y = y0;
        while(y <= y1)
        {
            try {
                framebuffer[x][y] = 255;
            }
            catch (IndexOutOfBoundsException e) {

            }
            if(D > 0)
            {
                x = x + xi;
                D = D - 2*dy;
            }
            D = D + 2*dx;
            y++;
        }
    }

    public static void fill(int value, double[][] shape, int[] center, int framebuffer[][])
    {
        // finds the
        // only x and y rows
        double[] greatestValuesd = new double[] {Double.MIN_VALUE,Double.MIN_VALUE};
        double[] lowestValuesd = new double[] {Double.MAX_VALUE,Double.MAX_VALUE};
        for(int i = 0; i < shape.length-2; i++)
        {
            for(double c: shape[i])
            {
                greatestValuesd[i] =  Double.max(c,greatestValuesd[i]);
                lowestValuesd[i] =  Double.min(c,lowestValuesd[i]);
            }
        }
        int[] greatestValues = new int[] {(int)greatestValuesd[0],(int)greatestValuesd[1]};
        int[] lowestValues = new int[] {(int)lowestValuesd[0],(int)lowestValuesd[1]};

        for(int x = lowestValues[0]; x < greatestValues[0]; x++)
        {
            for(int y= lowestValues[1]; y < greatestValues[1]; y++)
            {
                try {

                    if(BaseOp.isInside(shape,x,y))
                        framebuffer[y][x] = value;
                } catch (ArrayIndexOutOfBoundsException ex)
                    { }
            }
        }
    }
        /*
        for(int i = 0; i < face.length; i++)
        {
            for(int j = 0; j < face.length; j++)
            {
                face[i][j] = value;
            }
        }
        */

}

