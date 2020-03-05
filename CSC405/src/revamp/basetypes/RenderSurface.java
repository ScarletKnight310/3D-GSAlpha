package revamp.basetypes;

import java.awt.image.BufferedImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class RenderSurface extends WritableImage {
	
	private int surface[][];

	public RenderSurface(int width, int height)
	{
		super(width, height);
		surface = new int[height][width];

//		for (int i = 0; i < height; ++i) {
//			for (int j = 0; j < width; ++j) {
//				surface[i][j] = (int)(Math.random() * 255);
//			}
//		}
		insertArray();
	}
	
	public int[][] getSurface()
	{
		return surface;
	}
	
    public void insertArray()
    {
        //Creating a writable image 
    	int height = surface.length;
    	int width = surface[0].length;
        //getting the pixel writer 
        PixelWriter writer = this.getPixelWriter();
        for(int y = 0; y < height; y++) { 
           for(int x = 0; x < width; x++) { 
        	   double pixel = surface[y][x] / 256.0;
              //Setting the color to the writable image 
              writer.setColor(x, y, Color.color(pixel, pixel, pixel));              
           }
        }
    }
    
	public BufferedImage toImage() 
	{
		BufferedImage bi = new BufferedImage(surface[0].length, surface.length, BufferedImage.TYPE_INT_RGB);
    	// -- prepare output image
    	for (int i = 0; i < bi.getHeight(); ++i) {
    	    for (int j = 0; j < bi.getWidth(); ++j) {
    			int pixel =	(surface[i][j] << 16) | (surface[i][j] << 8) | (surface[i][j]);
    			bi.setRGB(j, i, pixel);
    		}
    	}
    	return bi;
	}

	public void clear()
	{
		surface = new int[surface.length][surface[0].length];
	}
}
