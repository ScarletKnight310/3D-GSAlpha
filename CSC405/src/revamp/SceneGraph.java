package revamp;

public class SceneGraph
{
     private double[][] shape;
     private double[][] original;
     public double[] fixedPoint;

     public SceneGraph()
     {
         shape = new double[][]{
                 {-100, 100, 100, -100},
                 {-100, -100, 100, 100},
                 {0, 0, 0, 0},
                 {1, 1, 1, 1}};
         original = shape;
         fixedPoint = new double[]{0,0,0,1};
     }


     public void render(int[][] framebuffer) // place the stuff
     {
         Line.drawLine(shape[0][0],shape[1][0],shape[0][1],shape[1][1],framebuffer);
         Line.drawLine(shape[0][1],shape[1][1],shape[0][2],shape[1][2],framebuffer);
         Line.drawLine(shape[0][2],shape[1][2],shape[0][3],shape[1][3],framebuffer);
         Line.drawLine(shape[0][3],shape[1][3],shape[0][0],shape[1][0],framebuffer);
//         for(int i = 0; i < shape[0].length-1; i++)
//         {
//             Line.drawLine(shape[0][i],shape[1][i],shape[0][i+1],shape[1][i+1],framebuffer);
//         }
     }

     public void change(double[][] new_shape)
     {
         shape = new_shape;
     }

     public double[][] getShape()
     {
         return shape;
     }

     public void reset()
     {
         shape = original;
         fixedPoint = new double[]{0,0,0,1};
     }

}
