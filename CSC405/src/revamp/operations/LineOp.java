package revamp.operations;

public class LineOp
{
    /// Ref: Wikipedia - https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm#Algorithm
    public static void drawLine(int x0, int y0, int x1, int y1, int[][] framebuffer)
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

    public static void drawLine(double x0, double y0, double x1, double y1, int[][] framebuffer)
    {
        drawLine((int) x0,(int) y0,(int) x1, (int) y1,framebuffer);
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
}
