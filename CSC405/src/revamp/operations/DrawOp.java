package revamp.operations;

import static revamp.app.RenderSurface.defValue;

public class DrawOp
{

    /// Ref: Wikipedia - https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm#Algorithm
    private static void line(int x0, int y0, int x1, int y1, int color, int[][] framebuffer)
    {
        //System.out.println("( " + x0 + ", " + y0 + " )" +"( " + x1 + ", " + y1 + " )" );
        if (Math.abs(y1 - y0) < Math.abs(x1 - x0))
        {
            if (x0 > x1)
                plotLineLow(x1, y1, x0, y0, color,framebuffer);
            else
                plotLineLow(x0, y0, x1, y1, color,framebuffer);
        }
        else {
            if (y0 > y1)
                plotLineHigh(x1, y1, x0, y0, color,framebuffer);
            else
                plotLineHigh(x0, y0, x1, y1, color,framebuffer);
        }
    }

    private static void plotLineLow(int x0, int y0, int x1, int y1, int color, int[][] framebuffer)
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
                framebuffer[x][y] = color;
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

    private static void plotLineHigh(int x0, int y0, int x1, int y1, int color, int[][] framebuffer)
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
                framebuffer[x][y] = color;
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

    // corrects x-axis and y-axis
    private static void drawLine(int x0, int y0, int x1, int y1, int color, int[][] framebuffer)
    {
        line(y0,x0,y1, x1, color,framebuffer);
    }

    // also corrects x-axis and y-axis, def color white
    public static void drawLine(double x0, double y0, double x1, double y1, int[][] framebuffer)
    {
        line((int) y0,(int) x0,(int) y1, (int) x1,255,framebuffer);
    }

    // color option with it
    public static void drawLine(double x0, double y0, double x1, double y1, int color, int[][] framebuffer)
    {
        line((int) y0,(int) x0,(int) y1, (int) x1,color,framebuffer);
    }

    // Added a shape to to reduce the number of calls
    public static void fill(int value, int shape_top, int framebuffer[][])
    {
       for(int i = shape_top; i < framebuffer.length; i++)
       {
               int j = 0;
               while (j < framebuffer[0].length && framebuffer[i][j] == defValue)//left
               {
                   j++;
               }

               if (j >= framebuffer[0].length)// not
                   continue;

               int x0 = j;
               int y = i;
               j = framebuffer[0].length-1;
               while (j > 0 && framebuffer[i][j] == defValue){
                   j--;
               }
               int x1 = j;

               drawLine(x0, y,x1, y, value, framebuffer);
       }
    }

    public static void fill(int value, int framebuffer[][])
    {
        fill(value,0,framebuffer);
    }

    public static int[][] empty(int WIDTH, int HEIGHT)
    {
        int[][] result = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                result[i][j] = defValue;
            }
        }
        return result;
    }

    public static int[][] merge(int[][] a,int[][] b)
    {
        for (int i = 0; i < a.length; ++i) {
            for (int j = 0; j < a[i].length; ++j) {
                if(b[i][j] != defValue)
                    a[i][j] = b[i][j];
            }
        }
        return a;
    }
}

