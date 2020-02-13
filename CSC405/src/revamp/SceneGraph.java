package revamp;

public class SceneGraph
{
     private int[][] sce;

     public SceneGraph()
     {
         sce = new int[][]{
                 {-100, 100, 100, -100},
                 {-100, -100, 100, 100},
                 {0, 0, 0, 0},
                 {1, 1, 1, 1}};
     }

     public void render(int[][] framebuffer) // place the stuff
     {
         Line.drawLine(sce[0][0],sce[1][0],sce[0][1],sce[1][1],framebuffer);
         Line.drawLine(sce[0][1],sce[1][1],sce[0][2],sce[1][2],framebuffer);
         Line.drawLine(sce[0][2],sce[1][2],sce[0][3],sce[1][3],framebuffer);
         Line.drawLine(sce[0][3],sce[1][3],sce[0][0],sce[1][0],framebuffer);
     }
}
